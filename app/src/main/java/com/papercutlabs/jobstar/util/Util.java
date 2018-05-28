package com.papercutlabs.jobstar.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.papercutlabs.jobstar.R;
import com.papercutlabs.jobstar.constant.StaticConstant;
import com.papercutlabs.jobstar.model.UserClass;

import java.io.IOException;

/**
 * Created by knowalluser on 07-04-2018.
 */

public class Util {

    private static String USERCLASS = "USERCLASS";
    private static final String PREF_NAME = "MohanOverseasPrefs";

    /**
     * To check Internet Connection
     */
    public static boolean isInternetAvailable(Context context) {

        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conManager.getActiveNetworkInfo();
        if ((i == null) || (!i.isConnected()) || (!i.isAvailable())) {

            return false;
        }
        return true;
    }

    /**
     * Hide Soft Keyboard
     **/
    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * Show Soft Keyboard
     **/
    public static void showSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ;
    }
    /**
     * This method checks wether the provided email is valid or not.
     */
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    /**
     * Show log
     *
     * @param type       of log 0 for info,1 error,2 for verbosa
     * @param logtitle
     * @param logcontent
     * @return
     */
    public static void printLog(int type, String logtitle, String logcontent) {

        switch (type) {
            case 0:
                Log.e(logtitle, logcontent + "");
                break;
            case 1:
                Log.e(logtitle, logcontent + "");
                break;
            case 2:
                Log.e(logtitle, logcontent + "");
                break;

            default:
                Log.e(logtitle, logcontent + "");
                break;
        }
    }

    /**
     * show global alert message
     *
     * @param context   application context
     * @param title     alert title
     * @param btn_title alert click button name
     * @param msg       alert message
     */
    public static void alertMessage(Context context, String title, String btn_title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg).setCancelable(false).setPositiveButton(btn_title, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Shows custom alert dialog with OK option.
     **/
    public static void showMessageWithOk(final Activity mContext, final String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setMessage(message);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });
    }

    /**
     * Shows custom alert dialog with OK option and a callback
     **/
    public static void showCallBackMessageWithOkCallback(final Context mContext, final String message,
                                                         final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onSubmit();
                    }
                });

                alert.show();
                //alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(neededColor);
            }
        });
    }

    /**
     * Shows custom alert dialog with OK and cancel
     **/
    public static void showCallBackMessageWithOkCancel(final Context mContext, final String message,
                                                       final AlertDialogCallBack callBack) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            public void run() {

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle(R.string.app_name);
                alert.setCancelable(false);
                alert.setMessage(message);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onSubmit();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callBack.onCancel();
                    }
                });
                alert.show();
            }
        });
    }

    /**
     * Show Toast message
     */
    public static void initToast(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Saving UserClass details
     **/
    public static void saveUserClass(final Context mContext, UserClass userClass) {
        SharedPreferences KnwEduPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = KnwEduPrefs.edit();
        try {
            prefsEditor.putString(USERCLASS, ObjectSerializer.serialize(userClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefsEditor.commit();
    }

    /**
     * Fetching UserClass details
     **/
    public static UserClass fetchUserClass(final Context mContext) {
        SharedPreferences KnwEduPrefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        UserClass userClass = null;
        String serializeOrg = KnwEduPrefs.getString(USERCLASS, null);
        try {
            if (serializeOrg != null) {
                userClass = (UserClass) ObjectSerializer.deserialize(serializeOrg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userClass;
    }
    //check if choose file is image or video
    @SuppressLint("DefaultLocale")
    public static int checkFileExtention(Context context,Uri selectedFile){
        int type = -1;
        try{
            ContentResolver cR = context.getContentResolver();
            String extention = cR.getType(selectedFile);


            if(extention!=null){
                //checking for  extention
                for (String extensionval : StaticConstant.fileTypes)
                {
                    if (extention.toLowerCase().contains(extensionval))
                    {
                        type = 1;
                        break;
                    }
                }
            }else{
                String extention1 = selectedFile.getPath();
                //checking for image extention
                for (String extensionval :  StaticConstant.fileTypes)
                {
                    if (extention1.toLowerCase().contains(extensionval))
                    {
                        type = 1;
                        break;
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return type;
    }

}
