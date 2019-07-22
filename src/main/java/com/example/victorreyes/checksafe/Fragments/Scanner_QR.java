package com.example.victorreyes.checksafe.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victorreyes.checksafe.Entidades.Usuario;
import com.example.victorreyes.checksafe.Entidades.VolleySingleton;
import com.example.victorreyes.checksafe.R;
import com.example.victorreyes.checksafe.Utilidades.RegistroHora;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scanner_QR.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scanner_QR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scanner_QR extends Fragment implements ZXingScannerView.ResultHandler, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variables de la clase
    private ZXingScannerView vistaescaner;
    Boolean selecthour = false;
    //Boolean continuarScan = false;
    String resultado;
    JsonObjectRequest jsonRequest;
    View dialogLayout;

    private AlertDialog dialog;
    private View v;

    private OnFragmentInteractionListener mListener;

    public Scanner_QR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scanner_QR.
     */
    // TODO: Rename and change types and number of parameters
    public static Scanner_QR newInstance(String param1, String param2) {
        Scanner_QR fragment = new Scanner_QR();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner__qr, container, false);
        v = view;

        Escanear();
        abilitarBotones("#192144", "#848484");

        //crear dialog layout
        //crearInstanciaLayout();

        //Botones
        View btnEntrada = view.findViewById(R.id.btnEntrada);
        View btnSalida = view.findViewById(R.id.btnSalida);

        btnEntrada.setOnClickListener(this);
        btnSalida.setOnClickListener(this);


        return v;
    }

    public void crearInstanciaLayout(){

        //layout inflater datos
        LayoutInflater inflaters = getLayoutInflater();
        dialogLayout = inflaters.inflate(R.layout.layout_escaner_usuario, null);
        View btnContinuar = dialogLayout.findViewById(R.id.btnContinuarScan);
        View btnDenegar = dialogLayout.findViewById(R.id.btnDenegarScan);
        btnContinuar.setOnClickListener(this);
        btnDenegar.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btnEntrada) {
            selecthour = false;
            abilitarBotones("#192144", "#848484");
        } else if (id == R.id.btnSalida) {
            selecthour = true;
            abilitarBotones("#848484", "#192144");
        } else if (id == R.id.btnContinuarScan) {
            RegistroHora entrada = new RegistroHora();

            if (selecthour) {
                entrada.horaSalida(getContext(), resultado);
            } else {
                entrada.horaEntrada(getContext(), resultado);
            }
            resultado = "";
            dialog.cancel();
            vistaescaner.resumeCameraPreview(this);
            //dialog.dismiss();
        } else if (id == R.id.btnDenegarScan) {
            dialog.cancel();
            vistaescaner.resumeCameraPreview(this);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void Escanear(){

        vistaescaner = new ZXingScannerView(getContext());
        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.relative_scan_take_single);
        rl.addView(vistaescaner);
        vistaescaner.setResultHandler(this);
        //setContentView(vistaescaner);
        vistaescaner.startCamera();
        vistaescaner.setSoundEffectsEnabled(true);
    }

    public void abilitarBotones(String btnEntrada, String btnSalida){

        v.findViewById(R.id.btnEntrada).setBackgroundColor(Color.parseColor(btnEntrada));
        v.findViewById(R.id.btnSalida).setBackgroundColor(Color.parseColor(btnSalida));
    }


    public void datosLayout(String identificador){

        crearInstanciaLayout();
        final TextView txtVNombreScan = (TextView) dialogLayout.findViewById(R.id.textNombreScan);
        final TextView txtVApellidoScan = (TextView) dialogLayout.findViewById(R.id.textApellidoScan);
        final TextView txtVEmailScan = (TextView) dialogLayout.findViewById(R.id.textEmailScan);
        final TextView txtVGradoScan = (TextView) dialogLayout.findViewById(R.id.textGradoScan);
        final TextView txtVGrupoScan = (TextView) dialogLayout.findViewById(R.id.textGrupoScan);
        final TextView txtVSexoScan = (TextView) dialogLayout.findViewById(R.id.textSexoScan);
        final ImageView campoImageScan = (ImageView) dialogLayout.findViewById(R.id.campoImagenScan);
        String url = "http://192.168.8.103/DataBase_CheckSafe/CheckSafe_DB_Consulta_Usuario.php?NoCuenta=" + identificador;
        jsonRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(getContext(), "Se ha Consultado Exitosamente", Toast.LENGTH_SHORT).show();

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

                Toast.makeText(getContext(),"No se ha podido establecer conexión", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonRequest);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout);
        dialog = builder.show();
        //dialog.show();
    }

    @Override
    public void handleResult(Result result) {

        Log.v("HandleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setTitle("Numero de cuenta:");
        //builder.setMessage(result.getText());
        AlertDialog alerDialog = builder.create();
        alerDialog.show();
        resultado = result.getText();
        datosLayout(resultado);
    }
}
