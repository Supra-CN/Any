
package tw.supra.any.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.widget.ProgressBar;

import com.google.analytics.tracking.android.EasyTracker;

import tw.supra.any.R;
import tw.supra.any.app.MainActivity;
import tw.supra.any.views.PullToRefreshWebView;

public class UIManager {
    private static final String LOG_TAG = "supra-anyren";

    private static UIManager instance;
    private MainActivity mActivity;
    private ProgressBar mProgress;
    private ValueCallback<Uri> mUploadMessage = null;

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

    public MainActivity getMainActivity() {
        return mActivity;
    }

    public EasyTracker getGaTracker() {
        return EasyTracker.getInstance(mActivity.getApplicationContext());
    }

    private ProgressBar getProgressBar() {
        return mProgress;
    }

    private void setProgress(int progress) {
        // getProgressBar().setProgress(progress);
    }

    public void onLoad(int newProgress) {
        PullToRefreshWebView container = mActivity.getContainer();
        if (newProgress < 100) {
            setProgress(newProgress);
            if (!container.isRefreshing()) {
                container.setRefreshing(true);
            }
        } else {
            setProgress(0);
            onLoadComplete();
        }
    }

    public void onLoadComplete() {
        PullToRefreshWebView container = mActivity.getContainer();
        container.onRefreshComplete();
    }

    // Android > 4.1.1 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
            String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        getMainActivity().startActivityForResult(
                Intent.createChooser(
                        intent,
                        getMainActivity()
                                .getString(R.string.file_chooser_prompt)),
                MainActivity.ACTIVITY_REQUEST_FILE_CHOOSER);

    }

    public void onFileChooserResult(int resultCode, Intent data){
            if (null == mUploadMessage)
             return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null
              : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
    }

}
