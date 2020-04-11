package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText editString;
    TextView result;
    Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editString  =  findViewById(R.id.editString);
        result =  findViewById(R.id.Result);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        /*
            Create Button
        */
        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = editString.getText().toString();
                result.setText(text );
                editString.getText().clear();
            }
        });
    }
}