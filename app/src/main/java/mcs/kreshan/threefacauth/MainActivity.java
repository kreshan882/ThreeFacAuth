package mcs.kreshan.threefacauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{
    public static final String LOG_CLASS="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_CLASS,"onCreate");



    }


    public void onButtonClick(View view) {
        TextView res=(TextView) findViewById(R.id.button_login);
        res.setText("sucess login");
        Log.i(LOG_CLASS,"onButtonClick");
    }

    @Override
    public void onClick(View view) {
        Log.i(LOG_CLASS,"onclickkkkkkkkkkk0");
        if (view.getId() == R.id.button_login) {

            Log.i(LOG_CLASS,"onclickkkkkkkkkkk1");
//            userName = txtUsername.getText().toString();
//            password = txtPassword.getText().toString();
//
//            if(userName.length() == 0 || password.length() == 0){
//                lay_empty.setVisibility(View.VISIBLE);
//            }
//            else{
//                lay_empty.setVisibility(View.INVISIBLE);
//                if(CommonMethod.getInstance().isOnline(this)) {
//                    LoginRequest();
//                }else
//                    CommonMethod.getInstance().networkEnabledialog(this);
//            }
        }
    }
//    Button clickButton = (Button) findViewById(R.id.button2_sub);
//
//        clickButton.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                /***Do what you want with the click here***/
//            }
//        });

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


//    @Override
//    public void onClick(View v) {
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
//    }
}
