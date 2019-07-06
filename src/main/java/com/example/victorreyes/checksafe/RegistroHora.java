package com.example.victorreyes.checksafe;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistroHora {

    //private Context TheThis;//
    RequestQueue request;
    StringRequest stringRequest2;


    public void horaEntrada(final Context context, String usuario){

        request = Volley.newRequestQueue(context);
        final String user = usuario;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        Toast.makeText(context, currentDateandTime,Toast.LENGTH_LONG).show();

        String url = "http://192.168.8.105/DataBase_CheckSafe/CheckSafe_DB_Fecha_Entrada.php?";
        //url = url.replace(" ", "%20");

        stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equalsIgnoreCase("registra")){

                    Toast.makeText(context,"Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(context,"No se ha registrado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"No se ha podido establecer conexión", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //String NoCuenta = campoId.getText().toString();
                String HoraEntrada = currentDateandTime;
                String IdUsuario = user;

                Map<String, String> parametros = new HashMap<>();
                //parametros.put("NoCuenta", NoCuenta);
                parametros.put("HoraEntrada", HoraEntrada);
                parametros.put("IdUsuario", IdUsuario);

                return parametros;
            }

        };
        //Toast.makeText(getApplicationContext(), "Sexo:"+campoSexo,Toast.LENGTH_LONG).show();

        request.add(stringRequest2);

    }

    public void horaSalida(final Context context, String usuario){

        request = Volley.newRequestQueue(context);
        final String user = usuario;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        Toast.makeText(context, currentDateandTime,Toast.LENGTH_LONG).show();

        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_Fecha_Salida.php?";
        //url = url.replace(" ", "%20");

        stringRequest2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equalsIgnoreCase("registra")){

                    Toast.makeText(context,"Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(context,"No se ha registrado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"No se ha podido establecer conexión", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //String NoCuenta = campoId.getText().toString();
                String HoraSalida = currentDateandTime;
                String IdUsuario = user;

                Map<String, String> parametros = new HashMap<>();
                //parametros.put("NoCuenta", NoCuenta);
                parametros.put("HoraSalida", HoraSalida);
                parametros.put("IdUsuario", IdUsuario);

                return parametros;
            }

        };
        //Toast.makeText(getApplicationContext(), "Sexo:"+campoSexo,Toast.LENGTH_LONG).show();

        request.add(stringRequest2);

    }
}
