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

import Models.ApartmentsModel;

public class Apartamentos extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreApartmentsList;
    FirestoreRecyclerAdapter adapter;
    String idUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamentos);
        rvFirestoreApartmentsList = findViewById(R.id.rvFirestoreApartmentsList);
        this.idUsuario = getIntent().getStringExtra("idUsuario");
        //query a la base de datos ()
        Query query = db.collection("apartments").whereEqualTo("idPropietario", getIntent().getStringExtra("idUsuario"));

        //configurar opciones del adaptador
       FirestoreRecyclerOptions<ApartmentsModel> options = new FirestoreRecyclerOptions.Builder<ApartmentsModel>()
                .setQuery(query, ApartmentsModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ApartmentsModel, ApartmentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ApartmentsViewHolder holder, int position, @NonNull ApartmentsModel model) {
                //asigna a los datos cada elemento
                holder.tvCiudadApt.setText(model.getCiudad());
                holder.tvPais.setText(model.getPais());
                holder.tvDireccion.setText(model.getDireccion());
                holder.tvnValor.setText(model.getValor());
                holder.tvResena.setText(model.getResena());
                String id = getSnapshots().getSnapshot(position).getId();
                holder.btnEliminarApartment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Apartamentos.this, "" + id,Toast.LENGTH_SHORT).show();
                        deleteApartment(id);
                    }
                });
                holder.btnEditApartment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Apartamentos.this,EditarApartamento.class);
                        intent.putExtra("ciudad", model.getCiudad());
                        intent.putExtra("pais", model.getPais());
                        intent.putExtra("direccion", model.getDireccion());
                        intent.putExtra("resena",model.getResena());
                        intent.putExtra("valor",model.getValor());
                        intent.putExtra("idApartment",id);
                        intent.putExtra("idUsuario", getIntent().getStringExtra("idUsuario"));
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ApartmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // crea un elemtento grafico por cada item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_agregar_apartamentos, parent, false);
                return new ApartmentsViewHolder(view);
            }
        };
        rvFirestoreApartmentsList.setHasFixedSize(true);
        rvFirestoreApartmentsList.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreApartmentsList.setAdapter(adapter);

    }

    private class ApartmentsViewHolder extends RecyclerView.ViewHolder{
        TextView tvCiudadApt,tvPais,tvDireccion,tvnValor,tvResena;
        Button btnEditApartment, btnEliminarApartment;
        public ApartmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCiudadApt = itemView.findViewById(R. id.tvCiudadApt);
            tvPais = itemView.findViewById(R.id.tvPais);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvnValor = itemView.findViewById(R.id.tvnValor);
            tvResena = itemView.findViewById(R.id.tvResena);
            btnEditApartment = itemView.findViewById(R.id.btnEditApartment);
            btnEliminarApartment = itemView.findViewById(R.id.btnEliminarApartment);
        }
    }

    public void deleteApartment(String id) {
        db.collection("apartments").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Apartamentos.this, "Apartamento Eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Apartamentos.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void addApartments (View view){
        Intent intent = new Intent ( this, RegistroApartamentos.class);
        intent.putExtra("idUsuario", this.idUsuario);
        startActivity(intent);
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