package mcs.kreshan.threefacauth;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONObject;

import mcs.kreshan.utill.SeqServiceConnection;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by kreshan88 on 12/18/2017.
 */

public class QRCordActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    public static final String LOG_CLASS="MainActivity";
    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);
        // setContentView(R.layout.activity_main);
        Log.i(LOG_CLASS,"onCreate1");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Log.i(LOG_CLASS,"onCreate2");
            if(checkPermition()){
                Toast.makeText(this,"permition have",Toast.LENGTH_LONG).show();
                Log.i(LOG_CLASS,"onCreate3");
            }else{
                requestPermissions();
                Log.i(LOG_CLASS,"onCreate4");
            }

        }
    }

    private boolean checkPermition(){
        return (ContextCompat.checkSelfPermission(QRCordActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(QRCordActivity.this,
                new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionResult(int requestCord,String permission[],int grandResult[]) {
        Log.i(LOG_CLASS, "onRequestPermissionResult");
        switch (requestCord) {
            case REQUEST_CAMERA:
                if (grandResult.length > 0) {

                    boolean cameraAccess = grandResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccess) {
                        Toast.makeText(QRCordActivity.this, "permition granded", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QRCordActivity.this, "permition denide", Toast.LENGTH_LONG).show();
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            displayAllertMessage("do u need allow confirmantion",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                        }
                                    }
                            );
                            return;
                        }
                    }
                }
                break;
        }
    }

    public void displayAllertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(QRCordActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("cancle",null)
                .create()
                .show();
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.i(LOG_CLASS,"onResume");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Log.i(LOG_CLASS,"version ok");
            if(checkPermition()){
                Log.i(LOG_CLASS,"permition ok");
                Toast.makeText(QRCordActivity.this,"permition have",Toast.LENGTH_LONG).show();
                if(scannerView==null){
                    Log.i(LOG_CLASS,"scannerView");
                    scannerView=new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
                Log.i(LOG_CLASS,"startCamera");
            }else{
                Log.i(LOG_CLASS,"requestPermition");
                requestPermissions();
            }

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();

    }
    @Override
    public void handleResult(Result result) {
        try {
            String resultmy = result.getText();
            Log.i(LOG_CLASS, ">>>>>>:" + resultmy.length());
            Log.i(LOG_CLASS, ">>>>>>:" + resultmy);


            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("enc_msg", resultmy);

            Log.i(LOG_CLASS, "Sending json request: " + jsonObject.toString());

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        String res = SeqServiceConnection.sendAndRec(jsonObject.toString(), "send_qr_detail");
                        Log.i(LOG_CLASS, "Received Json response: " + res);
                        Toast.makeText(QRCordActivity.this,"qr send success",Toast.LENGTH_LONG).show();
                        Thread.sleep(3000);
                        Intent intent=new Intent(QRCordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

