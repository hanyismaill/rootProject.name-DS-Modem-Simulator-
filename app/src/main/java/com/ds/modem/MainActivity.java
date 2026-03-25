package com.ds.modem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView t = new TextView(this);
        t.setBackgroundColor(0xFF000000);
        t.setTextColor(0xFFFFFFFF);
        t.setTextSize(14);

        t.setText(
            "iDIRECT Modem Virtualization\n\n" +
            "Starting system...\n"
        );

        setContentView(t);
    }
}
