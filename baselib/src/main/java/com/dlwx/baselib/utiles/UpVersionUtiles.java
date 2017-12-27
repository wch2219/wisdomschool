package com.dlwx.baselib.utiles;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * 版本更新
 */

public class UpVersionUtiles {
    private Context ctx;
    private ProgressDialog pd;
    private AlertDialog dialog;

    public UpVersionUtiles(Context ctx){
            this.ctx = ctx;
        }
    public void showVersionDia(final String downUrl) {
        // 点击不可以去掉这个进度条
        dialog = new AlertDialog.Builder(ctx).setTitle("发现新版本，是否更新")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pd = new ProgressDialog(ctx);

                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                        // 点击不可以去掉这个进度条
                        pd.setCanceledOnTouchOutside(false);

                        pd.setMessage("正在下载更新");

                        pd.show();
                        upVersion(downUrl);
                    }
                }).show();
    }


    private void upVersion(String downUrl) {
        int len = 0;
        OkGo.<File>get(downUrl)
                .tag(this)

                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
                        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
                        intentFilter.addDataScheme("package");
                        ctx.registerReceiver(new MyBroadcastReceiver(), intentFilter);
                        // 核心是下面几句代码
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //判断是否是AndroidN以及更高的版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(ctx,
                                    "com.dlwx.onedrop.fileprovider", response.body());
                            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                        } else {
                            intent.setDataAndType(Uri.fromFile(response.body()),
                                    "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
//                        android.os.Process.killProcess(android.os.Process.myPid());
                        ctx.startActivity(intent);
                    }
                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        long totalSize = progress.totalSize;
                        long currentSize = progress.currentSize;
                        pd.setMax((int) totalSize/1024/1024);
                        pd.setProgress((int) currentSize/1024/1024);
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        String url = request.getUrl();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<File> response) {
                        int code = response.code();
                        Log.i("wch",code+"错误码");
                        pd.dismiss();
                        Toast.makeText(ctx, "网络连接失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PackageManager manager = context.getPackageManager();
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                Toast.makeText(context, "安装成功"+packageName, Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                Toast.makeText(context, "卸载成功"+packageName, Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
                String packageName = intent.getData().getSchemeSpecificPart();
                Toast.makeText(context, "替换成功"+packageName, Toast.LENGTH_LONG).show();
            }


        }
    }
}
