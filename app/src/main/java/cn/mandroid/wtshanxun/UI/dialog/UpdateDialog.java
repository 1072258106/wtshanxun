package cn.mandroid.wtshanxun.UI.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pgyersdk.javabean.AppBean;

import java.io.File;

import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.UI.widget.MyProgressBar;
import cn.mandroid.wtshanxun.utils.Const;
import cn.mandroid.wtshanxun.utils.MToast;

/**
 * Created by Administrator on 2015-09-22.
 */
public class UpdateDialog extends SuperDialog implements View.OnClickListener {
    private Context context;
    private TextView contentText;
    private MyProgressBar progressBar;
    private Button submit;
    private AppBean bean;
    private static UpdateDialog dialog;

    public static UpdateDialog instance(Context context, AppBean bean) {
        if (dialog == null) {
            dialog = new UpdateDialog(context);
        }
        dialog.setData(bean);
        return dialog;
    }

    private UpdateDialog(Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.doalog_update);
        this.context = context;
        contentText = (TextView) findViewById(R.id.contentText);
        progressBar = (MyProgressBar) findViewById(R.id.updateProgressBar);
        progressBar.setIndeterminate(false);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    private void setData(AppBean bean) {
        this.bean = bean;
        contentText.append("版本号：" + bean.getVersionName());
        contentText.append("\n");
        contentText.append("更新说明\n" + bean.getReleaseNote());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                File file = new File(Const.getDownloadPath() + "/闪讯wifi助手 " + bean.getVersionName() + ".apk");
                if (file.exists()) {
                    installApk(file);
                    return;
                }
                MToast.show(context, "正在下载");
                submit.setClickable(false);
                submit.setText("正在下载");
                progressBar.setVisibility(View.VISIBLE);
                Ion.with(context).load(bean.getDownloadURL()).progressBar(progressBar).write(file).setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        submit.setClickable(true);
                        submit.setText("安装");
                        installApk(result);
                    }
                });
                break;
        }
    }

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
