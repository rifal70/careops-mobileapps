package com.digimaster.digicourse.digicyber.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author TERAVIN
 */
public class Encryption {

    private static String KEYWORD = "bL05$oM-m@gN0l!a";
    private static final String TRUE_STR = "1";
    private static final String FALSE_STR = "2";

    public String encrypt(String value) throws AppException {
        try {
            if (value != null && !value.isEmpty()) {
                DESKeySpec keySpec = new DESKeySpec(KEYWORD.getBytes(StandardCharsets.UTF_8));
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(keySpec);

                byte[] clearText = value.getBytes(StandardCharsets.UTF_8);
                // Cipher is not thread safe
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.ENCRYPT_MODE, key);

                return Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT).trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new AppException("DB", e.getMessage());
        }
    }

    public String encrypt(int value) throws AppException {
        return encrypt(String.valueOf(value));
    }

    public String encrypt(double value) throws AppException {
        return encrypt(String.valueOf(value));
    }

    public String encrypt(long value) throws AppException {
        return encrypt(String.valueOf(value));
    }

    public String encrypt(boolean value) throws AppException {
        return encrypt(value ? TRUE_STR : FALSE_STR);
    }

    public String decrypt(String value) throws AppException {
        try {
            if (value != null && !value.equals("")) {
                DESKeySpec keySpec = new DESKeySpec(KEYWORD.getBytes(StandardCharsets.UTF_8));
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey key = keyFactory.generateSecret(keySpec);

                byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
                // cipher is not thread safe
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

                return new String(decrypedValueBytes).trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new AppException("Encryption", e.getMessage());
        }
    }

    public String decryptString(String value) throws AppException {
        return decrypt(value);
    }

    public int decryptInt(String value) throws AppException {
        return Integer.parseInt(decrypt(value));
    }

    public long decryptLong(String value) throws AppException {
        return Long.parseLong(decrypt(value));
    }

    public double decryptDouble(String value) throws AppException {
        return Double.parseDouble(decrypt(value));
    }

    public boolean decryptBoolean(String value) throws AppException {
        return TRUE_STR.equals(decrypt(value));
    }

    public String md5(String s) throws NoSuchAlgorithmException {
        if (!s.isEmpty()) {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return hexString.toString();
        } else {
            return "";
        }
    }

}