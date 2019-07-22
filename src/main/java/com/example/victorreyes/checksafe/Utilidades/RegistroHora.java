package com.example.victorreyes.checksafe.Utilidades;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.victorreyes.checksafe.Entidades.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistroHora {

    StringRequest stringRequest2;


    public void horaEntrada(final Context context, String usuario){

        final String user = usuario;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        Toast.makeText(context, currentDateandTime,Toast.LENGTH_LONG).show();

        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_Fecha_Entrada.php?";

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

                String HoraEntrada = currentDateandTime;
                String IdUsuario = user;

                Map<String, String> parametros = new HashMap<>();
                parametros.put("HoraEntrada", HoraEntrada);
                parametros.put("IdUsuario", IdUsuario);

                return parametros;
            }

        };

        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest2);

    }

    public void horaSalida(final Context context, String usuario){

        final String user = usuario;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());
        Toast.makeText(context, currentDateandTime,Toast.LENGTH_LONG).show();

        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_Fecha_Salida.php?";

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

                String HoraSalida = currentDateandTime;
                String IdUsuario = user;

                Map<String, String> parametros = new HashMap<>();
                parametros.put("HoraSalida", HoraSalida);
                parametros.put("IdUsuario", IdUsuario);

                return parametros;
            }

        };
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(stringRequest2);

    }
}
