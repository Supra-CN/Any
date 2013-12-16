
package tw.supra.anyqq.manager;

import android.widget.ProgressBar;

import tw.supra.anyqq.MainActivity;
import tw.supra.anyqq.R;

public class UIManager {
    private static UIManager instance;
    private MainActivity mActivity;
    private ProgressBar mProgress;

    public static UIManager getInstance() {
        return instance;
    }

    private UIManager(MainActivity activity) {
        mActivity = activity;
    }

    public static void create(MainActivity activity) {
        if (instance == null) {
            instance = new UIManager(activity);
        }
    }

    private ProgressBar getProgressBar() {
        if (mProgress == null) {
            mProgress = (ProgressBar) mActivity.findViewById(R.id.progress);
        }
        return mProgress;
    }

    public void setProgress(int progress){
        getProgressBar().setProgress(progress);
    }
}
