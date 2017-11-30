package mcs.kreshan.utill;

import android.content.Context;
import android.os.Environment;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Base64;
import android.util.Log;

import org.jpos.iso.ISOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import mcs.kreshan.threefacauth.FingerPrintActivity;

/**
 * Created by kreshan88 on 11/19/2017.
 */

public class SequrityHelper {
    public static final String LOG_CLASS = "MainActivity";

    public static String encryptionByFingerPrientKey(String pin) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        String encryptedPassword="";
        try {
            byte[] passwordBytes = pin.getBytes("UTF-8");

            SecretKey secretKey = FingerPrintActivity.createKey();
            //Log.i(LOG_CLASS,"Get Key :"+secretKey.getEncoded()); // return null value
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPasswordBytes = cipher.doFinal(passwordBytes);
            encryptedPassword = ISOUtil.hexString(encryptedPasswordBytes);//Base64.encodeToString(encryptedPasswordBytes, Base64.DEFAULT);
        } catch (Exception e) {
            Log.i(LOG_CLASS,"error::"+e.getMessage());
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    public static String getSHA2(String msg) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
        return ISOUtil.hexString(encodedhash);
    }

    public static String cncryptionByCert(String msg) throws Exception{

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        File sdCard = Environment.getExternalStorageDirectory();
        Certificate cert = cf.generateCertificate(new FileInputStream(sdCard.getAbsolutePath()+"/"+ SysConfiguration.CERTIFICATE_PATH+"/"+SysConfiguration.KEY_FILE_NAME));
        cf = null;

        Cipher cipher= Cipher.getInstance("RSA/NONE/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, cert.getPublicKey());
        byte[] res = cipher.doFinal(msg.getBytes());
        return ISOUtil.hexString(res);
    }

    public static void writeSSLCertificate(Context cn) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/"+ SysConfiguration.CERTIFICATE_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir, SysConfiguration.KEY_FILE_NAME);

            if(file.exists()){
                file.delete();
            }

            if(!file.exists()){
                FileOutputStream f = null;
                InputStream in = null;

                file.createNewFile();
                try {
                    f = new FileOutputStream(file);
                    in = cn.getAssets().open(SysConfiguration.KEY_FILE_NAME);

                    byte[] buffer = new byte[2048];
                    int len1 = 0;
                    while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if(f != null){
                            f.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            Log.i(LOG_CLASS,e.getMessage());
            e.printStackTrace();
        }
    }
}
