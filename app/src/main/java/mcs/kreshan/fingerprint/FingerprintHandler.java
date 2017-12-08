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

import org.json.JSONObject;

import mcs.kreshan.threefacauth.FingerPrintSuccessActivity;
//import mcs.kreshan.threefacauth.R;
import mcs.kreshan.utill.SeqServiceConnection;
import mcs.kreshan.utill.SequrityHelper;

/**
 * Created by kreshan88 on 11/22/2017.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    public static final String LOG_CLASS = "MainActivity";
    private Context context;
    private static String imeiG,passG,bioTG;
    static  String res="";

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
        String encPassword= SequrityHelper.encryptionByFingerPrientKey(imeiG+passG); //encrypet by finger prient
        String msgSHA2= SequrityHelper.getSHA2(encPassword);
        Log.i(LOG_CLASS,"MsgSHA2:"+msgSHA2);

        String msgCertEnc= SequrityHelper.encryptionByCert(msgSHA2);
        Log.i(LOG_CLASS,"MsgCertEnc:"+msgCertEnc);

        /////////////////////////
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("enc_msg", msgCertEnc);

        Log.i(LOG_CLASS,"Sending json request: "+jsonObject.toString());

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    res= SeqServiceConnection.sendAndRec(jsonObject.toString());
                    Log.i(LOG_CLASS,"Resived Json responce: "+res);

                    ((Activity) context).finish();
                    Intent intent = new Intent(context, FingerPrintSuccessActivity.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        /////////////////////


    }catch (Exception e){
        Log.i(LOG_CLASS,e.toString());
        e.printStackTrace();
    }
    }






}
