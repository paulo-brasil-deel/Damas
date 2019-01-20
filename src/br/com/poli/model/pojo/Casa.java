package br.com.poli.model.pojo;

import br.com.poli.model.pojo.enums.CorCasa;

public class Casa {

    //Atributos
    private CorCasa cor;
    private boolean ocupada;
    private Peca peca;
    
	//Construtor
    public Casa(Peca peca, boolean ocupada, CorCasa cor) {
        this.peca = peca;
        this.ocupada = ocupada;
        this.cor = cor;
    }

    //Construtor Default
    public Casa() {
    }

    //Getters e Setters
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public boolean getOcupada() {
        return this.ocupada;
    }

    public Peca getPeca() {
        return this.peca;
    }

    public void setCor(CorCasa cor) {
        this.cor = cor;
    }

    public CorCasa getCor() {
        return this.cor;
    }
}
