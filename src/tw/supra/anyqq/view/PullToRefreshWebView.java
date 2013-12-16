package tw.supra.anyqq.view;

import android.content.Context;
import android.util.AttributeSet;

import tw.supra.anyqq.CustomWebChromeClient;
import tw.supra.anyqq.CustomWebView;
import tw.supra.anyqq.CustomWebViewClient;

public class PullToRefreshWebView extends PullToRefreshBase<CustomWebView> {

    public PullToRefreshWebView(Context context) {
        super(context);
    }

    @Override
    protected CustomWebView createRefreshableView(Context context, AttributeSet attrs) {
        CustomWebView wv = new CustomWebView(context,false);
        wv.getSettings().setJavaScriptEnabled(true); 
        wv.setWebChromeClient(new CustomWebChromeClient());
        wv.setWebViewClient(new CustomWebViewClient());
        return wv;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return refreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullUp() {
//        return refreshableView.getScrollY() >= (refreshableView
//                .getContentHeight()
//                * refreshableView.getScale()
//                - refreshableView.getHeight() - 1);
        return false;
    }
    
}
