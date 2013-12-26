
package tw.supra.anyren;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import tw.supra.anyren.manager.UIManager;

public class AboutDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_view);
        TextView version = (TextView) findViewById(R.id.AboutVersionText);
        version.setText(getVersion());
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        UIManager.getInstance().getGaTracker().activityStart(this);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        UIManager.getInstance().getGaTracker().activityStart(this);
    }
    
    /**
     * Get the current package version.
     * @return The current version.
     */
    private String getVersion() {
        String result = "";     
        try {

            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);

            result = String.format(getString(R.string.AboutVersionText), info.versionName, info.versionCode);

        } catch (NameNotFoundException e) {
            Log.w(AboutDialogActivity.class.toString(), "Unable to get application version: " + e.getMessage());
            result = "Unable to get application version.";
        }

        return result;
    }
}
