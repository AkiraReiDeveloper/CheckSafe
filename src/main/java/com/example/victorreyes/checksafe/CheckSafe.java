package com.example.victorreyes.checksafe;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CheckSafe extends AppCompatActivity {

    int SOLICITUD_UTILIZAR_CAMARA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_safe);

        //Permiso usuario

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, SOLICITUD_UTILIZAR_CAMARA);
    }

    public void onClick( View view){

        Intent miIntent = null;

        switch (view.getId()){

            case R.id.btnUsers:
                miIntent = new Intent(CheckSafe.this, UsuariosCRUD.class);
                break;
            case R.id.btnScanner:
                miIntent = new Intent(CheckSafe.this, Scanner.class);
                break;
        }

        if (miIntent!=null){

            startActivity(miIntent);
        }
    }
}
