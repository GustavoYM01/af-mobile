package com.example.af_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText inptEmail;
    private EditText inptSenha;
    private Button btnLogin, btnCriarConta;
    private TextView textCallback;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        inptEmail = findViewById(R.id.inptEmail);
        inptSenha = findViewById(R.id.inptSenha);
        btnLogin = findViewById(R.id.btnLogin);
        textCallback = findViewById(R.id.textoCallBack);
        btnCriarConta = findViewById(R.id.btnCriarConta);

        btnLogin.setOnClickListener(view -> entrar());
        btnCriarConta.setOnClickListener(view -> criarUsuario());
    }

    private void entrar() {
        String email = inptEmail.getText().toString();
        String senha = inptSenha.getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {
            textCallback.setText("AGUARDE UM MOMENTO...");
            auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            textCallback.setText("BEM-VINDO(A) " + auth.getCurrentUser().getEmail().toString().split("@")[0]);
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            inptEmail.setText("");
                            inptSenha.setText("");
                            textCallback.setText("");
                        }
                        else {
                            textCallback.setText("");
                            if (task.getException().getMessage().toLowerCase().contains("credential is incorrect"))
                                Toast.makeText(MainActivity.this, "ERRO: credenciais inválidas!", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "ERRO: tente novamente mais tarde", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else if (email.isEmpty() && !senha.isEmpty())
            Toast.makeText(MainActivity.this, "email está em branco!", Toast.LENGTH_LONG).show();
        else if (senha.isEmpty() && !email.isEmpty())
            Toast.makeText(MainActivity.this, "senha está em branco!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "email e senha estão em branco!", Toast.LENGTH_LONG).show();
    }

    private void criarUsuario() {
        String email = inptEmail.getText().toString();
        String senha = inptSenha.getText().toString();

        if (!email.isEmpty() && !senha.isEmpty()) {
            textCallback.setText("AGUARDE UM MOMENTO...");
            auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            textCallback.setText("BEM-VINDO(A) " + auth.getCurrentUser().getEmail().toString().split("@")[0]);
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            inptEmail.setText("");
                            inptSenha.setText("");
                            textCallback.setText("");
                        }
                        else {
                            textCallback.setText("");
                            Toast.makeText(MainActivity.this, "ERRO: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else if (email.isEmpty() && !senha.isEmpty())
            Toast.makeText(MainActivity.this, "email está em branco!", Toast.LENGTH_LONG).show();
        else if (senha.isEmpty() && !email.isEmpty())
            Toast.makeText(MainActivity.this, "senha está em branco!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "email e senha estão em branco!", Toast.LENGTH_LONG).show();
    }
}