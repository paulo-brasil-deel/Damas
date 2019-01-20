package br.com.poli.model.pojo;

import br.com.poli.model.pojo.enums.CorPeca;

public class Peca {
    //Atributos
    private CorPeca cor;
    private Jogador jogador;

    //Construtor
    
    public Peca(){
        
    }
    
    public Peca(Jogador jogador, CorPeca cor) {
        this.jogador = jogador;
        this.cor = cor;
    }

    //Getters e Setters
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public CorPeca getCor() {
        return this.cor;
    }

    public void setCor(CorPeca cor) {
        this.cor = cor;
    }
}
