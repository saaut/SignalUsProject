package com.example.signalussample1_java.fragment;

import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getX;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getY;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getleftX;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getleftY;

import android.Manifest;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.camera.core.ExperimentalUseCaseGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.signalussample1_java.R;
import com.example.signalussample1_java.R.id;
import com.example.signalussample1_java.databinding.FragmentCameraBinding;
//import com.google.mediapipe.solutioncore.CameraInput;
import com.google.mediapipe.solutioncore.SolutionGlSurfaceView;
import com.google.mediapipe.solutions.hands.Hands;
import com.google.mediapipe.solutions.hands.HandsOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@ExperimentalUseCaseGroup @Metadata(
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

    private com.example.signalussample1_java.fragment.CameraInput cameraInput;
    FragmentCameraBinding binding;

    // 카메라 광각, 전면, 후면
    private static final String CAM_WHAT = "2";
    private static final String CAM_FRONT = "1";

    private float[] coords;
    private String mCamId;
    private ServerSocket serverSocket;


    private Boolean isJavaConnect =false;
    private Boolean isPyConnect=true;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String ip ="172.30.1.33";
    private int port=5555;
    private int cameraframe=0;

    private String connect_tag = "connect";

    static TextView server_result;
    private Hands hands;
    //private CameraInput cameraInput;
    private SolutionGlSurfaceView glSurfaceView;
    private static int desiredWidth=16;
    private static int desiredHeight=9;
    Button recordButton;

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
                .setPermissions(android.Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET)
                .check();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false);
        mCamId = CAM_FRONT;//캠 기본 설정은 전면
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
        ((Button)this._$_findCachedViewById(id.recordStartButton)).setOnClickListener((View.OnClickListener)this);

        recordButton=view.findViewById(id.recordStartButton);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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
                    case CAM_FRONT://전면 카메라 사용 중일 경우 후방 카메라 사용
                        mCamId = CAM_WHAT;
                        changeCamera();
                        break;
                    case CAM_WHAT://후방 카메라 사용 중일 경우 전방 카메라 사용
                        mCamId = CAM_FRONT;
                        changeCamera();
                        break;
                }
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
        }var3 = id.recordStartButton;
        if (var2 != null){
            if(var2== var3){
                CharSequence text = recordButton.getText();
                if ("녹화 시작".equals(text)) {
                    connect3(port);
                    recordButton.setText("녹화 중지");
                } else if ("녹화 중지".equals(text)) {
                    sendCloseSignal();
                    recordButton.setText("녹화 시작");
                }
            }
        }

    }

    // mediapipe hands 라이브러리 사용하여 스트리밍 모드의 파이프라인을 설정한다.
    private void setupStreamingModePipeline() {//카메라 입력 프레임을 지속적으로 처리하여, 실시간으로 손을 감지하는 스트리밍 모드용 파이프라인을 설정한다.
        coords = new float[84];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = 0.0f;
        }

        HandsResultGlRenderer renderer = new HandsResultGlRenderer();
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
        //glSurfaceView.setRotation(90.0f);




        // Set the result listener to update the GLSurfaceView with the hands data and request rendering
        hands.setResultListener(result -> {//프레임에 따른 결과가 나올때마다 실행되는 것
            glSurfaceView.setRenderData(result);
            glSurfaceView.requestRender();
            cameraframe++;
            if(cameraframe%6==0) {
                System.arraycopy(getX(), 0, coords, 0, 21);
                System.arraycopy(getY(), 0, coords, 21, 21);
                System.arraycopy(getleftX(), 0, coords, 42, 21);
                System.arraycopy(getleftY(), 0, coords, 63, 21);

                Log.d("cameraframe", "좌표 값"+coords[4]);
                if(isJavaConnect ==true) {
                    sendCoords(coords);
                }
                Log.w("cameraframe", "cameraframe"+cameraframe);
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
        if(mCamId==CAM_FRONT){
            cameraInput.start(
                    getActivity(),
                    hands.getGlContext(),
                    CameraInput.CameraFacing.FRONT,
                    desiredWidth,
                    desiredHeight
            );
        } else if (mCamId==CAM_WHAT) {
            cameraInput.start(
                    getActivity(),
                    hands.getGlContext(),
                    CameraInput.CameraFacing.BACK,
                    glSurfaceView.getWidth(),
                    glSurfaceView.getHeight()
            );
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
            setupStreamingModePipeline();
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


    public String readUTF8 (DataInputStream in) throws IOException {
        int length = in.readInt();//문자열의 길이를 받는다.
        byte[] encoded = new byte[length];//데이터 손실 없이 정확한 길이의 문자열을 받기 위해 bytearray를 생성한다.
        in.readFully(encoded, 0, length);//해당 길이의 bytearray를 받는다.
        return new String(encoded, StandardCharsets.UTF_8);//문자열로 바꾸기 위해 UTF8로 디코딩을 해준다.
    }


    CompositeDisposable mCompositeDisposable;

    // $FF: synthetic method
    private void changeCamera(){    //실질적 사용을 위해서는 오른손 왼손을 반전시켜서 적용하는 코드 필요.
        isJavaConnect =false;
        cameraInput.close();
        binding.control.removeView(glSurfaceView);
        setupStreamingModePipeline();
    }

    private Socket clientSocket;
    private Socket pyClientSocket;

    private void connect3(int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(connect_tag, "Waiting for server connection...");

                    while(true){
                        if (!isJavaConnect) {
                            clientSocket = serverSocket.accept();
                            Log.d(connect_tag, "Connected with Java client.");
                            isJavaConnect = true;
                        } else if (isJavaConnect) {
                            pyClientSocket = serverSocket.accept();
                            Log.d(connect_tag, "Connected with Python client.");
                            isPyConnect = true;
                            Thread clientThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        DataInputStream dis = new DataInputStream(pyClientSocket.getInputStream());

                                        StringBuilder stringBuilder = new StringBuilder();
                                        char delimiter = '\n';
                                        int charValue;
                                        while ((charValue = dis.read()) != -1) {
                                            if (charValue == delimiter) {
                                                break;  // End of string reached
                                            }
                                            stringBuilder.append((char) charValue);
                                        }

                                        final String result = stringBuilder.toString();

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                server_result.setText(result);
                                            }
                                        });

                                        pyClientSocket.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            // start client thread
                            clientThread.start();
                        }
                    }

                } catch (IOException e) {
                    Log.w(connect_tag, "Error waiting for server connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendCoords(float[] coords) {
        Thread sendCoordsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isJavaConnect) {
                    try {
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                        // 배열 길이 전송
                        int length = coords.length;
                        dos.writeInt(length);

                        // 배열 값 전송
                        for (float coord : coords) {
                            dos.writeFloat(coord);
                        }
                        dos.flush();  // 버퍼 비우기

                        Log.d("sendCoords", "Coords 전송 완료");
                    } catch (IOException e) {
                        Log.e("sendCoords", "Coords 전송 중 에러: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    Log.w("sendCoords", "서버에 접속하지 않았습니다.");
                }
            }
        });
        sendCoordsThread.start();
    }
    public void sendCloseSignal(){
        Log.w("sendCoords", "버튼 클릭");
        coords[0]=99;
        Thread sendCloseSignalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (isJavaConnect) {
                    try {
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                        // 배열 길이 전송
                        int length = 1;
                        dos.writeInt(length);

                        // 배열 값 전송
                        dos.writeFloat(99);
                        dos.flush();  // 버퍼 비우기

                        Log.d("sendCoords", "CloseSignal 전송 완료");
                    } catch (IOException e) {
                        Log.e("sendCoords", "CloseSignal 전송 중 에러: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    Log.w("CloseSignal", "서버에 접속하지 않았습니다.");
                }
            }
        });
        sendCloseSignalThread.start();
        isJavaConnect=false;
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDestroyView() {

        try {
            pyClientSocket.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Log.w("End", "종료");
        try{
        cameraInput.close();
        } catch(Exception e){

        }
        try{
        binding.control.removeView(glSurfaceView);
        } catch(Exception e){

        }
        isJavaConnect =false;
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
}