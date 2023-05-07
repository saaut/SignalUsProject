package com.example.signalussample1_java.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.signalussample1_java.R;
import com.example.signalussample1_java.databinding.ActivityVideoBinding;


public class VideoActivity extends AppCompatActivity {

    ActivityVideoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, cameraFragment.newInstance())
                    .commit();
        }
    }
}
