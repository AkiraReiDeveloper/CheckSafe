package com.example.victorreyes.checksafe;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    EditText codigo;
    Button escaner;
    private ZXingScannerView vistaescaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Escanear();
    }


    public void Escanear(){

        vistaescaner = new ZXingScannerView(this);
        vistaescaner.setResultHandler(this);
        setContentView(vistaescaner);
        vistaescaner.startCamera();
    }

    @Override
    public void handleResult(Result result) {

        Log.v("HandleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Datos Escaneados:");
        builder.setMessage(result.getText());
        AlertDialog alerDialog = builder.create();
        alerDialog.show();

        vistaescaner.resumeCameraPreview(this);
    }


}
