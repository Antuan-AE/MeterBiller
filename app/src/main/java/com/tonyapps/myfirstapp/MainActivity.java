package com.tonyapps.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        final EditText firstNumEditText = (EditText) findViewById(R.id.lastReadingEditText);
        final EditText secondNumEditText = (EditText) findViewById(R.id.currentReadingEditText);
        final TextView resultTextView = (TextView) findViewById(R.id.resultTextView);

        Button addBtn = (Button) findViewById(R.id.calculateBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = (firstNumEditText.getText().length() == 0) ? 0 : Integer.parseInt(firstNumEditText.getText().toString());
                int num2 = (secondNumEditText.getText().length() == 0) ? 0 : Integer.parseInt(secondNumEditText.getText().toString());
                resultTextView.setText((num1 + num2) + "");
            }
        });
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

}
