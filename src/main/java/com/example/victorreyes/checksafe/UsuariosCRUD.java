package com.example.victorreyes.checksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

                break;
            case R.id.btnListar:

                break;
            case R.id.btnEliminar:

                break;
        }

        if (miIntent!=null){

            startActivity(miIntent);
        }
    }
}
