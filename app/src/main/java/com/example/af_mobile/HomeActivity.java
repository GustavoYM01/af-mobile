package com.example.af_mobile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.af_mobile.models.Serie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Serie> serieArrayList;
    RecyclerView recyclerView;
    SerieAdapter serieAdapter;

    Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        FirebaseApp.initializeApp(HomeActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recycleViewSeries);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        serieArrayList = new ArrayList<>();

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAdicionar viewDialogAdicionar = new ViewDialogAdicionar();
                viewDialogAdicionar.showDialog(HomeActivity.this);
            }
        });
        
        readData();
        
//        TextView vazio = findViewById(R.id.vazio);
//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        FloatingActionButton add = findViewById(R.id.addSerie);
//
//        add.setOnClickListener(view -> {
//            View view1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.add_serie_dialog, null, false);
//
////            TextInputLayout nomeSerieLayout, diaSemanaLayout, plataformaLayout, temporadaLayout, ultimoEpAssLayout;
////            nomeSerieLayout = view1.findViewById(R.id.nomeSerieLayout);
////            diaSemanaLayout = view1.findViewById(R.id.diaSemanaLayout);
////            plataformaLayout = view1.findViewById(R.id.plataformaLayout);
////            temporadaLayout = view1.findViewById(R.id.temporadaLayout);
////            ultimoEpAssLayout = view1.findViewById(R.id.ultimoEpAssLayout);
//
//            TextInputEditText nomeSerieInpt, diaSemanaInpt, plataformaInpt, temporadaInpt, ultimoEpAssInpt;
//            nomeSerieInpt = view1.findViewById(R.id.nomeSerieInpt);
//            diaSemanaInpt = view1.findViewById(R.id.diaSemanaInpt);
//            plataformaInpt = view1.findViewById(R.id.plataformaInpt);
//            temporadaInpt = view1.findViewById(R.id.temporadaInpt);
//            ultimoEpAssInpt = view1.findViewById(R.id.ultimoEpAssInpt);
//
//            AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this)
//                    .setTitle("Adicionar")
//                    .setView(view1)
//                    .setPositiveButton("Adicionar", (dialogInterface, i) -> {
//                        // VERIFICA CAMPOS EM BRANCO
//                        if (Objects.requireNonNull(nomeSerieInpt.getText()).toString().isEmpty() || Objects.requireNonNull(diaSemanaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(plataformaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(temporadaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(ultimoEpAssInpt.getText()).toString().isEmpty()
//                        )
//                            Toast.makeText(HomeActivity.this, "1 ou mais campos estão vazios!", Toast.LENGTH_SHORT).show();
//                        else {
//                            ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
//                            dialog.setMessage("Aguarde, salvando no banco de dados...");
//                            dialog.show();
//
//                            Serie s = new Serie();
//
//                            s.setNome(nomeSerieInpt.getText().toString());
//                            s.setDiaSemana(diaSemanaInpt.getText().toString());
//                            s.setPlataforma(plataformaInpt.getText().toString());
//                            s.setTemporadaAtual(Integer.parseInt(temporadaInpt.getText().toString()));
//                            s.setUltimoEp(Integer.parseInt(ultimoEpAssInpt.getText().toString()));
//
//                            database.getReference().child("series").push().setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    dialog.dismiss();
//                                    dialogInterface.dismiss();
//                                    Toast.makeText(HomeActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    dialog.dismiss();
//                                    Toast.makeText(HomeActivity.this, "Ocorreu um erro, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    })
//                    .setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss())
//                    .create();
//            alertDialog.show();
//        });
//
//        database.getReference().child("series").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<Serie> arrayList = new ArrayList<>();
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                    Serie serie = dataSnapshot.getValue(Serie.class);
//                    Objects.requireNonNull(serie).setChave(dataSnapshot.getKey());
//                    arrayList.add(serie);
//                }
//
//                if (arrayList.isEmpty())
//                    vazio.setVisibility(View.VISIBLE);
//                else
//                    vazio.setVisibility(View.GONE);
//
//                SerieAdapter adapter = new SerieAdapter(HomeActivity.this, arrayList);
//                recyclerView.setAdapter(adapter);
//
//                adapter.setOnItemClickListener(new SerieAdapter.OnItemClickListener() {
//                    @Override
//                    public void onClick(Serie serie) {
//                        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.add_serie_dialog, null ,false);
//                        TextInputLayout nomeSerieLayout, diaSemanaLayout, plataformaLayout, temporadaLayout, ultimoEpAssLayout;
//
//                        TextInputEditText nomeSerieInpt, diaSemanaInpt, plataformaInpt, temporadaInpt, ultimoEpAssInpt;
//
//                        nomeSerieInpt = view.findViewById(R.id.nomeSerieInpt);
//                        diaSemanaInpt = view.findViewById(R.id.diaSemanaInpt);
//                        plataformaInpt = view.findViewById(R.id.plataformaInpt);
//                        temporadaInpt = view.findViewById(R.id.temporadaInpt);
//                        ultimoEpAssInpt = view.findViewById(R.id.ultimoEpAssInpt);
//
//                        nomeSerieInpt.setText(serie.getNome());
//                        diaSemanaInpt.setText(serie.getDiaSemana());
//                        plataformaInpt.setText(serie.getPlataforma());
//                        temporadaInpt.setText(serie.getTemporadaAtual());
//                        ultimoEpAssInpt.setText(serie.getUltimoEp());
//
//                        ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
//
//                        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this)
//                                .setTitle("Edição")
//                                .setView(view)
//                                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        if (Objects.requireNonNull(nomeSerieInpt.getText()).toString().isEmpty() || Objects.requireNonNull(diaSemanaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(plataformaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(temporadaInpt.getText()).toString().isEmpty() || Objects.requireNonNull(ultimoEpAssInpt.getText()).toString().isEmpty()
//                                        )
//                                            Toast.makeText(HomeActivity.this, "1 ou mais campos estão vazios!", Toast.LENGTH_SHORT).show();
//                                        else {
//                                            progressDialog.setMessage("Aguarde, salvando...");
//                                            progressDialog.show();
//
//                                            Serie s1 = new Serie();
//
//                                            s1.setNome(nomeSerieInpt.getText().toString());
//                                            s1.setDiaSemana(diaSemanaInpt.getText().toString());
//                                            s1.setPlataforma(plataformaInpt.getText().toString());
//                                            s1.setTemporadaAtual(Integer.parseInt(temporadaInpt.getText().toString()));
//                                            s1.setUltimoEp(Integer.parseInt(ultimoEpAssInpt.getText().toString()));
//
//                                            database.getReference().child("series").child(serie.getChave()).setValue(s1).addOnSuccessListener(unused -> {
//                                                progressDialog.dismiss();
//                                                dialogInterface.dismiss();
//                                                Toast.makeText(HomeActivity.this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
//                                            }).addOnFailureListener(e -> {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(HomeActivity.this, "Ocorreu um erro, tente novamente mais tarde.", Toast.LENGTH_LONG).show();
//                                            });
//                                        }
//                                    }
//                                })
//                                .setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                })
//                                .setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        progressDialog.setTitle("Excluindo...");
//                                        progressDialog.show();
//                                        database.getReference().child("series").child(serie.getChave()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(HomeActivity.this, "Excluído com sucesso!", Toast.LENGTH_LONG).show();
//                                            }
//                                        })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        progressDialog.dismiss();
//                                                    }
//                                                });
//                                    }
//                                }).create();
//                        alertDialog.show();
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(HomeActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void readData() {
        databaseReference.child("SERIES").orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serieArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Serie serie = dataSnapshot.getValue(Serie.class);
                    serieArrayList.add(serie);
                }
                serieAdapter = new SerieAdapter(HomeActivity.this, serieArrayList);
                recyclerView.setAdapter(serieAdapter);
                serieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAdicionar {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_nova_serie);

            EditText txtNomeSerie = dialog.findViewById(R.id.txtNomeSerie);
            EditText txtDiaSemana = dialog.findViewById(R.id.txtDiaSemana);
            EditText txtPlataforma = dialog.findViewById(R.id.txtPlataforma);
            EditText txtTemporadaAtual = dialog.findViewById(R.id.txtTemporadaAtual);
            EditText txtUltimoEp = dialog.findViewById(R.id.txtUltimoEp);

            Button btnAdicionar = dialog.findViewById(R.id.btnAddDialog);
            Button btnSair = dialog.findViewById(R.id.btnSairDialog);

            btnAdicionar.setText("ADICIONAR");
            btnAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "serie" + new Date().getTime();
                    String nomeSerie = txtNomeSerie.getText().toString();
                    String diaSemana = txtDiaSemana.getText().toString();
                    String plataforma = txtPlataforma.getText().toString();
                    int temporadaAtual = Integer.parseInt(txtTemporadaAtual.getText().toString());
                    int ultimoEp = Integer.parseInt(txtUltimoEp.getText().toString());

                    if (nomeSerie.isEmpty() || diaSemana.isEmpty() || plataforma.isEmpty() || String.valueOf(temporadaAtual).isEmpty() || String.valueOf(ultimoEp).isEmpty())
                        Toast.makeText(context, "1 ou mais campos estão em branco!", Toast.LENGTH_LONG).show();
                    else {
                        databaseReference.child("SERIES").child(id).setValue(new Serie(id, nomeSerie, diaSemana, plataforma, temporadaAtual, ultimoEp));
                        Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnSair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }
}