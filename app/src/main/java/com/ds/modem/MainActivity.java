package com.ds.modem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    EditText input;
    int stage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        screen = new TextView(this);
        screen.setText("iDIRECT Modem Virtualization\n\nReady...\n\nUsername: ");

        input = new EditText(this);
        input.setHint("Type here...");

        layout.addView(screen);
        layout.addView(input);

        setContentView(layout);

        input.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER) {

                handleInput();
