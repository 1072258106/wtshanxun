package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected Map<String, List<String>> finalMap(Context context, Map<String, String> map) {
        String time =System.currentTimeMillis()/1000+"";
        map.put("appModel", Const.getAppModel(context));
        map.put("mac", Tools.getMacAddress(context));
        map.put("timestamp",time );
        String sign= MD5.encode(Preference.instance(context).getString(Preference.SX_USER)+Tools.getMacAddress(context)+time+Tools.sign);
        map.put("sign",sign);
        Map<String, List<String>> params = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<String> list = new ArrayList<>();
            list.add(entry.getValue());
            params.put(entry.getKey(), list);
        }
        return params;
    }

    protected void getData(Context context, String result, FetchCallback fetchCallback) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == Constant.SUCCESS) {
                if (jsonObject.getInt("code") != Constant.Code.SUCCESS) {
                    MToast.show(context, jsonObject.getString("msg"));
                }
                fetchCallback.get(jsonObject.getString("data"));
            } else {
                fetchCallback.error();
                MToast.showError(context, jsonObject.getString("msg"));
            }
        } catch (JSONException e) {
            fetchCallback.error();
        }
    }

}
