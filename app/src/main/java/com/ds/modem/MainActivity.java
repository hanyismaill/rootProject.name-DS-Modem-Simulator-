package com.ds.modem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    EditText input;

    int stage = 0; // 0=username, 1=password, 2=CLI
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screen = new TextView(this);
        input = new EditText(this);

        screen.setTextSize(16);
        input.setHint("");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.addView(screen);
        layout.addView(input);

        setContentView(layout);

        // Start directly like modem
        screen.setText("Username: ");

        input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                String text = input.getText().toString().trim();
                input.setText("");

                if (!text.isEmpty()) {
                    processInput(text);
                }

                return true;
            }
            return false;
        });
    }

    private void processInput(String text) {

        if (stage == 0) {
            username = text;
            screen.append(username + "\nPassword: ");
            stage = 1;

        } else if (stage == 1) {
            screen.append("****\n");

            if (username.equals("admin") && text.equals("admin")) {
                screen.append("\n");
                stage = 2;
                showPrompt();
            } else {
                screen.append("\nAccess Denied\n\nUsername: ");
                stage = 0;
            }

        } else if (stage == 2) {
            handleCommand(text);
        }
    }

    private void handleCommand(String cmd) {

        cmd = cmd.toLowerCase();

        screen.append(cmd + "\n");

        switch (cmd) {

            case "rx snr":
                screen.append("12.5 dB\n");
                break;

            case "tx power":
                screen.append("3.2 dBm\n");
                break;

            case "sn":
                screen.append("DS-123456\n");
                break;

            case "clear":
                screen.setText("");
                showPrompt();
                return;

            default:
                screen.append("Invalid command\n");
                break;
        }

        showPrompt();
    }

    private void showPrompt() {
        screen.append("> ");
    }
}
