package mcs.kreshan.threefacauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import mcs.kreshan.utill.SequrityHelper;


public class FingerPrintSuccessActivity extends AppCompatActivity {
    public static final String LOG_CLASS="MainActivity";
    Button but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_sucess);

        but=(Button)findViewById(R.id.button_home);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"onButtonClick");

                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });


    }
}
