package com.example.victorreyes.checksafe.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victorreyes.checksafe.Adapters.UsuariosAdapter;
import com.example.victorreyes.checksafe.Entidades.Usuario;
import com.example.victorreyes.checksafe.Entidades.VolleySingleton;
import com.example.victorreyes.checksafe.R;
import com.example.victorreyes.checksafe.Utilidades.VariablesEstaticas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListarUsuarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListarUsuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarUsuarios extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //variables de la clase
    RecyclerView recyclerUsuarios;
    ArrayList<Usuario> listaUsuarios;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public ListarUsuarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarUsuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarUsuarios newInstance(String param1, String param2) {
        ListarUsuarios fragment = new ListarUsuarios();
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
        //return inflater.inflate(R.layout.fragment_listar_usuarios, container, false);
        View view = inflater.inflate(R.layout.fragment_listar_usuarios, container, false);

        listaUsuarios = new ArrayList<>();
        recyclerUsuarios = (RecyclerView) view.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerUsuarios.setHasFixedSize(true);
        cargarWebService();

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
    private void cargarWebService() {

        String ipAddress = VariablesEstaticas.ipAddress;
        String url = ipAddress+"/DataBase_CheckSafe/CheckSafe_DB_Consulta_Lista_Usuario.php";
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

        Usuario usuario=null;

        JSONArray json=response.optJSONArray("usuarios");

        try {

            for (int i=0;i<json.length();i++){

                usuario=new Usuario();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                usuario.setId(jsonObject.optInt("NoCuenta"));
                usuario.setNombre(jsonObject.optString("Nombre"));
                usuario.setApellido(jsonObject.optString("Apellido"));
                usuario.setEmail(jsonObject.optString("Email"));
                usuario.setGrado(jsonObject.optInt("Grado"));
                usuario.setGrupo(jsonObject.optString("Grupo"));
                usuario.setSexo(jsonObject.optString("Sexo"));
                usuario.setDato(jsonObject.optString("Foto"));
                listaUsuarios.add(usuario);
            }
            //progress.hide();
            UsuariosAdapter adapter=new UsuariosAdapter(listaUsuarios);
            recyclerUsuarios.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            //progress.hide();
        }

    }
}
