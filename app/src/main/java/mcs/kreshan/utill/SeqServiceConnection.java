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


    public static void sendK() {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("imei", "4216890405043373");
            jsonObject.put("enc_msg", "1234");

            Log.i(LOG_CLASS,"Sending json request: "+jsonObject.toString());
            //String responce=SeqServiceConnection.sendAndRec(jsonObject.toString());
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                       SeqServiceConnection.sendAndRec("{\"enc_msg\":\"1234\",\"imei\":\"4216890405043373\"}");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            //Log.i(LOG_CLASS,"Resived Json responce: "+responce);

        } catch (Exception e) {
            Log.i(LOG_CLASS,"error");
            Log.i(LOG_CLASS,"error:"+e.toString());
            e.printStackTrace();
        }
    }



//    private class SendfeedbackJob extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String[] params) {
//            try {
//                String res;
//                Log.i(LOG_CLASS, "req0");
//                URL url = new URL("http://192.168.8.102:8080/ThreeFac_SEC/api/mobile_Reg");
//                //URL url = new URL("http://127.0.0.1:8080/ThreeFac_SEC/api/mobile_Reg");
//                URLConnection connection = url.openConnection();
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setConnectTimeout(10000);
//                connection.setReadTimeout(5000);
//                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//                Log.i(LOG_CLASS, "rew1");
//                out.write("{\"enc_msg\":\"1234\",\"imei\":\"4216890405043373\"}");
//                out.close();
//                Log.i(LOG_CLASS, "req2");
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                res = in.readLine();
//                Log.i(LOG_CLASS, "req3");
//                in.close();
//                return res;
//            }catch (Exception e){
//                e.toString();
//            }
//            return "some message";
//        }
//
//        @Override
//        protected void onPostExecute(String message) {
//            //process message
//        }
//    }
    
    public static String sendAndRec(String req)throws Exception{
        String res;
        Log.i(LOG_CLASS,"req0");
        URL url = new URL("http://192.168.8.102:8080/ThreeFac_SEC/api/mobile_Reg");
        //URL url = new URL("http://127.0.0.1:8080/ThreeFac_SEC/api/mobile_Reg");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(5000);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        Log.i(LOG_CLASS,"rew1");
        out.write(req);
        out.close();
        Log.i(LOG_CLASS,"req2");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        res=in.readLine();
        Log.i(LOG_CLASS,"req3");
        in.close();
        return res;
    }
}