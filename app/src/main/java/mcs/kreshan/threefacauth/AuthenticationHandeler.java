package mcs.kreshan.threefacauth;

import android.hardware.fingerprint.FingerprintManager;

/**
 * Created by kreshan88 on 8/27/2017.
 */

public class AuthenticationHandeler extends FingerprintManager.AuthenticationCallback{

 private FingerPrintLoad fingerPrintLoad;
    public AuthenticationHandeler(FingerPrintLoad fingerPrintLoad ){
        fingerPrintLoad=fingerPrintLoad;
    }



    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
