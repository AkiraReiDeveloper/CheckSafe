package com.example.victorreyes.checksafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView vistaescaner;
    RequestQueue request;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_escanner_select_hour);
        Escanear();

    }


    public void Escanear(){

        vistaescaner = new ZXingScannerView(this);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
        rl.addView(vistaescaner);
        vistaescaner.setResultHandler(this);
        //setContentView(vistaescaner);
        vistaescaner.startCamera();
        vistaescaner.setSoundEffectsEnabled(true);
    }



    @Override
    public void handleResult(Result result) {

        Log.v("HandleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Numero de cuenta:");
        builder.setMessage(result.getText());
        AlertDialog alerDialog = builder.create();
        alerDialog.show();
        String resultado = result.getText();
        RegistroHora entrada = new RegistroHora();
        entrada.horaEntrada(this, resultado);

        //vistaescaner.resumeCameraPreview(this);
    }



}
