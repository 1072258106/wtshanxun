package cn.mandroid.wtshanxun.UI.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.mandroid.wtshanxun.R;

/**
 * Created by Administrator on 2015-09-22.
 */
public class NoticeDialog extends SuperDialog implements View.OnClickListener {
    private Context context;
    private TextView titleText;
    private TextView contentText;
    private Button submit;

    public NoticeDialog(Context context, String title, String content) {
        super(context, R.style.MyDialog);
        setContentView(R.layout.doalog_notice);
        this.context = context;
        contentText = (TextView) findViewById(R.id.contentText);
        titleText = (TextView) findViewById(R.id.titleText);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        setContent(content);
        setTitle(title);
    }
    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setContent(String content) {
        contentText.setText(content);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                cancel();
                break;
        }
    }
}
