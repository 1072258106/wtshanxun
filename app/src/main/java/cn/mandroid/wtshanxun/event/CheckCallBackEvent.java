package cn.mandroid.wtshanxun.event;

/**
 * Created by Administrator on 2015-11-23.
 */
public class CheckCallBackEvent {
    public final static int CHECK_NETWORK_VALID=1;
    private boolean isSuccess;

    public CheckCallBackEvent(boolean isSuccess, int action) {
        this.isSuccess = isSuccess;
        this.action = action;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    private int action;

}
