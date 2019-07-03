package com.example.victorreyes.checksafe;

import android.Manifest;
import android.accounts.AuthenticatorException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.WriterException;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {

    EditText campoId, campoNombre, campoApellido, campoEmail, campoGrado, campoGrupo;//contenedores para los valores cachados de los EditText
    RadioButton campoFemenino, campoMasculino;//contenedores para RadioButton
    String campoSexo = "";
    Bitmap bitmap, bmap;//Aqui se almacenan imagenes
    ImageView imagen;//contenedor para ImagenView
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String mipath;//almacena la ruta de la imagen
    File fileImagen;
    public static final int COD_GALERIA = 10;
    public static final int COD_FOTO = 20;


    RequestQueue request;
    StringRequest stringRequest;

    //permission STORAGE
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            // We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CAMERA},5
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
    //Muestra un dialog con opciones
    private void mostrarDialogOpciones() {

        final CharSequence[] opciones = { "Tomar Foto", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if(opciones[i].equals("Tomar Foto")){

                    abriCamara();
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

    //
    private void abriCamara() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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

            /*
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
            {
                String authorities=this.getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(this,authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }*/
            startActivityForResult(intent,COD_FOTO);
        }

    }


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

            case COD_FOTO:
                MediaScannerConnection.scanFile(this, new String[]{mipath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String mipath, Uri uri) {
                                Log.i("Path",""+ mipath);
                            }
                        });

                bitmap= BitmapFactory.decodeFile(mipath);
                imagen.setImageBitmap(bitmap);

                break;
        }
        bitmap=redimencionarImagen(bitmap, 600, 800);
    }

    private Bitmap redimencionarImagen(Bitmap bitmap, float ancho, float alto) {

        int anchoImg = bitmap.getWidth();
        int altoImg = bitmap.getHeight();

        if(anchoImg>ancho || altoImg>alto){

            float escalaAncho = ancho/anchoImg;
            float escalaAlto = alto/altoImg;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,anchoImg,altoImg,matrix,false);
        }else{

            return bitmap;
        }

    }

    public void onClick(View view){

        cargarWebSevice();
        GenerarCodigoQR();
    }

    private void GenerarCodigoQR() {
        //instacia de variable de tipo GenerarQR
        GenerarQR generarQR = new GenerarQR();
        //instancia de variable de tipo EnviarEmail
        EnviarEmail mail = new EnviarEmail();

        if(campoId.getText().toString()!=null){
            try {
                String NameOfImage = campoId.getText().toString()  + "_" +  campoNombre.getText().toString()  + "_" +  campoGrado.getText().toString()  + "_" +  campoGrupo.getText().toString()+ "_" + generarQR.getCurrentDateAndTime();
                String NameOfFolder = "/Gastronomia " + "Salon: " +  campoGrado.getText().toString()  + "_" +  campoGrupo.getText().toString();
                bmap = generarQR.TextToImageEncode(campoId.getText().toString(), this);
                generarQR.saveImage(bmap, this, NameOfImage, NameOfFolder );  //give read write permission
                //Metodo de la clase enviar EnviarEmail para enviar email
                mail.enviarImagen(NameOfFolder, NameOfImage, campoEmail.getText().toString(), campoEmail.getText().toString(), this);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }

    private void cargarWebSevice() {



        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_RegistrarUsuario.php?";
        //url = url.replace(" ", "%20");

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if(response.trim().equalsIgnoreCase("registra")){

                    campoId.setText("");
                    campoNombre.setText("");
                    campoApellido.setText("");
                    campoEmail.setText("");
                    campoGrupo.setText("");
                    campoGrado.setText("");

                    Toast.makeText(getApplicationContext(),"Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getApplicationContext(),"No se ha registrado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"No se ha podido establecer conexión", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String NoCuenta = campoId.getText().toString();
                String Nombre = campoNombre.getText().toString();
                String Apellido = campoApellido.getText().toString();
                String Email = campoEmail.getText().toString();
                String Grado = campoGrado.getText().toString();
                String Grupo = campoGrupo.getText().toString();
                if (campoMasculino.isChecked()==true) {

                    campoSexo = "Masculino";
                } else
                if (campoFemenino.isChecked()==true) {

                    campoSexo = "Femenino";
                }

                String Imagen = convertirImgString(bitmap);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("NoCuenta", NoCuenta);
                parametros.put("Nombre", Nombre);
                parametros.put("Apellido", Apellido);
                parametros.put("Email", Email);
                parametros.put("Grado", Grado);
                parametros.put("Grupo", Grupo);
                parametros.put("Sexo", campoSexo);
                parametros.put("Foto", Imagen);

                return parametros;
            }

        };
        //Toast.makeText(getApplicationContext(), "Sexo:"+campoSexo,Toast.LENGTH_LONG).show();
        request.add(stringRequest);
    }

    private String convertirImgString(Bitmap bitmap){

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
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
