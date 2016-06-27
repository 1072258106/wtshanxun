package cn.mandroid.wtshanxun.Model;

import android.content.Context;
import android.text.TextUtils;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.mandroid.wtshanxun.Model.Bean.FetchResult;
import cn.mandroid.wtshanxun.utils.Const;
import cn.mandroid.wtshanxun.utils.Constant;
import cn.mandroid.wtshanxun.utils.MD5;
import cn.mandroid.wtshanxun.utils.MToast;
import cn.mandroid.wtshanxun.utils.Preference;
import cn.mandroid.wtshanxun.utils.Tools;

/**
 * Created by Administrator on 2015-11-15.
 */
public class ApiManager {
    protected TreeMap<String, List<String>> finalMap(Context context, Map<String, String> map, long timestamp) {
        if (map == null) {
            map = new TreeMap<>();
        }
        map.put("appModel", Const.getAppModel(context));
        map.put("mac", Tools.getMacAddress(context));
        map.put("timestamp", timestamp + "");
        TreeMap<String, List<String>> params = new TreeMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> list = new ArrayList<>();
            list.add(entry.getValue());
            params.put(entry.getKey(), list);
        }
        return params;
    }

    protected void postOtherApi(Context context, String url, TreeMap<String, String> params, final ApiCallback callback) {
        Ion.with(context)
                .load(url)
                .setBodyParameters(finalMap(context, params, 0))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            callback.onSuccess(result);
                        } else {
                            callback.error();
                        }
                    }
                });
    }

    protected void post(Context context, String url, TreeMap<String, String> params, final FetchCallback callback) {
        final long timestamp = System.currentTimeMillis();
        TreeMap<String, List<String>> fMap = finalMap(context, params, timestamp);
        String pa = getParams(fMap);
        Ion.with(context)
                .load(Constant.API_URL + url)
                .addHeader("appName", "androidApp")
                .addHeader("code", MD5.encode(pa))
                .addHeader("ip", TextUtils.isEmpty(Constant.DEVICES_IP) ? "" : Constant.DEVICES_IP)
                .setBodyParameters(fMap)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            callback.get(FetchResult.instance(result, timestamp));
                        } else {
                            callback.error();
                        }
                    }
                });
    }

    private static String getParams(TreeMap<String, List<String>> params) {
        if (params == null) {
            params = new TreeMap<>();
        }
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            buffer.append(entry.getKey()).append("=").append(entry.getValue().get(0)).append("&");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }
}
