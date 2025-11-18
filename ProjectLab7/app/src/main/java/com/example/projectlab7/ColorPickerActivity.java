package com.example.projectlab7;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ColorPickerActivity extends AppCompatActivity {

    private View viewPreview;
    private SeekBar seekR, seekG, seekB;
    private TextView tvRVal, tvGVal, tvBVal, tvRgb, tvCmy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        mapViews();
        setupSeekbars(64, 128, 192); // giá trị khởi đầu (tuỳ chọn)
        bindNavButtons();
    }

    private void mapViews() {
        viewPreview = findViewById(R.id.viewPreview);
        seekR = findViewById(R.id.seekR);
        seekG = findViewById(R.id.seekG);
        seekB = findViewById(R.id.seekB);
        tvRVal = findViewById(R.id.tvRVal);
        tvGVal = findViewById(R.id.tvGVal);
        tvBVal = findViewById(R.id.tvBVal);
        tvRgb  = findViewById(R.id.tvRgb);
        tvCmy  = findViewById(R.id.tvCmy);

        Button btnBackBai1 = findViewById(R.id.btnBackBai1);
        Button btnGotoBai2 = findViewById(R.id.btnGotoBai2);

        btnBackBai1.setOnClickListener(v -> finish()); // quay về Bài 1 (MainActivity)
        btnGotoBai2.setOnClickListener(v ->
                startActivity(new Intent(ColorPickerActivity.this, CustomNotifyActivity.class))
        );
    }

    private void setupSeekbars(int r0, int g0, int b0) {
        seekR.setMax(255);
        seekG.setMax(255);
        seekB.setMax(255);

        seekR.setProgress(r0);
        seekG.setProgress(g0);
        seekB.setProgress(b0);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { updateColor(); }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        };

        seekR.setOnSeekBarChangeListener(listener);
        seekG.setOnSeekBarChangeListener(listener);
        seekB.setOnSeekBarChangeListener(listener);

        updateColor(); // render lần đầu
    }

    private void updateColor() {
        int r = seekR.getProgress();
        int g = seekG.getProgress();
        int b = seekB.getProgress();

        tvRVal.setText(String.valueOf(r));
        tvGVal.setText(String.valueOf(g));
        tvBVal.setText(String.valueOf(b));

        // cập nhật màu preview
        int color = Color.rgb(r, g, b);
        viewPreview.setBackgroundColor(color);

        // RGB + HEX
        String hex = String.format("#%02X%02X%02X", r, g, b);
        tvRgb.setText("RGB(" + r + ", " + g + ", " + b + ")  |  HEX " + hex);

        // CMY = 255 - RGB
        int c = 255 - r;
        int m = 255 - g;
        int y = 255 - b;
        tvCmy.setText("CMY(" + c + ", " + m + ", " + y + ")");
    }

    private void bindNavButtons() {
        // Đã bind trong mapViews() — tách riêng cho dễ nhìn; không cần thêm gì nữa.
    }
}
