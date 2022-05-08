package com.animalhelpapp.proyectofinal_supet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //declaro instancia de Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        //si el usuario ya tiene la sesión iniciada se cierra esta activity
        if (mAuth.getCurrentUser() != null) {
            finish();
        }

        //botón de login
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        //acción ir a RegisterActivity
        TextView txvregistrar = findViewById(R.id.txv_registrar);
        txvregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraRegistrar();
            }
        });
    }

    //método iniciarSesion
    private void iniciarSesion(){
        //conectar parte gráfica
        EditText txv_email = findViewById(R.id.txv_email);
        EditText txv_password = findViewById(R.id.txv_password);

        //leer los campos
        String email = txv_email.getText().toString();
        String password = txv_password.getText().toString();

        //error si algún campo está vacio
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        abrirMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, "No se ha podido iniciar sesión.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    //método iraRegistrar()
    private void iraRegistrar() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    //método abrirMainActivity()
    private void abrirMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
