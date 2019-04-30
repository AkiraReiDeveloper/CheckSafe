package com.example.victorreyes.checksafe;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;

public class EnviarEmail {

    public void enviarImagen(String nombreCarpeta, String nombreArchivo, String emailDestino, String emailCopia, Context context){

        String imgFile = String.valueOf(new File(Environment.getExternalStorageDirectory() + nombreCarpeta+"/",nombreArchivo + ".jpg" ));


        Uri fileUri;
        fileUri = FileProvider.getUriForFile(context, "com.myfileprovider", new File(imgFile));
        //Validación de acuerdo al OS.
        /*if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M){


        }else {

            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.N) {
                fileUri =Uri.parse(pdfFile);

            } else{
                fileUri =Uri.fromFile((new File(Environment.getExternalStorageDirectory().getAbsolutePath() + nombreCarpeta, nombreArchivo + ".jpg" )));
            }
        }*/


        //Instanciamos un Intent del tipo ACTION_SEND
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        //Aqui definimos la tipologia de datos del contenido dle Email en este caso text/html
        emailIntent.setType("plain/text");

        // Indicamos con un Array de tipo String las direcciones de correo a las cuales enviar
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailDestino});

        // Aqui definimos un email para enviar como copia
        emailIntent.putExtra(Intent.EXTRA_CC, new String[]{emailCopia});

        // Aqui definimos un titulo para el Email
        emailIntent.putExtra(Intent.EXTRA_TITLE, "Identificador QR Gastronomia 2019");

        // Aqui definimos un Asunto para el Email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, " Identificador QR Gastronomia 2019 ");

        // Aqui obtenemos la referencia al texto y lo pasamos al Email Intent
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Con motivo de hacerle llegar su identificador de codigo QR para el ingreso al area de cocina de la facultad de gastronomia");

        //Permisos de Lectura URI
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Colocamos el adjunto en el stream
        emailIntent.putExtra(Intent.EXTRA_STREAM, fileUri);

        //indicamos el tipo de dato
        emailIntent.setType("image/*");
        //emailIntent.setData(uri);

        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            context.startActivity(Intent.createChooser(emailIntent, "Selecciona metodo de envio..."));
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "No hay ninguna aplicación de correo electrónico instalada.", Toast.LENGTH_LONG).show();
        }
    }
}