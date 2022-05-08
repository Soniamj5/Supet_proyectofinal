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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    //instancia firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        //si el usuario ya está registrado se cierra esta activity
        if (mAuth.getCurrentUser() != null) {
            finish();
        }

        //Botón registrar
        Button btnRegistrar = findViewById(R.id.btn_registrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        //acción volver a loginactivity
        TextView txvlogin = findViewById(R.id.txv_login);
        txvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverLogin();
            }
        });
    }
    //método registrarUsuario
    private void registrarUsuario() {
        //conectar con la parte gráfica
        TextView txv_nombre = findViewById(R.id.txv_nombre);
        EditText email2 = findViewById(R.id.email2);
        EditText password2 = findViewById(R.id.password2);
        EditText password3 = findViewById(R.id.password3);

        //leer los campos
        String nombre = txv_nombre.getText().toString();
        String email = email2.getText().toString();
        String contrasena = password2.getText().toString();
        String repeatcontrasena = password3.getText().toString();

        //error si algún campo está vacio
        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty() || repeatcontrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        } else if (!repeatcontrasena.equals(contrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //creamos un objeto para inicializar los datos del nuevo usuario
                    User user = new User(nombre, email);
                    //guardar los datos del nuevo usuario en la base de datos
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(task1 -> abrirMainActivity());
                } else {
                    Toast.makeText(RegisterActivity.this, "Usuario no creado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //método volverLogin
    private void volverLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //método abrirMainActivity
    private void abrirMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
