
package tw.supra.any.app;

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
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import tw.supra.any.ga.GaDef;
import tw.supra.any.manager.UIManager;
import tw.supra.any.views.PullToRefreshWebView;
import tw.supra.any.views.PullToRefreshBase.OnRefreshListener;
import tw.supra.anyren.R;

public class MainActivity extends Activity {
    // private WebView wv;
    private PullToRefreshWebView mContainer;
    private AlertDialog mAboutDialog;
    private AlertDialog mNetworkDialog;
    private AlertDialog mExitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY, WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY);
        UIManager.create(this);
        setContentView(R.layout.activity_main);
        ((FrameLayout) findViewById(R.id.container)).addView(getContainer());
    }

    @Override
    protected void onStart() {
        super.onStart();
        UIManager.getInstance().getGaTracker().activityStart(this);
        if (!isNetworkAvailable(this)) {
            UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent(GaDef.ACTION_VIEW, "dialog", "network", null).build());
            getNetWorkDialog().show();
        } else {
            loadUrl("http://m.renren.com");
        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        UIManager.getInstance().getGaTracker().activityStop(this);
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
            case R.id.action_about:
                UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent(GaDef.ACTION_VIEW, "dialog", "about", null).build());
//                getAboutDialog().show();
                Intent intent = new Intent(getApplicationContext(), AboutDialogActivity.class);
                startActivity(intent);
                break;
            
            case R.id.action_exit:
                UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent(GaDef.ACTION_VIEW, "dialog", "exit", null).build());
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
    
    private AlertDialog getAboutDialog(){
        if (mAboutDialog == null) {
            mAboutDialog = createAboutDialog();
        }
        return mAboutDialog;
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
    
    private AlertDialog createAboutDialog(){
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        dialog.cancel();
                        break;

                    default:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(new AboutView(this));
//        builder.setIcon(R.drawable.ic_launcher);
//        builder.setTitle(R.string.about_dialog_title);
//        builder.setMessage(R.string.about_dialog_msg);
//        builder.setPositiveButton(R.string.about_dialog_positive, listener);
        AlertDialog dialog = builder.create();
//        dialog.setContentView(R.layout.about_view);
        return dialog;
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
        builder.setIcon(R.drawable.ic_launcher);
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
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle(R.string.network_dialog_title);
        builder.setMessage(R.string.network_dialog_msg);
        builder.setPositiveButton(R.string.network_dialog_positive, listener);
        builder.setNeutralButton(R.string.network_dialog_neutral, listener);
        builder.setNegativeButton(R.string.network_dialog_negative, listener);
        return builder.create();
    }
}
