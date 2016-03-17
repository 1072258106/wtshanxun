package cn.mandroid.wtshanxun.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.provider.Settings;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import cn.mandroid.wtshanxun.UI.common.BasicActivity;

/**
 * Created by Administrator on 2015-10-02.
 */
public class Tools {
    public static  String sign;
    public static void setWifiNeverDormancy(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        int value = Settings.System.getInt(resolver, Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
        if (Settings.System.WIFI_SLEEP_POLICY_NEVER != value) {
            Settings.System.putInt(resolver, Settings.System.WIFI_SLEEP_POLICY, Settings.System.WIFI_SLEEP_POLICY_NEVER);

        }
    }
    //    public static void getSms(Context context, boolean isSet) {
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage("106593005", null, "MM", null, null);
//        SmsReceiver receiver = SmsReceiver.instance(isSet);
//        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//        context.registerReceiver(receiver, filter);
//    }
    public static void initSign(BasicActivity context) {
        // TODO Auto-generated method stub
        if(sign!=null){
            return ;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(sign.toByteArray()));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            Tools.sign= MD5.encode(pubKey + signNumber);
            MLog.i(Tools.sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersion(Context context) {

        try {

            PackageManager manager = context.getPackageManager();

            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            String version = info.versionName;

            return version;

        } catch (Exception e) {

            e.printStackTrace();


        }
        return "";
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }
}
