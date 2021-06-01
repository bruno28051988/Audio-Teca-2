package com.br.audiotecaapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.br.audiotecaapp.R;
import com.br.audiotecaapp.helpers.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        inicializaComponente();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        // Configurações da toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Audioteca");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Configurando a pesquisa
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                deslogarUsuario();
                break;
            case R.id.menuConfiguracoes:
                abrirConfiguracoes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void abrirConfiguracoes() {
        startActivity(new Intent(this, SplashScreenActivity.class));
    }


    private void deslogarUsuario() {
        try {
            autenticacao.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializaComponente(){
        searchView=findViewById(R.id.materialsearchview);
    }
}