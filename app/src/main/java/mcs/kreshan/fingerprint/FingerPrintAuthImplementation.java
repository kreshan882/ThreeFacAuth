package mcs.kreshan.fingerprint;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import mcs.kreshan.utill.ResponceCode;


public class FingerPrintAuthImplementation {
    public static final String LOG_CLASS = "MainActivityFP";
    private static final String KEY_NAME = UUID.randomUUID().toString();

    //error messages
    private static final String ERROR_FAILED_TO_GENERATE_KEY = "Failed to generate secrete key for authentication.";
    private static final String ERROR_FAILED_TO_INIT_CHIPPER = "Failed to generate cipher key for authentication.";

    private KeyStore mKeyStore;
    private Cipher mCipher;

    //Instance of the caller class.
    private Context mContext;

    //notify the parent caller about the authentication status.
    private FingerPrintAuthInterface mCallback;

    //for finger print authentication.
    private CancellationSignal mCancellationSignal;

    private boolean isScanning;

    /**
     * Private constructor.
     */
    private FingerPrintAuthImplementation(@NonNull Context context, @NonNull FingerPrintAuthInterface callback) {
        mCallback = callback;
        mContext = context;
    }


    private FingerPrintAuthImplementation() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.FingerPrintAuthImplementation");
        throw new RuntimeException("Use getHelper() to initialize FingerPrintAuthImplementation.");
    }


    public static FingerPrintAuthImplementation getHelper(@NonNull Context context, @NonNull FingerPrintAuthInterface callback) {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.getHelper");
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null.");
        } else if (callback == null) {
            throw new IllegalArgumentException("FingerPrintAuthInterface cannot be null.");
        }

        return new FingerPrintAuthImplementation(context, callback);
    }

    //Check if the finger print hardware is available.
    private boolean checkFingerPrintAvailability(@NonNull Context context) {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.checkFingerPrintAvailability");
        // Check if we're running on Android 6.0 (M) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);

            if (!fingerprintManager.isHardwareDetected()) {
                mCallback.onNoFingerPrintHardwareFound();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                mCallback.onNoFingerPrintRegistered();
                return false;
            }
            return true;
        } else {
            mCallback.onBelowMarshmallow();
            return false;
        }
    }


    @TargetApi(23)
    private boolean generateKey() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.generateKey");
        mKeyStore = null;
        KeyGenerator keyGenerator;

        //Get the instance of the key store.
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            return false;
        } catch (KeyStoreException e) {
            return false;
        }

        //generate key.
        try {
            mKeyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

            return true;
        } catch (NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException e) {
            return false;
        }
    }



    @TargetApi(23)
    private boolean cipherInit() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.cipherInit");
        boolean isKeyGenerated = generateKey();

        if (!isKeyGenerated) {
            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY);
            return false;
        }

        try {
            mCipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_GENERATE_KEY);
            return false;
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            Log.i(LOG_CLASS,"key");
            Log.i(LOG_CLASS,"key"+key.toString());
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
            return false;
        }
    }

    @TargetApi(23)
    @Nullable
    private FingerprintManager.CryptoObject getCryptoObject() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.getCryptoObject");
        return cipherInit() ? new FingerprintManager.CryptoObject(mCipher) : null;
    }



    @TargetApi(Build.VERSION_CODES.M)
    public void startAuth() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.startAuth");
        if (isScanning) stopAuth();

        //check if the device supports the finger print hardware?
        if (!checkFingerPrintAvailability(mContext)) return;

        FingerprintManager fingerprintManager = (FingerprintManager) mContext.getSystemService(Context.FINGERPRINT_SERVICE);
        FingerprintManager.CryptoObject cryptoObject = getCryptoObject();

        if (cryptoObject == null) {
            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, ERROR_FAILED_TO_INIT_CHIPPER);
        } else {
            Log.i(LOG_CLASS,"FingerPrintAuthImplementation.startAuth->.cryptoObject have");
            mCancellationSignal = new CancellationSignal();
            //noinspection MissingPermission
            fingerprintManager.authenticate(cryptoObject,
                    mCancellationSignal,
                    0,
                    new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errMsgId, CharSequence errString) {
                            mCallback.onAuthFailed(ResponceCode.NON_RECOVERABLE_ERROR, errString.toString());
                        }

                        @Override
                        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                            mCallback.onAuthFailed(ResponceCode.RECOVERABLE_ERROR, helpString.toString());
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            mCallback.onAuthFailed(ResponceCode.CANNOT_RECOGNIZE_ERROR, null);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                            Log.i(LOG_CLASS,"FingerPrintAuthImplementation.startAuth-> cryptoObject have .onAuthenticationSucceeded");
                            mCallback.onAuthSuccess(result.getCryptoObject());
                        }
                    }, null);
        }
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.startAuth finesh");
    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void stopAuth() {
        Log.i(LOG_CLASS,"FingerPrintAuthImplementation.stopAuth");
        if (mCancellationSignal != null) {
            isScanning = true;
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }


    public boolean isScanning() {
        return isScanning;
    }
}
