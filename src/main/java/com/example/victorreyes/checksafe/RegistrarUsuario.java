package com.example.victorreyes.checksafe;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText campoId, campoNombre, campoApellido, campoEmail, campoGrado, campoGrupo;
    RadioButton campoFemenino, campoMasculino;
    String sexo = "";
    Bitmap bitmap, bmap ;
    ImageView imagen;
    /*private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String mipath;//almacena la ruta de la imagen
    File fileImagen;*/
    public static final int COD_GALERIA = 10;
    public static final int COD_FOTO = 20;


    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    //permission STORAGE
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }//fin permission STORAGE


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        verifyStoragePermissions(this);

        request = Volley.newRequestQueue(this);

        campoId = (EditText) findViewById(R.id.txtNoCuenta);
        campoNombre = (EditText) findViewById(R.id.txtNombre);
        campoApellido = (EditText) findViewById(R.id.txtApellido);
        campoEmail = (EditText) findViewById(R.id.txtEmail);
        campoGrado = (EditText) findViewById(R.id.txtGrado);
        campoGrupo = (EditText) findViewById(R.id.txtGrupo);
        campoFemenino = (RadioButton) findViewById(R.id.radFemenino);
        campoMasculino = (RadioButton) findViewById(R.id.radMasculino);
        imagen = (ImageView) findViewById(R.id.imgNewUser);

    }

    public void onClickimg(View view){

        mostrarDialogOpciones();
    }

    private void mostrarDialogOpciones() {

        final CharSequence[] opciones = { "Tomar Foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if(opciones[i].equals("Tomar Foto")){

                    //abriCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"),COD_GALERIA);
                    }else{

                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    /*private void abriCamara() {

        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            mipath=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(mipath);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            ////
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
            {
                String authorities=this.getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(this,authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);

            ////

        }

    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case COD_GALERIA:
                Uri path = data.getData();
                imagen.setImageURI(path);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                    imagen.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            /*case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{mipath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String mipath, Uri uri) {
                                Log.i("Path",""+ mipath);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(mipath);
                imagen.setImageBitmap(bitmap);

                break;*/
        }
    }

    public void onClick(View view){

        cargarWebSevice();
        GenerarCodigoQR();
    }

    private void GenerarCodigoQR() {
        GenerarQR generarQR = new GenerarQR();
        if(campoId.getText().toString()!=null){
            try {
                bitmap = generarQR.TextToImageEncode(campoId.getText().toString(), this);
                generarQR.saveImage(bitmap, this, campoId.getText().toString(), campoNombre.getText().toString(), campoGrado.getText().toString(), campoGrupo.getText().toString() );  //give read write permission
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }

    private void cargarWebSevice() {



        String url = "http://192.168.8.101/DataBase_CheckSafe/CheckSafe_DB.php?NoCuenta=" + campoId.getText().toString() + "&Nombre=" + campoNombre.getText().toString() + "&Apellido=" + campoApellido.getText().toString() + "&Email=" + campoEmail.getText().toString() + "&Grado=" + campoGrado.getText().toString() + "&Grupo=" + campoGrupo.getText().toString() + "&Sexo=" + sexo;
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
