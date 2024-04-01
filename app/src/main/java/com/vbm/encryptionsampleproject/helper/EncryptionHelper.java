package com.vbm.encryptionsampleproject.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.vbm.encryptionsampleproject.SampleModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionHelper {

    private static final String TAG = "EncryptionHelper";
    private static final String password = "jdsklowerjlkssd3";

    private static final int KEY_LENGTH_BITS = 256;
    private static final String KEY_ALGORITHM = "AES";
    private static final String HASH_ALGORITHM = "SHA-256";

    private static SecretKey generateKey() {
        try {
            byte[] keyBytes = new byte[KEY_LENGTH_BITS / 8];
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            secureRandom.nextBytes(keyBytes);

            MessageDigest sha = MessageDigest.getInstance(HASH_ALGORITHM);
            keyBytes = sha.digest(password.getBytes());

            return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveEncryptedData(Context context, String key, String value) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey());

            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            String encryptedValue = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);

            SharedPreferences.Editor editor = context.getSharedPreferences("encryptedData", Context.MODE_PRIVATE).edit();
            editor.putString(key, encryptedValue);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SampleModel getDecryptedData(Context context, String key) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey());

            SharedPreferences sharedPreferences = context.getSharedPreferences("encryptedData", Context.MODE_PRIVATE);
            String encryptedValue = sharedPreferences.getString(key, null);
            if (encryptedValue != null) {
                byte[] encryptedBytes = Base64.decode(encryptedValue, Base64.DEFAULT);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                return new SampleModel(new String(decryptedBytes), encryptedValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
