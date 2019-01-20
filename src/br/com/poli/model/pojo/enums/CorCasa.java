package br.com.poli.model.pojo.enums;

public enum CorCasa {
    //Atributos
    BRANCA(1), PRETA(0);

    private final int valor;

    //Construtor
    private CorCasa(int valor) {
        this.valor = valor;
    }

    //Getter
    public int getValor() {
        return this.valor;
    }
}
