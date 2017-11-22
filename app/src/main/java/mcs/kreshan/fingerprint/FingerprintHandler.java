package mcs.kreshan.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import java.security.PrivateKey;
import java.security.Signature;

import mcs.kreshan.threefacauth.FingerPrintSuccessActivity;

/**
 * Created by kreshan88 on 11/22/2017.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    public static final String LOG_CLASS = "MainActivity";
    private Context context;
    private static String imeiG,passG,bioTG;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext; //FingerPrintActivity
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject,String imei,String pass,String bioT) {
        imeiG=imei; passG=pass; bioTG=bioT;
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
//        Signature sig=cryptoObject.getSignature();
//        //PrivateKey key = (PrivateKey) keyStore.getKey(KEY_NAME, null);
//        sig.update("dddd".);
//        byte[] sigBytes = sig.sign();
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
;

        Log.i(LOG_CLASS,"FingerprintHandler.onAuthenticationSucceeded==>"+imeiG+":"+passG+":"+bioTG);
        this.update("Fingerprint Authentication success.--> send data to backend server get responce");
        ((Activity) context).finish();
        Intent intent = new Intent(context, FingerPrintSuccessActivity.class);
        context.startActivity(intent);
    }

    private void update(String e){
        Log.i(LOG_CLASS,"FingerprintHandler"+e);

//        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
//        textView.setText(e);
    }

}