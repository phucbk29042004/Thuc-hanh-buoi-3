package com.example.projectlab7;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CustomNotifyActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_notify);

        Button btnToast = findViewById(R.id.btnShowToast);
        Button btnDialog = findViewById(R.id.btnShowDialog);
        Button btnBack   = findViewById(R.id.btnBackToBai1);

        btnToast.setOnClickListener(v -> showCustomToast("Thành công!", "Đây là Custom Toast minh hoạ."));
        btnDialog.setOnClickListener(v -> showCustomDialog());
        btnBack.setOnClickListener(v -> finish());
    }

    private void showCustomToast(String title, String message) {
        View toastView = LayoutInflater.from(this).inflate(R.layout.view_custom_toast, null);
        TextView tvTitle = toastView.findViewById(R.id.tvToastTitle);
        TextView tvMsg   = toastView.findViewById(R.id.tvToastMsg);
        tvTitle.setText(title);
        tvMsg.setText(message);

        Toast t = new Toast(this);
        t.setView(toastView);
        t.setDuration(Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 120);
        t.show();
    }

    private void showCustomDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_custom_dialog, null);
        TextView tvTitle = dialogView.findViewById(R.id.tvDlgTitle);
        TextView tvMsg   = dialogView.findViewById(R.id.tvDlgMsg);
        tvTitle.setText("Xác nhận");
        tvMsg.setText("Bạn có muốn thực hiện thao tác này không?");

        AlertDialog dlg = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        dialogView.findViewById(R.id.btnDlgOk).setOnClickListener(v -> {
            showCustomToast("OK", "Bạn đã xác nhận!");
            dlg.dismiss();
        });
        dialogView.findViewById(R.id.btnDlgCancel).setOnClickListener(v -> {
            showCustomToast("Huỷ", "Bạn đã huỷ thao tác.");
            dlg.dismiss();
        });

        dlg.show();
    }
}
