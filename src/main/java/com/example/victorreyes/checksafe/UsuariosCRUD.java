package com.example.victorreyes.checksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class UsuariosCRUD extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_crud);

    }

    public void onClick( View view){

        Intent miIntent = null;

        switch (view.getId()){

            case R.id.btnAgregar:
                miIntent = new Intent(UsuariosCRUD.this, RegistrarUsuario.class);
                break;
            case R.id.btnConsultar:
                miIntent = new Intent(UsuariosCRUD.this, ConsultarUsuario.class);
                break;
            case R.id.btnListar:
                miIntent = new Intent(UsuariosCRUD.this, ListarUsuarios.class);
                break;
            case R.id.btnEliminar:
                break;
        }

        if (miIntent!=null){

            startActivity(miIntent);
        }
    }


}
