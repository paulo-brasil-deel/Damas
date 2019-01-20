package br.com.poli.model.pojo.enums;

public enum Resultados {
    //Atributos
    EMPATE(1), COMVENCEDOR(0);

    private final int valor;

    //Construtor
    private Resultados(int valor) {
        this.valor = valor;
    }

    //Getter
    public int getValor() {
        return this.valor;
    }
}
