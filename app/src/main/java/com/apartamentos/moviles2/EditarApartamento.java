package com.apartamentos.moviles2;

import android.content.Intent;
import android.os.Bundle;
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

public class EditarApartamento extends AppCompatActivity {

    EditText etCiudad1, etPais1,etDireccion1, etValor1, etResena1;
    Button btnCancelar1,btnGuardar1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_apartamento);
        etCiudad1 = findViewById(R.id.etCiudad1);
        etPais1 = findViewById(R.id.etPais1);
        etDireccion1 = findViewById(R.id.etDireccion1);
        etValor1 = findViewById(R.id.etValor1);
        etResena1 = findViewById(R.id.etResena1);

        String ciudad = getIntent().getStringExtra("ciudad");
        String pais = getIntent().getStringExtra("pais");
        String direccion = getIntent().getStringExtra("direccion");
        String valor = getIntent().getStringExtra("valor");
        String resena = getIntent().getStringExtra("resena");


        etCiudad1.setText(ciudad);
        etPais1.setText(pais);
        etDireccion1.setText(direccion);
        etValor1.setText(valor);
        etResena1.setText(resena);
    }


    public void actualizarApartamento(View view) {
        Map<String, Object> apartments = new HashMap<>();
        String ciudad = etCiudad1.getText().toString();
        String Pais = etPais1.getText().toString();
        String Direccion = etDireccion1.getText().toString();
        String Valor = etValor1.getText().toString();
        String Reseña = etResena1.getText().toString();
        apartments.put("ciudad", ciudad);
        apartments.put("pais", Pais);
        apartments.put("direccion", Direccion);
        apartments.put("valor", Valor);
        apartments.put("resena", Reseña);


        DocumentReference apartmentsRef = db.collection("apartments").document(getIntent().getStringExtra("idApartment"));
        apartmentsRef
                .update(apartments)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditarApartamento.this, "Apartamento Actualizado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent ( EditarApartamento.this, Apartamentos.class);
                        intent.putExtra("idUsuario",getIntent().getStringExtra("idUsuario"));
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditarApartamento.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void cancelar (View view){
        Intent intent = new Intent ( this, Apartamentos.class);
        intent.putExtra("idUsuario",getIntent().getStringExtra("idUsuario"));
        startActivity(intent);
    }
}