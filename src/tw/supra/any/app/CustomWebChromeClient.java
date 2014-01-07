/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package tw.supra.any.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

import com.google.analytics.tracking.android.MapBuilder;

import tw.supra.any.R;
import tw.supra.any.ga.GaDef;
import tw.supra.any.manager.UIManager;
import tw.supra.any.utils.Log;

public class CustomWebChromeClient extends WebChromeClient {
    private static final String LOG_TAG = "supra-anyren";

    private Bitmap mDefaultVideoPoster = null;
    private View mVideoProgressView = null;

    public CustomWebChromeClient() {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void updateAlpha(WebView view, int newProgress) {
        float fromAlpha = view.getAlpha();
        float toAlpha = (newProgress / 100f);
        if(fromAlpha >= toAlpha){
            return;
        }
        AlphaAnimation anim = new AlphaAnimation(fromAlpha, toAlpha);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // mUIManager.onProgressChanged(view, newProgress);
        UIManager.getInstance().onLoad(newProgress);
        
        Log.i(LOG_TAG, "progress:" + newProgress);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
            updateAlpha(view, newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        // mUIManager.onReceivedTitle(view, title);\
        Log.i(LOG_TAG, "title:" + title);
        Log.i(LOG_TAG, "title:" + title);
        UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent(GaDef.ACTION_VIEW, "title", title, null).build());
//        UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent("ACTION_VIEW", "originalUrl", view.getOriginalUrl(), null).build());
        UIManager.getInstance().getGaTracker().send(MapBuilder.createEvent(GaDef.ACTION_VIEW, "url", view.getUrl(), null).build());
        // if (!view.isPrivateBrowsingEnabled()) {
        // UpdateHistoryTask task = new
        // UpdateHistoryTask(mUIManager.getMainActivity());
        // task.execute(view.getTitle(), view.getUrl(),
        // view.getOriginalUrl());
        // }
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        // mUIManager.onReceivedIcon(view, icon);
        //
        // UpdateFaviconTask task = new
        // UpdateFaviconTask(mUIManager.getMainActivity().getContentResolver(),
        // view.getUrl(), view.getOriginalUrl(), icon);
        // task.execute();
    }
    
    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        Log.i(LOG_TAG, "IconUrl:" + url);
    }

    // @Override
    // public boolean onCreateWindow(WebView view, final boolean dialog, final
    // boolean userGesture, final Message resultMsg) {
    // WebView.WebViewTransport transport = (WebView.WebViewTransport)
    // resultMsg.obj;
    //
    // CustomWebView curentWebView = mUIManager.getCurrentWebView();
    //
    // mUIManager.addTab(false, curentWebView.isPrivateBrowsingEnabled());
    //
    // transport.setWebView(mUIManager.getCurrentWebView());
    // resultMsg.sendToTarget();
    //
    // return true;
    // }


    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        String acceptType = "";
        openFileChooser(uploadMsg, acceptType);
      }
    
    // 3.0 + 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
            String acceptType) {
        String capture = "";
        openFileChooser(uploadMsg, acceptType, capture);
    }
      
      // Android > 4.1.1 调用这个方法
      public void openFileChooser(ValueCallback<Uri> uploadMsg,
        String acceptType, String capture) {
          UIManager.getInstance().openFileChooser(uploadMsg, acceptType, capture);          
      }

    // @Override
    // public Bitmap getDefaultVideoPoster() {
    // if (mDefaultVideoPoster == null) {
    // mDefaultVideoPoster =
    // BitmapFactory.decodeResource(mUIManager.getMainActivity().getResources(),
    // R.drawable.default_video_poster);
    // }
    //
    // return mDefaultVideoPoster;
    // }

    // @Override
    // public View getVideoLoadingProgressView() {
    // if (mVideoProgressView == null) {
    // LayoutInflater inflater =
    // LayoutInflater.from(mUIManager.getMainActivity());
    // mVideoProgressView = inflater.inflate(R.layout.video_loading_progress,
    // null);
    // }
    //
    // return mVideoProgressView;
    // }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        // new AlertDialog.Builder(mUIManager.getMainActivity())
        // .setTitle(R.string.JavaScriptAlertDialog)
        // .setMessage(message)
        // .setPositiveButton(android.R.string.ok,
        // new AlertDialog.OnClickListener()
        // {
        // public void onClick(DialogInterface dialog, int which) {
        // result.confirm();
        // }
        // })
        // .setCancelable(false)
        // .create()
        // .show();
        //
        // return true;
        Log.i(LOG_TAG, "onJsAlert");
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        // new AlertDialog.Builder(mUIManager.getMainActivity())
        // .setTitle(R.string.JavaScriptConfirmDialog)
        // .setMessage(message)
        // .setPositiveButton(android.R.string.ok,
        // new DialogInterface.OnClickListener()
        // {
        // public void onClick(DialogInterface dialog, int which) {
        result.confirm();
        // }
        // })
        // .setNegativeButton(android.R.string.cancel,
        // new DialogInterface.OnClickListener()
        // {
        // public void onClick(DialogInterface dialog, int which) {
        // result.cancel();
        // }
        // })
        // .create()
        // .show();
        // return true;

        Log.i(LOG_TAG, "onJsConfirm");
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
            String defaultValue, final JsPromptResult result) {

        // final LayoutInflater factory =
        // LayoutInflater.from(mUIManager.getMainActivity());
        // final View v = factory.inflate(R.layout.javascript_prompt_dialog,
        // null);
        // ((TextView)
        // v.findViewById(R.id.JavaScriptPromptMessage)).setText(message);
        // ((EditText)
        // v.findViewById(R.id.JavaScriptPromptInput)).setText(defaultValue);
        //
        // new AlertDialog.Builder(mUIManager.getMainActivity())
        // .setTitle(R.string.JavaScriptPromptDialog)
        // .setView(v)
        // .setPositiveButton(android.R.string.ok,
        // new DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int whichButton) {
        // String value = ((EditText)
        // v.findViewById(R.id.JavaScriptPromptInput)).getText()
        // .toString();
        // result.confirm(value);
        // }
        // })
        // .setNegativeButton(android.R.string.cancel,
        // new DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int whichButton) {
        // result.cancel();
        // }
        // })
        // .setOnCancelListener(
        // new DialogInterface.OnCancelListener() {
        // public void onCancel(DialogInterface dialog) {
        // result.cancel();
        // }
        // })
        // .show();
        // return true;

        Log.i(LOG_TAG, "onJsPrompt");
        return super.onJsPrompt(view, url, message, defaultValue, result);

    }

    // @Override
    // public void onHideCustomView() {
    // super.onHideCustomView();
    // mUIManager.onHideCustomView();
    // }

    // @Override
    // public void onShowCustomView(View view, int requestedOrientation,
    // CustomViewCallback callback) {
    // super.onShowCustomView(view, requestedOrientation, callback);
    // mUIManager.onShowCustomView(view, requestedOrientation, callback);
    // }

    // @Override
    // public void onShowCustomView(View view, CustomViewCallback callback) {
    // super.onShowCustomView(view, callback);
    // mUIManager.onShowCustomView(view, -1, callback);
    // }

    // @Override
    // public void onGeolocationPermissionsShowPrompt(String origin, Callback
    // callback) {
    // mUIManager.onGeolocationPermissionsShowPrompt(origin, callback);
    // }

    // @Override
    // public void onGeolocationPermissionsHidePrompt() {
    // mUIManager.onGeolocationPermissionsHidePrompt();
    // }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.i(LOG_TAG, "onConsoleMessage");
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Log.i(LOG_TAG, "onJsBeforeUnload");
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onJsTimeout() {
        Log.i(LOG_TAG, "onJsTimeout");
        return super.onJsTimeout();
    }

    @Override
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
            long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
        Log.i(LOG_TAG, "onExceededDatabaseQuota");
        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize,
                totalQuota,
                quotaUpdater);
    }

}
