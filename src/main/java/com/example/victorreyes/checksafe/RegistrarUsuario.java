package com.example.victorreyes.checksafe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText campoId, campoNombre, campoApellido, campoEmail, campoGrado, campoGrupo;
    //Button RegistButton;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        request = Volley.newRequestQueue(this);

        campoId = (EditText) findViewById(R.id.txtNoCuenta);
        campoNombre = (EditText) findViewById(R.id.txtNombre);
        campoApellido = (EditText) findViewById(R.id.txtApellido);
        campoEmail = (EditText) findViewById(R.id.txtEmail);
        campoGrado = (EditText) findViewById(R.id.txtGrado);
        campoGrupo = (EditText) findViewById(R.id.txtGrupo);
    }

    public void onClick(View view){

        cargarWebSevice();
    }

    private void cargarWebSevice() {



        String url = "http://192.168.8.101/DataBase_CheckSafe/CheckSafe_DB.php?NoCuenta=" + campoId.getText().toString() + "&Nombre=" + campoNombre.getText().toString() + "&Apellido=" + campoApellido.getText().toString() + "&Email=" + campoEmail.getText().toString() + "&Grado=" + campoGrado.getText().toString() + "&Grupo=" + campoGrupo.getText().toString();
        //url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Se Ha Generado Un Error" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.w("ERROR",error.toString());
        limpiarCampos();
    }

    @Override
    public void onResponse(JSONObject response) {

        Log.i("Correcto","Respuesta es : " + response);
        Toast.makeText(getApplicationContext(), "Se Ha Registrado Exitosamente", Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void limpiarCampos() {

        campoId.setText("");
        campoNombre.setText("");
        campoApellido.setText("");
        campoEmail.setText("");
        campoGrado.setText("");
        campoGrupo.setText("");
    }
}
