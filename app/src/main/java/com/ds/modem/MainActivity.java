package com.ds.modem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView screen;

    StringBuilder currentInput = new StringBuilder();
    int stage = 0; // 0=username, 1=password, 2=cli
    String username = "";

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screen = new TextView(this);
        screen.setTextSize(14);
        screen.setFocusableInTouchMode(true);
        screen.requestFocus();

        setContentView(screen);

        screen.setText("Username: ");

        screen.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() != KeyEvent.ACTION_DOWN) return false;

            // ENTER
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                processInput(currentInput.toString());
                currentInput.setLength(0);
                return true;
            }

            // BACKSPACE
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                    removeLastChar();
                }
                return true;
            }

            // NORMAL CHAR
            char c = (char) event.getUnicodeChar();
            if (c != 0) {
                currentInput.append(c);

                // Hide password typing
                if (stage == 1) {
                    screen.append("*");
                } else {
                    screen.append(String.valueOf(c));
                }
            }

            return true;
        });
    }

    private void processInput(String input) {

        screen.append("\n");

        if (stage == 0) {
            username = input;
            screen.append("Password: ");
            stage = 1;

        } else if (stage == 1) {

            if (username.equals("admin") && input.equals("admin")) {
                stage = 2;
                printHeader();
            } else {
                screen.append("Access Denied\n\nUsername: ");
                stage = 0;
            }

        } else if (stage == 2) {
            handleCommand(input);
        }
    }

    private void printHeader() {
        int session = 10000 + random.nextInt(90000);

        screen.append("[RMT:" + session + "] admin@telnet:127.0.0.1;4494\n");
        screen.append("> ");
    }

    private void handleCommand(String cmd) {

        cmd = cmd.toLowerCase();

        screen.append(cmd + "\n");

        if (cmd.equals("rx power")) {
            screen.append("Rx Power: -66.830002\n\n");

        } else if (cmd.equals("rx snr")) {
            screen.append("Rx SNR: 11.930000\n\n");

        } else if (cmd.equals("rx freq")) {
            screen.append("Rx Frequency = 1100.0000 MHz\n\n");

        } else if (cmd.equals("tx freq")) {
            screen.append("Tx Frequency = 1800.0000 MHz\n\n");

        } else {
            screen.append("Unknown Command: '" + cmd + "'\n\n");
        }

        printHeader();
    }

    private void removeLastChar() {
        String text = screen.getText().toString();
        if (text.length() > 0) {
            screen.setText(text.substring(0, text.length() - 1));
        }
    }
}
