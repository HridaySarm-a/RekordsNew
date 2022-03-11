package com.main.rekordsnew.Objects;
import android.app.Activity;
import androidx.core.content.res.ResourcesCompat;
import com.main.rekordsnew.R;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class MrToast {

    public static void showSuccess(Activity context, String title, String message) {
        MotionToast.Companion.createColorToast(context,
                title,
                message,
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, R.font.helvetica_regular));
    }

    public static void showError(Activity context, String title, String message) {
        MotionToast.Companion.createColorToast(context,
                title,
                message,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, R.font.helvetica_regular));
    }

    public static  void showWarning(Activity context, String title, String message) {
        MotionToast.Companion.createColorToast(context,
                title,
                message,
                MotionToastStyle.WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, R.font.helvetica_regular));
    }

    public static void showDelete(Activity context, String title, String message) {
        MotionToast.Companion.createColorToast(context,
                title,
                message,
                MotionToastStyle.DELETE,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, R.font.helvetica_regular));
    }

    public static void showInfo(Activity context, String title, String message) {
        MotionToast.Companion.createColorToast(context,
                title,
                message,
                MotionToastStyle.INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(context, R.font.helvetica_regular));
    }

}
