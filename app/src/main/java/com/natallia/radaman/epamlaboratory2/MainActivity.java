package com.natallia.radaman.epamlaboratory2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * It is the main activity which tries to launch the Application which is protected via custom
 * dangerous permission.
 *
 * @author Natallia Radaman
 * @version 0.1
 */
public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PERMISSION_TO_APP2 = 0;
    private static final String PERMISSION_TO_APP2 = "com.natallia.radaman.epamlab2module2.permission.OPEN_APP2_ACTIVITY";

    private Button btnGoSecondActivity;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoSecondActivity = (Button) findViewById(R.id.buttonToSecondApp);
        mView = (View) findViewById(R.id.layout_main);

        btnGoSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToApp2();
            }
        });
    }

    /**
     * Check the permission for opening the second application.
     * <p>
     * If application doesn't have permission, it creates request for the needed permission.
     * If permission has been already granted, it runs method startApp2().
     * <p>
     * {@link #startApp2()}
     * {@link #requestPermissionToApp2()}
     */
    private void goToApp2() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_TO_APP2) == PackageManager.PERMISSION_GRANTED) {
            startApp2();
        } else {
            requestPermissionToApp2();
        }
    }

    /**
     * Open a new activity in the second application
     */
    private void startApp2() {
        Intent intentToApp2 = new Intent()
                .setAction("com.natallia.radaman.epamlab2module2.APPLICATION_ACTIVITY")
                .addCategory("android.intent.category.DEFAULT");
        startActivity(intentToApp2);
    }

    /**
     * Check permission and open a Dialog or a Snackbar for asking a permission.
     */
    private void requestPermissionToApp2() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION_TO_APP2)) {
            Snackbar.make(mView, R.string.message_permission_app2, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.access, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{PERMISSION_TO_APP2}, REQUEST_PERMISSION_TO_APP2);
                        }
                    }).show();
        } else {
            Snackbar.make(mView, R.string.permission_not_available, Snackbar.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_TO_APP2}, REQUEST_PERMISSION_TO_APP2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION_TO_APP2:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(mView, R.string.activity_access, Snackbar.LENGTH_SHORT).show();
                    startApp2();
                } else {
                    Snackbar.make(mView, R.string.activity_denied, Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
