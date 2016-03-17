package cn.mandroid.wtshanxun.UI.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.event.ExitApp;
import cn.mandroid.wtshanxun.utils.Tools;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015-09-22.
 */
public class ExitDialog extends SuperDialog implements View.OnClickListener {
    private Context context;
    private TextView contentText;
    private Button exitBut;
    private static ExitDialog dialog;

    public static ExitDialog instance(Context context) {
        return new ExitDialog(context);
    }

    private ExitDialog(Context context) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.doalog_exit);
        this.context = context;
        contentText = (TextView) findViewById(R.id.contentText);
        exitBut = (Button) findViewById(R.id.exitAppBut);
        exitBut.setOnClickListener(this);
        setContent();
    }

    private void setContent() {
        contentText.append(context.getResources().getString(R.string.app_name) + " V");
        contentText.append(Tools.getVersion(context));
        contentText.append("\n");
        contentText.append("本软件主要针对浙江闪讯用户，支持大多数老版本的路由器，具体请自行测试");
        contentText.append("，不支持L2TP模式登录的闪讯用户");
        contentText.append("\n");
        contentText.append("摇一摇即可给我们留言/反馈");
        contentText.append("\n");
        contentText.append("也有闪讯路由器出售，需要可以联系我们");
        contentText.append("\n");
        contentText.append("联系QQ:75376632");
        contentText.append("\n");
        contentText.append("软件交流QQ群：415664606");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exitAppBut:
                dismiss();
                EventBus.getDefault().post(new ExitApp());
                break;
        }
    }
}
