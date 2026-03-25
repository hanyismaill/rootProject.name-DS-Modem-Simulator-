package com.ds.modem;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView output;
    EditText input;

    int stage = 0; // 0=user, 1=pass, 2=cli
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        output = new TextView(this);
        output.setTextColor(0xFFFFFFFF);
        output.setBackgroundColor(0xFF000000);
        output.setTextSize(14);
        output.setPadding(20, 20, 20, 20);
        output.setMovementMethod(new ScrollingMovementMethod());

        input = new EditText(this);
        input.setTextColor(0xFFFFFFFF);
        input.setBackgroundColor(0xFF000000);

        layout.addView(output);
        layout.addView(input);

        setContentView(layout);

        startLogin();

        input.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                handleCommand(input.getText().toString());
                input.setText("");
                return true;
            }
            return false;
        });
    }

    private void startLogin() {
        output.setText("iDIRECT Modem Virtualization\n\nUsername: ");
        stage = 0;
    }

    private void handleCommand(String cmd) {
        output.append(cmd + "\n");

        if (stage == 0) {
            username = cmd;
            output.append("Password: ");
            stage = 1;
        }
        else if (stage == 1) {
            if (username.equals("admin") && cmd.equals("P@55w0rd!")) {
                output.append("\nLogin successful\n");
                output.append("[RMT:17231] admin@telnet:127.0.0.1;4494\n");
                stage = 2;
            } else {
                output.append("Login failed\n\nUsername: ");
                stage = 0;
            }
        }
        else if (stage == 2) {
            if (cmd.equals("help")) {
                output.append("Available commands: help, version\n");
            } else if (cmd.equals("version")) {
                output.append("iDirect OS v1.0\n");
            } else {
                output.append("Unknown Command: '" + cmd + "'\n");
            }

            output.append("[RMT:17231] admin@telnet:127.0.0.1;4494\n");
        }
    }
}
