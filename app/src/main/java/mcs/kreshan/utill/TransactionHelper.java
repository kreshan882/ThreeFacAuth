package mcs.kreshan.utill;

import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Base64;
import android.util.Log;

import org.jpos.iso.ISOUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import mcs.kreshan.threefacauth.FingerPrintActivity;

/**
 * Created by kreshan88 on 11/19/2017.
 */

public class TransactionHelper {
    public static final String LOG_CLASS = "MainActivity";

    public static String encryption(String pin) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        String encryptedPassword="";
        try {
            SecretKey secretKey = FingerPrintActivity.createKey();
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] passwordBytes = pin.getBytes("UTF-8");
            byte[] encryptedPasswordBytes = cipher.doFinal(passwordBytes);
            encryptedPassword = Base64.encodeToString(encryptedPasswordBytes, Base64.DEFAULT);
        } catch (UserNotAuthenticatedException e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    public static String getSHA2(String msg) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(msg.getBytes(StandardCharsets.UTF_8));
        String res = ISOUtil.hexString(encodedhash);
        return res;
    }
}
