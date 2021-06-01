package com.br.audiotecaapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.br.audiotecaapp.R;
import com.br.audiotecaapp.helpers.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        // Configurações da toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações do usuário");
        setSupportActionBar(toolbar);
        // Para mostrar a seta de voltar para home
        // Necessário configurar no AndroidManifests
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


}