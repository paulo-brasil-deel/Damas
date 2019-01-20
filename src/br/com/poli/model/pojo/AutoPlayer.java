package br.com.poli.model.pojo;

import br.com.poli.model.executor.Jogo;

public interface AutoPlayer {
	boolean jogarAuto(Jogo jogo);
	Jogador vencedor();
}
