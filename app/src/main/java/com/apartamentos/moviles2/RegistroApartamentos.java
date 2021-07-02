package com.apartamentos.moviles2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistroApartamentos extends AppCompatActivity {
    EditText etCiudad, etPais, etDireccion, etValor,etResena;
    Button btnGuardar, btnCancelar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_apartamentos);
        etCiudad = findViewById(R.id.etCiudad);
        etPais = findViewById(R.id.etPais);
        etDireccion = findViewById(R.id.etDireccion);
        etValor = findViewById(R.id.etValor);
        etResena = findViewById(R.id.etResena);
    }
    public void saveApartment(View view){
        Map<String, Object> user = new HashMap<>();
        String Ciudad = etCiudad.getText().toString();
        String Pais = etPais.getText().toString();
        String Direccion = etDireccion.getText().toString();
        String Valor = etValor.getText().toString();
        String resena = etResena.getText().toString();

        user.put("ciudad", Ciudad);
        user.put("pais",  Pais );
        user.put("direccion", Direccion);
        user.put("valor", Valor);
        user.put("resena", resena);
        user.put("idPropietario", getIntent().getStringExtra("idUsuario"));

        db.collection("apartments")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegistroApartamentos.this, "Apartamento Registrado Correctamente", Toast.LENGTH_SHORT).show();
                        Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent intent = new Intent(RegistroApartamentos.this, Usuarios.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroApartamentos.this, "Hubo Un Error ", Toast.LENGTH_SHORT).show();
                        Log.w("firebase.", "Error adding users", e);
                        Intent intent = new Intent(RegistroApartamentos.this, Usuarios.class);
                        startActivity(intent);
                    }
                });

    }
    public void cancelar (View view){
        Intent intent = new Intent ( this, Usuarios.class);
        startActivity(intent);
    }
}