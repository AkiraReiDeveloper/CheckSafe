package com.example.victorreyes.checksafe.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.victorreyes.checksafe.Entidades.Usuario;
import com.example.victorreyes.checksafe.R;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosHolder> {

    List<Usuario> listaUsuarios;

    public UsuariosAdapter(List<Usuario> listaUsuarios){

        this.listaUsuarios = listaUsuarios;

    }
    @Override
    public UsuariosHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listar_usuarios,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder( UsuariosAdapter.UsuariosHolder holder, int position) {

        holder.textNoCuenta.setText(listaUsuarios.get(position).getId().toString());
        holder.textNombre.setText(listaUsuarios.get(position).getNombre().toString());
        holder.textApellidos.setText(listaUsuarios.get(position).getApellido().toString());
        holder.textEmail.setText(listaUsuarios.get(position).getEmail().toString());
        holder.textGrado.setText(listaUsuarios.get(position).getGrado().toString());
        holder.textGrupo.setText(listaUsuarios.get(position).getGrupo().toString());
        holder.textSexo.setText(listaUsuarios.get(position).getSexo().toString());

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class  UsuariosHolder extends RecyclerView.ViewHolder{

        TextView textNoCuenta, textNombre, textApellidos, textEmail, textGrado, textGrupo, textSexo;

        public UsuariosHolder(View itemView){
            super(itemView);
            textNoCuenta = itemView.findViewById(R.id.textNoCuentaU);
            textNombre = itemView.findViewById(R.id.textNombreU);
            textApellidos = itemView.findViewById(R.id.textApellidosU);
            textEmail = itemView.findViewById(R.id.textEmailU);
            textGrado = itemView.findViewById(R.id.textGradoU);
            textGrupo = itemView.findViewById(R.id.textGrupoU);
            textSexo = itemView.findViewById(R.id.textSexoU);
        }
    }
}
