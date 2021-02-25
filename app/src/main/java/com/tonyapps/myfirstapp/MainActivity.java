package com.tonyapps.myfirstapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

import com.tonyapps.meterlogic.Meter;

public class MainActivity extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        this.lastReadingEditText = findViewById(R.id.lastReadingEditText);
        this.currentReadingEditText = findViewById(R.id.currentReadingEditText);
        this.resultTextView = findViewById(R.id.resultTextView);
        this.calculateBtn = findViewById(R.id.calculateBtn);

        JSONObject tariff = loadJSONFileFromAssets();

        try {
            Meter meter = new Meter(tariff);
        } catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject loadJSONFileFromAssets() {
        String json = null;
        JSONObject ob = new JSONObject();
        try {
            InputStream is = getAssets().open("tariff2009.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            try{
                ob = new JSONObject(json);
                return ob;
            }catch(JSONException e){
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    public void calcBtnClicked(View view) {
        btnListener.onClick(view);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer last = 0, current = 0, difference;
            TextView resultTextView = findViewById(R.id.resultTextView);
            try {
                last = Integer.parseInt(((EditText) findViewById(R.id.lastReadingEditText))
                              .getText().toString());
                current  = Integer.parseInt(((EditText) findViewById(R.id.currentReadingEditText))
                                  .getText().toString());
            } catch(NumberFormatException ex) {
                ex.printStackTrace();
                return;
            }

            difference = (current >= last) ? current - last :
                         (current + MeterOverFlow.METER_MAXIMUM_VALUE.value - last);
            resultTextView.setText(getText(R.string.paymentAmount));
            resultTextView.append(getText(R.string.tabulator));
            resultTextView.append(difference.toString());
            //resultTextView.append(getText(R.string.consumptionUnit));
            resultTextView.append(getText(R.string.nextLine));
            resultTextView.append(getText(R.string.nextLine));
            //resultTextView.append(getText(R.string.paymentAmount));
            resultTextView.append(getText(R.string.tabulator));
            ///TODO display Bussiness logic result here...
//            resultTextView.append(currencyFormatted.format((double)logic.getBill(difference)));
            resultTextView.append(getText(R.string.nextLine));
            resultTextView.setTextColor(Color.BLACK);
            resultTextView.setTypeface(null, Typeface.BOLD);

            resultTextView.getTextColors();
            ColorDrawable cd = (ColorDrawable) findViewById(R.id.toolbar).getBackground();
            int icolor = cd.getColor();
            ForegroundColorSpan colorSpan = (difference > 300) ?
                    new ForegroundColorSpan(Color.RED) :
                    new ForegroundColorSpan(cd.getColor());

            Spannable spannText = (Spannable) resultTextView.getText();
            spannText.setSpan(colorSpan, 0, resultTextView.getText().length(), 2);
        }
    };

    private enum MeterOverFlow{
        METER_MAXIMUM_VALUE(100000);
        private final Integer value;
        MeterOverFlow(Integer value) {
            this.value = value;
        }
    }

    private EditText lastReadingEditText, currentReadingEditText;
    private TextView resultTextView;
    private Button calculateBtn;
}
