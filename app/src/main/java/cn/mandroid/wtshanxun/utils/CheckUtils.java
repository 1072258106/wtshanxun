package cn.mandroid.wtshanxun.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.mandroid.wtshanxun.UI.common.BasicActivity;
import cn.mandroid.wtshanxun.event.CheckCallBackEvent;
import de.greenrobot.event.EventBus;


public class CheckUtils {
    public static String sign;

    public static Boolean stringIsEmpty(String... s) {
        for (String string : s) {
            if (string == null) {
                return true;
            } else if (string.trim().equals("")) {
                return true;
            } else if (string.trim().toLowerCase().equals("null")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    public static boolean isEmail(String phone) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }

    public static boolean sxAcountIsValid(String username) {
        return (isEmail(username) && username.endsWith("XY"));
    }

    public static boolean passIsValid(String password) {
        return (password.length() >= 6 && password.length() <= 20);
    }

    public static boolean isWebSite(String s) {
        Pattern p = Pattern
                .compile("(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*");
        Matcher m = p.matcher(s);
        return m.find();
    }

    public static boolean isRouterSite(String s) {
        return (isWebSite(s) && !s.contains("?") && !s.contains("/") && !s.contains("\\"));
    }

    public static boolean routerIsValid(Context context, Preference preference) {
        // TODO Auto-generated method stub
        String routerAcc = preference.getString(Preference.ROUTER_ACC);
        String routerPass = preference.getString(Preference.ROUTER_PASS);
        if (CheckUtils.stringIsEmpty(routerAcc, routerPass)) {
            MToast.show(context, "路由器配置有误");
            return false;
        }
        return true;
    }

    public static boolean shanxunIsValid(Context context, String sxUser, String sxPass) {
        // TODO Auto-generated method stub
        if (CheckUtils.stringIsEmpty(sxUser, sxPass)) {
            MToast.show(context, "闪讯账号或密码输入有误");
            return false;
        }
        if (!CheckUtils.sxAcountIsValid(sxUser)) {
            MToast.show(context, "闪讯账号有误");
            return false;
        }
        return true;
    }

    public static void networkIsValid(Context context) {
        Ion.with(context).load("http://www.baidu.com").setTimeout(3000).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                EventBus.getDefault().post(new CheckCallBackEvent(e == null, CheckCallBackEvent.CHECK_NETWORK_VALID));
            }
        });
    }
}
