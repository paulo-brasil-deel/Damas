package br.com.poli.model.pojo;

import br.com.poli.model.pojo.enums.CorPeca;

public class PecaDama extends Peca {

	public PecaDama() {

	}

	public PecaDama(Jogador jogador, CorPeca cor) {
		super(jogador, cor);
	}

	public boolean comparar(Object peca) {
		return (peca instanceof PecaDama);
	}
}
