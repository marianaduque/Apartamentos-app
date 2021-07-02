package com.apartamentos.moviles2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrousuario extends AppCompatActivity {

    EditText etName, etCorreo, etCiudad, etContraseña;
    RadioButton rbAnfitrion;
    RadioButton rbPropietario;
    Button btnSend, btnRegistro;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrousuario);
        etName = findViewById(R.id.etName);
        etCorreo = findViewById(R.id.etCorreo);
        etCiudad = findViewById(R.id.etCiudad);
        etContraseña = findViewById(R.id.etContraseña);
        rbAnfitrion = findViewById(R.id.rbAnfitrion);
        rbPropietario = findViewById(R.id.rbPropietario);
        // Create a new user with a first and last name

    }
    public void  registerUser(String Correo, String Contraseña){
        mAuth.createUserWithEmailAndPassword(Correo, Contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Toast.makeText(Registrousuario.this,"Usuario registrado",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registrousuario.this,"error registrando usuario",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public void saveUser(View view){
        Map<String, Object> user = new HashMap<>();
        String Name = etName.getText().toString();
        String Correo = etCorreo.getText().toString();
        String Ciudad = etCiudad.getText().toString();
        String Contraseña = etContraseña.getText().toString();
        String Anfitrion = rbAnfitrion.getText().toString();
        String Propietario = rbPropietario.getText().toString();
        user.put("name", Name);
        user.put("correo",  Correo );
        user.put("ciudad", Ciudad);
        user.put("contraseña", Contraseña);

        if(rbAnfitrion.isChecked()) {
            user.put("Rol", Anfitrion);
        }else if (rbPropietario.isChecked()){
            user.put("Rol", Propietario);
        }

        this.registerUser(Correo, Contraseña);
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       Toast.makeText(Registrousuario.this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
                       Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registrousuario.this, "Hubo Un Error ", Toast.LENGTH_SHORT).show();
                        Log.w("firebase.", "Error adding users", e);
                    }
                });

        Intent intent = new Intent(Registrousuario.this, MainActivity.class);
        startActivity(intent);
    }


    public void MainActivity (View view){
        Intent intent = new Intent ( this, MainActivity.class);
        //Intent intent = new Intent ( this, Usuarios.class);
        startActivity(intent);
    }

    public void goToLogin (View view){
        Intent intent = new Intent ( this, MainActivity.class);
        startActivity(intent);
    }

}
