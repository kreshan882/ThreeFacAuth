package mcs.kreshan.threefacauth;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by kreshan88 on 8/27/2017.
 */

public class FingerPrintLoad extends AppCompatActivity {
    public static final String LOG_CLASS = "MainActivity";
    private String KEY_NAME = "my securiy key";

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_print);

        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fm = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (fm.isHardwareDetected()) {
            Log.i(LOG_CLASS, "fingetprint harware find sucess");
        } else {
            Log.i(LOG_CLASS, "fingetprint harware not find");

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_CLASS, "fingetprint permition rejected    ");
            return;
        }else{
            Log.i(LOG_CLASS, "fingetprint permition sucess    ");
        }

        if(!km.isKeyguardSecure()){
            Log.i(LOG_CLASS, "keygard not enabled");
            return;
        }else{
            Log.i(LOG_CLASS, "keygard enabled");
        }

        KeyStore keyStore;
        KeyGenerator keyGenerator;
        try{
            keyStore=KeyStore.getInstance("AndroidKeyStore");
            keyGenerator=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
            Log.i(LOG_CLASS, "debug1");

            keyStore.load(null);
            //keyGenerator.init(256, new SecureRandom()); //nead check more
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,                                                 KeyProperties.PURPOSE_ENCRYPT |                                                        KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC) .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
            Log.i(LOG_CLASS, "debug2");

           // Cipher cipher=Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");

              keyStore.load(null);
            SecretKey secretKey=(SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            Log.i(LOG_CLASS, "debug3");

            FingerprintManager.CryptoObject cryptoObject=new FingerprintManager.CryptoObject(cipher);
            CancellationSignal cancellationSignal=new CancellationSignal();
            Log.i(LOG_CLASS, "debug4");
           // fm.authenticate(cryptoObject,cancellationSignal,0,new AuthenticationHandeler(this),null);

        }catch (Exception e){
            Log.i(LOG_CLASS, "keyStore exception:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
