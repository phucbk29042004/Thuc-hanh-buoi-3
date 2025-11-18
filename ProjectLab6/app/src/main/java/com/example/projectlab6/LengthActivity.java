package com.example.projectlab6;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectlab6.R; // đảm bảo dùng R của app

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LengthActivity extends AppCompatActivity {

    private EditText edtValue;
    private Spinner spUnit;
    private ListView lvResults;
    private Button btnBack;


    private static final double[][] LEN = {
            /*           Hải lý      Dặm        Km         Lý         Met        Yard       Foot        Inch */
            /*Hải lý*/ { 1.0,        1.15077945, 1.85200000, 20.2537183, 1852.00000, 2025.37183, 6076.11549, 72913.38583 },
            /*Dặm  */ { 0.86897624, 1.0,        1.60934400, 17.6000000, 1609.34400, 1760.00000, 5280.00000, 63360.00000 },
            /*Km   */ { 0.53995680, 0.62137119, 1.0,        10.9361330, 1000.00000, 1093.61330, 3280.83990, 39370.07874 },
            /*Lý   */ { 0.04937365, 0.05681818, 0.09144000, 1.0,        91.44000,   100.00000,  300.00000,  3600.00000  },
            /*Met  */ { 0.00053996, 0.00062137, 0.00100000, 0.01093610, 1.0,        1.09361330, 3.28084000, 39.37007874 },
            /*Yard */ { 0.00049374, 0.00056818, 0.00091440, 0.01000000, 0.91440000, 1.0,        3.00000000, 36.00000000 },
            /*Foot */ { 0.00016458, 0.00018939, 0.00030480, 0.00333333, 0.30480000, 0.33333333, 1.0,        12.00000000 },
            /*Inch */ { 0.00001371, 0.00001578, 0.00002540, 0.00027778, 0.02540000, 0.02777778, 0.08333333, 1.0         }
    };

    private final DecimalFormat df = new DecimalFormat("#,##0.####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length);
        mapViews();
        setupSpinner();
        setupEvents();
        renderList();
    }

    private void mapViews() {
        edtValue = findViewById(R.id.edtValue);
        spUnit = findViewById(R.id.spUnit);
        lvResults = findViewById(R.id.lvResults);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.length_units, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnit.setAdapter(adapter);
        spUnit.setSelection(1); // Dặm
    }

    private void setupEvents() {
        edtValue.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { renderList(); }
            @Override public void afterTextChanged(Editable s) { }
        });

        spUnit.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) { renderList(); }
            @Override public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private void renderList() {
        String valStr = edtValue.getText().toString().trim();
        double value = 0.0;
        if (!TextUtils.isEmpty(valStr)) {
            try {
                value = Double.parseDouble(valStr);
                if (value < 0) {
                    Toast.makeText(this, "Giá trị phải >= 0", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá trị không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        int from = spUnit.getSelectedItemPosition();
        if (from < 0 || from >= LEN.length) return;

        String[] units = getResources().getStringArray(R.array.length_units);
        List<String> rows = new ArrayList<>();
        for (int to = 0; to < LEN.length; to++) {
            double res = value * LEN[from][to];
            rows.add(df.format(res) + "   —   " + units[to]);
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows);
        lvResults.setAdapter(listAdapter);
    }
}
