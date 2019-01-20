package br.com.poli.model.pojo.enums;

public enum CorPeca {
    //Atributos
    CLARA(1), ESCURA(0);

    private final int valor;

    //Construtor
    private CorPeca(int valor) {
        this.valor = valor;
    }

    //Getter
    public int getValor() {
        return this.valor;
    }
}  
