package tw.supra.anyren.views;

import android.content.Context;
import android.util.AttributeSet;

import tw.supra.anyren.CustomWebChromeClient;
import tw.supra.anyren.CustomWebView;
import tw.supra.anyren.CustomWebViewClient;

public class PullToRefreshWebView extends PullToRefreshBase<CustomWebView> {

    public PullToRefreshWebView(Context context) {
        super(context, MODE_PULL_DOWN_TO_REFRESH);
    }

    @Override
    protected CustomWebView createRefreshableView(Context context, AttributeSet attrs) {
        CustomWebView wv = new CustomWebView(context,false);
        wv.setWebChromeClient(new CustomWebChromeClient());
        wv.setWebViewClient(new CustomWebViewClient());
        return wv;
    }

    @Override
    protected boolean isReadyForPullDown() {
        if(!isRefreshing() && getRefreshableView().getScrollY() == 0){
            return true;
        }else {
            return false;
        }
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
