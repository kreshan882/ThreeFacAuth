package mcs.kreshan.threefacauth;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mcs.kreshan.utill.SeqServiceConnection;
import mcs.kreshan.utill.SequrityHelper;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_CLASS="MainActivity";
    public static final int RequestPermissionCode = 7;
    public  String imeiNumber;
    Button but;
    EditText imei;
    EditText pCode;
    EditText pCodeRe;

    RadioButton rb1;
    RadioButton rb2;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);
        Log.i(LOG_CLASS,"onCreate");

        but=(Button)findViewById(R.id.button_login);
        imei=(EditText)findViewById(R.id.imeiNum);
        pCode=(EditText)findViewById(R.id.input_password);
        pCodeRe=(EditText)findViewById(R.id.input_password_re);
        rb1=(RadioButton) findViewById(R.id.radioButton_face);
        rb2=(RadioButton) findViewById(R.id.radioButton_finger);
        rg=(RadioGroup) findViewById(R.id.radioGroup);

        if(!CheckingPermissionIsEnabled(this))
            RequestMultiplePermission();
        else {
            getImeiNumber();
        }
        SequrityHelper.writeSSLCertificate(getApplicationContext());

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"onButtonClick");

                String imeiNum=imei.getText().toString();
                String passCode=pCode.getText().toString();
                String passCodeRe=pCodeRe.getText().toString();
                RadioButton radBut=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String rBut=radBut.getText().toString();

                Log.i(LOG_CLASS,"Imei:"+imeiNum+"   passCode:"+passCode+"   passCodeRe:"+passCodeRe+"  rBut:"+rBut);
                if(passCode.equals(passCodeRe)){
                    if(rBut.equals("Finger")){
                        Toast.makeText(MainActivity.this,"please give your:"+rBut,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this, FingerPrintActivity.class);
                        intent.putExtra("imei", imeiNum);
                        intent.putExtra("pass", passCode);
                        intent.putExtra("bioT", rBut);
                        startActivity(intent);

                    }else{
                        SeqServiceConnection.sendK("ssssssss","rrrrrrrrr");

                        Toast.makeText(MainActivity.this,"please give your:"+rBut,Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"password miss match",Toast.LENGTH_LONG).show();
                }

            }
        });


    }







//
//    public void onButtonClick(View v){//alt+enter //set propoty onClick onbutton click
//        Log.i(LOG_CLASS,"onButtonClock");
//        EditText un=(EditText)findViewById(R.id.editText3_un);
//        EditText pw=(EditText)findViewById(R.id.editText4_pw);
//        TextView res=(TextView) findViewById(R.id.textView3_res);
//        Log.i(LOG_CLASS,"un"+un);
//        if(un.equals("admin") && pw.equals("1234")){
//            res.setText("sucess login");
//        }else{
//            res.setText("fail login");
//        }
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_CLASS, "On Destroy .....");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_CLASS, "On Pause .....");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_CLASS, "On Restart .....");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_CLASS, "On Resume .....");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_CLASS, "On Start .....");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_CLASS, "On Stop .....");
    }

    public boolean CheckingPermissionIsEnabled(Context context) {
        int FirstPermissionResult  = ContextCompat.checkSelfPermission(context.getApplicationContext(), READ_PHONE_STATE);
        return FirstPermissionResult   == PackageManager.PERMISSION_GRANTED ;
    }

    private void RequestMultiplePermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                READ_PHONE_STATE,READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    public void getImeiNumber(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imeiNumber = tm.getDeviceId();
        imei.setText(imeiNumber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i(LOG_CLASS, "on RequestPermissionsResult .....");
        switch (requestCode) {

            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean ReadPhoneStatus         = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean StoragePermission       = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (ReadPhoneStatus && StoragePermission) {
                        // Toast.makeText(RegistrationActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                       getImeiNumber();
                    }else{
                        RequestMultiplePermission();
                    }
                }
                break;
        }
    }




}
