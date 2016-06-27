package cn.mandroid.wtshanxun.Model;

import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.mandroid.wtshanxun.utils.Constant;
import cn.mandroid.wtshanxun.utils.Preference;

/**
 * Created by Administrator on 2015-11-15.
 */
@EBean
public class UserManager extends ApiManager {
    public void getNotice(final Context context, final String user, final FetchCallback callback) {
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("user", user);
        post(context,"/getNotice",map,callback);
    }
}
