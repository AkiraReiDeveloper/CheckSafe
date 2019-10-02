package com.example.victorreyes.checksafe.Utilidades;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victorreyes.checksafe.Adapters.UtileriaAdapter;
import com.example.victorreyes.checksafe.Entidades.Utileria;
import com.example.victorreyes.checksafe.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VariablesEstaticas {
    public static String ipAddress = "http://192.168.3.111";
    public String[] nombreUtilLista;
    JsonObjectRequest stringRequest2;

}
