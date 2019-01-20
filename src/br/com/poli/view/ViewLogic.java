package br.com.poli.view;

import br.com.poli.controller.MovimentoInvalidoException;
import br.com.poli.controller.NomesInvalidosException;
import br.com.poli.model.pojo.Jogador;
import br.com.poli.model.pojo.JogadorAutonomo;
import br.com.poli.model.pojo.enums.Resultados;

public interface ViewLogic {
	Tabuleiro getTabuleiro();
	Jogador getVencedor();
	int getContadorJogadas();
	boolean isJogadorAtualPreto();
	int getNumeroPecasClaras();
	int getNumeroPecasEscuras();
	Jogador iniciarPartida(String jogador1, String jogador2) throws NomesInvalidosException;
	void jogar(int movX, int movY, int origemX, int origemY) throws MovimentoInvalidoException;
	Jogador getJogadorAtual();
	JogadorAutonomo getComputador();
	Resultados getResultado();
}
