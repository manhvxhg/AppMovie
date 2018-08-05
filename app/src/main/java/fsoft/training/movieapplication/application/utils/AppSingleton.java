package fsoft.training.movieapplication.application.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import fsoft.training.movieapplication.constant.Constants;
import fsoft.training.movieapplication.data.sqlite.FavouriteHelper;
import fsoft.training.movieapplication.domain.model.dto.moviedetail.MovieDetailDto;

/**
 * Created by ManhND16 on 9/22/2017.
 */

public class AppSingleton extends Application {
    ////////////////////////////////////////////////////////////////////////////
    // instance fields
    ////////////////////////////////////////////////////////////////////////////
    private static AppSingleton singleton;
    public static boolean typeLayout;



    public static String titleActionBar;
    public static MovieDetailDto movieDetailSingleton = null;
    public static boolean isChangelayout = false;
    public static boolean isClickDetail = false;
    ////////////////////////////////////////////////////////////////////////////
    // public method
    ////////////////////////////////////////////////////////////////////////////
    public static AppSingleton getInstance() {
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static void sendDataCount(Context context, FavouriteHelper favouriteHelper) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.COUNT");
        intent.putExtra(Constants.FLAG_ACTION_BROADCAST_COUNT_FAVOURITE, favouriteHelper.getAllFavourite().size() + "");
        Log.d("SEND_BROADCAST", "sendDataCount: " + favouriteHelper.getAllFavourite().size());
        context.sendBroadcast(intent);
    }
    ////////////////////////////////////////////////////////////////////////////
    // private method
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    // inner class
    ////////////////////////////////////////////////////////////////////////////


}
