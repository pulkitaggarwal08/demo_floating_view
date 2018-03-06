package com.demo_floating_view.pulkit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();
    }

    private void findIds() {

        btn_start = (Button) findViewById(R.id.btn_start);
    }

    private void init() {

        btn_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                showChatHead(getApplicationContext(), true);
            }
        });
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showChatHead(getApplicationContext(), false);
        }
    }

    @SuppressLint("NewApi")
    private void showChatHead(Context context, boolean isShowOverlayPermission) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            final Intent intent = new Intent(context, FloatingWindowService.class);
            ContextCompat.startForegroundService(context, intent);
            return;
        }

        if (Settings.canDrawOverlays(context)) {
            final Intent intent = new Intent(context, FloatingWindowService.class);
            ContextCompat.startForegroundService(context, intent);
            return;
        }

        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100;


}
