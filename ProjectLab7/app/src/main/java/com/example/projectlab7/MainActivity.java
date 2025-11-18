package com.example.projectlab7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.example.projectlab7.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button colored = findViewById(R.id.btnColored);
        Button coloredDisabled = findViewById(R.id.btnColoredDisabled);
        Button round = findViewById(R.id.btnRound);
        Button gradient = findViewById(R.id.btnGradient);
        Button selectorShape = findViewById(R.id.btnSelectorShape);
        TextView emoji1 = findViewById(R.id.emoji1);
        TextView emoji2 = findViewById(R.id.emoji2);

        colored.setOnClickListener(v -> Toast.makeText(this, "Colored selector", Toast.LENGTH_SHORT).show());
        round.setOnClickListener(v -> Toast.makeText(this, "Round shape", Toast.LENGTH_SHORT).show());
        gradient.setOnClickListener(v -> Toast.makeText(this, "Gradient shape", Toast.LENGTH_SHORT).show());
        selectorShape.setOnClickListener(v -> Toast.makeText(this, "Selector shape", Toast.LENGTH_SHORT).show());
        emoji1.setOnClickListener(v -> Toast.makeText(this, "😊 clicked", Toast.LENGTH_SHORT).show());
        emoji2.setOnClickListener(v -> Toast.makeText(this, "😎 clicked", Toast.LENGTH_SHORT).show());
        Button btnOpenExercise2 = findViewById(R.id.btnOpenExercise2);
        btnOpenExercise2.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CustomNotifyActivity.class))
        );
        Button btnOpenExercise3 = findViewById(R.id.btnOpenExercise3);
        btnOpenExercise3.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ColorPickerActivity.class))
        );
    }
}
