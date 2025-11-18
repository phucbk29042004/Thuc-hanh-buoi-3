package com.example.projectlab6; // đổi cho khớp với package của bạn

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edtAmount;
    private Spinner spFrom, spTo;
    private Button btnConvert;
    private TextView tvResult;

    private Button btnOpenLength;



    private static final double[][] RATES = {
            //      VND      USD       EUR       JPY       GBP       AUD
            /*VND*/ {1.0,    0.000039, 0.000036, 0.0061,   0.000031, 0.000058},
            /*USD*/ {25500,  1.0,      0.92,     155.0,    0.81,     1.48},
            /*EUR*/ {27700,  1.09,     1.0,      168.0,    0.88,     1.61},
            /*JPY*/ {165.0,  0.0065,   0.0059,   1.0,      0.0052,   0.0096},
            /*GBP*/ {31700,  1.23,     1.14,     193.0,    1.0,      1.83},
            /*AUD*/ {17300,  0.68,     0.62,     104.0,    0.55,     1.0}
    };

    private final DecimalFormat formatter = new DecimalFormat("#,##0.####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapViews();
        setupSpinners();
        bindActions();
    }

    private void mapViews() {
        edtAmount = findViewById(R.id.edtAmount);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);
        btnOpenLength = findViewById(R.id.btnOpenLength);
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.currencies, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);


        spFrom.setSelection(0);
        spTo.setSelection(1);
    }

    private void bindActions() {
        btnConvert.setOnClickListener(v -> convert());
        btnOpenLength.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LengthActivity.class)));
    }

    private void convert() {
        String amountStr = edtAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amountStr)) {
            edtAmount.setError("Hãy nhập số tiền");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            edtAmount.setError("Giá trị không hợp lệ");
            return;
        }

        if (amount < 0) {
            edtAmount.setError("Số tiền phải >= 0");
            return;
        }

        int from = spFrom.getSelectedItemPosition();
        int to = spTo.getSelectedItemPosition();

        if (from < 0 || to < 0 || from >= RATES.length || to >= RATES.length) {
            Toast.makeText(this, "Lựa chọn tiền tệ không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        double rate = RATES[from][to];
        double result = amount * rate;

        String fromCode = spFrom.getSelectedItem().toString();
        String toCode = spTo.getSelectedItem().toString();

        tvResult.setText(
                String.format("%s %s = %s %s",
                        formatter.format(amount), fromCode,
                        formatter.format(result), toCode)
        );
    }
}
