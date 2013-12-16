
package tw.supra.anyqq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import tw.supra.anyqq.view.PullToRefreshWebView;

public class MainActivity extends Activity {
    // private WebView wv;
    private PullToRefreshWebView mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer = new PullToRefreshWebView(this);
        setContentView(mContainer);
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
            // }else{
            // moveTaskToBack(true);
        }
    }

    private CustomWebView getWebView() {
        if (mContainer != null && mContainer.refreshableView != null) {
            return mContainer.refreshableView;
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private AlertDialog getNetWorkDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        break;

                    case AlertDialog.BUTTON_NEGATIVE:
                        finish();
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
        builder.setNegativeButton(R.string.network_dialog_negative, listener);
        return builder.create();
    }
}
