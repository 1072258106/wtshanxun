package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;
import java.util.Map;
import java.util.TreeMap;

import cn.mandroid.wtshanxun.Model.Bean.BeanManager;
import cn.mandroid.wtshanxun.Model.Bean.FetchResult;
import cn.mandroid.wtshanxun.Model.Bean.HeartBean;
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
        getHeartData(context);
    }

    private void getHeartData(Context context) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("user", BeanManager.getUserBean(context).getSxAcount());
        post(context, "/getHeart", map, new FetchCallback() {
            @Override
            public void get(FetchResult result) {
                if (result.isSuccess()) {
                    HeartBean bean = new Gson().fromJson(result.getData(), HeartBean.class);
                    try {
                        HeartService.eventBus.post(Udp.instance(bean.getAddress(), bean.getPort(), Base64.decode(bean.getData().toCharArray())));
                        TotalEvent event = new TotalEvent();
                        event.setSendHeartCount();
                        HeartService.eventBus.post(event);
                    } catch (SocketException e) {
                        return;
                    }
                }
            }
            @Override
            public void error() {

            }
        });
    }
}
