package cn.mandroid.wtshanxun.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class Const {
    public static String LOG_TAG = "LOG_TAG";
    public static String TBPAY_URL = "https://item.taobao.com/item.htm?spm=686.1000925.0.0.yJP7hz&id=522902751643https://item.taobao.com/item.htm?spm=686.1000925.0.0.yJP7hz&id=522902751643";
    public static String ADID = "50d71542fec25f97";
    public static String ADSECRET = "5445243e7e902992";
    private static String APP_MODEL = "original V";
    public static String getAppModel(Context context){
        return APP_MODEL+Tools.getVersion(context);
    }
    public static String getDownloadPath() {
        File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        File dir = new File(sdDir.toString() + "/sxDownload");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return sdDir.toString() + "/sxDownload";
    }

}
