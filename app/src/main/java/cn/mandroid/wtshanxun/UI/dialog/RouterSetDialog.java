package cn.mandroid.wtshanxun.UI.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.mandroid.wtshanxun.R;
import cn.mandroid.wtshanxun.utils.CheckUtils;
import cn.mandroid.wtshanxun.utils.MToast;
import cn.mandroid.wtshanxun.utils.Preference;

public class RouterSetDialog extends SuperDialog implements View.OnClickListener {
    private EditText routerIpEdit;
    private EditText routerAccEdit;
    private EditText routerPassEdit;
    private Button button;
    private String routerIp;
    private String routerAcc;
    private String routerPass;
    private Preference preference;
    private Context context;

    public static RouterSetDialog instance(Context context) {
        return new RouterSetDialog(context);
    }

    private RouterSetDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        setContentView(R.layout.doalog_router_set);
        initView();
        preference = Preference.instance(context);
        initData();
    }

    private void initData() {
        // TODO Auto-generated method stub
        routerAcc = preference.getString(Preference.ROUTER_ACC);
        routerIp = preference.getString(Preference.ROUTER_IP).equals("") ? "192.168.1.1"
                : preference.getString(Preference.ROUTER_IP);
        routerPass = preference.getString(Preference.ROUTER_PASS);
        routerIpEdit.setText(routerIp);
        routerPassEdit.setText(routerPass);
        routerAccEdit.setText(routerAcc);
        button.setOnClickListener(this);

    }

    private void initView() {
        routerIpEdit = (EditText) findViewById(R.id.routerIPAddrEdit);
        routerAccEdit = (EditText) findViewById(R.id.routerAccEdit);
        routerPassEdit = (EditText) findViewById(R.id.routerPassEdit);
        button = (Button) findViewById(R.id.saveRouterInfoBut);
        button.setOnClickListener(this);

    }

    private boolean checkInput() {
        // TODO Auto-generated method stub
        if (!CheckUtils.isRouterSite(routerIp)) {
            MToast.show(context, "路由器后台地址有误");
            return false;
        }
        if (CheckUtils.stringIsEmpty(routerAcc, routerPass)) {
            MToast.show(context, "路由器账号或密码输入有误");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveRouterInfoBut:
                save();
                break;
        }
    }

    private void save() {
        routerAcc = routerAccEdit.getText().toString();
        routerIp = routerIpEdit.getText().toString();
        routerPass = routerPassEdit.getText().toString();
        if (!checkInput()) {
            return;
        }
        preference.putString(Preference.ROUTER_ACC, routerAcc);
        preference.putString(Preference.ROUTER_IP, routerIp);
        preference.putString(Preference.ROUTER_PASS, routerPass);
        MToast.show(context, "已保存");
        cancel();
    }
}
