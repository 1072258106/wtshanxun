package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

import cn.mandroid.wtshanxun.utils.Constant;
import cn.mandroid.wtshanxun.utils.Preference;

/**
 * Created by Administrator on 2015-11-15.
 */
@EBean
public class UserManager extends ApiManager {
    public void getNotice(final Context context, final String user, final FetchCallback callback) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", user);
        Ion.with(context)
                .load(Constant.API_URL + "/getNotice")
                .addHeader("appName", "androidApp")
                .setBodyParameters(finalMap(context, map))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            if (result.get("code").getAsInt() == 1) {
                                callback.get(result.get("message").getAsString());
                                return;
                            }
                        }
                        callback.error();
                    }
                });
    }
}
