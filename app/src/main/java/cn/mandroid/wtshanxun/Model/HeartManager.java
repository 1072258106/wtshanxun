package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import cn.mandroid.wtshanxun.Model.Bean.BeanManager;
import cn.mandroid.wtshanxun.UI.Service.HeartService;
import cn.mandroid.wtshanxun.event.StopHeartEvent;
import cn.mandroid.wtshanxun.event.TotalEvent;
import cn.mandroid.wtshanxun.utils.Base64;
import cn.mandroid.wtshanxun.utils.Constant;
import cn.mandroid.wtshanxun.utils.Preference;
import cn.mandroid.wtshanxun.utils.Udp;

/**
 * Created by Administrator on 2015-11-26.
 */
@EBean
public class HeartManager extends ApiManager {
    public void sendHeart(Context context) {
        Ion.with(context).load(Constant.API_URL + "/heart/GetIp").asString().setCallback(ipCallback(context));
    }

    private void getHeartData(Context context, String ip) {
        Map<String, String> map = new TreeMap<>();
        map.put("user", BeanManager.getUserBean(context).getSxAcount());
        map.put("ip", ip);
        Ion.with(context).load(Constant.API_URL + "/heart/getByApp").setMultipartParameters(finalMap(context, map)).asJsonObject().setCallback(heartDataCallback(context));
    }

    private FutureCallback<JsonObject> heartDataCallback(final Context context) {
        return new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (e == null) {
                    try {
                        if (result.get("code").getAsInt() == 4) {
                            HeartService.eventBus.post(new StopHeartEvent());
                            return;
                        }else if(result.get("code").getAsInt() != 1){
                            return;
                        }
                        JsonObject data = result.get("data").getAsJsonObject();
                        String heartData = data.get("data").getAsString();
                        String heartIp = data.get("sendIp").getAsString();
                        int heartPort = data.get("sendPort").getAsInt();
                        HeartService.eventBus.post(Udp.instance(heartIp, heartPort, Base64.decode(heartData.toCharArray())));
                    } catch (Exception e1) {
                        return;
                    }
                    TotalEvent event = new TotalEvent();
                    event.setSendHeartCount();
                    HeartService.eventBus.post(event);
                }
            }
        };
    }

//    private FutureCallback<String> heartDataCallback(Context context) {
//        return new FutureCallback<String>() {
//            @Override
//            public void onCompleted(Exception e, String result) {
//                if(e==null){
//
//
//                }
//            }
//        };
//    }

    private FutureCallback<String> ipCallback(final Context context) {
        return new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (e == null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String ip = jsonObject.getString("ip");
                        getHeartData(context, ip);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }
}
