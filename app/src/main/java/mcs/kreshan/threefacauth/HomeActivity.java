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
    Button but_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        but_qr=(Button)findViewById(R.id.read_qrcord_but);
        but_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_CLASS,"home function read QR cord");

                Intent intent=new Intent(HomeActivity.this, QRCordActivity.class);
                startActivity(intent);

            }
        });
    }

}
