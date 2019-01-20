package br.com.poli.controller;
public class NomesInvalidosException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public NomesInvalidosException() {
    }
    
    public NomesInvalidosException(String msg) {
        super(msg);
    }
}
