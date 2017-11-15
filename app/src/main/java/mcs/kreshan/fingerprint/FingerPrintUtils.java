package mcs.kreshan.fingerprint;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

/**
 * Created by Keval on 07-Oct-16.
 *
 * @author 'https://github.com/kevalpatel2106'
 */

public class FingerPrintUtils {
    public static final String LOG_CLASS = "MainActivityFP";
    //Open the Security settings screen.
    public static void openSecuritySettings(@NonNull Context context) {
        Log.i(LOG_CLASS,"FingerPrintUtils.openSecuritySettings>>");
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        context.startActivity(intent);
    }

    //Check if the device have supported hardware for the finger print scanner.
    public static boolean isSupportedHardware(Context context) {
        Log.i(LOG_CLASS,"FingerPrintUtils.isSupportedHardware>>");
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.isHardwareDetected();
    }
}
