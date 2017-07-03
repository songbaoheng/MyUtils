package com.zckj.wisdomsave.common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;



/**
 * 用户权限管理工具类
 *
 * @author 宋保衡
 * @Copright: Copyright(智存)
 * @date 2017/4/25 17:25
 */
public class PermissionUtil {
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public static final int GETCONTACTCODE = 1;//获取通讯录权限code
    public static final int GETCALLLOGCODE = 2;//获取通话记录权限code
    public static final int PHONESTATUS = 3;//获取电话状态
    public static final int GETFILE = 4;//文件读写权限
    private static String[] perArray;

    public static String[] getAPPPermissList() {
        if (perArray == null) {
            perArray = new String[]{Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.PROCESS_OUTGOING_CALLS
                    };
        }
        return perArray;
    }

    /**判断app是否获取相应权限
     * @param context
     * @return
     */
public static boolean hasAppPermission(Context context){
    boolean  hasPermission=true;
   // if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        perArray=getAPPPermissList();
        for(int i=0;i<perArray.length;i++){
            if(ContextCompat.checkSelfPermission(context,perArray[i])!=PackageManager.PERMISSION_GRANTED){
                hasPermission=false;
                break;
            }
        }
  //  }

    return hasPermission;

}
    /**
     * 判断是否获取此权限
     *
     * @param permission
     * @param appContext
     * @return
     */
    public static boolean checkPermission(String permission, Activity appContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = ActivityCompat.checkSelfPermission(appContext, permission);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                return false;

            }
        }
        return true;
    }


    /**
     * 获取权限dialog
     *
     * @param appContext
     * @param requestCode
     * @return
     */
    public static AlertDialog getPermisssionDialog( final Activity appContext, final int requestCode) {
        return new AlertDialog.Builder(appContext)
                .setMessage("为保证软件正常运行，app需要获取信任权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            appContext.requestPermissions(getAPPPermissList(), requestCode);
                        }
                    }
                })
                .setNegativeButton("拒绝", null)
                .create();
    }



    /**
     * 判断用户权限获取结果
     *
     * @param grantResult
     * @return
     */
    public static boolean permissionResult(int[] grantResult, Context context) {
        boolean getPermission = true;
        for (int i = 0; i < grantResult.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != grantResult[i]) {
                getPermission = false;
                Toast.makeText(context, "申请权限被拒,请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return getPermission;

    }
    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission(context);
        }
    }
    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
           context. startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission(context);
        }
    }
    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }
    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
