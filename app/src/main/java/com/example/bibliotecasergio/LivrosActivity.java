package com.example.bibliotecasergio;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
public class LivrosActivity extends AppCompatActivity {

    List<Carros> CarrosList;
    CarrosAdapter CarrosAdapter;
    SQLiteDatabase dbCarros;
    ListView listViewCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livro_layout);

        listViewCarros = findViewById(R.id.listaCarrosView);
        CarrosList = new ArrayList<>();

        dbCarros = openOrCreateDatabase(MainActivity.NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);
        visualizarLivrosDatabase();
    }
    private void visualizarLivrosDatabase() {
        Cursor cursorCarros = dbCarros.rawQuery("SELECT * FROM Carros", null);
        if(cursorCarros.moveToFirst()) {
            do{
                CarrosList.add(new Carros(
                        cursorCarros.getInt(0),
                        cursorCarros.getString(1),
                        cursorCarros.getString(2),
                        cursorCarros.getString(3),
                        cursorCarros.getString(4)
                ));
            }while(cursorCarros.moveToNext());
        }
        cursorCarros.close();

        //Verificar Layout
        CarrosAdapter = new CarrosAdapter(this, R.layout.livro_model, CarrosList, dbCarros);

        listViewCarros.setAdapter(CarrosAdapter);
    }
}