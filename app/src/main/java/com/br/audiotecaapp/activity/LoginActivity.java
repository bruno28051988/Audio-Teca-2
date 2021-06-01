package com.br.audiotecaapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.br.audiotecaapp.R;
import com.br.audiotecaapp.helpers.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Instanciando os elementos do código
    private Button btnAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Chamando os componentes
       inicializaComponentes();
       autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
       // autenticacao.signOut();
       verificarUsuarioLogado();

        // Configurando o Botão Acessar
        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Recuperando as informações digitadas
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                // Verifica o Email
                if (!email.isEmpty()) {
                    // Verifica a Senha
                    if (!senha.isEmpty()) {

                        // Verificamos o estado do Switch
                        if (tipoAcesso.isChecked()) { // Cadastro
                            autenticacao.createUserWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        // Caso o cadastro seja realizado com sucesso
                                        Toast.makeText(LoginActivity.this,
                                                "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        // Chama a tela de Home
                                        abrirTelaPrincipal();

                                    } else {
                                        // Em caso de erro, mostrar as mensagens correspondentes
                                        String erroExcecao = "";

                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            erroExcecao = "Digite uma senha mais forte!";
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor, digite um e-mail válido!";
                                        } catch (FirebaseAuthUserCollisionException e) {
                                            erroExcecao = "E-mail já cadastrado!";
                                        } catch (Exception e) {
                                            erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                        }

                                        // Montagem da mensagem em caso de erro
                                        Toast.makeText(LoginActivity.this,
                                                "Erro: " + erroExcecao,
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        } else { // Login
                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        // Mensagem de Sucesso
                                        Toast.makeText(LoginActivity.this,
                                                "Logado com sucesso!",
                                                Toast.LENGTH_SHORT).show();

                                        // Chamando a tela principal
                                        abrirTelaPrincipal();
                                    } else {
                                        // Mensagem de Erro
                                        Toast.makeText(LoginActivity.this,
                                                "Erro ao fazer login!" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    } else {
                        // Mensagem em caso de Senha Vazia
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mensagem em caso de E-mail Vazio
                    Toast.makeText(LoginActivity.this,
                            "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


        private void verificarUsuarioLogado(){
            FirebaseUser usuarioLogado = autenticacao.getCurrentUser();
            if( usuarioLogado != null){
                String tipoUsuario = usuarioLogado.getDisplayName();
                abrirTelaPrincipal();
            }
        }


        // Método responsável por abrir a tela principal
        private void abrirTelaPrincipal() {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        // Inicializando os componentes
        private void inicializaComponentes(){
            campoEmail = findViewById(R.id.editCadastroEmail);
            campoSenha = findViewById(R.id.editCadastroSenha);
            btnAcessar = findViewById(R.id.btnEntrar);
            tipoAcesso = findViewById(R.id.switchAcesso);

    }
}