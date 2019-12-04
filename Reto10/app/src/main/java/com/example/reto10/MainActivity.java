package com.example.reto10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private Button searchData;
    public static String nombreMuseo = "";
    public static String localidad = "";

    private EditText nombreMuseoEditText;
    private EditText localidadMuseoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchData = (Button) findViewById(R.id.button_view_all_museos);

        nombreMuseoEditText = (EditText) findViewById(R.id.edit_text_nombre_museo);
        localidadMuseoEditText = (EditText) findViewById(R.id.edit_text_localidad);



        searchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreMuseo  = nombreMuseoEditText.getText().toString();
                localidad  = localidadMuseoEditText.getText().toString();

                Intent i = new Intent(MainActivity.this, ViewAllMuseos.class);
                startActivity(i);
            }
        });


    }
}
