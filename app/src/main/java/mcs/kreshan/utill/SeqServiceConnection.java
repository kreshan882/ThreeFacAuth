package mcs.kreshan.utill;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
/**
 * Created by kreshan88 on 11/27/2017.
 */

public class SeqServiceConnection {
    public static final String LOG_CLASS = "MainActivity";
    static  String res="";

//    public static void sendToSeqService(String encMsg) {
//
//        try {
//
//            final JSONObject jsonObject = new JSONObject();
//            jsonObject.put("enc_msg", encMsg);
//
//            Log.i(LOG_CLASS,"Sending json request: "+jsonObject.toString());
//
//            Thread thread = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try  {
//                        res= SeqServiceConnection.sendAndRec(jsonObject.toString());
//                        Log.i(LOG_CLASS,"Resived Json responce: "+res);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//
//
//        } catch (Exception e) {
//            Log.i(LOG_CLASS,"error");
//            Log.i(LOG_CLASS,"error:"+e.toString());
//            e.printStackTrace();
//        }
//    }


    
    public static String sendAndRec(String req)throws Exception{
        String res="000";
        Log.i(LOG_CLASS,"req>>>"+req);
//        URL url = new URL("http://192.168.43.35:8080/ThreeFac_SEC/api/mobile_Reg");
//        //URL url = new URL("http://127.0.0.1:8080/ThreeFac_SEC/api/mobile_Reg");
//        URLConnection connection = url.openConnection();
//        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setConnectTimeout(10000);
//        connection.setReadTimeout(5000);
//        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//        out.write(req);
//        out.close();
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        res=in.readLine();
//        in.close();
        return res;
    }
}
