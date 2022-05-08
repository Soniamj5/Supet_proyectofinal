package com.animalhelpapp.proyectofinal_supet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //declarar variables globales
    TextView nombre_regis, email_regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializar firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //obtener usuario registrado/iniciado sesión
        FirebaseUser usuarioActual = mAuth.getCurrentUser();
        //si no hay ningún usuario
        if (usuarioActual == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            //conectar parte gráfica
            nombre_regis = findViewById(R.id.nombre_regis);
            email_regis = findViewById(R.id.email_regis);

            //botón de salir
            Button btn_salir = findViewById(R.id.btn_salir);
            btn_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cerrarSesion();
                }
            });

            //conectar con la base de datos
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference reference = db.getReference("users").child(usuarioActual.getUid());
            //actualizar db con listener cada vez que haya una modificación
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        nombre_regis.setText("Nombre: " + user.nombre);
                        email_regis.setText("Correo electrónico: " + user.email);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    //método cerrarSesion()
    private void cerrarSesion() {
        //cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut();
        //cambiar de activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}