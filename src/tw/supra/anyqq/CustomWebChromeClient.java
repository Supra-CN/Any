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

package tw.supra.anyqq;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

import tw.supra.anyqq.manager.UIManager;

public class CustomWebChromeClient extends WebChromeClient {
    private static final String LOG_TAG = "supra-anyqq";

    private Bitmap mDefaultVideoPoster = null;
    private View mVideoProgressView = null;

    public CustomWebChromeClient() {
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void updateAlpha(WebView view, int newProgress){
        float toAlpha = (newProgress / 100f);
        AlphaAnimation anim = new AlphaAnimation(view.getAlpha(), toAlpha);
        anim.setDuration(500);
        view.startAnimation(anim);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // mUIManager.onProgressChanged(view, newProgress);
        UIManager.getInstance().setProgress(newProgress);
        Log.i(LOG_TAG, "progress:" + newProgress);
        updateAlpha(view, newProgress);
    }
    
    @Override
    public void onReceivedTitle(WebView view, String title) {
        // mUIManager.onReceivedTitle(view, title);\
        Log.i(LOG_TAG, "title:" + title);

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

    // public void openFileChooser(ValueCallback<Uri> uploadMsg) {
    // mUIManager.setUploadMessage(uploadMsg);
    // Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    // i.addCategory(Intent.CATEGORY_OPENABLE);
    // i.setType("*/*");
    // mUIManager.getMainActivity().startActivityForResult(
    // Intent.createChooser(i,
    // mUIManager.getMainActivity().getString(R.string.FileChooserPrompt)),
    // TintBrowserActivity.ACTIVITY_OPEN_FILE_CHOOSER);
    // }

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
