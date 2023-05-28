package com.example.signalussample1_java.fragment;


import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getX;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getY;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getleftY;
import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getleftX;

import static com.example.signalussample1_java.fragment.HandsResultGlRenderer.getZ;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


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

    float[] leftx;
    float[] lefty;
    private String mCamId;
    private Socket socket;
    private Boolean isConnect=false;

    private DataOutputStream dos;
    private DataInputStream dis;
    private String ip ="172.30.1.62";
    private int port=3000;
    private int cameraframe=0;

    static TextView server_result;
    private Hands hands;
    private CameraInput cameraInput;
    private SolutionGlSurfaceView glSurfaceView;

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


        connect();
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
        hands.setResultListener(result -> {//프레임에 따른 결과가 나올때마다 실행되는 것
            glSurfaceView.setRenderData(result);
            glSurfaceView.requestRender();
            cameraframe++;
            if(cameraframe%25==0) {
                x = getX();
                y = getY();
                lefty=getleftY();
                leftx=getleftX();
                receiveServerData();//서버로 보내고 받고 해보자.
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
                glSurfaceView.getWidth(),
                glSurfaceView.getHeight()
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

    void connect(){//서버에 연결
        Thread connectThread = new Thread(new Runnable() {
            public void run() {
                // Server connection. Socket communication implementation
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                    isConnect=true;
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    server_result.setText("서버 접속에 실패하였습니다. 다시 시도해주세요.");
                    e1.printStackTrace();
                }
            }
        });
        connectThread.start();
        try {
            connectThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established");

    }
    private void receiveServerData(){

        if (socket != null && socket.isConnected()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                            dos = new DataOutputStream(socket.getOutputStream());
                            dis = new DataInputStream(socket.getInputStream());
                            
                            for (int i = 0; i < 21; i++) {
                                dos.writeUTF("," + String.valueOf(x[i]));
                                dos.flush();
                                dos.writeUTF("," + String.valueOf(y[i]));
                                dos.flush();
                            }
                            for (int i = 0; i < 21; i++){
                                dos.writeUTF("," + String.valueOf(leftx[i]));
                                dos.flush();
                                dos.writeUTF("," + String.valueOf(lefty[i]));
                                dos.flush();
                            }
                            Log.w("Buffer", "버퍼 생성 잘됨");

                            try {
                                String line = "";
                                int line2;
                                    //line = (String) dis.readUTF();
                                    line2 = (int) dis.read();
                                    Log.w("서버에서 받아온 값 ", "" + line2);
                                    if (line=="종료") {
                                        socket.close();
                                    }else {
                                        server_result.setText(line);
                                    }


                            } catch (Exception e) {

                            }


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.w("Buffer", "버퍼 생성 잘못됨");
                    }

                }
            });
            thread.start();
    }
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
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();//그냥 바이트로 보내는 것
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

    CompositeDisposable mCompositeDisposable;

    // $FF: synthetic method
    private void changeCamera(){
        try {
            socket.close();
            isConnect=false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cameraInput.close();
        binding.control.removeView(glSurfaceView);
        connect();
        setupStreamingModePipeline();
    }
    public void onDestroyView() {
        Thread sendThread = new Thread() {
        public void run() {
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("끝");
                dos.flush();
                socket.close();

            } catch (Exception e) {
                Log.w("Error", "An error occurred while sending data");
                e.printStackTrace();
            }
        }
    };sendThread.start();
        Log.w("End", "종료");
        cameraInput.close();
        binding.control.removeView(glSurfaceView);
        isConnect=false;
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
}

