package mcs.kreshan.threefacauth;

import android.annotation.TargetApi;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.security.KeyStore;

import javax.crypto.Cipher;

import mcs.kreshan.fingerprint.FingerPrintAuthImplementation;
import mcs.kreshan.fingerprint.FingerPrintAuthInterface;
import mcs.kreshan.fingerprint.FingerPrintUtils;
import mcs.kreshan.utill.ResponceCode;

/**
 * Created by kreshan88 on 8/27/2017.
 */

public class FingerPrintActivity extends AppCompatActivity implements FingerPrintAuthInterface {
    public static final String LOG_CLASS = "MainActivity";
//    private TextView mAuthMsgTv;
//    private ViewSwitcher mSwitcher;
//    private Button mGoToSettingsBtn;
//    private FingerPrintAuthImplementation mFingerPrintAuthHelper;

    private KeyStore keyStore;
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView auth_message_tv;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_print);

        Log.i(LOG_CLASS,"FingerPrintActivity.onCreate");


        Log.i(LOG_CLASS,"FingerPrintActivity.onCreat getHelper end");


    }

    @Override
    protected void onResume() {
        Log.i(LOG_CLASS,"FingerPrintActivity.onResume");
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.i(LOG_CLASS,"FingerPrintActivity.onPause");
        super.onPause();

    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Log.i(LOG_CLASS,"FingerPrintActivity.onNoFingerPrintHardwareFound");

    }

    @Override
    public void onNoFingerPrintRegistered() {
        Log.i(LOG_CLASS,"FingerPrintActivity.onNoFingerPrintRegistered");

    }

    @Override
    public void onBelowMarshmallow() {
        Log.i(LOG_CLASS,"FingerPrintActivity.onBelowMarshmallow");

    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Log.i(LOG_CLASS,"FingerPrintActivity.onAuthSuccess");
        Toast.makeText(FingerPrintActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(FingerPrintActivity.this, FingerPrintSuccessActivity.class));
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Log.i(LOG_CLASS,"FingerPrintActivity.onAuthFailed");
        Log.i(LOG_CLASS,"onAuthFailed"+errorCode);

    }
}
