package com.apartamentos.moviles2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActualizarUsuario extends AppCompatActivity {


    EditText etName, etCorreo, etCiudad, etPassword;
    RadioButton rbAnfitrion;
    RadioButton rbPropietario;
    Button btnSend, btnRegistro;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
        etName = findViewById(R.id.etName);
        etCorreo = findViewById(R.id.etCorreo);
        etCiudad = findViewById(R.id.etCiudad);
        etPassword = findViewById(R.id.etPassword);
        rbAnfitrion = findViewById(R.id.rbAnfitrion);
        rbPropietario = findViewById(R.id.rbPropietario);

        String name =  getIntent().getStringExtra("nombre");
        String correo =  getIntent().getStringExtra("correo");
        String ciudad =  getIntent().getStringExtra("ciudad");
        String password =  getIntent().getStringExtra("password");

        etName.setText(name);
        etCorreo.setText(correo);
        etCiudad.setText(ciudad);
        etPassword.setText(password);

    }

    public void actualizarUser(View view) {
        Map<String, Object> user = new HashMap<>();
        String Name = etName.getText().toString();
        String Correo = etCorreo.getText().toString();
        String Ciudad = etCiudad.getText().toString();
        String Contraseña = etPassword.getText().toString();
        user.put("name", Name);
        user.put("correo", Correo);
        user.put("ciudad", Ciudad);
        user.put("contraseña", Contraseña);

        DocumentReference userRef = db.collection("users").document(getIntent().getStringExtra("id"));
        userRef
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ActualizarUsuario.this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent ( ActualizarUsuario.this, Usuarios.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActualizarUsuario.this, "Error Usuario Actualizado", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void cancel (View view){
        Intent intent = new Intent ( this, Usuarios.class);
        startActivity(intent);
    }
}













