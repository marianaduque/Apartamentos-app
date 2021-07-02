package com.apartamentos.moviles2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.UserModel;

public class Usuarios extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreUserList;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        rvFirestoreUserList = findViewById(R.id.rvFirestoreUserList);

         //query a la base de datos ()
        Query query = db.collection("users");

    //configurar opciones del adaptador
    FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
            .setQuery(query, UserModel.class)
            .build();

    adapter = new FirestoreRecyclerAdapter<UserModel, UsersViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserModel model) {
            //asigna a los datos cada elemento
            holder.tvName.setText(model.getName());
            holder.tvCorreo.setText(model.getCorreo());
            holder.tvCiudad.setText(model.getCiudad());
            holder.tvContraseña.setText(model.getContraseña());
            String id = getSnapshots().getSnapshot(position).getId();
            holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   deleteUser(id);
                }
            });
            holder.btnEditUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = getSnapshots().getSnapshot(position).getId();
                    Intent intent = new Intent(Usuarios.this,ActualizarUsuario.class);
                    intent.putExtra("nombre", model.getName());
                    intent.putExtra("correo", model.getCorreo());
                    intent.putExtra("ciudad", model.getCiudad());
                    intent.putExtra("password",model.getContraseña());
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });
            holder.btnAgregarApartamentos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = getSnapshots().getSnapshot(position).getId();
                    Intent intent = new Intent ( Usuarios.this, Apartamentos.class);
                    intent.putExtra("idUsuario", id);
                    startActivity(intent);
                }
            });

        }

        @NonNull
        @Override
        public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // crea un elemtento grafico por cada item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_editar_usuarios, parent, false);
            return new UsersViewHolder(view);
        }
    };
        rvFirestoreUserList.setHasFixedSize(true);
        rvFirestoreUserList.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreUserList.setAdapter(adapter);


}


 private class UsersViewHolder extends RecyclerView.ViewHolder{
    TextView tvName,tvCorreo,tvCiudad,tvContraseña;
    Button btnEditUser, btnDeleteUser,btnAgregarApartamentos;
    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R. id.tvName);
        tvCorreo = itemView.findViewById(R.id.tvCorreo);
        tvCiudad = itemView.findViewById(R.id.tvCiudad);
        tvContraseña = itemView.findViewById(R.id.tvContraseña);
        btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        btnEditUser = itemView.findViewById(R.id.btnEditApartment);
        btnAgregarApartamentos = itemView.findViewById(R.id.btnAgregarApartamentos);
    }
}
    public void deleteUser(String id) {
        db.collection("users").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Usuarios.this, "Documento Eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Usuarios.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}