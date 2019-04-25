package com.example.victorreyes.checksafe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victorreyes.checksafe.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText campoId;
    TextView txtVNombre, txtVApellido, txtVEmail, txtVGrado, txtVGrupo, txtVSexo;
    ImageView campoImagen;
    //Button RegistButton;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        request = Volley.newRequestQueue(this);

        campoId = (EditText) findViewById(R.id.txtNoCuentaConsulta);
        txtVNombre = (TextView) findViewById(R.id.textNombre);
        txtVApellido = (TextView) findViewById(R.id.textApellido);
        txtVEmail = (TextView) findViewById(R.id.textEmail);
        txtVGrado = (TextView) findViewById(R.id.textGrado);
        txtVGrupo = (TextView) findViewById(R.id.textGrupo);
        txtVSexo = (TextView) findViewById(R.id.textSexo);
        campoImagen = (ImageView) findViewById(R.id.imgConsultarUsuarios);
    }

    public void onClick(View view){

        cargarWebService();
    }

    private void cargarWebService() {

        String url = "http://192.168.8.101/DataBase_CheckSafe/CheckSafe_DB_Consulta_Usuario.php?NoCuenta=" + campoId.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Se Ha Generado Un Error" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.w("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        Toast.makeText(getApplicationContext(), "Se Ha Consultado Exitosamente", Toast.LENGTH_SHORT).show();

        Usuario miUsuario = new Usuario();

        JSONArray json = response.optJSONArray("usuarios");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);
            miUsuario.setNombre(jsonObject.optString("Nombre"));
            miUsuario.setApellido(jsonObject.optString("Apellido"));
            miUsuario.setEmail(jsonObject.optString("Email"));
            miUsuario.setGrado(jsonObject.optInt("Grado"));
            miUsuario.setGrupo(jsonObject.optString("Grupo"));
            miUsuario.setSexo(jsonObject.optString("Sexo"));
            miUsuario.setDato(jsonObject.optString("Foto"));

        }catch (JSONException e){

            e.printStackTrace();
        }

        txtVNombre.setText("Nombre: " + miUsuario.getNombre());
        txtVApellido.setText("Apellido: " + miUsuario.getApellido());
        txtVEmail.setText("Email: " + miUsuario.getEmail());
        txtVGrado.setText("Grado: " + miUsuario.getGrado());
        txtVGrupo.setText("Grupo: " + miUsuario.getGrupo());
        txtVSexo.setText("Sexo: " + miUsuario.getSexo());
        if(miUsuario.getImagen()!=null){

            campoImagen.setImageBitmap(miUsuario.getImagen());
        }else {

            campoImagen.setImageResource(R.drawable.img_base);
        }


    }
}
