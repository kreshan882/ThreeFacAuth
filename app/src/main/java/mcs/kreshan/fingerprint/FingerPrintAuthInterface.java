package mcs.kreshan.fingerprint;

import android.hardware.fingerprint.FingerprintManager;


public interface FingerPrintAuthInterface {

    //no finger print hardware found on the device.
    void onNoFingerPrintHardwareFound();

    //device supports finger print authentication but, finger print not registered.
    void onNoFingerPrintRegistered();

    //check android version,below API 23. As starting from the
    void onBelowMarshmallow();

    //This method will occur whenever  user authentication is successful.,with the scanned finger print.
    void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject);

    //This method will execute whenever any error occurs during the authentication.
    void onAuthFailed(int errorCode, String errorMessage);
}
