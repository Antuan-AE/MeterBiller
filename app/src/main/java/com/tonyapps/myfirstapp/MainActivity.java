package com.tonyapps.myfirstapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

import com.tonyapps.meterlogic.Meter;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject tariff = loadJSONFileFromAssets();

        try {
            Meter meter = new Meter(tariff);
        } catch(JSONException ex) {
            ex.printStackTrace();
        }

        final EditText firstNumEditText = findViewById(R.id.lastReadingEditText);
        final EditText secondNumEditText = findViewById(R.id.currentReadingEditText);
        final TextView resultTextView = findViewById(R.id.resultTextView);

//        Button addBtn = findViewById(R.id.calculateBtn);
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int num1 = (firstNumEditText.getText().length() == 0) ? 0 : Integer.parseInt(firstNumEditText.getText().toString());
//                int num2 = (secondNumEditText.getText().length() == 0) ? 0 : Integer.parseInt(secondNumEditText.getText().toString());
//                resultTextView.setText((num1 + num2) + "");
//            }
//        });
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
<<<<<<< Updated upstream
            difference = (current >= last) ? current - last :
                         (current + MeterOverFlow.METER_MAXIMUM_VALUE.value - last);
            resultTextView.setText(getText(R.string.consumption));
            resultTextView.append(getText(R.string.tabulator));
            resultTextView.append(difference.toString());
            resultTextView.append(getText(R.string.consumptionUnit));
            resultTextView.append(getText(R.string.nextLine));
            resultTextView.append(getText(R.string.nextLine));
            resultTextView.append(getText(R.string.paymentAmount));
            resultTextView.append(getText(R.string.tabulator));
            ///TODO display Bussiness logic result here...
//            resultTextView.append(currencyFormatted.format((double)logic.getBill(difference)));
            resultTextView.append(getText(R.string.nextLine));
            resultTextView.setTextColor(Color.BLACK);
            resultTextView.setTypeface(null, Typeface.BOLD);

            resultTextView.getTextColors();
            ForegroundColorSpan colorSpan = (difference > 300) ?
                    new ForegroundColorSpan(Color.RED) :
                    new ForegroundColorSpan(Color.GREEN);
            Spannable spannText = (Spannable) resultTextView.getText();
            spannText.setSpan(colorSpan, 0, resultTextView.getText().length(), 2);
=======

            difference = (current >= last) ? current - last :
                         (current + MeterOverFlow.METER_MAXIMUM_VALUE.value - last);
            resultTextView.setText("Consumtion: ");
            resultTextView.append(difference.toString());
>>>>>>> Stashed changes
        }
    };

    private enum MeterOverFlow{
        METER_MAXIMUM_VALUE(100000);
        private final Integer value;
        MeterOverFlow(Integer value) {
            this.value = value;
        }
    }
}
