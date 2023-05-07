package com.example.signalussample1_java.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.Camera;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.signalussample1_java.R;
import com.example.signalussample1_java.R.id;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.example.signalussample1_java.databinding.FragmentCameraBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J&\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001d"},
        d2 = {"Lcom/example/signalussample1/fragment/cameraFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "body_part", "", "getBody_part", "()Ljava/lang/String;", "setBody_part", "(Ljava/lang/String;)V", "navController", "Landroidx/navigation/NavController;", "getNavController", "()Landroidx/navigation/NavController;", "setNavController", "(Landroidx/navigation/NavController;)V", "onClick", "", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_debug"}
)
public final class cameraFragment extends Fragment implements View.OnClickListener  {
    @NotNull
    private String body_part = "";
    public NavController navController;
    private HashMap _$_findViewCache;
    private static final String TAG = "VideoFragment";
    //Superbrain 대신 원하는 폴더이름을 만들면 됩니다.
    private static final String DETAIL_PATH = "DCIM/SignalUs/";

    FragmentCameraBinding binding;

    // 카메라 광각, 전면, 후면
    private static final String CAM_WHAT = "2";
    private static final String CAM_FRONT = "1";
    private static final String CAM_REAR = "0";

    private String mCamId;

    CameraCaptureSession mCameraCaptureSession;
    CameraDevice mCameraDevice;
    CameraManager mCameraManager;

    Size mVideoSize;
    Size mPreviewSize;
    CaptureRequest.Builder mCaptureRequestBuilder;

    int mSensorOrientation;

    Semaphore mSemaphore = new Semaphore(1);

    HandlerThread mBackgroundThread;
    Handler mBackgroundHandler;

    MediaRecorder mMediaRecorder;

    private String mNextVideoAbsolutePath;
    private boolean mIsRecordingVideo;
    //public Handler handler;
    public static cameraFragment newInstance() {
        return new cameraFragment();
    }
    @NotNull
    public final String getBody_part() {
        return this.body_part;
    }

    public final void setBody_part(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.body_part = var1;
    }

    @NotNull
    public final NavController getNavController() {
        NavController var10000 = this.navController;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navController");
        }

        return var10000;
    }

    public final void setNavController(@NotNull NavController var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.navController = var1;
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TedPermission.create()
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
                .check();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        mCamId = CAM_REAR;
        mCompositeDisposable = new CompositeDisposable();

        String var4 = "";
        Bundle arguments = getArguments();
        if (arguments != null) {
            var4 = arguments.getString("body_part", "");
        }
        body_part = var4;

        return binding.getRoot();
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);


        this.navController = Navigation.findNavController(view);
        TextView var10000 = (TextView)this._$_findCachedViewById(id.translated);

        Intrinsics.checkNotNullExpressionValue(var10000, "translated");

        var10000.setText((CharSequence)this.body_part);

        ((ImageView)this._$_findCachedViewById(id.back_btn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.stt_btn)).setOnClickListener((View.OnClickListener)this);


    }

    public void onClick(@Nullable View v) {

        Integer var2 = v != null ? v.getId() : null;
        int var3 = id.stt_btn;
        NavController var10000;
        if (var2 != null) {
            if (var2 == var3) {
                var10000 = this.navController;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("navController");
                }

                var10000.navigate(R.id.action_cameraFragment_to_voiceRecordFragment);
                return;
            }
        }

        var3 = id.back_btn;
        if (var2 != null) {
            if (var2 == var3) {
                var10000 = this.navController;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("navController");
                }

                var10000.popBackStack();
            }
        }
        switch (var2) {
            case R.id.switchImgBtn:
                switch (mCamId) {
                    case CAM_REAR:
                        mCamId = CAM_FRONT;
                        break;
                    case CAM_FRONT:
                        mCamId = CAM_WHAT;
                        break;
                    case CAM_WHAT:
                        mCamId = CAM_REAR;
                        break;
                }
                closeCamera();
                openCamera(binding.preview.getWidth(), binding.preview.getHeight());
                break;
        }


    }


    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            View var10000 = this.getView();
            if (var10000 == null) {
                return null;
            }

            var2 = var10000.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }
    PermissionListener permission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getContext(), "권한 허가", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getContext(), "권한 거부", Toast.LENGTH_SHORT).show();

        }
    };

    /* PermissionListener permission = new PermissionListener() {
         public void onPermissionGranted() {//권한 허용되면 카메라 실행
             Toast.makeText(getContext(), "권한 허가", Toast.LENGTH_SHORT).show();

             camera = Camera.open();
             camera.setDisplayOrientation(90);
             surfaceView = (SurfaceView)_$_findCachedViewById(R.id.surfaceView);
             surfaceHolder = surfaceView.getHolder();
             surfaceHolder.addCallback(cameraFragment.this);
             surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

         }

         @Override
         public void onPermissionDenied(List<String> deniedPermissions) {
             Toast.makeText(getContext(), "권한 거부", Toast.LENGTH_SHORT).show();

         }

     };

     @Override
     public void surfaceCreated(SurfaceHolder holder) {

     }

     private void refreshCamera(Camera camera) {//카메라를 초기화해주는 작업
         if (surfaceHolder.getSurface() == null) {
             return;
         }

         try {
             camera.stopPreview();
         } catch (Exception e) {
             e.printStackTrace();
         }

         setCamera(camera);
     }

     private void setCamera(Camera cam) {

         camera = cam;

     }


     @Override
     public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//surface의 변화 감지
         refreshCamera(camera);
     }

     @Override
     public void surfaceDestroyed(SurfaceHolder holder) {

     }*/
    public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getActivity() != null;
    }

    @Override
    public void onResume() {
        super.onResume();

        startBackgroundThread();
        if (binding.preview.isAvailable()) {
            openCamera(binding.preview.getWidth(), binding.preview.getHeight());
        } else {
            binding.preview.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }


    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera(binding.preview.getWidth(), binding.preview.getHeight());
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            if(!mIsRecordingVideo) configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();
            mSemaphore.release();
            if (null != binding.preview) {
                configureTransform(binding.preview.getWidth(), binding.preview.getHeight());
            }

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            mSemaphore.release();
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            mSemaphore.release();
            camera.close();
            mCameraDevice = null;
            Activity activity = getActivity();
            assert activity != null;
            activity.finish();
        }
    };


    //카메라 기능 호출
    private void openCamera(int width, int height) {
        Activity activity = getActivity();
        assert activity != null;
        mCameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        try {
            if (!mSemaphore.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            CameraCharacteristics cc = mCameraManager.getCameraCharacteristics(mCamId);
            StreamConfigurationMap scm = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mSensorOrientation = cc.get(CameraCharacteristics.SENSOR_ORIENTATION);
            if (scm == null) {
                throw new RuntimeException("Cannot get available preview/video sizes");
            }

            mVideoSize = chooseVideoSize(scm.getOutputSizes(MediaRecorder.class));
            mPreviewSize = chooseOptimalSize(scm.getOutputSizes(SurfaceTexture.class), width, height, mVideoSize);

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.preview.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            } else {
                binding.preview.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }

            configureTransform(width, height);
            mMediaRecorder = new MediaRecorder();
            mCameraManager.openCamera(mCamId, mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException | SecurityException | NullPointerException | InterruptedException e) {
            e.printStackTrace();
            activity.finish();
        }

    }

    private static Size chooseVideoSize(Size[] choices) {
        for (Size size : choices) {
            // 해상도에 맞게 설정하면 될듯?
            if(size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080){
                return size;
            }
        }
        return choices[choices.length - 1];
    }

    private static Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size ops : choices) {
            if(ops.getHeight() == ops.getWidth() * h / w && ops.getWidth() >= width && ops.getHeight() >= height) {
                bigEnough.add(ops);
            }
        }

        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            return choices[0];
        }
    }


    // 카메라 닫기
    private void closeCamera() {
        try {
            mSemaphore.acquire();
            closePreviewSession();
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mMediaRecorder) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            mSemaphore.release();
        }
    }

    //미리보기 기능
    private void startPreview() {
        if(null == mCameraDevice || !binding.preview.isAvailable() || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();
            SurfaceTexture texture = binding.preview.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            Surface previewSurface = new Surface(texture);
            mCaptureRequestBuilder.addTarget(previewSurface);
            mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mCameraCaptureSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Activity activity = getActivity();
                    assert activity != null;
                    Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (null == mCameraDevice) {
            return;
        }
        try {
            setUpCaptureRequestBuilder(mCaptureRequestBuilder);
            HandlerThread thread = new HandlerThread("CameraPreview");
            thread.start();
            mCameraCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
    }


    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = getActivity();
        if (null == binding.preview || null == mPreviewSize || null == activity) {
            return;
        }
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getWidth(), mPreviewSize.getHeight());

        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth()
            );
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        activity.runOnUiThread(() -> binding.preview.setTransform(matrix));
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closePreviewSession() {
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession.close();
            mCameraCaptureSession = null;
        }
    }

    private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();
    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }

    //영상녹화 설정
    private void setUpMediaRecorder() throws IOException {
        final Activity activity = getActivity();
        if(null == activity) return;

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (mNextVideoAbsolutePath == null || mNextVideoAbsolutePath.isEmpty()) {
            mNextVideoAbsolutePath = getVideoFilePath();
        }
        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        mMediaRecorder.prepare();
    }

    //파일 이름 및 저장경로를 만듭니다.
    private String getVideoFilePath() {

        final File dir = Environment.getExternalStorageDirectory().getAbsoluteFile();
        String path = dir.getPath() + "/" + DETAIL_PATH;
        File dst = new File(path);
        if(!dst.exists()) dst.mkdirs();
        return path + System.currentTimeMillis() + ".mp4";
    }

    //녹화시작
    private void startRecordingVideo() {
        if (null == mCameraDevice || !binding.preview.isAvailable() || null == mPreviewSize) {
            return;
        }
        assert getActivity() != null;

        try {
            closePreviewSession();
            setUpMediaRecorder();
            SurfaceTexture texture = binding.preview.getSurfaceTexture();
            assert texture != null;
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            List<Surface> surfaces = new ArrayList<>();

            Surface previewSurface = new Surface(texture);
            surfaces.add(previewSurface);
            mCaptureRequestBuilder.addTarget(previewSurface);

            Surface recordSurface = mMediaRecorder.getSurface();
            surfaces.add(recordSurface);
            mCaptureRequestBuilder.addTarget(recordSurface);

            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mCameraCaptureSession = session;
                    updatePreview();
                    getActivity().runOnUiThread(() -> {
                        mIsRecordingVideo = true;

                        mMediaRecorder.start();
                    });
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Activity activity = getActivity();
                    if (null != activity) {
                        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }, mBackgroundHandler);
        } catch (CameraAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    //녹화 중지
    private void stopRecordingVideo() {
        mIsRecordingVideo = false;
        mMediaRecorder.stop();
        mMediaRecorder.reset();

        Activity activity = getActivity();
        if (null != activity) {
            Toast.makeText(activity, "Video saved: " + mNextVideoAbsolutePath,
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Video saved: " + mNextVideoAbsolutePath);
            File file = new File(mNextVideoAbsolutePath);
            // 아래 코드가 없으면 갤러리 저장 적용이 안됨.
            if(!file.exists()) file.mkdir();
            getActivity().getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }
        mNextVideoAbsolutePath = null;
        startPreview();
    }
    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    CompositeDisposable mCompositeDisposable;
// 여기까지는 타이머 부분이기 때문에 사용안하셔도 됩니다.

    // $FF: synthetic method
    public void onDestroyView() {
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
}
