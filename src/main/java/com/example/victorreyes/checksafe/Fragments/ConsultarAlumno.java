package com.example.victorreyes.checksafe.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victorreyes.checksafe.Entidades.Usuario;
import com.example.victorreyes.checksafe.Entidades.VolleySingleton;
import com.example.victorreyes.checksafe.R;
import com.example.victorreyes.checksafe.Utilidades.VariablesEstaticas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultarAlumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultarAlumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarAlumno extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Variables de la clase
    EditText campoId;
    TextView txtVNombre, txtVApellido, txtVEmail, txtVGrado, txtVGrupo, txtVSexo;
    ImageView campoImagen;
    //Button RegistButton;

    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public ConsultarAlumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarAlumno.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarAlumno newInstance(String param1, String param2) {
        ConsultarAlumno fragment = new ConsultarAlumno();
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
        View view = inflater.inflate(R.layout.fragment_consultar_alumno, container, false);
        campoId = (EditText) view.findViewById(R.id.txtNoCuentaConsulta);
        txtVNombre = (TextView) view.findViewById(R.id.textNombre);
        txtVApellido = (TextView) view.findViewById(R.id.textApellido);
        txtVEmail = (TextView) view.findViewById(R.id.textEmail);
        txtVGrado = (TextView) view.findViewById(R.id.textGrado);
        txtVGrupo = (TextView) view.findViewById(R.id.textGrupo);
        txtVSexo = (TextView) view.findViewById(R.id.textSexo);
        campoImagen = (ImageView) view.findViewById(R.id.imgConsultarUsuarios);

        //Botones

        View btnConsultar = view.findViewById(R.id.btnConsulta);
        btnConsultar.setOnClickListener(this);
        return view;
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
        String id = campoId.getText().toString();
        cargarWebService(id);
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


    public void cargarWebService(String id) {

        String ipAddress = VariablesEstaticas.ipAddress;
        String url = ipAddress+"/DataBase_CheckSafe/CheckSafe_DB_Consulta_Usuario.php?NoCuenta=" + id;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "Se Ha Generado Un Error" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.w("ERROR",error.toString());
    }

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

        txtVNombre.setText("Nombre: " + miUsuario.getNombre());
        txtVApellido.setText("Apellido: " + miUsuario.getApellido());
        txtVEmail.setText("Email: " + miUsuario.getEmail());
        txtVGrado.setText("Grado: " + miUsuario.getGrado());
        txtVGrupo.setText("Grupo: " + miUsuario.getGrupo());
        txtVSexo.setText("Sexo: " + miUsuario.getSexo());
        if(miUsuario.getImagen()!=null){

            campoImagen.setImageBitmap(miUsuario.getImagen());
        }else {

            campoImagen.setImageResource(R.drawable.img_base);
        }


    }
}
