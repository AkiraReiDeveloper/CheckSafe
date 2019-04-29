package com.example.victorreyes.checksafe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victorreyes.checksafe.Adapters.UsuariosAdapter;
import com.example.victorreyes.checksafe.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarUsuarios extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RecyclerView recyclerUsuarios;
    ArrayList<Usuario> listaUsuarios;
    //comentario
    //ProgressDialog progress;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_listar_usuarios);

        listaUsuarios = new ArrayList<>();

        recyclerUsuarios = (RecyclerView) findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerUsuarios.setHasFixedSize(true);
        request = Volley.newRequestQueue(this);

        cargarWebService();


    }



    private void cargarWebService() {

        String url = "http://192.168.8.100/DataBase_CheckSafe/CheckSafe_DB_Consulta_Lista_Usuario.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Se Ha Generado Un Error" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.w("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        Usuario usuario=null;

        JSONArray json=response.optJSONArray("usuarios");

        try {

            for (int i=1;i<json.length();i++){

                usuario=new Usuario();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                usuario.setId(jsonObject.optInt("NoCuenta"));
                usuario.setNombre(jsonObject.optString("Nombre"));
                usuario.setApellido(jsonObject.optString("Apellido"));
                usuario.setEmail(jsonObject.optString("Email"));
                usuario.setGrado(jsonObject.optInt("Grado"));
                usuario.setGrupo(jsonObject.optString("Grupo"));
                usuario.setSexo(jsonObject.optString("Sexo"));
                usuario.setDato(jsonObject.optString("Foto"));
                listaUsuarios.add(usuario);
            }
            //progress.hide();
            UsuariosAdapter adapter=new UsuariosAdapter(listaUsuarios);
            recyclerUsuarios.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            //progress.hide();
        }

    }
}
