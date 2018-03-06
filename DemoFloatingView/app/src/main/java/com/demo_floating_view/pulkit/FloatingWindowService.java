package com.demo_floating_view.pulkit;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo_floating_view.pulkit.floatingStyle.FloatingViewListener;
import com.demo_floating_view.pulkit.floatingStyle.FloatingViewManager;

/**
 * Created by pulkit on 22/12/17.
 */

public class FloatingWindowService extends Service implements FloatingViewListener {

    private static final String TAG = "Floating Window Service";

    private FloatingViewManager mFloatingViewManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        LayoutInflater inflater = LayoutInflater.from(this);
        ImageView iconView = (ImageView) inflater.inflate(R.layout.widget_floating_view, null, false);

        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "okk");
            }
        });

        mFloatingViewManager = new FloatingViewManager(this, this);

        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);

        final FloatingViewManager.Options options = new FloatingViewManager.Options();
//        options.overMargin = (int) (16 * metrics.density);
        mFloatingViewManager.addViewToWindow(iconView, options);

        return START_REDELIVER_INTENT;

    }

    private void setListners() {

//        ivFloatingIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "Click");
//            }
//        });

//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                windowManager.removeView(ll);
//                stopSelf();
//                System.exit(0);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
        Log.d(TAG, "Deleted");
    }

    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (isFinishing) {
            Log.d(TAG, "k");
            destroy();
        } else {
            Log.d(TAG, "O");
        }
    }

    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }

}

