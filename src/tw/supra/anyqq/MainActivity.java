
package tw.supra.anyqq;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.webkit.WebView;

public class MainActivity extends Activity {
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wv = new CustomWebView(this,false);
        wv.getSettings().setJavaScriptEnabled(true); 
        wv.setWebChromeClient(new CustomWebChromeClient());
        wv.setWebViewClient(new CustomWebViewClient());
        setContentView(wv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != wv && TextUtils.isEmpty(wv.getUrl())) {
            wv.loadUrl("http://m.renren.com");
            
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
        if(wv.canGoBack()){
            wv.goBack();
//        }else{
//            moveTaskToBack(true);
        }
    }
    
}
