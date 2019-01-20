package br.com.poli.controller;
public class MovimentoInvalidoException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public MovimentoInvalidoException() {
    }
    
    public MovimentoInvalidoException(String msg) {
        super(msg);
    }
}
