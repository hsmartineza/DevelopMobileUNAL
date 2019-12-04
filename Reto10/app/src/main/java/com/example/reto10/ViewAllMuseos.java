package com.example.reto10;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class ViewAllMuseos extends ListActivity {

    List<Museo> museosList;
    private RequestQueue queue;
    private ArrayAdapter<Museo> adapter;
    private Context context = this;
    private EditText nombreMuseoEditText;
    private EditText localidadMuseoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_museos);


        String nombreMuseoEditText = MainActivity.nombreMuseo;
        String localidadMuseoEditText = MainActivity.localidad;

        String url = "";

        if(!nombreMuseoEditText.isEmpty() && !localidadMuseoEditText.isEmpty() ){
            url = "https://datos.gov.co/resource/d8dw-68hx.json?nombre_del_museo=" + nombreMuseoEditText.toUpperCase() +"&=localidad="
                    +localidadMuseoEditText.toUpperCase();
        }
        else if(!nombreMuseoEditText.isEmpty()){
            url = "https://datos.gov.co/resource/d8dw-68hx.json?nombre_del_museo=" + nombreMuseoEditText.toUpperCase();

        }
        else if(!localidadMuseoEditText.isEmpty()){
            url = "https://datos.gov.co/resource/d8dw-68hx.json?localidad=" + localidadMuseoEditText.toUpperCase();

        }
        else{
            url = "https://www.datos.gov.co/resource/d8dw-68hx.json";
        }

        queue = Volley.newRequestQueue(this);
        museosList = new ArrayList<>();
        JsonArrayRequest museos = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject tmp = null;
                            Museo museo = new Museo();
                            try {
                                tmp = response.getJSONObject(i);

                                museo.setNo(Long.parseLong(tmp.getString("no")));
                                museo.setNombreMuseo(tmp.getString("nombre_del_museo"));
                                museo.setTelefonoFijo(tmp.getString("telefono_fijo"));
                                museo.setCelular(tmp.getString("celular"));
                                museo.setCorreo(tmp.getString("correo_electr_nico"));
                                museo.setPaginaWeb(tmp.getString("pagina_web"));
                                museo.setDireccion(tmp.getString("direccion"));
                                museo.setLocalidad(tmp.getString("localidad"));
                                museo.setUpz(tmp.getString("upz"));
                                museo.setEntidadAdministradora(tmp.getString("nombre_de_la_entidad_administradora_del_equipamiento"));
                                museo.setAnoInicio(Long.parseLong(tmp.getString("ano_inicio")));
                                museo.setCaracter(tmp.getString("caracter"));
                                museo.setDelOrden(tmp.getString("del_orden"));
                                museo.setDelOrden(tmp.getString("del_orden"));

                                museosList.add(museo);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter = new ArrayAdapter<>(context,
                                android.R.layout.simple_list_item_1, museosList);
                        setListAdapter(adapter);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REQ", "bad");
                    }
                });

        queue.add(museos);



    }

}
