package mcs.kreshan.threefacauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_CLASS="MainActivity";
    Button but;
    EditText uName;
    EditText pWord;

    RadioButton rb1;
    RadioButton rb2;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_CLASS,"onCreate");

        but=(Button)findViewById(R.id.button_login);
        uName=(EditText)findViewById(R.id.input_name);
        pWord=(EditText)findViewById(R.id.input_password);
        rb1=(RadioButton) findViewById(R.id.radioButton_face);
        rb2=(RadioButton) findViewById(R.id.radioButton_finger);
        rg=(RadioGroup) findViewById(R.id.radioGroup);




        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"onButtonClick");

                String un=uName.getText().toString();
                String pw=pWord.getText().toString();
                RadioButton radBut=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String rBut=radBut.getText().toString();

                Log.i(LOG_CLASS,"userName"+un+"   passwd:"+pw+"   rBut:"+rBut);
                if(un.equals("a")&& pw.equals("b")){
                    Toast.makeText(MainActivity.this,"please give your:"+rBut,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(MainActivity.this, FingerPrintLoad.class));
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





}
