package com.example.bibliotecasergio;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarrosAdapter extends ArrayAdapter<Carros> {

    Context myContext;
    int livrosLayout;
    List<Carros> CarrosList;
    SQLiteDatabase dbCarros;

    public CarrosAdapter(@NonNull Context myContext, int livrosLayout, List<Carros> CarrosList, SQLiteDatabase dbCarros) {
        super(myContext, livrosLayout, CarrosList);

        this.myContext = myContext;
        this.livrosLayout = livrosLayout;
        this.CarrosList = CarrosList;
        this.dbCarros = dbCarros;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(livrosLayout, null);

        final Carros carros = CarrosList.get(position);

        TextView txtViewPlaca = view.findViewById(R.id.placa_carro);
        TextView txtViewModelo = view.findViewById(R.id.modelo_carro);
        TextView textViewCor = view.findViewById(R.id.cor_carro);


        txtViewPlaca.setText(carros.getPlaca());
        txtViewModelo.setText(carros.getModelo());
        textViewCor.setText(carros.getCor());

        Button btnEditar = view.findViewById(R.id.btnEditar);
        Button btnExcluir = view.findViewById(R.id.btnExcluir);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarCarros(carros);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Deseja Apagar?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM carros WHERE id = ?";
                        dbCarros.execSQL(sql, new Integer[]{carros.getId()});
                        //calling metodo atualizar a listra de carros
                        recarregarCarrosDB();

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void alterarCarros (final Carros carros) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(myContext);

        LayoutInflater inflater = LayoutInflater.from(myContext);

        View view = inflater.inflate(R.layout.caixa_livro_alterar, null);
        builder.setView(view);

        final EditText txtEditarPlaca = view.findViewById(R.id.txtEditarPlaca);
        final EditText txtEditarModelo = view.findViewById(R.id.txtEditarModelo);
        final EditText txtEditarMarca = view.findViewById(R.id.txtEditarMarca);
        final EditText txtEditarCor = view.findViewById(R.id.txtEditarCor);

        txtEditarPlaca.setText(carros.getPlaca());
        txtEditarModelo.setText(carros.getModelo());
        txtEditarMarca.setText(carros.getMarca());
        txtEditarCor.setText(carros.getCor());


        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placa = txtEditarPlaca.getText().toString().trim();
                String modelo = txtEditarModelo.getText().toString().trim();
                String marca = txtEditarMarca.getText().toString().trim();
                String cor = txtEditarCor.getText().toString().trim();

                if(placa.isEmpty()) {
                    txtEditarPlaca.setError("A Placa esta vazia.");
                    txtEditarPlaca.requestFocus();
                    return;
                }

                if(modelo.isEmpty()) {
                    txtEditarModelo.setError("O modelo do carro esta vazio.");
                    txtEditarModelo.requestFocus();
                    return;
                }
                if(marca.isEmpty()) {
                    txtEditarModelo.setError("O modelo do carro esta vazia.");
                    txtEditarModelo.requestFocus();
                    return;
                }

                if(cor.isEmpty()) {
                    txtEditarCor.setError("A cor do carro esta vazia.");
                    txtEditarCor.requestFocus();
                    return;
                }


                String sql = "UPDATE carros SET placa = ?, modelo = ?, marca = ? , cor = ? WHERE id = ?";
                dbCarros.execSQL(sql, new String[]{ placa, modelo, marca, cor, String.valueOf(carros.getId())});
                Toast.makeText(myContext, "Carro alterado com sucesso.", Toast.LENGTH_LONG).show();

                //Chamando método para atualizar a lista de carros
                recarregarCarrosDB();

                //Limpando a estrutura do AlertDialog
                dialog.dismiss();
            }

        });

    }

    public void recarregarCarrosDB() {
        Cursor cursorCarros = dbCarros.rawQuery("SELECT * FROM carros", null);
        if(cursorCarros.moveToFirst()) {
            CarrosList.clear();
            do {
                CarrosList.add(new Carros(
                        cursorCarros.getInt(0),
                        cursorCarros.getString(1),
                        cursorCarros.getString(2),
                        cursorCarros.getString(3),
                        cursorCarros.getString(4)
                ));
            } while (cursorCarros.moveToNext());
        }
        cursorCarros.close();
        notifyDataSetChanged();
    }
}
