package mcs.kreshan.mcs.kreshan.test;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
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

import mcs.kreshan.fingerprint.AuthErrorCodes;
import mcs.kreshan.fingerprint.FingerPrintAuthCallback;
import mcs.kreshan.fingerprint.FingerPrintAuthHelper;
import mcs.kreshan.fingerprint.FingerPrintUtils;
import mcs.kreshan.threefacauth.R;

public class MainActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    public static final String LOG_CLASS = "MainActivityFP";
    private TextView mAuthMsgTv;
    private ViewSwitcher mSwitcher;
    private Button mGoToSettingsBtn;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_print);
        Log.i(LOG_CLASS,"MainActivity.read Fingerprint Sensor");
        mGoToSettingsBtn = (Button) findViewById(R.id.go_to_settings_btn);
        mGoToSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"MainActivity.read Fingerprint Sensor start");
                FingerPrintUtils.openSecuritySettings(MainActivity.this);
                Log.i(LOG_CLASS,"MainActivity.read Fingerprint Sensor end");
            }
        });

        mSwitcher = (ViewSwitcher) findViewById(R.id.main_switcher);
        mAuthMsgTv = (TextView) findViewById(R.id.auth_message_tv);

        EditText pinEt = (EditText) findViewById(R.id.pin_et);
        pinEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(LOG_CLASS,">>>>>>>>>>>>>>>>>>>>>>>>>"+s.toString());
                if (s.toString().equals("1234")){
                    Toast.makeText(MainActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AuthSuccessScreen.class));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.i(LOG_CLASS,"MainActivity.call getHelper");
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        Log.i(LOG_CLASS,"MainActivity.end getHelper");
    }

    @Override
    protected void onResume() {
        Log.i(LOG_CLASS,"MainActivity.onResume");
        super.onResume();
        mGoToSettingsBtn.setVisibility(View.GONE);

        mAuthMsgTv.setText("Scan your finger");

        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        Log.i(LOG_CLASS,"MainActivity.onPause");
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Log.i(LOG_CLASS,"MainActivity.onNoFingerPrintHardwareFound");
        mAuthMsgTv.setText("Your device does not have finger print scanner. Please type 1234 to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        Log.i(LOG_CLASS,"MainActivity.onNoFingerPrintRegistered");
        mAuthMsgTv.setText("There are no finger prints registered on this device. Please register your finger from settings.");
        mGoToSettingsBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBelowMarshmallow() {
        Log.i(LOG_CLASS,"MainActivity.onBelowMarshmallow");
        mAuthMsgTv.setText("You are running older version of android that does not support finger print authentication. Please type 1234 to authenticate.");
        mSwitcher.showNext();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Log.i(LOG_CLASS,"MainActivity.onAuthSuccess");
        Toast.makeText(MainActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, AuthSuccessScreen.class));
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Log.i(LOG_CLASS,"MainActivity.onAuthFailed");
        Log.i(LOG_CLASS,"onAuthFailed"+errorCode);
        switch (errorCode) {
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                mAuthMsgTv.setText("Cannot recognize your finger print. Please try again.");
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                mAuthMsgTv.setText("Cannot initialize finger print authentication. Please type 1234 to authenticate.");
                mSwitcher.showNext();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                mAuthMsgTv.setText(errorMessage);
                break;
        }
    }
}
