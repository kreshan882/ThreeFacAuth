package mcs.kreshan.threefacauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by kreshan88 on 12/17/2017.
 */

public class HomeActivity extends AppCompatActivity {
    public static final String LOG_CLASS = "MainActivity";
    Button but_add_web_sys;
    Button but_qr_login;
    Button but_pass_change;
    Button but_bio_change;
    Button but_device_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        but_add_web_sys=(Button)findViewById(R.id.but_add_web_system);
        but_add_web_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"Add web system......");

//                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
//                startActivity(intent);

            }
        });

        but_qr_login=(Button)findViewById(R.id.but_read_qrcord);
        but_qr_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"QR read for login");

                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
                startActivity(intent);

            }
        });

        but_pass_change=(Button)findViewById(R.id.but_chng_pass);
        but_pass_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"pass cord change");

//                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
//                startActivity(intent);

            }
        });


        but_bio_change=(Button)findViewById(R.id.but_chng_bio);
        but_bio_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"biometric change");

//                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
//                startActivity(intent);

            }
        });


        but_device_change=(Button)findViewById(R.id.but_chng_device);
        but_device_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"change mobile device");

//                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
//                startActivity(intent);

            }
        });
    }

}
