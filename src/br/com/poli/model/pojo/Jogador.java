package br.com.poli.model.pojo;

public class Jogador {
    //Atributos
    private String nome;

    //Construtor
    public Jogador(String nome) {
        this.nome = nome;
    }

    //Getters e Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}
