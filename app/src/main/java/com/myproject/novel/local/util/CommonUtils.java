package com.myproject.novel.local.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.myproject.novel.R;
import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.ui.main.MainActivity;
import com.myproject.novel.ui.novel.NovelActivity;
import com.myproject.novel.ui.novel.ReadActivity;
import com.myproject.novel.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class CommonUtils {


    public static boolean checkHasNavigationBar(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }


    public static void setFullScreen(final Activity activity, final boolean enable) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        window.setNavigationBarColor(activity.getResources().getColor(R.color.primary_color));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (enable) {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            layoutParams.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        window.setAttributes(layoutParams);
    }

    public static void setFullScreenWithStatusBar(final Activity activity, boolean enable) {

        setFullScreen(activity);

        if (enable) {
            enableLightStatusBar(activity);
        } else {
            clearLightStatusBar(activity);
        }

    }

    public static void setFullScreen(Activity activity) {
        Window window = activity.getWindow();
        hideSystemUI(activity);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    public static void hideSystemUI(Activity activity) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white));
    }

    public static void showSystemUI(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void enableLightStatusBar(Activity activity) {

        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        int systemUiVisibilityFlags = decorView.getSystemUiVisibility();
        systemUiVisibilityFlags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(systemUiVisibilityFlags);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT); // optional

    }

    public static void clearLightStatusBar(Activity activity) {
        int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
        flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
        activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT); // optional
    }

    public static Bitmap makeGradient(Bitmap originalBitmap, int colorStart, int colorEnd) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);

        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height, colorStart, colorEnd, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);
        return updatedBitmap;
    }

    public static void test(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    public static void startActivity(Activity activity, Class<?> className, HashMap<String, String> data) {
        Intent intent = new Intent(activity, className);
        intent.putExtra(UC.CLASS_NAME, className.toString());
        if (data != null) {
            data.forEach(intent::putExtra);
        }
        activity.startActivity(intent);
    }


    public static ArrayList<ChapterModel> reverseArrayList(ArrayList<ChapterModel> arrList) {
        // Arraylist for storing reversed elements
        ArrayList<ChapterModel> revArrayList = new ArrayList<>();
        for (int i = arrList.size() - 1; i >= 0; i--) {
            revArrayList.add(arrList.get(i));
        }
        // Return the reversed arraylist
        return revArrayList;
    }

    public static ShimmerDrawable shimmerEffect() {
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1800)
                .setBaseAlpha(0.7f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        return shimmerDrawable;
    }

    public static HashMap<String,String> buildData(String target, String value) {
        HashMap<String,String> data = new HashMap<>();
        final String mainActivityClass = MainActivity.class.toString().replaceAll("class ", "");
       final String novelActivityClass = NovelActivity.class.toString().replaceAll("class ", "");
       final String readActivityClass = ReadActivity.class.toString().replaceAll("class ", "");
       final String searchActivityClass = SearchActivity.class.toString().replaceAll("class ", "");

        if(target.equals(novelActivityClass)) {
            data.put(UC.NOVEL_ID,value);
        }
        return data;
    }
}



