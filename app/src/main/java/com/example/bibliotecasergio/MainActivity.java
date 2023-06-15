package com.example.bibliotecasergio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String NOME_BANCO_DE_DADOS = "carros.db";

    EditText txtPlaca, txtModelo, txtMarca, txtCor;
    Button btnInserir, btnExibir;

    SQLiteDatabase dbCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mostrando componentes XMl
        txtPlaca = findViewById(R.id.txtPlaca);
        txtModelo = findViewById(R.id.txtModelo);
        txtMarca = findViewById(R.id.txtMarca);
        txtCor = findViewById(R.id.txtCor);


        btnInserir = findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(this);
        btnExibir = findViewById(R.id.btnExibir);
        btnExibir.setOnClickListener(this);

        //Criando DataBase
        dbCarros = openOrCreateDatabase(NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        //Criando a tabela no banco de dados especificado
        criarTabelaCarros();

    }
    private void criarTabelaCarros(){
        String sql = "CREATE TABLE IF NOT EXISTS carros ("+
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "placa varchar(255) NOT NULL," +
                "modelo varchar(255) NOT NULL," +
                "marca varchar(255) NOT NULL," +
                "cor varchar(255) NOT NULL);";
        dbCarros.execSQL(sql);
    }
    private boolean verificarEntrada(String Placa, String Modelo, String Marca, String Cor) {

        if(Placa.isEmpty()) {
            txtPlaca.setError("Digite o nome da Placa");
            txtPlaca.requestFocus();
            return false;
        }
        if(Modelo.isEmpty()) {
            txtModelo.setError("Digite o modelo do carro");
            txtModelo.requestFocus();
            return false;
        }
        if(Marca.isEmpty()) {
            txtMarca.setError("Digite a marca do carro");
            txtMarca.requestFocus();
            return false;
        }

        if(Cor.isEmpty()) {
            txtCor.setError("Digite a cor do carro");
            txtCor.requestFocus();
            return false;
        }
        return true;
    }

    private void adicionarCarro(){
        String placaCarro = txtPlaca.getText().toString().trim();
        String modeloCarro = txtModelo.getText().toString().trim();
        String marcaCarro = txtMarca.getText().toString().trim();
        String corCarro = txtCor.getText().toString().trim();


        if(verificarEntrada(placaCarro, modeloCarro, marcaCarro, corCarro)) {
            String insertSQL = "INSERT INTO carros(" +
                                "placa," +
                                "modelo," +
                                "marca," +
                                "cor)" +
                                "VALUES(?,?,?,?);";
            dbCarros.execSQL(insertSQL, new String[]{placaCarro, modeloCarro, marcaCarro, corCarro});
            Toast.makeText(getApplicationContext(), "Carro adicionado com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInserir:
                adicionarCarro();
                break;
            case R.id.btnExibir:
                startActivity(new Intent(getApplicationContext(), LivrosActivity.class));
                break;
        }
    }

}