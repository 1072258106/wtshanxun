package cn.mandroid.wtshanxun.Model.Bean;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.mandroid.wtshanxun.utils.DES;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FetchResult {
    private boolean isSuccess;
    private int code;
    private String data;
    private String message;

    private FetchResult() {
    }

    public static FetchResult instance(String data,long timestamp) {
        FetchResult fetchResult = new FetchResult();
        try {
            data = DES.aesDecrypt(data,timestamp);
        } catch (Exception e) {
            return fetchResult;
        }
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(data);
        JsonObject jsonObject = je.getAsJsonObject();
        fetchResult.code = jsonObject.get("code").getAsInt();
        if (fetchResult.code == 1) {
            fetchResult.isSuccess = true;
            fetchResult.data = jsonObject.has("data") ? jsonObject.get("data").toString() : null;
        }
        fetchResult.message = jsonObject.has("message") ? jsonObject.get("message").getAsString() : null;
        return fetchResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
