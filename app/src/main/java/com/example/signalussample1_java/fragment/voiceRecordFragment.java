package com.example.signalussample1_java.fragment;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.signalussample1_java.R.id;
import com.example.signalussample1_java.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J&\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001a\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0017"},
        d2 = {"Lcom/example/signalussample1/fragment/voiceRecordFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "()V", "navController", "Landroidx/navigation/NavController;", "getNavController", "()Landroidx/navigation/NavController;", "setNavController", "(Landroidx/navigation/NavController;)V", "onClick", "", "v", "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "view", "app_debug"}
)
public final class voiceRecordFragment extends Fragment implements View.OnClickListener {
    public NavController navController;
    private HashMap _$_findViewCache;
    private TextView textView;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;
    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    Button startButton;

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
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        //setContentView(R.layout.fragment_voice_record); // 레이아웃 파일에 맞게 수정해야 함
        View rootView = inflater.inflate(R.layout.fragment_voice_record, container, false);
        return rootView;

        //return inflater.inflate(R.layout.fragment_voice_record, container, false);
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(id.recordResultTextView);
        startButton = view.findViewById(id.voiceRecordStartButton);
        this.navController = Navigation.findNavController(view);
        ((ImageView) this._$_findCachedViewById(id.back_btn)).setOnClickListener((View.OnClickListener) this);
        ((Button)this._$_findCachedViewById(id.voiceRecordStartButton)).setOnClickListener((View.OnClickListener)this);


        // Check for RECORD_AUDIO permission
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.RECORD_AUDIO}, 1);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Set RecognitionListener
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            public void onReadyForSpeech(Bundle params) {
                textView.setText("Listening...");
            }

            public void onBeginningOfSpeech() {
                textView.setText("Listening...");
            }

            public void onRmsChanged(float rmsdB) {
                // Do something with the RMS dB value if needed
            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            public void onEndOfSpeech() {
                textView.setText("");
            }

            public void onError(int error) {
                textView.setText("Error: " + error);
            }

            public void onResults(Bundle results) {
                // Get the recognized speech as text
                ArrayList<String> speechResults =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (speechResults != null && !speechResults.isEmpty()) {
                    String recognizedSpeech = speechResults.get(0);
                    textView.setText(recognizedSpeech);
                } else {
                    textView.setText("No speech recognized.");
                }
            }
            public void onPartialResults(Bundle partialResults) {
                // Handle partial recognition results if needed
            }
            public void onEvent(int eventType, Bundle params) {
                // Handle events if needed
            }
        });


    }




    public void onClick(@Nullable View v) {
        Integer var2 = v != null ? v.getId() : null;
        int var3 = id.back_btn;
        if (var2 != null) {
            if (var2 == var3) {
                NavController var10000 = this.navController;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("navController");
                }
                var10000.popBackStack();
            }
        }
        var3 =id.voiceRecordStartButton;
        if(var2 != null){
            if(var2==var3){
                CharSequence text = startButton.getText();
                if ("녹음 시작".equals(text)) {
                    speechRecognizer.startListening(speechRecognizerIntent);
                    startButton.setText("녹음 중지");
                } else if ("녹음 중지".equals(text)) {
                    speechRecognizer.stopListening();
                    startButton.setText("녹음 시작");
                }
            }
        }


    }
    // Initialize SpeechRecognizer



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedSpeech = result.get(0);
                textView.setText(recognizedSpeech);
            } else {
                textView.setText("No speech recognized.");
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
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}
