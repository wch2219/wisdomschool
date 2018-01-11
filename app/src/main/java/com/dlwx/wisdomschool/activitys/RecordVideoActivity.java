package com.dlwx.wisdomschool.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.wisdomschool.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 录制视频
 */
public class RecordVideoActivity extends BaseActivity implements
        SurfaceHolder.Callback {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.sfv_view)
    SurfaceView sfvView;
    @BindView(R.id.iv_direction)
    CheckBox ivDirection;
    @BindView(R.id.cb_start)
    CheckBox cbStart;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_upvideo)
    TextView tv_upvideo;
    private File outfilePath;//录制输出路径
    private MediaRecorder mediaRecorder;
    private long startmilliTime;
    private File videoFile;
    private int maxMillTime = 60;//最大时间
    private Camera mCamera;
    private SurfaceHolder holder;
    private static final int FRONT = 1;//前置摄像头标记
    private static final int BACK = 2;//后置摄像头标记
    private int currentCameraType = -1;//当前打开的摄像头标记

    @Override
    protected void initView() {
        setContentView(R.layout.activity_record_video);
        ButterKnife.bind(this);
        outfilePath = createFile();
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        holder = sfvView.getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        holder = surfaceHolder;
        if (mCamera == null) {
            mCamera = Camera.open();//开启相机，可以放参数 0 或 1，分别代表前置、后置摄像头，默认为 0
            try {
                Camera.Parameters params = mCamera.getParameters();
                params.set("orientation", "portrait");
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mCamera.setParameters(params);
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);//整个程序的核心，相机预览的内容放在 holder
                mCamera.cancelAutoFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        holder = surfaceHolder;
        mCamera.startPreview();//该方法只有相机开启后才能调用
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        holder = surfaceHolder;
        if (mCamera != null) {
            mCamera.release();//释放相机资源
            mCamera = null;
        }
    }

    @Override
    protected void initData() {
        tvTitle.setText("录制视频");
        initTabBar(toolBar);

    }

    @Override
    protected void initListener() {
        cbStart.setOnCheckedChangeListener(changeListener);
        ivDirection.setOnCheckedChangeListener(backListener);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                start();
            } else {
                stop();
            }
        }
    };
    private CompoundButton.OnCheckedChangeListener backListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                currentCameraType = BACK;
            } else {
                currentCameraType = FRONT;
            }
            try {
                changeCamera();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void start() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long l = System.currentTimeMillis();
            String format = simpleDateFormat.format(new Date(l));
            videoFile = new File(outfilePath, format + ".mp4");
            wch(videoFile);
            mCamera.unlock();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.reset();
            mediaRecorder.setCamera(mCamera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//采集声音
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//采集图像
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//声音格式
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);//图像编码格式
            //设置录制的视频编码比特率
            mediaRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
            mediaRecorder.setVideoSize(640, 480);
            mediaRecorder.setVideoFrameRate(50);
            mediaRecorder.setOutputFile(videoFile.getAbsolutePath());
            // 判断是前置摄像头还是后置摄像头 然后设置视频旋转 如果不加上 后置摄像头没有问题 但是前置摄像头录制的视频会导致上下翻转
            if (currentCameraType == FRONT) {
                mediaRecorder.setOrientationHint(180);
            } else {
                mediaRecorder.setOrientationHint(90);
            }
            mediaRecorder.setMaxDuration(maxMillTime * 1000);
            mediaRecorder.setPreviewDisplay(holder.getSurface());
            mediaRecorder.prepare();
            isRecorder = true;
            ivDirection.setVisibility(View.GONE);
            mediaRecorder.start();
            progressBar.setMax(maxMillTime * 1000);
            startanimation(cbStart);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startmilliTime = SystemClock.currentThreadTimeMillis();
        StartProgress();

    }

    public void StartProgress() {
        //开辟新的Thread用于定期刷新SeekBar;
        DelayThread dThread = new DelayThread(100);
        dThread.start();
    }



    @OnClick(R.id.tv_upvideo)
    public void onViewClicked() {
        Intent intent = new Intent(ctx,MyRecordVideoListActivity.class);

        startActivityForResult(intent,1);

    }

    public class DelayThread extends Thread {
        int milliseconds;

        public DelayThread(int i) {
            milliseconds = i;
        }

        public void run() {
            while (isRecorder) {
                try {
                    curmilliTime = curmilliTime + 100;
                    sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private boolean isRecorder;
    private int curmilliTime = 0;

    private void stop() {
        if (mediaRecorder != null) {
            isRecorder = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Uri localUri = Uri.fromFile(videoFile);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            sendBroadcast(localIntent);
            Intent intent = new Intent();
            intent.putExtra("vodeofile", videoFile + "");
            setResult(1, intent);
            finish();
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            String bpath = "file://" + videoFile;
//            intent.setDataAndType(Uri.parse(bpath), "video/*");
//            startActivity(intent);
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressBar.setProgress(curmilliTime);
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            Date date = new Date(curmilliTime);
            String tiem = format.format(date);
            tvTime.setText(tiem);
            if (curmilliTime == maxMillTime) {
                stop();
            }
        }
    };

    private File createFile() {
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath();
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
        return sd;
    }

    @Override
    protected void onDestroy() {
        isRecorder = false;
        if (mediaRecorder != null) {

            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        super.onDestroy();
    }

    private void changeCamera() throws IOException {
        mCamera.stopPreview();
        mCamera.release();
        if (currentCameraType == FRONT) {
            mCamera = openCamera(BACK);
        } else if (currentCameraType == BACK) {
            mCamera = openCamera(FRONT);
        }
        Camera.Parameters params = mCamera.getParameters();
        params.set("orientation", "portrait");
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(params);
        mCamera.cancelAutoFocus();
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(holder);
        mCamera.startPreview();
    }

    @SuppressLint("NewApi")
    private Camera openCamera(int type) {
        int frontIndex = -1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontIndex = cameraIndex;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backIndex = cameraIndex;
            }
        }

        currentCameraType = type;
        if (type == FRONT && frontIndex != -1) {
            return Camera.open(frontIndex);
        } else if (type == BACK && backIndex != -1) {
            return Camera.open(backIndex);
        }
        return null;
    }

    private void startanimation(View view) {
        AnimationDrawable drawable = (AnimationDrawable) view.getBackground();

        if (!drawable.isRunning()) {

            drawable.start();
        } else {
            drawable.stop();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (requestCode){
                    case 1:
                        if (data == null) {
                            return;
                        }
                        String videofile = data.getStringExtra("videofile");
                        Intent intent = new Intent();
                        intent.putExtra("videofile",videofile);
                        setResult(1,intent);
                        finish();
                        break;
                }
    }
}
