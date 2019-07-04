package com.example.victorreyes.checksafe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victorreyes.checksafe.Entidades.Usuario;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView vistaescaner;
    Boolean selecthour = false;
    //Boolean continuarScan = false;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonRequest;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_escanner_select_hour);
        Escanear();
        abilitarBotones("#192144", "#848484");

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

    public void abilitarBotones(String btnEntrada, String btnSalida){

        findViewById(R.id.btnEntrada).setBackgroundColor(Color.parseColor(btnEntrada));
        findViewById(R.id.btnSalida).setBackgroundColor(Color.parseColor(btnSalida));
    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.btnEntrada:
                selecthour = false;
                abilitarBotones("#192144", "#848484");
                break;
            case R.id.btnSalida:
                selecthour = true;
                abilitarBotones("#848484", "#192144");
                break;
            case R.id.btnContinuarScan:
                RegistroHora entrada = new RegistroHora();

                if (selecthour) {
                    entrada.horaSalida(this, resultado);
                } else {
                    entrada.horaEntrada(this, resultado);
                }
                resultado = "";
                dialog.cancel();
                vistaescaner.resumeCameraPreview(this);
                //dialog.dismiss();
                break;
            case R.id.btnDenegarScan:
                dialog.cancel();
                vistaescaner.resumeCameraPreview(this);
                break;
        }
    }

    public void datosLayout(String identificador){

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.layout_escaner_usuario, null);
        final TextView txtVNombreScan = (TextView) dialogLayout.findViewById(R.id.textNombreScan);
        final TextView txtVApellidoScan = (TextView) dialogLayout.findViewById(R.id.textApellidoScan);
        final TextView txtVEmailScan = (TextView) dialogLayout.findViewById(R.id.textEmailScan);
        final TextView txtVGradoScan = (TextView) dialogLayout.findViewById(R.id.textGradoScan);
        final TextView txtVGrupoScan = (TextView) dialogLayout.findViewById(R.id.textGrupoScan);
        final TextView txtVSexoScan = (TextView) dialogLayout.findViewById(R.id.textSexoScan);
        final ImageView campoImageScan = (ImageView) dialogLayout.findViewById(R.id.campoImagenScan);
        request = Volley.newRequestQueue(this);
        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_Consulta_Usuario.php?NoCuenta=" + identificador;
        jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getApplicationContext(), "Se ha Consultado Exitosamente", Toast.LENGTH_SHORT).show();

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

                txtVNombreScan.setText("Nombre: " + miUsuario.getNombre());
                txtVApellidoScan.setText("Apellido: " + miUsuario.getApellido());
                txtVEmailScan.setText("Email: " + miUsuario.getEmail());
                txtVGradoScan.setText("Grado: " + miUsuario.getGrado());
                txtVGrupoScan.setText("Grupo: " + miUsuario.getGrupo());
                txtVSexoScan.setText("Sexo: " + miUsuario.getSexo());
                if(miUsuario.getImagen()!=null){

                    campoImageScan.setImageBitmap(miUsuario.getImagen());
                }else {

                    campoImageScan.setImageResource(R.drawable.img_base);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"No se ha podido establecer conexión", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonRequest);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogLayout);
        dialog = builder.show();
        //dialog.show();
    }

    @Override
    public void handleResult(Result result) {

        Log.v("HandleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Numero de cuenta:");
        //builder.setMessage(result.getText());
        AlertDialog alerDialog = builder.create();
        alerDialog.show();
        resultado = result.getText();
        datosLayout(resultado);
    }



}
