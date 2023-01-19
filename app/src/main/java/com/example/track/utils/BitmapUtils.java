package com.example.track.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class BitmapUtils {

    public static Bitmap circleBitmap(Bitmap source) {
        //默认只对宽进行处理
        int width = source.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();    //设置抗锯齿
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return bitmap;
    }
    /**   * 该方法用于图片压缩处理,注意width、height参数的类型必须是float   * */
    /**
     * 放大缩小图片
     *
     * @param bitmap 位图
     * @param w      新的宽度
     * @param h      新的高度
     * @return Bitmap
     */
    public static Bitmap zoom(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * 按比例裁剪图片
     *
     * @param bitmap 位图
     * @param wScale 裁剪宽 0~100%
     * @param hScale 裁剪高 0~100%
     * @return bitmap
     */
    public static Bitmap cropBitmap(Bitmap bitmap, float wScale, float hScale) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int wh = (int) (w * wScale);
        int hw = (int) (h * hScale);

        int retX = (int) (w * (1 - wScale) / 2);
        int retY = (int) (h * (1 - hScale) / 2);

        return Bitmap.createBitmap(bitmap, retX, retY, wh, hw, null, false);
    }
    /**
     * 将图片转成base64
     * @return Base64 String
     */
    public static String bitmapBase64(Bitmap bitmap) {
        String imageString;
        //将图片转换成Base64编码
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return imageString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Base64转Bitmap
     *
     * @param base64 base64数据流
     * @return Bitmap 图片
     */
    public static Bitmap base642Bitmap(String base64) {
        byte[] imageBytes = Base64.decode(base64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return bitmap;
    }
}