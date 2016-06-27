package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.mandroid.wtshanxun.Model.Bean.BeanManager;
import cn.mandroid.wtshanxun.Model.Bean.IpConfig;
import cn.mandroid.wtshanxun.Model.Bean.UserBean;
import cn.mandroid.wtshanxun.utils.CheckUtils;
import cn.mandroid.wtshanxun.utils.Pin;
import cn.mandroid.wtshanxun.utils.Sto16;
import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.Service.HeartService_;
import cn.mandroid.wtshanxun.utils.Base64;
import cn.mandroid.wtshanxun.utils.Constant;
import cn.mandroid.wtshanxun.utils.MToast;
import cn.mandroid.wtshanxun.utils.Preference;

/**
 * Created by Administrator on 2015-11-23.
 */
@EBean
public class ShanxunManager extends ApiManager {
    public void routerDial(Context context, String sxAcount, String sxPass, ApiCallback callback) throws UnsupportedEncodingException {
        Preference preference = Preference.instance(context);
        String acount = getFinalUser(sxAcount);
        String routerIp = preference.getString(Preference.ROUTER_IP);
        String RouterAcc = preference.getString(Preference.ROUTER_ACC);
        String routerPass = preference.getString(Preference.ROUTER_PASS);
        String getRequest = "wan=0" + "&wantype=2" + "&acc=" + acount + "&psw="
                + sxPass + "&confirm=" + sxPass + "&specialDial=0"
                + "&SecType=0" + "&sta_ip=0.0.0.0" + "&sta_mask=0.0.0.0"
                + "&linktype=4" + "&waittime2=0" + "&Connect=%C1%AC+%BD%D3";
        String url = "http://" + routerIp + "/userRpm/PPPoECfgRpm.htm?"
                + getRequest;
        String ro = RouterAcc + ":" + routerPass;
        String auth = "Basic%20" + new String(Base64.encode(ro.getBytes("GB18030")));
        Ion.with(context)
                .load(url)
                .setTimeout(10000)
                .setHeader("Authorization", auth)
                .setHeader("Cookie", "Authorization=" + auth + "; ChgPwdSubTag=")
                .setHeader("Host", routerIp)
                .setHeader("Referer", "http://" + routerIp + "/userRpm/PPPoECfgRpm.htm")
                .setHeader("Content-Type", context.getResources().getText(R.string.content_type).toString())
                .setHeader("User-Agent", context.getResources().getText(R.string.user_agent).toString())
                .setHeader("Accept", context.getResources().getText(R.string.accept).toString())
                .setHeader("Accept-Language", context.getResources().getText(R.string.accept_language).toString())
                .setHeader("Accept-Encoding", context.getResources().getText(R.string.accept_encoding).toString())
                .asString()
                .setCallback(dialCall(context, callback));
    }

    private FutureCallback<String> dialCall(final Context context, final ApiCallback callback) {
        return new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (e == null) {
                    callback.onSuccess("dial_finish");
                    checkNetwork(context, callback);
                } else {
                    callback.error();
                }

            }
        };
    }

    private void checkNetwork(final Context context, final ApiCallback callback) {
        postOtherApi(context, "http://1212.ip138.com/ic.asp", null, new ApiCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    String ip = result.substring(result.indexOf("[")+ 1, result.indexOf("]") );
                    if (CheckUtils.isIp(ip)) {
                        Constant.DEVICES_IP = ip;
                        callback.onSuccess("dial_success");
                        checkNeedSentHeart(context);
                    } else {
                        callback.error();
                    }
                } catch (Exception e) {
                    callback.error();
                }

            }

            @Override
            public void error() {
                callback.error();
            }
        });
    }

    public void checkNeedSentHeart(Context context) {
        boolean needHeart = Preference.instance(context).getBoolean(Preference.SEND_HEART);
        if (needHeart) {
            HeartService_.intent(context).start();
        }
    }

    private String getFinalUser(String sxAcount) {
        String after = Pin.getpin(sxAcount.getBytes());
        String yhm = Sto16.bin2hex(after);
        return yhm;
    }


}
