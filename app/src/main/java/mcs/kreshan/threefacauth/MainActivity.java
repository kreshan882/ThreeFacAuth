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
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_CLASS="MainActivity";
    public static final int RequestPermissionCode = 7;
    public  String imeiNumber;
    Button but;
    TextView imei;
    EditText pCode;
    EditText pCodeRe;

    RadioButton rb1;
    RadioButton rb2;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_CLASS,"onCreate");

        but=(Button)findViewById(R.id.button_login);
        imei=(TextView)findViewById(R.id.input_name);
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


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"onButtonClick");

                String un=imei.getText().toString();
                String pw=pCode.getText().toString();
                RadioButton radBut=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String rBut=radBut.getText().toString();

                Log.i(LOG_CLASS,"userName"+un+"   passwd:"+pw+"   rBut:"+rBut);
                if(un.equals("a")&& pw.equals("b")){
                    Toast.makeText(MainActivity.this,"please give your:"+rBut,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(MainActivity.this, FingerPrintActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"login error try a,b",Toast.LENGTH_LONG).show();
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
                READ_PHONE_STATE}, RequestPermissionCode);
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

                    if (ReadPhoneStatus) {
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
