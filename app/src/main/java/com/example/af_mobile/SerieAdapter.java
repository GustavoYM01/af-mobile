package com.example.af_mobile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.af_mobile.models.Serie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.ViewHolder> {

    Context context;
    ArrayList<Serie> serieArrayList;
    DatabaseReference databaseReference;

    public SerieAdapter(Context context, ArrayList<Serie> serieArrayList) {
        this.context = context;
        this.serieArrayList = serieArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public SerieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.serie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieAdapter.ViewHolder holder, int position) {
        Serie series = serieArrayList.get(position);

        holder.txtNomeSerie.setText("Série: " + series.getNome());
        holder.txtDiaSemana.setText("Dia da semana: " + series.getDiaSemana());
        holder.txtPlataforma.setText("Plataforma: " + series.getPlataforma());
        holder.txtTemporadaAtual.setText("Temporada atual: " + String.valueOf(series.getTemporadaAtual()));
        holder.txtultimoEp.setText("Último ep. assistido: " + String.valueOf(series.getUltimoEp()));

        holder.btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAtualizar viewDialogAtualizar = new ViewDialogAtualizar();
                viewDialogAtualizar.showDialog(context, series.getId(), series.getNome(), series.getDiaSemana(), series.getPlataforma(), series.getTemporadaAtual(), series.getUltimoEp());
            }
        });

        holder.btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogExcluir viewDialogExcluir = new ViewDialogExcluir();
                viewDialogExcluir.showDialog(context, series.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return serieArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNomeSerie, txtDiaSemana, txtPlataforma, txtTemporadaAtual, txtultimoEp;
        Button btnApagar, btnAtualizar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNomeSerie = itemView.findViewById(R.id.nomeSerie);
            txtDiaSemana = itemView.findViewById(R.id.diaSemana);
            txtPlataforma = itemView.findViewById(R.id.plataforma);
            txtTemporadaAtual = itemView.findViewById(R.id.temporadaAtual);
            txtultimoEp = itemView.findViewById(R.id.ultimoEp);

            btnApagar = itemView.findViewById(R.id.btnApagar);
            btnAtualizar = itemView.findViewById(R.id.btnAtualizar);
        }
    }

    public class ViewDialogAtualizar {
        public void showDialog(Context context, String id, String nomeSerie, String diaSemana, String plataforma, int temporadaAtual, int ultimoEp) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_nova_serie);

            EditText txtNomeSerie = dialog.findViewById(R.id.txtNomeSerie);
            EditText txtDiaSemana = dialog.findViewById(R.id.txtDiaSemana);
            EditText txtPlataforma = dialog.findViewById(R.id.txtPlataforma);
            EditText txtTemporadaAtual = dialog.findViewById(R.id.txtTemporadaAtual);
            EditText txtUltimoEp = dialog.findViewById(R.id.txtUltimoEp);

            txtNomeSerie.setText(nomeSerie);
            txtDiaSemana.setText(diaSemana);
            txtPlataforma.setText(plataforma);
            txtTemporadaAtual.setText(String.valueOf(temporadaAtual));
            txtUltimoEp.setText(String.valueOf(ultimoEp));

            Button btnAtualizar = dialog.findViewById(R.id.btnAddDialog);
            Button btnSair = dialog.findViewById(R.id.btnSairDialog);

            btnAtualizar.setText("ATUALIZAR");
            btnAtualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String novoNomeSerie = txtNomeSerie.getText().toString();
                    String novoDiaSemana = txtDiaSemana.getText().toString();
                    String novoPlataforma = txtPlataforma.getText().toString();
                    int novoTemporadaAtual = Integer.parseInt(txtTemporadaAtual.getText().toString());
                    int novoUltimoEp = Integer.parseInt(txtUltimoEp.getText().toString());

                    if (nomeSerie.isEmpty() || diaSemana.isEmpty() || plataforma.isEmpty() || String.valueOf(temporadaAtual).isEmpty() || String.valueOf(ultimoEp).isEmpty())
                        Toast.makeText(context, "1 ou mais campos estão em branco!", Toast.LENGTH_LONG).show();
                    else if (novoNomeSerie.equals(nomeSerie) && novoDiaSemana.equals(diaSemana) && novoPlataforma.equals(plataforma) && String.valueOf(novoTemporadaAtual).equals(String.valueOf(temporadaAtual))
                    && String.valueOf(novoUltimoEp).equals(String.valueOf(ultimoEp))) {
                        Toast.makeText(context, "Dados iguais à um registro já existente!", Toast.LENGTH_LONG).show();
                    } else {
                        databaseReference.child("SERIES").child(id).setValue(new Serie(id, nomeSerie, diaSemana, plataforma, temporadaAtual, ultimoEp));
                        Toast.makeText(context, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
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

    public class ViewDialogExcluir {
        public void showDialog(Context context, String id) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_confirm_del);

            Button btnExcluir = dialog.findViewById(R.id.btnDelConfirm);
            Button btnSair = dialog.findViewById(R.id.btnCancelConfirm);

            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("SERIES").child(id).removeValue();
                    Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            btnSair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}
