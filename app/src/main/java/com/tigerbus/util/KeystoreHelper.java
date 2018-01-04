package com.tigerbus.util;

import java.security.KeyStore;

public final class KeystoreHelper {

    public static final String ANDROIDKEYSTORE = "AndroidKeyStore";
    private static KeyStore keyStore;

    public static KeyStore getInstance() throws Exception {
        synchronized (KeyStore.class) {
            if (keyStore == null)
                initKeystore();
            return keyStore;
        }
    }

    private static void initKeystore() throws Exception {
        keyStore = KeyStore.getInstance(ANDROIDKEYSTORE);
        keyStore.load(null);
    }
}
