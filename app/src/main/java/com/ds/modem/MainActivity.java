package com.ds.modem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView output;
    EditText input;

    int stage = 0; // 0=username, 1=password, 2=CLI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        output = new TextView(this);
        input = new EditText(this);

        output.setText("iDIRECT Modem Virtualization\n\nUsername:\n");
        output.setTextColor(0xFFFFFFFF);
        input.setTextColor(0xFFFFFFFF);

        input.setSingleLine(true);

        setContentView(output);
        addContentView(input, new android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
        ));

        input.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                String cmd = input.getText().toString().trim();
                input.setText("");

                handleInput(cmd);
                return true;
            }
            return false;
        });
    }

    void handleInput(String cmd) {
        output.append(cmd + "\n");

        if (stage == 0) {
            if (cmd.equals("admin")) {
                output.append("Password:\n");
                stage = 1;
            } else {
                output.append("Username:\n");
            }
        }

        else if (stage == 1) {
            if (cmd.equals("P@55w0rd!")) {
                output.append("\n[RMT:17231] admin@telnet:127.0.0.1;4494\n");
                stage = 2;
            } else {
                output.append("Login incorrect\nUsername:\n");
                stage = 0;
            }
        }

        else if (stage == 2) {
            if (cmd.equals("help")) {
                output.append("Available commands: help, status\n");
            } else if (cmd.equals("status")) {
                output.append("Modem status: ONLINE\n");
            } else {
                output.append("Unknown Command: '" + cmd + "'\n");
            }

            output.append("[RMT:17231] admin@telnet:127.0.0.1;4494 ");
        }
    }
}
