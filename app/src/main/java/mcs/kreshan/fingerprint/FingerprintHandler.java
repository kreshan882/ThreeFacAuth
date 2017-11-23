package mcs.kreshan.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import mcs.kreshan.threefacauth.FingerPrintActivity;
import mcs.kreshan.threefacauth.FingerPrintSuccessActivity;
import mcs.kreshan.threefacauth.R;
import mcs.kreshan.utill.TransactionHelper;

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
        Log.i(LOG_CLASS,"Fingerprint Authentication error\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.i(LOG_CLASS,"Fingerprint Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        Log.i(LOG_CLASS,"Fingerprint Authentication failed.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
;
    try {
        Log.i(LOG_CLASS, "FingerprintHandler.onAuthenticationSucceeded==>" + imeiG + ":" + passG + ":" + bioTG);
        String encPassword=TransactionHelper.encryption(imeiG+passG);
        String msgSHA2=TransactionHelper.getSHA2(encPassword);
        Log.i(LOG_CLASS,"encrypet Fingerprint sha2:"+msgSHA2);

        

        ((Activity) context).finish();
        Intent intent = new Intent(context, FingerPrintSuccessActivity.class);
        context.startActivity(intent);
    }catch (Exception e){
        e.printStackTrace();
    }
    }






}
