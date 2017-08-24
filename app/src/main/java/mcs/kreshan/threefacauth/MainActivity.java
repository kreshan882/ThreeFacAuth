package mcs.kreshan.threefacauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_CLASS="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOG_CLASS,"onCreate");

    }
}
