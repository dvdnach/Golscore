package com.example.dm2.golscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.dm2.golscore.Adapter.CambioAdapter;
import com.example.dm2.golscore.Adapter.CategoriaAdapter;
import com.example.dm2.golscore.Clases.Cambio;
import com.example.dm2.golscore.Clases.Categoria;
import com.example.dm2.golscore.Clases.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView categoriaRV;
    private List<Categoria> listaCategoria;
    private CategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        categoriaRV=(RecyclerView) findViewById(R.id.categoriaRV);
        categoriaRV.setLayoutManager(new LinearLayoutManager(this));

        listaCategoria =new ArrayList<>();
        adapter= new CategoriaAdapter(listaCategoria);

        categoriaRV.setAdapter(adapter);

        FirebaseDatabase dbCategoria=FirebaseDatabase.getInstance();
        dbCategoria.getReference().child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaCategoria.removeAll(listaCategoria);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Categoria categoria =snapshot.getValue(Categoria.class);
                    listaCategoria.add(categoria);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Snackbar.make(getCurrentFocus(), "¿Deseas salir de la aplicación?", Snackbar.LENGTH_LONG)
            .setAction("Salir", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            })
            .show();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        } if (id == R.id.nav_location) {
            intent=new Intent(MainActivity.this,LocalizacionTodosEstadio.class);
            startActivity(intent);
        } else if (id == R.id.nav_primera) {
            intent=new Intent(MainActivity.this,GrupoActivity.class);
            intent.putExtra("nombreCategoria","Primera Autonómica");
            intent.putExtra("idCategoria","1");
            startActivity(intent);
        } else if (id == R.id.nav_preferente) {
            intent=new Intent(MainActivity.this,GrupoActivity.class);
            intent.putExtra("nombreCategoria","Preferente");
            intent.putExtra("idCategoria","2");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
