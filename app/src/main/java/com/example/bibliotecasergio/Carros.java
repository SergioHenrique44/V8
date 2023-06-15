package com.example.bibliotecasergio;

public class Carros {
    private int id;

    private String Placa, Modelo, Marca, Cor;

    public Carros () {

    }

    public Carros(int id, String placa, String modelo, String marca, String cor) {
        this.id = id;
        Placa = placa;
        Modelo = modelo;
        Marca = marca;
        Cor = cor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getCor() {
        return Cor;
    }

    public void setCor(String cor) {
        Cor = cor;
    }

}
