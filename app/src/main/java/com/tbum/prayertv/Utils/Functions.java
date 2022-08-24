package com.tbum.prayertv.Utils;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.tbum.prayertv.Activities.MainActivity;
import com.tbum.prayertv.Alarms.NotificationHelper;
import com.tbum.prayertv.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {


    public static Dialog loadingDialog;

    public static int currentApiVersion;

    public static void changeStatusBarColor(Context context, int color) {
        Window window = ((Activity) context).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(context, color));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void fullScreenWithNav(Context context) {
        Activity activity = (Activity) context;
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

    public static void changeNavigationBarColor(Context context, int color) {
        Activity activity = (Activity) context;
        activity.getWindow().setNavigationBarColor(activity.getResources().getColor(color));
    }


    public static void showSnackBar(Context context, String message) {
        Activity activity = (Activity) context;
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("Ok", view -> {
                }).setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light)).show();
    }

    public static String getCurrentDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }




    public static Date getCurrentDateTimeX() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);

        Date d = null;
        try {
            d = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return d;
    }

    public static Date getYesterdayDateTimeX() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) - 1));

        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);

        Date d = null;
        try {
            d = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static String getCurentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d-MMM-yy", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getTimeAgo(String time) {
        String enddate = getCurrentDateTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date startDate = null;   // initialize start date
        try {
            startDate = myFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null; // initialize  end date
        try {
            endDate = myFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);


        if (days > 365) {
            return changeDateFormate2(time);
        }

        if (days > 1) {
            return ChangeDateFormate(time);
        }
        if (days == 1) {
            return "1 day ago";
        }

        if (days == 0) {
            if (hours > 1) {
                return hours + " hours ago";
            }
            if (hours == 1) {
                return "1 hour ago";
            }

            if (min > 1) {
                return min + " minutes ago";
            }
            if (min == 1) {
                return "1 minute ago";
            }
        }

        return "now";

    }

    public static String getTimeAgoX(Date time) {
        String enddate = getCurrentDateTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date startDate = null;   // initialize start date
        startDate = time;

        Date endDate = null; // initialize  end date
        try {
            endDate = myFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);

        if (days > 365) {
            return changeDateFormate2X(time);
        }

        if (days > 1) {
            return ChangeDateFormateX(time);
        }
        if (days == 1) {
            return "1 day ago";
        }

        if (days == 0) {
            if (hours > 1) {
                return hours + " hours ago";
            }
            if (hours == 1) {
                return "1 hour ago";
            }

            if (min > 1) {
                return min + " minutes ago";
            }
            if (min == 1) {
                return "1 minute ago";
            }
        }

        return "now";
    }

    public static String ChangeDateFormate(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String ChangeDateFormateX(Date date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd");
        Date date1 = null;
        date1 = date;

        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String changeDateFormate2(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String changeDateFormate2X(Date date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date1 = null;
        date1 = date;

        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }


    public static void setToolbar(Activity activity) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }


    public static void checkNotificationPolicy(Context ctx) {
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            ctx.startActivity(intent);
        }
    }

    public static void checkOverlayPermission(Context ctx) {
        if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.P) && (!Settings.canDrawOverlays(ctx))) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + ctx.getPackageName()));
            ((Activity) ctx).startActivityForResult(intent, 45);
        }
    }

    public static void showNotificationAPI26(Context ctx) {
        NotificationHelper helper;
        Notification.Builder builder;
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(ctx, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        helper = new NotificationHelper(ctx);
        builder = helper.getNotification("title", "body", soundUri, resultPendingIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setShowWhen(true);
        helper.getManager().notify(new Random().nextInt(), builder.build());
    }
}
