package com.example.victorreyes.checksafe;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.example.victorreyes.checksafe.Fragments.AlumnosRegistro;
import com.example.victorreyes.checksafe.Fragments.ConsultarAlumno;
import com.example.victorreyes.checksafe.Fragments.ListarUsuarios;
import com.example.victorreyes.checksafe.Fragments.MainCheckSafe;
import com.example.victorreyes.checksafe.Fragments.RegistrarAlumno;
import com.example.victorreyes.checksafe.Fragments.RegistrarUtileria;
import com.example.victorreyes.checksafe.Fragments.Scanner_QR;
import com.example.victorreyes.checksafe.Fragments.UtileriaRegistro;
import com.example.victorreyes.checksafe.Utilidades.Validaciones;
import com.example.victorreyes.checksafe.Utilidades.VariablesEstaticas;


public class CheckSafe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AlumnosRegistro.OnFragmentInteractionListener,
        MainCheckSafe.OnFragmentInteractionListener, RegistrarAlumno.OnFragmentInteractionListener, ListarUsuarios.OnFragmentInteractionListener, ConsultarAlumno.OnFragmentInteractionListener,
        Scanner_QR.OnFragmentInteractionListener, RegistrarUtileria.OnFragmentInteractionListener, UtileriaRegistro.OnFragmentInteractionListener {

    int SOLICITUD_UTILIZAR_CAMARA;
    TextView ipAddress;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_safe);
        //Permiso usuario
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, SOLICITUD_UTILIZAR_CAMARA);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new MainCheckSafe();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.layout_ipaddress, null);
            ipAddress = (TextView) dialogLayout.findViewById(R.id.txtIpAddress);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogLayout);
            dialog = builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Toast.makeText(getApplicationContext(), "Se ha Consultado Exitosamente", Toast.LENGTH_SHORT).show();

        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;


        if (id == R.id.nav_camera) {
            miFragment=new MainCheckSafe();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_gallery) {
            miFragment=new AlumnosRegistro();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_slideshow) {
            miFragment=new UtileriaRegistro();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_manage) {
            miFragment=new Scanner_QR();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (fragmentSeleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickMain( View view){

        switch (view.getId()){

            case R.id.btnCancelarIp:
                dialog.cancel();
                break;
            case R.id.btnContinuarIp:
                if(Validaciones.esIpAddress(ipAddress.getText().toString())){
                    VariablesEstaticas.ipAddress = "http://"+ipAddress.getText().toString();
                }else{

                    Toast.makeText(getApplicationContext(),"No se ingreso una direccion IP correcta", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),"ipAddress"+ipAddress.getText().toString(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
