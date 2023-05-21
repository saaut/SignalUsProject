package com.example.signalussample1_java.fragment;


import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getX;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getY;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getZ;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.*;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.example.signalussample1_java.databinding.FragmentCameraBinding;
import com.google.mediapipe.solutioncore.CameraInput;
import com.google.mediapipe.solutioncore.SolutionGlSurfaceView;
import com.google.mediapipe.solutions.hands.Hands;
import com.google.mediapipe.solutions.hands.HandsOptions;
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

    FragmentCameraBinding binding;

    // 카메라 광각, 전면, 후면
    private static final String CAM_WHAT = "2";
    private static final String CAM_FRONT = "1";
    private static final String CAM_REAR = "0";
    float[] x ;
    float[] y ;
    float[] z ;

    private String mCamId;
    private Handler mHandler;//TCP connection을 thread상에서 진행할 것이기 때문에 Handler를 선언해준다.
    private Socket socket;
    private byte[] sendByte;


    private DataOutputStream dos;
    private DataInputStream dis;
    private String ip ="172.30.1.51";
    private int port=3000;

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
    static TextView server_result;
    private Hands hands;
    private CameraInput cameraInput;
    private SolutionGlSurfaceView glSurfaceView;


    private boolean mIsRecordingVideo;
    public Handler handler;
    public static cameraFragment newInstance() {
        return new cameraFragment();
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
                .setPermissions(android.Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET)
                .check();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        mCamId = CAM_FRONT;
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
        server_result = (TextView)this._$_findCachedViewById(id.server_result);


        Intrinsics.checkNotNullExpressionValue(var10000, "translated");


        var10000.setText((CharSequence)this.body_part);

        ((ImageView)this._$_findCachedViewById(id.back_btn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.stt_btn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.switchImgBtn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.imageView12)).setOnClickListener((View.OnClickListener)this);



        connect();//서버 연결


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

        var3 = id.switchImgBtn;
        if(var2!=null) {
            if (var2 == var3) {
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
                //openCamera(binding.preview.getWidth(), binding.preview.getHeight());
            }
        }
        var3 = id.imageView12;
        if(var2!=null){
            if(var2==var3){

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

    }

    // mediapipe hands 라이브러리 사용하여 스트리밍 모드의 파이프라인을 설정한다.
    private void setupStreamingModePipeline() {//카메라 입력 프레임을 지속적으로 처리하여, 실시간으로 손을 감지하는 스트리밍 모드용 파이프라인을 설정한다.
        // Initialize the Hands pipeline
        hands = new Hands(//손 감지
                getContext(),
                HandsOptions.builder()
                        .setStaticImageMode(false)//정적 이미지 모드 비활성화
                        .setMaxNumHands(2)//탐지할 최대 손 수 설정
                        .setRunOnGpu(true)//GPU가속 활성화
                        .build()
        );
        hands.setErrorListener((message, e) -> Log.e("TAG", "MediaPipe Hands error: " + message));

        // Create the camera input and set the new frame listener to send frames to the Hands pipeline
        cameraInput = new CameraInput(getActivity());//카메라 입력 프레임에 액세스 할 수 있다.
        cameraInput.setNewFrameListener(hands::send);//프레임수신기. 카메라 프레임을 Hands개체로 전송하도록.

        // Create the GLSurfaceView for rendering the results
        //결과 수신기는 손 감지 프로세스의 출력을 수신하도록 설정됩니다. 결과 데이터는 렌더링을 위해 GL 표면 보기로 전달됩니다.
        //requestRender() 메서드는 GL 표면 뷰에서 렌더링을 트리거하기 위해 호출됩니다.
        glSurfaceView = new SolutionGlSurfaceView(getContext(), hands.getGlContext(), hands.getGlMajorVersion());
        glSurfaceView.setSolutionResultRenderer(new HandsResultGlRenderer());
        glSurfaceView.setRenderInputImage(true);

        // Set the result listener to update the GLSurfaceView with the hands data and request rendering
        hands.setResultListener(result -> {
            glSurfaceView.setRenderData(result);
            glSurfaceView.requestRender();
            x = getX();
            y = getY();
            z = getZ();


            for(int i=0;i<21;i++) {
                sendToServer(x[i]);
                Log.d("", "cameraFragment쪽에서 받아온 포인트[" + i + "] 값 : x " + String.valueOf(x[i]) + " y " + String.valueOf(y[i]) + " z " + String.valueOf(z[i]));
            }

        });

        glSurfaceView.post(this::startCamera);

        // Add the GLSurfaceView to the FrameLayout defined in activity_main.xml





        binding.control.removeAllViewsInLayout();
        binding.control.addView(glSurfaceView);
        glSurfaceView.setVisibility(View.VISIBLE);
        binding.control.requestLayout();
    }

    private void startCamera() {
        cameraInput.start(
                getActivity(),
                hands.getGlContext(),
                CameraInput.CameraFacing.FRONT,
                glSurfaceView.getWidth(),
                glSurfaceView.getHeight()
        );
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
            setupStreamingModePipeline();
            glSurfaceView.post(new Runnable() {
                @Override
                public void run() {
                    //startCamera();
                }
            });
            glSurfaceView.setVisibility(View.VISIBLE);


        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getContext(), "권한 거부", Toast.LENGTH_SHORT).show();

        }
    };

    public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getActivity() != null;
    }

    @Override
    public void onResume() {
        super.onResume();

        startBackgroundThread();
        if (binding.preview.isAvailable()) {
            //openCamera(binding.preview.getWidth(), binding.preview.getHeight());
        } else {
            binding.preview.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }


    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //openCamera(binding.preview.getWidth(), binding.preview.getHeight());
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
            // 해상도에 맞게 설정
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
            Toast.makeText(getContext(), "CameraDeviceIsNotAvailable", Toast.LENGTH_SHORT).show();
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

    void connect(){//서버에 연결

        mHandler = new Handler();
        Thread connectThread = new Thread() {
            public void run() {
                // Server connection. Socket communication implementation
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    dos.writeUTF("Signal US에서 서버로 연결요청");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("Buffer", "버퍼 생성 잘못됨");
                }

                Log.w("Buffer", "버퍼 생성 잘됨");

                    try {
                        String line = "";
                        int line2;
                        while (true) {
                            //line = (String) dis.readUTF();
                            line2 = (int) dis.read();
                            //Log.w("서버에서 받아온 값 ", "" + line);
                            //Log.w("서버에서 받아온 값 ", "" + line2);

                            if(line2 > 0) {
                                Log.w("------서버에서 받아온 값 ", "" + line2);
                                dos.writeUTF("하나 받았습니다. : " + line2);
                                server_result.setText(line2);
                                dos.flush();
                            }
                            if(line2 == 99) {
                                Log.w("------서버에서 받아온 값 ", "" + line2);
                                socket.close();

                                break;
                            }
                        }
                    } catch (Exception e) {

                    }


            }
        };
        connectThread.start();
        try {
            connectThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established");

    }
    private void sendToServer(byte[] bytes){
        Thread sendThread = new Thread() {
            public void run() {
                try {
                    dos.writeUTF(Integer.toString(bytes.length)); // Send the length of the byte array as a string
                    dos.flush();

                    dos.write(bytes); // Send the byte array
                    dos.flush();

                    String result = readUTF8(dis); // Read the response from the server

                    socket.close();
                } catch (Exception e) {
                    Log.w("Error", "An error occurred while sending data");
                    e.printStackTrace();
                }
            }
        };
        sendThread.start();
        try {
            sendThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Data sent successfully");
    }
    private void sendToServer(float data){
        Thread sendThread = new Thread()
        {
            public void run() {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    DataOutputStream dosTemp = new DataOutputStream(baos);
                    dosTemp.writeFloat(data);
                    dosTemp.flush();

                    byte[] byteData = baos.toByteArray();

                    dos.writeUTF(Integer.toString(byteData.length)); // Send the length of the byte array as a string
                    dos.flush();

                    dos.write(byteData); // Send the byte array
                    dos.flush();

                    String result = readUTF8(dis); // Read the response from the server

                    socket.close();
                } catch (Exception e) {
                    Log.w("Error", "An error occurred while sending data");
                    e.printStackTrace();
                }
            }
        };
        sendThread.start();
        try {
            sendThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Data sent successfully");
    }
    public String readUTF8 (DataInputStream in) throws IOException {
        int length = in.readInt();//문자열의 길이를 받는다.
        byte[] encoded = new byte[length];//데이터 손실 없이 정확한 길이의 문자열을 받기 위해 bytearray를 생성한다.
        in.readFully(encoded, 0, length);//해당 길이의 bytearray를 받는다.
        return new String(encoded, StandardCharsets.UTF_8);//문자열로 바꾸기 위해 UTF8로 디코딩을 해준다.
    }






    //캡처와 전송시작
    private void startCaptureImg() {
        if (null == mCameraDevice || !binding.preview.isAvailable() || null == mPreviewSize) {//카메라가 사용할 수 있는 상태인지 확인
            Log.e(TAG, "mCameraDevice is null, return");
            return;
        }
        Toast.makeText(getContext(), "이미지가져오기 실행", Toast.LENGTH_SHORT).show();

        assert getActivity() != null;//activity와 fragment가 잘 attach되어있고, 아직 destroy되지 않았다는 뜻
        SurfaceTexture texture = binding.preview.getSurfaceTexture();//Preview texture과 관련된 surfacetexture를 찾는다.

        assert texture !=null;//Preview texture를 사용할 수 있는 지 확인
        try {
            int width = 640;
            int height = 480;

            assert texture != null;
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);//JPEG 형식으로 캡처된 이미지를 수신할 ImageReader 인스턴스 생성
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(texture));


            final CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);//카메라실행을 위한 세팅. 카메라 캡처 요청에 대한 제어 모드를 자동으로 설정한다.


            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {//이미지를 사용할 수 있을 때 트리거되는 OnImageAvailableListener를 만든다.
                @Override
                public void onImageAvailable(ImageReader reader) {//readerListender 내부에서 캡처된 이미지를 획득
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        Toast.makeText(getContext(), "바이트 데이터 얻기 성공", Toast.LENGTH_SHORT).show();

                        sendImageToServer(bytes); // Call your method to send the byte data to the server
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }
            };

            HandlerThread thread = new HandlerThread("CameraPicture");
            thread.start();
            final Handler backgroudHandler = new Handler(thread.getLooper());
            reader.setOnImageAvailableListener(readerListener, backgroudHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session,//캡처 완료 시 서버로 전송
                                               CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    startPreview();
                }

            };

            mCameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, mBackgroundHandler);



        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void sendImageToServer(byte[] imageData) {
        // Implement your networking code here to send the byte data to the server
        // This can involve using libraries such as HttpURLConnection, OkHttp, Retrofit, etc.
        // Construct an HTTP request, set headers and parameters, and attach the byte data
        // to the request body before sending it to the server.
        sendToServer(imageData);
        Toast.makeText(getContext(), "서버로 이미지 전송 시도", Toast.LENGTH_SHORT).show();
    }

    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    CompositeDisposable mCompositeDisposable;

    // $FF: synthetic method
    public void onDestroyView() {
        stopBackgroundThread();
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
}

