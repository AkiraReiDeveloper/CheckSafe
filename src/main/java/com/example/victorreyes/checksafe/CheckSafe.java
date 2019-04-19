package com.example.victorreyes.checksafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CheckSafe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_safe);
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
