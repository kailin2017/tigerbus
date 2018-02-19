package com.tigerbus.util;

import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import com.tigerbus.app.log.Tlog;
import com.tigerbus.app.log.TlogType;

import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public final class AESGCMHelper {

    private static final String TAG = AESGCMHelper.class.getSimpleName();
    private static final String UTF8 = "UTF-8";
    private static final String ALIAS_AESGCM = "ALIAS_AESGCM";
    private static final String ALIAS_FINGERPRINT = "ALIAS_FINGERPRINT";
    private static final String ANDROIDKEYSTORE = "AndroidKeyStore";
    private static final String AESGCMNOPADDING = KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_GCM + "/" + KeyProperties.ENCRYPTION_PADDING_NONE;

    public final static Set<String> encrypt(String encryptString) {
        Set<String> stringSet = new HashSet<>();
        try {
            Cipher cipher = createCipher();
            cipher.init(Cipher.ENCRYPT_MODE, initSecretKey(ALIAS_AESGCM));
            String iv = Base64.encodeToString(cipher.getIV(), Base64.URL_SAFE);
            String result = Base64.encodeToString(cipher.doFinal(encryptString.getBytes(UTF8)), Base64.URL_SAFE);
            stringSet.add("iv:" + iv);
            stringSet.add("value:" + result);
            Tlog.printLog(TlogType.debug, TAG, "iv:" + iv + ",result:" + result);
        } catch (Exception e) {
            exception(e);
        }
        return stringSet;
    }

    public final static String decrypt(Set<String> stringSet) {
        String iv = "", value = "", result = "";
        try {
            for (String s : stringSet) {
                s.replace("\n", "");
                s.replace(" ", "");
                if (s.contains("iv:"))
                    iv = s.replace("iv:", "");
                if (s.contains("value:"))
                    value = s.replace("value:", "");
            }
            if (iv != null && value != null) {
                Cipher cipher = createCipher();
                cipher.init(Cipher.DECRYPT_MODE, initSecretKey(ALIAS_AESGCM), new GCMParameterSpec(128, Base64.decode(iv, Base64.URL_SAFE)));
                result = new String(cipher.doFinal(Base64.decode(value, Base64.URL_SAFE)));
                Tlog.printLog(TlogType.error, TAG, result);
            } else {
                Tlog.printLog(TlogType.error, TAG, "iv or value = null");
            }
        } catch (Exception e) {
            exception(e);
        }
        return result;
    }

    public final static FingerprintManager.CryptoObject getEncryptCipher() {
        return initFingerCipher(Cipher.ENCRYPT_MODE, null);
    }

    public final static FingerprintManager.CryptoObject getDecryptCipher(String iv) {
        return initFingerCipher(Cipher.DECRYPT_MODE, iv);
    }


    public final static boolean checkFingerpintKeystore() {
        return checkKeystore(ALIAS_FINGERPRINT);
    }

    private final static boolean checkKeystore(String alias) {
        boolean result = false;
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROIDKEYSTORE);
            keyStore.load(null);
            result = keyStore.containsAlias(alias);
        } catch (Exception e) {
            exception(e);
        }
        return result;
    }

    private final static FingerprintManager.CryptoObject initFingerCipher(int mode, String iv) {
        FingerprintManager.CryptoObject cryptoObject = null;
        try {
            Cipher cipher = createCipher();
            SecretKey secretKey = initSecretKey(ALIAS_FINGERPRINT);
            switch (mode) {
                case Cipher.ENCRYPT_MODE:
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    break;
                case Cipher.DECRYPT_MODE:
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, Base64.decode(iv, Base64.URL_SAFE)));
                    break;
            }
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
        } catch (Exception e) {
            exception(e);
        }
        return cryptoObject;
    }

    private final static Cipher createCipher() throws Exception {
        return Cipher.getInstance(AESGCMNOPADDING);
    }

    private final static SecretKey generatorKey(String alias) throws Exception {
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                .Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROIDKEYSTORE);
        keyGenerator.init(keyGenParameterSpec);
        return keyGenerator.generateKey();
    }

    private final static SecretKey initSecretKey(String alias) throws Exception {
        KeyStore keyStore = KeystoreHelper.getInstance();
        if (keyStore.containsAlias(alias)) {
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null);
            return secretKeyEntry.getSecretKey();
        } else {
            return generatorKey(alias);
        }
    }

    private final static void exception(Exception e) {
        Tlog.printLog(TlogType.error, TAG, e.toString());
    }
}
