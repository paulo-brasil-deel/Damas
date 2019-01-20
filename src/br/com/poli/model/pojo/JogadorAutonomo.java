package br.com.poli.model.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import br.com.poli.model.executor.Jogo;
import br.com.poli.model.pojo.enums.CorPeca;

public class JogadorAutonomo extends Jogador implements AutoPlayer {

	/*
	private void executaMovimentoDama(int movX, int movY, int origemX, int origemY) {
		if (movY > origemY) {
			if (movX > origemX) {
				getTabuleiro().getGrid()[movY - 1][movX - 1].setOcupada(true);
				getTabuleiro().getGrid()[movY - 1][movX - 1]
						.setPeca(getTabuleiro().getGrid()[origemY][origemX].getPeca());
				getTabuleiro().getGrid()[origemY][origemX].setOcupada(false);
				getTabuleiro().getGrid()[origemY][origemX].setPeca(null);
			} else if (movX < origemX) {
				getTabuleiro().getGrid()[movY - 1][movX + 1].setOcupada(true);
				getTabuleiro().getGrid()[movY - 1][movX + 1]
						.setPeca(getTabuleiro().getGrid()[origemY][origemX].getPeca());
				getTabuleiro().getGrid()[origemY][origemX].setOcupada(false);
				getTabuleiro().getGrid()[origemY][origemX].setPeca(null);
			}
		} else if (movY < origemY) {
			if (movX > origemX) {
				getTabuleiro().getGrid()[movY + 1][movX - 1].setOcupada(true);
				getTabuleiro().getGrid()[movY + 1][movX - 1]
						.setPeca(getTabuleiro().getGrid()[origemY][origemX].getPeca());
				getTabuleiro().getGrid()[origemY][origemX].setOcupada(false);
				getTabuleiro().getGrid()[origemY][origemX].setPeca(null);
			} else if (movX < origemX) {
				getTabuleiro().getGrid()[movY + 1][movX + 1].setOcupada(true);
				getTabuleiro().getGrid()[movY + 1][movX + 1]
						.setPeca(getTabuleiro().getGrid()[origemY][origemX].getPeca());
				getTabuleiro().getGrid()[origemY][origemX].setOcupada(false);
				getTabuleiro().getGrid()[origemY][origemX].setPeca(null);
			}
		}
	}
	*/

	private void atualizador(Jogo jogo) {
		if (jogo.getTabuleiro().getPeca1() != null && jogo.getTabuleiro().getPeca1().getJogador() != null && !jogo
				.getTabuleiro().getPeca1().getJogador().getNome().equalsIgnoreCase(jogo.getJogadorAtual().getNome())) {
			jogo.setJogadorAtual(jogo.getTabuleiro().getPeca1().getJogador());
		} else if (jogo.getTabuleiro().getPeca2() != null && jogo.getTabuleiro().getPeca2().getJogador() != null
				&& !jogo.getTabuleiro().getPeca2().getJogador().getNome()
				.equalsIgnoreCase(jogo.getJogadorAtual().getNome())) {
			jogo.setJogadorAtual(jogo.getTabuleiro().getPeca2().getJogador());
		}
	}

	public JogadorAutonomo(String nome) {
		super(nome);
	}

	@Override
	public boolean jogarAuto(Jogo jogo) {
		int[] movimentoPensado = cerebro(jogo);
		if (cerebro(jogo)[0] == 0) {
			return false;
		} else {

			if (movimentoPensado[1] == 3) {
				jogo.capturar(movimentoPensado[4], movimentoPensado[5], movimentoPensado[2], movimentoPensado[3]);
				atualizador(jogo);
			} else if (movimentoPensado[1] == 9) {
				jogo.capturar(movimentoPensado[4], movimentoPensado[5], movimentoPensado[2], movimentoPensado[3]);
			} else if (movimentoPensado[1] % 2 == 0) {

				jogo.getTabuleiro().executarMovimento(movimentoPensado[4], movimentoPensado[5], movimentoPensado[2],
						movimentoPensado[3]);
				atualizador(jogo);
			}

			return true;
		}
	}

	@Override
	public Jogador vencedor() {
		// TODO Auto-generated method stub
		return null;
	}

	private int geradorPesos(int origemX, int origemY, int movX, int movY, Jogo jogo) {

		// PECA PARA COMPARAR SE E DAMA
		PecaDama comparadorDama = new PecaDama();

		int pesoPecaOrigem = 1;
		int pesoPosicaoOrigem = 1;

		int pesoPecaMov = 1;
		int pesoPosicaoMov = 1;

		/**
		 * ---------------------------- VALORES DE pesoPeca: DAMA = 5; PECA BRANCA COM
		 * origemY ENTRE 0 E 2 = 3; PECA ESCURA COM origemY ENTRE 5 E 7 = 3;
		 * ---------------------------- VALORES DE pesoPosicao: SE (origemX=0 OU
		 * origemX=7)=9; SE (origemY=0 OU origemY=7)=9;
		 * 
		 * SE ((origemX>=1 || origemX<=6) E origemY=1) = 7; SE ((origemX>=1 ||
		 * origemX<=6) E origemY=6) = 7; SE ((origemY>=1 || origemY<=6) E origemX=1) =
		 * 7; SE ((origemY>=1 || origemY<=6) E origemX=6) = 7;
		 * 
		 * SE ((origemX>=2 || origemX<=5) E (origemY>=2 || origemY<=5)) = 5;
		 * ----------------------------
		 **/

		// APLICAR PESOS PELA PECA
		// ORIGEM
		// SE NAO FOR DAMA
		if (jogo.getTabuleiro().getGrid()[origemY][origemX].getOcupada()) {
			if (!comparadorDama.comparar(jogo.getTabuleiro().getGrid()[origemY][origemX].getPeca())) {

				// VERIFICA SE E ESCURA
				if (jogo.getTabuleiro().getGrid()[origemY][origemX].getPeca().getCor() == CorPeca.ESCURA) {
					if (origemY >= 5 || origemY <= 7) {
						pesoPecaOrigem = 3;
					}
					// SE FOR BRANCA
				} else if (origemY >= 0 || origemY <= 2) {
					pesoPecaOrigem = 3;
				}

			} else
				pesoPecaOrigem = 5;

			// MOVIMENTO
			// SE NAO FOR DAMA
			if (jogo.getTabuleiro().getGrid()[movY][movX].getOcupada()) {
				if (!comparadorDama.comparar(jogo.getTabuleiro().getGrid()[movY][movX].getPeca())) {

					// VERIFICA SE E ESCURA
					if (jogo.getTabuleiro().getGrid()[movY][movX].getPeca().getCor() == CorPeca.ESCURA) {
						if (movY >= 5 || movY <= 7) {
							pesoPecaMov = 3;
						}
						// SE FOR BRANCA
					} else if (movY >= 0 || movY <= 2) {
						pesoPecaMov = 3;
					}

				} else
					pesoPecaMov = 5;
			}

			// APLICAR PESOS PELA POSICAO
			// ORIGEM
			if ((origemX == 0 || origemX == 7) || (origemY == 0 || origemY == 7)) {
				pesoPosicaoOrigem = 4;
			} else if ((origemX >= 1 || origemX <= 6)) {
				if (origemY == 1) {
					pesoPosicaoOrigem = 3;
				} else if (origemY == 6) {
					pesoPosicaoOrigem = 3;
				}
			} else if ((origemY >= 1 || origemY <= 6)) {
				if (origemX == 1) {
					pesoPosicaoOrigem = 3;
				} else if (origemX == 6) {
					pesoPosicaoOrigem = 3;
				}
			} else if ((origemX >= 2 || origemX <= 5) && (origemY >= 2 || origemY <= 5)) {
				pesoPosicaoOrigem = 2;
			}

			// MOVIMENTO
			if ((movX == 0 || movX == 7) || (movY == 0 || movY == 7)) {
				pesoPosicaoMov = 4;
			} else if ((movX >= 1 || movX <= 6)) {
				if (movY == 1) {
					pesoPosicaoMov = 3;
				} else if (movY == 6) {
					pesoPosicaoMov = 3;
				}
			} else if ((movY >= 1 || movY <= 6)) {
				if (movX == 1) {
					pesoPosicaoMov = 3;
				} else if (movX == 6) {
					pesoPosicaoMov = 3;
				}
			} else if ((movX >= 2 || movX <= 5) && (movY >= 2 || movY <= 5)) {
				pesoPosicaoMov = 2;
			}
		}

		return ((pesoPecaOrigem * pesoPosicaoOrigem) + ((pesoPecaMov * pesoPosicaoMov)));
	}

	private int[] ranquearMovimentos(ArrayList<int[]> movimentos) {

		ArrayList<int[]> aleatorio = new ArrayList<int[]>();
		ArrayList<int[]> capturas = new ArrayList<int[]>();
		Random pensaRapido = new Random();

		int contadorRep = 0;
		int cont = 0;

		for (int i = 0; i < movimentos.size(); i++) {
			if (movimentos.get(i)[1] == 3 || movimentos.get(i)[1] == 9) {
				capturas.add(movimentos.get(i));
				cont++;
			}
		}

		if (cont > 0) {
			movimentos = capturas;
		}

		Collections.sort(movimentos, new Comparator<int[]>() {

			@Override
			public int compare(int[] a, int[] b) {
				return b[0] - a[0];
			}

		});

		Collections.sort(movimentos, new Comparator<int[]>() {

			@Override
			public int compare(int[] a, int[] b) {
				return b[1] - a[1];
			}

		});

		for (int i = 0; i < movimentos.size(); i++) {
			if (movimentos.get(i)[0] == movimentos.get(0)[0]) {
				aleatorio.add(movimentos.get(i));
				contadorRep++;
			}
		}

		if (contadorRep > 1) {
			return aleatorio.get(pensaRapido.nextInt(aleatorio.size()));
		} else
			return movimentos.get(0);
	}

	public int[] cerebro(Jogo jogo){

		PecaDama comparadorDama = new PecaDama();

		//ARRAYLIST DE ARRAY
		ArrayList<int[]> melhorMovimento = new ArrayList<int[]>();
		int[] pensamento = new int[7];

		int peso;
		int[] finalmente;

		int nada=0;
		int comer=3;
		int combo=9;
		int mover=2;
		int comido=1;
		int possivelcapturar=2;
		boolean possibilidadeComido = false;

		//LOOP PARA RECONHECER TODAS AS PECAS DISPONIVEIS E TODOS OS MOVIMENTOS POSSIVEIS.
		for (int origemY = 0; origemY < jogo.getTabuleiro().getGrid().length; origemY++) {
			for (int origemX = 0; origemX < jogo.getTabuleiro().getGrid().length; origemX++) {

				if (!comparadorDama.comparar(jogo.getTabuleiro().getGrid()[origemY][origemX].getPeca())) {	

					if(jogo.getTabuleiro().getGrid()[origemY][origemX].getOcupada()){
						if(jogo.getTabuleiro().getGrid()[origemY][origemX].getPeca().getJogador().equals(jogo.getJogadorAtual())){	
							//-------------------------------ESQUERDA SUPERIOR
							if(!((origemY-1)<0 || (origemX-1)<0)){
								if(jogo.getTabuleiro().getGrid()[origemY-1][origemX-1].getOcupada()){
									if(!(jogo.getTabuleiro().getGrid()[origemY-1][origemX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){
										int tempX=origemX-1;
										int tempY=origemY-1;

										//ATRAS DA PECA INIMIGA
										if(!((tempY-1)<0 || (tempX-1)<0)){
											if(!(jogo.getTabuleiro().getGrid()[tempY-1][tempX-1].getOcupada())){
												tempX=tempX-1;
												tempY=tempY-1;

												//PECA INIMIGA ATRAS DO ESPACO VAZIO ATRAS DA PECA INIMIGA PRIMARIA
												if(!((tempY-1)<0 || (tempX-1)<0)){
													if(jogo.getTabuleiro().getGrid()[tempY-1][tempX-1].getOcupada()){
														if(!(jogo.getTabuleiro().getGrid()[tempY-1][tempX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

															int tempX1=tempX-1;
															int tempY1=tempY-1;
															//ESPACO VAZIO ATRAS DAPECA INIMIGA ATRAS DO ESPACO VAZIO ATRAS DA PECA INIMIGA PRIMARIA
															if(!((tempY1-1)<0 || (tempX1-1)<0)){
																if(!(jogo.getTabuleiro().getGrid()[tempY1-1][tempX1-1].getOcupada())){
																	peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
																	pensamento[0]= combo*peso;
																	pensamento[1]= combo;
																	pensamento[2]= origemX;
																	pensamento[3]= origemY;
																	pensamento[4]= origemX-1;
																	pensamento[5]= origemY-1;

																	melhorMovimento.add(pensamento);
																	int[] pensamento1 = new int[6];
																	pensamento = pensamento1;

																}
															}
														}else{
															peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
															pensamento[0]= comer*peso;
															pensamento[1]= comer;
															pensamento[2]= origemX;
															pensamento[3]= origemY;
															pensamento[4]= origemX-1;
															pensamento[5]= origemY-1;

															melhorMovimento.add(pensamento);
															int[] pensamento1 = new int[6];
															pensamento = pensamento1;

														}
													}else{
														peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
														pensamento[0]= comer*peso;
														pensamento[1]= comer;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX-1;
														pensamento[5]= origemY-1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;

													}
												}
											}else{
												peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
												pensamento[0]= nada*peso;
												pensamento[1]= nada;
												pensamento[2]= origemX;
												pensamento[3]= origemY;
												pensamento[4]= origemX-1;
												pensamento[5]= origemY-1;

												melhorMovimento.add(pensamento);
												int[] pensamento1 = new int[6];
												pensamento = pensamento1;
												if(!((origemY+1)>7 || (origemX+1)>7)){
													if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getOcupada())){
														possibilidadeComido=true;
													}
												}
											}
										}
									}else{
										peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
										pensamento[0]= nada*peso;
										pensamento[1]= nada;
										pensamento[2]= origemX;
										pensamento[3]= origemY;
										pensamento[4]= origemX-1;
										pensamento[5]= origemY-1;

										melhorMovimento.add(pensamento);
										int[] pensamento1 = new int[6];
										pensamento = pensamento1;

									}
								}else{
									peso=geradorPesos(origemX, origemY, origemX-1, origemY-1,jogo);
									pensamento[0]= nada*peso;
									pensamento[1]= nada;
									pensamento[2]= origemX;
									pensamento[3]= origemY;
									pensamento[4]= origemX-1;
									pensamento[5]= origemY-1;

									melhorMovimento.add(pensamento);
									int[] pensamento1 = new int[6];
									pensamento = pensamento1;

								}
							}

							//-------------------------------DIREITA SUPERIOR
							if(!((origemY-1)<0 || (origemX+1)>7)){
								if(jogo.getTabuleiro().getGrid()[origemY-1][origemX+1].getOcupada()){
									if(!(jogo.getTabuleiro().getGrid()[origemY-1][origemX+1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){
										int tempX=origemX+1;
										int tempY=origemY-1;
										if(!((tempY-1)<0 || (tempX+1)>7)){
											if(!(jogo.getTabuleiro().getGrid()[tempY-1][tempX+1].getOcupada())){
												tempX=tempX+1;
												tempY=tempY-1;

												if(!((tempY-1)<0 || (tempX+1)>7)){
													if(jogo.getTabuleiro().getGrid()[tempY-1][tempX+1].getOcupada()){
														if(!(jogo.getTabuleiro().getGrid()[tempY-1][tempX+1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

															int tempX1=tempX-1;
															int tempY1=tempY+1;

															if(!((tempY1-1)<0 || (tempX1+1)>7)){
																if(!(jogo.getTabuleiro().getGrid()[tempY1-1][tempX1+1].getOcupada())){
																	peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
																	pensamento[0]= combo*peso;
																	pensamento[1]= combo;
																	pensamento[2]= origemX;
																	pensamento[3]= origemY;
																	pensamento[4]= origemX+1;
																	pensamento[5]= origemY-1;

																	melhorMovimento.add(pensamento);
																	int[] pensamento1 = new int[6];
																	pensamento = pensamento1;

																}
															}
														}else{
															peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
															pensamento[0]= comer*peso;
															pensamento[1]= comer;
															pensamento[2]= origemX;
															pensamento[3]= origemY;
															pensamento[4]= origemX+1;
															pensamento[5]= origemY-1;

															melhorMovimento.add(pensamento);
															int[] pensamento1 = new int[6];
															pensamento = pensamento1;

														}
													}else{
														peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
														pensamento[0]= comer*peso;
														pensamento[1]= comer;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY-1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;

													}
												}
											}else{
												peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
												pensamento[0]= nada*peso;
												pensamento[1]= nada;
												pensamento[2]= origemX;
												pensamento[3]= origemY;
												pensamento[4]= origemX+1;
												pensamento[5]= origemY-1;

												melhorMovimento.add(pensamento);
												int[] pensamento1 = new int[6];
												pensamento = pensamento1;
												if(!((origemY+1)>7 || (origemX+1)>7)){
													if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getOcupada())){
														possibilidadeComido=true;
													}
												}
											}
										}
									}else{
										peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
										pensamento[0]= nada*peso;
										pensamento[1]= nada;
										pensamento[2]= origemX;
										pensamento[3]= origemY;
										pensamento[4]= origemX+1;
										pensamento[5]= origemY-1;

										melhorMovimento.add(pensamento);
										int[] pensamento1 = new int[6];
										pensamento = pensamento1;
									}
								}else{
									peso=geradorPesos(origemX, origemY, origemX+1, origemY-1,jogo);
									pensamento[0]= nada*peso;
									pensamento[1]= nada;
									pensamento[2]= origemX;
									pensamento[3]= origemY;
									pensamento[4]= origemX+1;
									pensamento[5]= origemY-1;


									melhorMovimento.add(pensamento);
									int[] pensamento1 = new int[6];
									pensamento = pensamento1;
								}
							}

							//-------------------------------ESQUERDA INFERIOR
							if(!((origemY+1)>7 || (origemX-1)<0)){
								if(jogo.getTabuleiro().getGrid()[origemY+1][origemX-1].getOcupada()){
									if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

										int tempX=origemX-1;
										int tempY=origemY+1;
										if(!((tempY+1)>7 || (tempX-1)<0)){
											if(!(jogo.getTabuleiro().getGrid()[tempY+1][tempX-1].getOcupada())){
												tempX=tempX+1;
												tempY=tempY-1;
												if(!((tempY+1)>7 || (tempX-1)<0)){
													if(jogo.getTabuleiro().getGrid()[tempY+1][tempX-1].getOcupada()){
														if(!(jogo.getTabuleiro().getGrid()[tempY+1][tempX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

															int tempX1=tempX+1;
															int tempY1=tempY-1;
															if(!((tempY1+1)>7 || (tempX1-1)<0)){
																if(!(jogo.getTabuleiro().getGrid()[tempY1+1][tempX1-1].getOcupada())){
																	peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
																	pensamento[0]= combo*peso;
																	pensamento[1]= combo;
																	pensamento[2]= origemX;
																	pensamento[3]= origemY;
																	pensamento[4]= origemX-1;
																	pensamento[5]= origemY+1;

																	melhorMovimento.add(pensamento);
																	int[] pensamento1 = new int[6];
																	pensamento = pensamento1;

																}
															}
														}else{
															peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
															pensamento[0]= comer*peso;
															pensamento[1]= comer;
															pensamento[2]= origemX;
															pensamento[3]= origemY;
															pensamento[4]= origemX-1;
															pensamento[5]= origemY+1;

															melhorMovimento.add(pensamento);
															int[] pensamento1 = new int[6];
															pensamento = pensamento1;

														}
													}else{
														peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
														pensamento[0]= comer*peso;
														pensamento[1]= comer;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX-1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;

													}
												}
											}else{
												peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
												pensamento[0]= nada*peso;
												pensamento[1]= nada;
												pensamento[2]= origemX;
												pensamento[3]= origemY;
												pensamento[4]= origemX-1;
												pensamento[5]= origemY+1;

												melhorMovimento.add(pensamento);
												int[] pensamento1 = new int[6];
												pensamento = pensamento1;
												if(!((origemY+1)>7 || (origemX+1)>7)){
													if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getOcupada())){
														possibilidadeComido=true;
													}
												}
											}
										}
									}else{
										peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
										pensamento[0]= nada*peso;
										pensamento[1]= nada;
										pensamento[2]= origemX;
										pensamento[3]= origemY;
										pensamento[4]= origemX-1;
										pensamento[5]= origemY+1;

										melhorMovimento.add(pensamento);
										int[] pensamento1 = new int[6];
										pensamento = pensamento1;

									}
								}else{
									int tempX=origemX+1;
									int tempY=origemY-1;

									if(possibilidadeComido){
										mover=mover*3;
									}
									if(!((tempY+1)>7 || (tempX-1)<0)){
										if(jogo.getTabuleiro().getGrid()[tempY+1][tempX-1].getOcupada() && !(jogo.getTabuleiro().getGrid()[tempY+1][tempX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){
											int tempX1=tempX+1;
											int tempY1=tempY-1;
											if(!((tempY1+1)>7 || (tempX1-1)<0)){
												if(!(jogo.getTabuleiro().getGrid()[tempY1+1][tempX1-1].getOcupada())) {
													peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
													pensamento[0]= mover*peso*possivelcapturar;
													pensamento[1]= mover;
													pensamento[2]= origemX;
													pensamento[3]= origemY;
													pensamento[4]= origemX-1;
													pensamento[5]= origemY+1;

													melhorMovimento.add(pensamento);
													int[] pensamento1 = new int[6];
													pensamento = pensamento1;
												}else {

													peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
													pensamento[0]= comido*peso;
													pensamento[1]= comido;
													pensamento[2]= origemX;
													pensamento[3]= origemY;
													pensamento[4]= origemX-1;
													pensamento[5]= origemY+1;

													melhorMovimento.add(pensamento);
													int[] pensamento1 = new int[6];
													pensamento = pensamento1;
												}
											}
										}else{
											peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
											pensamento[0]= mover*peso;
											pensamento[1]= mover;
											pensamento[2]= origemX;
											pensamento[3]= origemY;
											pensamento[4]= origemX-1;
											pensamento[5]= origemY+1;

											melhorMovimento.add(pensamento);
											int[] pensamento1 = new int[6];
											pensamento = pensamento1;

										}
									}
								}
							}

							//-------------------------------DIRETA INFERIOR
							if(!((origemY+1)>7 || (origemX+1)>7)){
								if(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getOcupada()){
									if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

										int tempX=origemX+1;
										int tempY=origemY+1;
										if(!((tempY+1)>7 || (tempX+1)>7)){
											if(!(jogo.getTabuleiro().getGrid()[tempY+1][tempX+1].getOcupada())){
												tempX=tempX+1;
												tempY=tempY-1;
												if(!((tempY+1)>7 || (tempX+1)>7)){
													if(jogo.getTabuleiro().getGrid()[tempY+1][tempX+1].getOcupada()){
														if(!(jogo.getTabuleiro().getGrid()[tempY+1][tempX+1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){

															int tempX1=tempX+1;
															int tempY1=tempY+1;
															if(!((tempY1+1)>7 || (tempX1+1)>7)){
																if(!(jogo.getTabuleiro().getGrid()[tempY1+1][tempX1+1].getOcupada())){
																	peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
																	pensamento[0]= combo*peso;
																	pensamento[1]= combo;
																	pensamento[2]= origemX;
																	pensamento[3]= origemY;
																	pensamento[4]= origemX+1;
																	pensamento[5]= origemY+1;

																	melhorMovimento.add(pensamento);
																	int[] pensamento1 = new int[6];
																	pensamento = pensamento1;

																}
															}
														}else{
															peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
															pensamento[0]= comer*peso;
															pensamento[1]= comer;
															pensamento[2]= origemX;
															pensamento[3]= origemY;
															pensamento[4]= origemX+1;
															pensamento[5]= origemY+1;

															melhorMovimento.add(pensamento);
															int[] pensamento1 = new int[6];
															pensamento = pensamento1;

														}
													}else{
														peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
														pensamento[0]= comer*peso;
														pensamento[1]= comer;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;

													}
												}
											}else{
												peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
												pensamento[0]= nada*peso;
												pensamento[1]= nada;
												pensamento[2]= origemX;
												pensamento[3]= origemY;
												pensamento[4]= origemX+1;
												pensamento[5]= origemY+1;

												melhorMovimento.add(pensamento);
												int[] pensamento1 = new int[6];
												pensamento = pensamento1;

												if(!(jogo.getTabuleiro().getGrid()[origemY+1][origemX+1].getOcupada())){
													possibilidadeComido=true;
												}
											}
										}
									}else{
										peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
										pensamento[0]= nada*peso;
										pensamento[1]= nada;
										pensamento[2]= origemX;
										pensamento[3]= origemY;
										pensamento[4]= origemX+1;
										pensamento[5]= origemY+1;

										melhorMovimento.add(pensamento);
										int[] pensamento1 = new int[6];
										pensamento = pensamento1;

									}
								}else{
									int tempX=origemX+1;
									int tempY=origemY+1;

									if(possibilidadeComido){
										mover=mover*3;
									}
									if(!((tempY+1)>7 || (tempX+1)>7)){
										if(jogo.getTabuleiro().getGrid()[tempY+1][tempX+1].getOcupada()){
											if(!(jogo.getTabuleiro().getGrid()[tempY+1][tempX+1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){
												int tempX1=tempX+1;
												int tempY1=tempY+1;
												if(((tempY+1)>7 || (tempX+1)>7)){
													if(!(jogo.getTabuleiro().getGrid()[tempY1+1][tempX1+1].getOcupada())) {
														peso=geradorPesos(origemX, origemY, origemX-1, origemY+1,jogo);
														pensamento[0]= mover*peso*possivelcapturar;
														pensamento[1]= mover;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;
													}else {
														peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
														pensamento[0]= comido*peso;
														pensamento[1]= comido;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;
													}
												}
											}else{
												peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
												pensamento[0]= mover*peso;
												pensamento[1]= mover;
												pensamento[2]= origemX;
												pensamento[3]= origemY;
												pensamento[4]= origemX+1;
												pensamento[5]= origemY+1;

												melhorMovimento.add(pensamento);
												int[] pensamento1 = new int[6];
												pensamento = pensamento1;

											}
										}else{
											if(!((tempY+1)>7 || (tempX+1)>7)){
												if(jogo.getTabuleiro().getGrid()[tempY-1][tempX-1].getOcupada()){
													if(!(jogo.getTabuleiro().getGrid()[tempY-1][tempX-1].getPeca().getJogador().equals(jogo.getJogadorAtual()))){
														peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
														pensamento[0]= comido*peso;
														pensamento[1]= comido;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;
													}else{
														peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
														pensamento[0]= mover*peso;
														pensamento[1]= mover;
														pensamento[2]= origemX;
														pensamento[3]= origemY;
														pensamento[4]= origemX+1;
														pensamento[5]= origemY+1;

														melhorMovimento.add(pensamento);
														int[] pensamento1 = new int[6];
														pensamento = pensamento1;

													}
												}else{
													peso=geradorPesos(origemX, origemY, origemX+1, origemY+1,jogo);
													pensamento[0]= mover*peso;
													pensamento[1]= mover;
													pensamento[2]= origemX;
													pensamento[3]= origemY;
													pensamento[4]= origemX+1;
													pensamento[5]= origemY+1;

													melhorMovimento.add(pensamento);
													int[] pensamento1 = new int[6];
													pensamento = pensamento1;

												}
											}
										}
									}
								}
							}
						}
					}

				}else {
					/*
					for (int movY = 0; movY < jogo.getTabuleiro().getGrid().length; movY++) {
						for (int movX = 0; movX < jogo.getTabuleiro().getGrid().length; movX++) {

							int contador = movY - origemY;

							if (contador < 0) {
								contador = contador * -1;
							}
							try {
								for (int a = 1; a <= contador; a++) {
									if (movY > origemY) {
										if (movX > origemX) {
											if (jogo.getTabuleiro().getGrid()[(origemY + a)][(origemX + a)].getOcupada()) {
												if (jogo.getTabuleiro().getGrid()[(origemY + a)][(origemX + a)].getPeca()
														.getCor() == jogo.getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
													pensamento[6] = 1;
													retorno[4] = origemX + a;
													retorno[5] = origemY + a;

												} else {
													pensamento[6] = 3;
													retorno[4] = origemX + a;
													retorno[5] = origemY + a;

												}
											}
											if ((origemY + a == movY) && (origemX + a == movX)) {
												pensamento[6] = 2;
											} 
										} else if (movX < origemX) {
											if (jogo.getTabuleiro().getGrid()[(origemY + a)][(origemX - a)].getOcupada()) {
												if (jogo.getTabuleiro().getGrid()[(origemY + a)][(origemX - a)].getPeca()
														.getCor() == jogo.getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
													pensamento[6] = 1;
													retorno[4] = origemX - a;
													retorno[5] = origemY + a;

												} else {
													pensamento[6] = 3;
													retorno[4] = origemX - a;
													retorno[5] = origemY + a;

												}
											}
											if ((origemY + a == movY) && (origemX - a == movX)) {
												pensamento[6] = 2;
											} 
										}
									} else if (movY < origemY) {
										if (movX > origemX) {
											if (jogo.getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getOcupada()) {
												if (jogo.getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getPeca() != null
														&& jogo.getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca() != null) {
													if (jogo.getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getPeca()
															.getCor() == jogo.getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca()
															.getCor()) {
														pensamento[6] = 1;
														retorno[4] = origemX + a;
														retorno[5] = origemY - a;

													} else {
														pensamento[6] = 3;
														retorno[4] = origemX + a;
														retorno[5] = origemY + -a;

													}
												}
											}
											if ((origemY - a == movY) && (origemX + a == movX)) {
												pensamento[6] = 2;
											} 
										} else if (movX < origemX) {
											if (jogo.getTabuleiro().getGrid()[(origemY - a)][(origemX - a)].getOcupada()) {
												if (jogo.getTabuleiro().getGrid()[(origemY - a)][(origemX - a)].getPeca()
														.getCor() == jogo.getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
													pensamento[6] = 1;
													retorno[4] = origemX - a;
													retorno[5] = origemY - a;

												} else {
													pensamento[6] = 3;
													pensamento[0]= comer
															pensamento[1]=
															pensamento[2]=
															pensamento[3]=
															pensamento[4] = origemX - a;
													pensamento[5] = origemY - a;

												}
											}
											if ((origemY - a == movY) && (origemX - a == movX)) {
												pensamento[6] = 2;
											}
										}
									}
								}
							} finally {
								//TODO
							}
						}
					}
				*/
				}
			}
		}
		
		finalmente = ranquearMovimentos(melhorMovimento);
		return finalmente;
	}
}
