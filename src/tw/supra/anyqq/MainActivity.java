
package tw.supra.anyqq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;

import tw.supra.anyqq.manager.UIManager;
import tw.supra.anyqq.view.PullToRefreshBase.OnRefreshListener;
import tw.supra.anyqq.view.PullToRefreshWebView;

public class MainActivity extends Activity {
    // private WebView wv;
    private PullToRefreshWebView mContainer;
    private AlertDialog mNetworkDialog;
    private AlertDialog mExitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIManager.create(this);
        setContentView(R.layout.activity_main);
        ((FrameLayout) findViewById(R.id.container)).addView(getContainer());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isNetworkAvailable(this)) {
            getNetWorkDialog().show();
        } else {
            loadUrl("http://m.renren.com");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        CustomWebView wv = getWebView();
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            getExitDialog().show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                getExitDialog().show();
                break;

//            case R.id.action_clean:
//                getWebView().loadDataWithBaseURL(null, "","text/html", "utf-8",null);
//                getWebView().clearCache(true);
//                CookieSyncManager.createInstance(MainActivity.this);
//                CookieSyncManager.getInstance().startSync();
//                CookieManager.getInstance().removeAllCookie();
//                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public PullToRefreshWebView getContainer() {
        if (mContainer == null) {
            mContainer = createContainer();
            mContainer.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (getContainer().getRefreshableView() != null) {
                        // mContainer.setRefreshing(false);
                        mContainer.getRefreshableView().reload();
                    }
                }
            });

        }
        return mContainer;
    }

    private PullToRefreshWebView createContainer() {
        PullToRefreshWebView container = new PullToRefreshWebView(this);
        container.setPullToRefreshEnabled(true);
        container.setDisableScrollingWhileRefreshing(false);

        // mContainer.setPullLabel(getResources().getString(
        // R.string.reader_channel_pull_hint));
        // mContainer.setRefreshingLabel(getResources().getString(
        // R.string.reader_channel_refresh_hint));
        // mContainer.setReleaseLabel(getResources().getString(
        // R.string.reader_channel_release_hint));
        return container;
    }

    private CustomWebView getWebView() {
        if (mContainer != null && mContainer.getRefreshableView() != null) {
            return mContainer.getRefreshableView();
        }
        return null;
    }

    private void loadUrl(String url) {
        CustomWebView wv = getWebView();
        if (null != wv && TextUtils.isEmpty(wv.getUrl())) {
            wv.loadUrl(url);
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        NetworkInfo info = getActiveNetworkInfo(c);
        return (null != info) && (info.isAvailable());
    }

    private static NetworkInfo getActiveNetworkInfo(Context context) {
        return ((ConnectivityManager) (context
                .getSystemService(Context.CONNECTIVITY_SERVICE)))
                .getActiveNetworkInfo();
    }

    private AlertDialog getExitDialog() {
        if (mExitDialog == null) {
            mExitDialog = createExitDialog();
        }
        return mExitDialog;
    }

    private AlertDialog getNetWorkDialog() {
        if (mNetworkDialog == null) {
            mNetworkDialog = createNetWorkDialog();
        }
        return mNetworkDialog;
    }

    private AlertDialog createExitDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        finish();
                        break;

                    case AlertDialog.BUTTON_NEUTRAL:
                        moveTaskToBack(true);
                        break;

                    case AlertDialog.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;

                    default:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.exit_dialog_title);
        builder.setMessage(R.string.exit_dialog_msg);
        builder.setPositiveButton(R.string.exit_dialog_positive, listener);
        builder.setNeutralButton(R.string.exit_dialog_neutral, listener);
        builder.setNegativeButton(R.string.exit_dialog_negative, listener);
        return builder.create();
    }

    private AlertDialog createNetWorkDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        break;

                    case AlertDialog.BUTTON_NEUTRAL:
                        finish();
                        break;

                    case AlertDialog.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;

                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.network_dialog_title);
        builder.setMessage(R.string.network_dialog_msg);
        builder.setPositiveButton(R.string.network_dialog_positive, listener);
        builder.setNeutralButton(R.string.network_dialog_neutral, listener);
        builder.setNegativeButton(R.string.network_dialog_negative, listener);
        return builder.create();
    }
}
