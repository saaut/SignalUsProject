package com.example.signalussample1_java.fragment;

import android.opengl.GLES20;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import com.google.common.collect.UnmodifiableIterator;
import com.google.mediapipe.formats.proto.ClassificationProto;
import com.google.mediapipe.formats.proto.LandmarkProto;
import com.google.mediapipe.solutioncore.ImageSolutionResult;
import com.google.mediapipe.solutioncore.ResultGlRenderer;
import com.google.mediapipe.solutions.hands.Hands;
import com.google.mediapipe.solutions.hands.HandsResult;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u0000 \u001e2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0003J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010\u0010\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J \u0010\u0014\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u0019\u001a\u00020\nJ\u001a\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001c\u001a\u00020\u000fH\u0016J\b\u0010\u001d\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"},
        d2 = {"Lcom/example/kotlintestdeleteme/HandsResultGlRenderer;", "Lcom/google/mediapipe/solutioncore/ResultGlRenderer;", "Lcom/google/mediapipe/solutions/hands/HandsResult;", "()V", "colorHandle", "", "positionHandle", "program", "projectionMatrixHandle", "drawCircle", "", "x", "", "y", "colorArray", "", "drawConnections", "handLandmarkList", "", "Lcom/google/mediapipe/formats/proto/LandmarkProto$NormalizedLandmark;", "drawHollowCircle", "loadShader", "type", "shaderCode", "", "release", "renderResult", "result", "projectionMatrix", "setupRendering", "Companion", "KotlinTestDELETEME.app.main"}
)
public final class HandsResultGlRenderer implements ResultGlRenderer {
    private int program;
    private int positionHandle;
    private int projectionMatrixHandle;

    static float[] x=new float[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static float[] y=new float[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};;
    static float[] z=new float[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};;
    private int colorHandle;
    private static final String TAG = "HandsResultGlRenderer";
    private static final float[] LEFT_HAND_CONNECTION_COLOR = new float[]{0.2F, 1.0F, 0.2F, 1.0F};
    private static final float[] RIGHT_HAND_CONNECTION_COLOR = new float[]{1.0F, 0.2F, 0.2F, 1.0F};
    private static final float CONNECTION_THICKNESS = 25.0F;
    private static final float[] LEFT_HAND_HOLLOW_CIRCLE_COLOR = new float[]{0.2F, 1.0F, 0.2F, 1.0F};
    private static final float[] RIGHT_HAND_HOLLOW_CIRCLE_COLOR = new float[]{1.0F, 0.2F, 0.2F, 1.0F};
    private static final float HOLLOW_CIRCLE_RADIUS = 0.01F;
    private static final float[] LEFT_HAND_LANDMARK_COLOR = new float[]{1.0F, 0.2F, 0.2F, 1.0F};
    private static final float[] RIGHT_HAND_LANDMARK_COLOR = new float[]{0.2F, 1.0F, 0.2F, 1.0F};
    private static final float LANDMARK_RADIUS = 0.008F;
    private static final int NUM_SEGMENTS = 120;
    private static final String VERTEX_SHADER = "uniform mat4 uProjectionMatrix;\nattribute vec4 vPosition;\nvoid main() {\n  gl_Position = uProjectionMatrix * vPosition;\n}";
    private static final String FRAGMENT_SHADER = "precision mediump float;\nuniform vec4 uColor;\nvoid main() {\n  gl_FragColor = uColor;\n}";
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker)null);

    private final int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void setupRendering() {
        this.program = GLES20.glCreateProgram();
        int vertexShader = this.loadShader(35633, "uniform mat4 uProjectionMatrix;\nattribute vec4 vPosition;\nvoid main() {\n  gl_Position = uProjectionMatrix * vPosition;\n}");
        int fragmentShader = this.loadShader(35632, "precision mediump float;\nuniform vec4 uColor;\nvoid main() {\n  gl_FragColor = uColor;\n}");
        GLES20.glAttachShader(this.program, vertexShader);
        GLES20.glAttachShader(this.program, fragmentShader);
        GLES20.glLinkProgram(this.program);
        this.positionHandle = GLES20.glGetAttribLocation(this.program, "vPosition");
        this.projectionMatrixHandle = GLES20.glGetUniformLocation(this.program, "uProjectionMatrix");
        this.colorHandle = GLES20.glGetUniformLocation(this.program, "uColor");
    }

    public void renderResult(@Nullable HandsResult result, @NotNull float[] projectionMatrix) {//HandsResult, 렌더링에 사용되는 플로트 배열 projectMatrix
        Intrinsics.checkNotNullParameter(projectionMatrix, "projectionMatrix");
        if (result != null) {//HandsResult가 null이 아니면 동작
            GLES20.glUseProgram(this.program);//지정된 OpenCL 프로그램 활성화
            GLES20.glUniformMatrix4fv(this.projectionMatrixHandle, 1, false, projectionMatrix, 0);//투명 매트릭스 균일하게 설정
            GLES20.glLineWidth(25.0F);//도면 작업에 사용할 선 폭을 설정
            int numHands = result.multiHandLandmarks().size();//HandsResult의 포인트 크기
            int i = 0;

            for(int var5 = numHands; i < var5; ++i) {
                Object var10000 = result.multiHandedness().get(i);
                Intrinsics.checkNotNullExpressionValue(var10000, "result.multiHandedness()[i]");
                boolean isLeftHand = Intrinsics.areEqual(((ClassificationProto.Classification)var10000).getLabel(), "Left");//현재 손의 핸들을 검색하여 왼쪽 손인지 오른쪽 손인지 확인한다.
                Object var10001 = result.multiHandLandmarks().get(i);
                Intrinsics.checkNotNullExpressionValue(var10001, "result.multiHandLandmarks()[i]");
                List var13 = ((LandmarkProto.NormalizedLandmarkList)var10001).getLandmarkList();
                Intrinsics.checkNotNullExpressionValue(var13, "result.multiHandLandmarks()[i].landmarkList");
                this.drawConnections(var13, isLeftHand ? LEFT_HAND_CONNECTION_COLOR : RIGHT_HAND_CONNECTION_COLOR);//랜드마크 그리기 호출(색상과 함께)
                int ind = 0;
                var10000 = result.multiHandLandmarks().get(i);
                Intrinsics.checkNotNullExpressionValue(var10000, "result.multiHandLandmarks()[i]");
                List var12 = ((LandmarkProto.NormalizedLandmarkList)var10000).getLandmarkList();
                Intrinsics.checkNotNullExpressionValue(var12, "result.multiHandLandmarks()[i].landmarkList");

                for(int var8 = ((Collection)var12).size(); ind < var8; ++ind) {
                    var10000 = result.multiHandLandmarks().get(i);
                    Intrinsics.checkNotNullExpressionValue(var10000, "result.multiHandLandmarks()[i]");
                    LandmarkProto.NormalizedLandmark lm = (LandmarkProto.NormalizedLandmark)((LandmarkProto.NormalizedLandmarkList)var10000).getLandmarkList().get(ind);
                    StringBuilder var14 = (new StringBuilder()).append("LandMark[").append(ind).append("] | x : ");
                    Intrinsics.checkNotNullExpressionValue(lm, "lm");
                    x[ind]= lm.getX();
                    y[ind]= lm.getY();
                    z[ind]= lm.getZ();
                    Log.d("HandsResultGlRenderer", var14.append(x[ind]).append(", y : ").append(y[ind]).append(", z : ").append(z[ind]).toString());

                }

                var10000 = result.multiHandLandmarks().get(i);
                Intrinsics.checkNotNullExpressionValue(var10000, "result.multiHandLandmarks()[i]");
                Iterator var11 = ((LandmarkProto.NormalizedLandmarkList)var10000).getLandmarkList().iterator();

                while(var11.hasNext()) {
                    LandmarkProto.NormalizedLandmark landmark = (LandmarkProto.NormalizedLandmark)var11.next();
                    Intrinsics.checkNotNullExpressionValue(landmark, "landmark");


                    this.drawCircle(landmark.getX(), landmark.getY(), isLeftHand ? LEFT_HAND_LANDMARK_COLOR : RIGHT_HAND_LANDMARK_COLOR);
                    this.drawHollowCircle(landmark.getX(), landmark.getY(), isLeftHand ? LEFT_HAND_HOLLOW_CIRCLE_COLOR : RIGHT_HAND_HOLLOW_CIRCLE_COLOR);
                }
            }

        }
    }
    static float[] getX(){
        return x;
    }
    static float[] getY(){
        return y;
    }
    static float[] getZ(){
        return z;
    }

    // $FF: synthetic method
    // $FF: bridge method
    public void renderResult(ImageSolutionResult var1, float[] var2) {
        this.renderResult((HandsResult)var1, var2);
    }

    public final void release() {
        GLES20.glDeleteProgram(this.program);
    }

    private final void drawConnections(List handLandmarkList, float[] colorArray) {
        GLES20.glUniform4fv(this.colorHandle, 1, colorArray, 0);
        UnmodifiableIterator var4 = Hands.HAND_CONNECTIONS.iterator();

        while(var4.hasNext()) {
            Hands.Connection c = (Hands.Connection)var4.next();
            LandmarkProto.NormalizedLandmark start = (LandmarkProto.NormalizedLandmark)handLandmarkList.get(c.start());
            LandmarkProto.NormalizedLandmark end = (LandmarkProto.NormalizedLandmark)handLandmarkList.get(c.end());
            float[] vertex = new float[]{start.getX(), start.getY(), end.getX(), end.getY()};
            FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertex.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertex);
            vertexBuffer.position(0);
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glVertexAttribPointer(this.positionHandle, 2, 5126, false, 0, (Buffer)vertexBuffer);
            GLES20.glDrawArrays(1, 0, 2);
        }

    }

    private final void drawCircle(float x, float y, float[] colorArray) {
        GLES20.glUniform4fv(this.colorHandle, 1, colorArray, 0);
        int vertexCount = 122;
        float[] vertices = new float[vertexCount * 3];
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = 0.0F;
        int i = 1;

        for(byte var7 = (byte) vertexCount; i < var7; ++i) {
            float angle = 2.0F * (float)i * (float)Math.PI / (float)120;
            int currentIndex = 3 * i;
            vertices[currentIndex] = x + (float)((double)0.008F * Math.cos((double)angle));
            vertices[currentIndex + 1] = y + (float)((double)0.008F * Math.sin((double)angle));
            vertices[currentIndex + 2] = 0.0F;
        }

        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices);
        vertexBuffer.position(0);
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 0, (Buffer)vertexBuffer);
        GLES20.glDrawArrays(6, 0, vertexCount);
    }

    private final void drawHollowCircle(float x, float y, float[] colorArray) {
        GLES20.glUniform4fv(this.colorHandle, 1, colorArray, 0);
        int vertexCount = 121;
        float[] vertices = new float[vertexCount * 3];
        int i = 0;

        for(byte var7 = (byte) vertexCount; i < var7; ++i) {
            float angle = 2.0F * (float)i * (float)Math.PI / (float)120;
            int currentIndex = 3 * i;
            vertices[currentIndex] = x + (float)((double)0.01F * Math.cos((double)angle));
            vertices[currentIndex + 1] = y + (float)((double)0.01F * Math.sin((double)angle));
            vertices[currentIndex + 2] = 0.0F;
        }

        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices);
        vertexBuffer.position(0);
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 0, (Buffer)vertexBuffer);
        GLES20.glDrawArrays(3, 0, vertexCount);
    }

    @Metadata(
            mv = {1, 7, 1},
            k = 1,
            d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"},
            d2 = {"Lcom/example/kotlintestdeleteme/HandsResultGlRenderer$Companion;", "", "()V", "CONNECTION_THICKNESS", "", "FRAGMENT_SHADER", "", "HOLLOW_CIRCLE_RADIUS", "LANDMARK_RADIUS", "LEFT_HAND_CONNECTION_COLOR", "", "LEFT_HAND_HOLLOW_CIRCLE_COLOR", "LEFT_HAND_LANDMARK_COLOR", "NUM_SEGMENTS", "", "RIGHT_HAND_CONNECTION_COLOR", "RIGHT_HAND_HOLLOW_CIRCLE_COLOR", "RIGHT_HAND_LANDMARK_COLOR", "TAG", "VERTEX_SHADER", "KotlinTestDELETEME.app.main"}
    )
    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

}
