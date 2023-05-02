package com.example.signalussample1_java.fragment;

import android.hardware.camera2.*;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.signalussample1_java.MainActivity;
import com.example.signalussample1_java.R;
import com.example.signalussample1_java.R.id;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J&\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00132\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u001d"},
        d2 = {"Lcom/example/signalussample1/fragment/cameraFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "body_part", "", "getBody_part", "()Ljava/lang/String;", "setBody_part", "(Ljava/lang/String;)V", "navController", "Landroidx/navigation/NavController;", "getNavController", "()Landroidx/navigation/NavController;", "setNavController", "(Landroidx/navigation/NavController;)V", "onClick", "", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_debug"}
)
public final class cameraFragment extends Fragment implements View.OnClickListener,SurfaceHolder.Callback  {
    @NotNull
    private String body_part = "";
    public NavController navController;
    private HashMap _$_findViewCache;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean recording = false;
    //public Handler handler;
    TextView textView;
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
        String var4;
        label11: {
            Intrinsics.checkNotNullParameter(inflater, "inflater");
            Bundle var10001 = this.getArguments();
            if (var10001 != null) {
                var4 = var10001.getString("body_part");
                if (var4 != null) {
                    break label11;
                }
            }

            var4 = "";
        }

        this.body_part = var4;
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);

        textView = (TextView)this._$_findCachedViewById(id.Result);
        TextView var10000 = (TextView)this._$_findCachedViewById(id.translated);

        Intrinsics.checkNotNullExpressionValue(var10000, "translated");

        var10000.setText((CharSequence)this.body_part);

        ((ImageView)this._$_findCachedViewById(id.back_btn)).setOnClickListener((View.OnClickListener)this);
        ((ImageView)this._$_findCachedViewById(id.stt_btn)).setOnClickListener((View.OnClickListener)this);


        TedPermission.create()
                .setPermissionListener(permission)
                .setRationaleMessage("녹화를 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO)
                .check();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "녹화가 시작되었습니다.", Toast.LENGTH_SHORT).show();
                try {
                    mediaRecorder = new MediaRecorder();
                    camera.unlock();
                    mediaRecorder.setCamera(camera);
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));//화질을 결정하는 부분
                    mediaRecorder.setOrientationHint(90);
                    mediaRecorder.setOutputFile("/sdcard/test.mp4");//원하는 저장경로로
                    mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());//미리보기 해줌
                    mediaRecorder.prepare();//준비
                    mediaRecorder.start();//시작
                    recording = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    mediaRecorder.release();
                }
            }
        });

    }

    public void onClick(@Nullable View v) {

        Integer var2 = v != null ? v.getId() : null;
        int var3 = 2131231220;
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

        var3 = 2131230827;
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

    // $FF: synthetic method
    public void onDestroyView() {
        super.onDestroyView();
        this._$_clearFindViewByIdCache();
    }
    PermissionListener permission = new PermissionListener() {
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

    }
}
