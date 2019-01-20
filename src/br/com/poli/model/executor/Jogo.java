package br.com.poli.model.executor;

import br.com.poli.controller.MovimentoInvalidoException;
import br.com.poli.controller.NomesInvalidosException;
import br.com.poli.model.pojo.Casa;
import br.com.poli.model.pojo.Jogador;
import br.com.poli.model.pojo.JogadorAutonomo;
import br.com.poli.model.pojo.Peca;
import br.com.poli.model.pojo.PecaDama;
import br.com.poli.model.pojo.enums.CorCasa;
import br.com.poli.model.pojo.enums.CorPeca;
import br.com.poli.model.pojo.enums.Resultados;
import br.com.poli.view.Tabuleiro;
import br.com.poli.view.ViewLogic;

public class Jogo implements ViewLogic {
	private static final NomesInvalidosException APELIDOS_IGUAIS_EXCEPTION = new NomesInvalidosException("Apelidos iguais!");
	private static final NomesInvalidosException APELIDOS_CURTO_EXCEPTION = new NomesInvalidosException("Apelido menor que 3 letras");
	
	private JogadorAutonomo computador;
	private Tabuleiro tabuleiro;
	private Resultados resultado;
	private Jogador vencedor;
	private int contadorJogadas;
	private Jogador jogadorAtual;

	@Override
	public JogadorAutonomo getComputador() {
		return this.computador;
	}
	
 	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	private void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Resultados getResultado() {
		return resultado;
	}

	private void setResultado(Resultados resultado) {
		this.resultado = resultado;
	}

	public Jogador getVencedor() {
		return vencedor;
	}

	private void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public int getContadorJogadas() {
		return contadorJogadas;
	}

	private void setContadorJogadas(int contadorJogadas) {
		this.contadorJogadas = contadorJogadas;
	}

	public Jogador getJogadorAtual() {
		return jogadorAtual;
	}

	public void setJogadorAtual(Jogador jogadorAtual) {
		this.jogadorAtual = jogadorAtual;
	}

	public boolean isJogadorAtualPreto() {
		return tabuleiro.getPeca2().getJogador().equals(getJogadorAtual());
	}

	public int getNumeroPecasClaras() {
		int contadorClaro = 0;

		Casa[][] grid = tabuleiro.getGrid();
		for (int i = 0; i < getTabuleiro().getGrid().length; i++) {
			for (int j = 0; j < getTabuleiro().getGrid().length; j++) {
				if (grid[i][j].getOcupada() && grid[i][j].getPeca().getCor() == CorPeca.CLARA)
					contadorClaro++;
			}
		}
		return contadorClaro;

	}

	public int getNumeroPecasEscuras() {
		int contadorEscura = 0;

		Casa[][] grid = tabuleiro.getGrid();
		for (int i = 0; i < getTabuleiro().getGrid().length; i++) {
			for (int j = 0; j < getTabuleiro().getGrid().length; j++) {
				if (grid[i][j].getOcupada() && grid[i][j].getPeca().getCor() == CorPeca.ESCURA)
					contadorEscura++;
			}
		}
		return contadorEscura;
	}

	@Override
	public Jogador iniciarPartida(String jogador1, String jogador2) throws NomesInvalidosException {

		if (jogador1.equals(jogador2)) {
			throw APELIDOS_IGUAIS_EXCEPTION;
		} else if (jogador1.length() < 3 || jogador2.length() < 3) {
			throw APELIDOS_CURTO_EXCEPTION;
		} else {
			resultado = null;
			vencedor= null;
			Jogador j2 = null;
			Peca p2 = null;
			
			Jogador j1 = new Jogador(jogador1);
			if(!(jogador2.equals("Computador"))) {
				j2 = new Jogador(jogador2);
				p2 = new Peca(j2, CorPeca.ESCURA);
			}else {
				this.computador = new JogadorAutonomo(jogador2);
				p2 = new Peca(this.computador, CorPeca.ESCURA);
			}
			
			Peca p1 = new Peca(j1, CorPeca.CLARA);
			
			Tabuleiro t1 = new Tabuleiro(p1, p2);
			// t1.TabuleiroTestes();
			t1.gerarTabuleiro();
			setTabuleiro(t1);
			if (getTabuleiro() != null) {
				if (getTabuleiro().getPeca1() != null) {
					if (getTabuleiro().getPeca1().getCor() != null) {
						if (getTabuleiro().getPeca1().getCor() == CorPeca.CLARA) {
							setJogadorAtual(getTabuleiro().getPeca1().getJogador());
						} else {
							setJogadorAtual(getTabuleiro().getPeca2().getJogador());
						}
					}
				}
			}
		}

		return jogadorAtual;
	}

	private int[] movimentoDama(int movX, int movY, int origemX, int origemY) throws MovimentoInvalidoException {
		int[] retorno = new int[3];
		int contador = movY - origemY;
		retorno[0] = -1;
		if (contador < 0) {
			contador = contador * -1;
		}
		try {
			for (int a = 1; a <= contador; a++) {
				if (movY > origemY) {
					if (movX > origemX) {
						if (getTabuleiro().getGrid()[(origemY + a)][(origemX + a)].getOcupada()) {
							if (getTabuleiro().getGrid()[(origemY + a)][(origemX + a)].getPeca()
									.getCor() == getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
								retorno[0] = 1;
								retorno[1] = origemX + a;
								retorno[2] = origemY + a;
								return retorno;
							} else {
								retorno[0] = 3;
								retorno[1] = origemX + a;
								retorno[2] = origemY + a;
								return retorno;
							}
						}
						if ((origemY + a == movY) && (origemX + a == movX)) {
							retorno[0] = 2;
						} else {
							retorno[0] = -1;
						}
					} else if (movX < origemX) {
						if (getTabuleiro().getGrid()[(origemY + a)][(origemX - a)].getOcupada()) {
							if (getTabuleiro().getGrid()[(origemY + a)][(origemX - a)].getPeca()
									.getCor() == getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
								retorno[0] = 1;
								retorno[1] = origemX - a;
								retorno[2] = origemY + a;
								return retorno;
							} else {
								retorno[0] = 3;
								retorno[1] = origemX - a;
								retorno[2] = origemY + a;
								return retorno;
							}
						}
						if ((origemY + a == movY) && (origemX - a == movX)) {
							retorno[0] = 2;
						} else {
							retorno[0] = -1;
						}
					}
				} else if (movY < origemY) {
					if (movX > origemX) {
						if (getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getOcupada()) {
							if (getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getPeca() != null
									&& getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca() != null) {
								if (getTabuleiro().getGrid()[(origemY - a)][(origemX + a)].getPeca()
										.getCor() == getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca()
												.getCor()) {
									retorno[0] = 1;
									retorno[1] = origemX + a;
									retorno[2] = origemY - a;
									return retorno;
								} else {
									retorno[0] = 3;
									retorno[1] = origemX + a;
									retorno[2] = origemY + -a;
									return retorno;
								}
							}
						}
						if ((origemY - a == movY) && (origemX + a == movX)) {
							retorno[0] = 2;
						} else {
							retorno[0] = -1;
						}
					} else if (movX < origemX) {
						if (getTabuleiro().getGrid()[(origemY - a)][(origemX - a)].getOcupada()) {
							if (getTabuleiro().getGrid()[(origemY - a)][(origemX - a)].getPeca()
									.getCor() == getTabuleiro().getGrid()[(origemY)][(origemX)].getPeca().getCor()) {
								retorno[0] = 1;
								retorno[1] = origemX - a;
								retorno[2] = origemY - a;
								return retorno;
							} else {
								retorno[0] = 3;
								retorno[1] = origemX - a;
								retorno[2] = origemY - a;
								return retorno;
							}
						}
						if ((origemY - a == movY) && (origemX - a == movX)) {
							retorno[0] = 2;
						} else {
							retorno[0] = -1;
						}
					}
				}
			}
			if (retorno[0] == -1) {
				throw new MovimentoInvalidoException("Movimento inválido");
			}
		} catch (NullPointerException e1) {
			retorno[0] = 0;
			throw new MovimentoInvalidoException("Movimento inválido");
		} catch (ArrayIndexOutOfBoundsException e2) {
			retorno[0] = 0;
			throw new MovimentoInvalidoException("Movimento inválido");
		}
		return retorno;
	}

	private int movimentoPossivel(int movX, int movY, int origemX, int origemY) throws MovimentoInvalidoException {
		PecaDama comparadorDama = new PecaDama();
		int retorno = 0;
		try {
			if (!getTabuleiro().getGrid()[origemY][origemX].getPeca().getJogador().equals(getJogadorAtual())) {
				throw new MovimentoInvalidoException("Movimento inválido");
			} else {
				if (!comparadorDama.comparar(getTabuleiro().getGrid()[origemY][origemX].getPeca())) {
					if (getTabuleiro().getGrid()[movY][movX].getCor() != CorCasa.PRETA) {
						throw new MovimentoInvalidoException("Movimento inválido");
					} else {
						if ((getTabuleiro().getGrid()[movY][movX].getOcupada())) {
							throw new MovimentoInvalidoException("Casa Ocupada");
						} else {
							if (getTabuleiro().getGrid()[origemY][origemX].getPeca().getCor() == CorPeca.CLARA) {
								if ((origemY - 1) != movY) {
									throw new MovimentoInvalidoException("Movimento inválido");
								} else {
									retorno = 1;
									return retorno;
								}
							} else if (getTabuleiro().getGrid()[origemY][origemX].getPeca()
									.getCor() == CorPeca.ESCURA) {
								if ((origemY + 1) != movY) {
									throw new MovimentoInvalidoException("Movimento invalido");
								} else {
									retorno = 1;
									return retorno;
								}
							}
						}
					}
				} else {
					retorno = 2;
					return retorno;
				}
			}
		} catch (NullPointerException e1) {
			throw new MovimentoInvalidoException("Movimento invalido");
		} catch (ArrayIndexOutOfBoundsException e2) {
			throw new MovimentoInvalidoException("Movimento invalido");
		}
		return retorno;
	}

	private void verificarCriacaoDama(int origemX, int origemY) throws MovimentoInvalidoException {
		try {
				for (int i = 0; i < getTabuleiro().getGrid().length; i++) {
					if (getTabuleiro().getGrid()[origemY][origemX] == getTabuleiro().getGrid()[0][i]) {
						if (getTabuleiro().getGrid()[origemY][origemX].getPeca().getCor() == CorPeca.CLARA) {
							PecaDama pecaDama = new PecaDama(getTabuleiro().getGrid()[0][i].getPeca().getJogador(),
									CorPeca.CLARA);
							getTabuleiro().getGrid()[0][i].setPeca(pecaDama);
							break;
						}
					} else if (getTabuleiro().getGrid()[origemY][origemX] == getTabuleiro().getGrid()[7][i]) {
						if (getTabuleiro().getGrid()[origemY][origemX].getPeca().getCor() == CorPeca.ESCURA) {
							PecaDama pecaDama = new PecaDama(getTabuleiro().getGrid()[7][i].getPeca().getJogador(),
									CorPeca.ESCURA);
							getTabuleiro().getGrid()[7][i].setPeca(pecaDama);
							break;
						}
					}
				}

		} catch (NullPointerException e1) {
			throw new MovimentoInvalidoException("Coordenada de Origem Invalida");
		} catch (ArrayIndexOutOfBoundsException e2) {
			throw new MovimentoInvalidoException("Coordenada de Origem Invalida");
		}
	}

	private int[] possibilidadeCapturar() {
		PecaDama comparadorDama = new PecaDama();
		int[] posicoes = new int[12];
		int contadorPossibilidades = 0;
		for (int origemX = 0; origemX < getTabuleiro().getGrid().length; origemX++) {
			for (int origemY = 0; origemY < getTabuleiro().getGrid().length; origemY++) {
				for (int movX = (getTabuleiro().getGrid().length) - 1; movX >= 0; movX--) {
					for (int movY = (getTabuleiro().getGrid().length) - 1; movY >= 0; movY--) {
						if ((getTabuleiro().getGrid()[origemY][origemX].getPeca() != null)
								&& (getTabuleiro().getGrid()[movY][movX].getPeca() != null)) {
							if (getTabuleiro().getGrid()[origemY][origemX].getPeca()
									.getCor() != getTabuleiro().getGrid()[movY][movX].getPeca().getCor()) {
								if (getTabuleiro().getGrid()[origemY][origemX].getPeca().getJogador()
										.equals(getJogadorAtual())) {
									if (!comparadorDama
											.comparar(getTabuleiro().getGrid()[origemY][origemX].getPeca())) {
										if (movY + 1 == origemY) {
											if (movX - 1 == origemX) {
												if (!((movY - 1) < 0 || (movX + 1) > 7)) {
													if (!getTabuleiro().getGrid()[(movY - 1)][(movX + 1)]
															.getOcupada()) {
														if (contadorPossibilidades <= 0) {
															posicoes[contadorPossibilidades] = origemX;
															posicoes[contadorPossibilidades + 1] = origemY;
															posicoes[contadorPossibilidades + 2] = movX;
															posicoes[contadorPossibilidades + 3] = movY;
														} else if (contadorPossibilidades > 0) {
															posicoes[contadorPossibilidades + 3] = origemX;
															posicoes[contadorPossibilidades + 4] = origemY;
															posicoes[contadorPossibilidades + 5] = movX;
															posicoes[contadorPossibilidades + 6] = movY;
														} else if (contadorPossibilidades > 1) {
															posicoes[contadorPossibilidades + 6] = origemX;
															posicoes[contadorPossibilidades + 7] = origemY;
															posicoes[contadorPossibilidades + 8] = movX;
															posicoes[contadorPossibilidades + 9] = movY;
														}
														contadorPossibilidades++;
													}
												}
											} else if (movX + 1 == origemX) {
												if (!((movY - 1) < 0 || (movX - 1) < 0)) {
													if (!getTabuleiro().getGrid()[(movY - 1)][(movX - 1)]
															.getOcupada()) {
														if (contadorPossibilidades <= 0) {
															posicoes[contadorPossibilidades] = origemX;
															posicoes[contadorPossibilidades + 1] = origemY;
															posicoes[contadorPossibilidades + 2] = movX;
															posicoes[contadorPossibilidades + 3] = movY;
														} else if (contadorPossibilidades > 0) {
															posicoes[contadorPossibilidades + 3] = origemX;
															posicoes[contadorPossibilidades + 4] = origemY;
															posicoes[contadorPossibilidades + 5] = movX;
															posicoes[contadorPossibilidades + 6] = movY;
														} else if (contadorPossibilidades > 1) {
															posicoes[contadorPossibilidades + 6] = origemX;
															posicoes[contadorPossibilidades + 7] = origemY;
															posicoes[contadorPossibilidades + 8] = movX;
															posicoes[contadorPossibilidades + 9] = movY;
														}
														contadorPossibilidades++;
													}
												}
											}
										} else if (movY - 1 == origemY) {
											if (movX - 1 == origemX) {
												if (!((movY + 1) > 7 || (movX + 1) > 7)) {
													if (!getTabuleiro().getGrid()[(movY + 1)][(movX + 1)]
															.getOcupada()) {
														if (contadorPossibilidades <= 0) {
															posicoes[contadorPossibilidades] = origemX;
															posicoes[contadorPossibilidades + 1] = origemY;
															posicoes[contadorPossibilidades + 2] = movX;
															posicoes[contadorPossibilidades + 3] = movY;
														} else if (contadorPossibilidades > 0) {
															posicoes[contadorPossibilidades + 3] = origemX;
															posicoes[contadorPossibilidades + 4] = origemY;
															posicoes[contadorPossibilidades + 5] = movX;
															posicoes[contadorPossibilidades + 6] = movY;
														} else if (contadorPossibilidades > 1) {
															posicoes[contadorPossibilidades + 6] = origemX;
															posicoes[contadorPossibilidades + 7] = origemY;
															posicoes[contadorPossibilidades + 8] = movX;
															posicoes[contadorPossibilidades + 9] = movY;
														}
														contadorPossibilidades++;
													}
												}
											} else if (movX + 1 == origemX) {
												if (!((movY + 1) > 7 || (movX - 1) < 0)) {
													if (!getTabuleiro().getGrid()[(movY + 1)][(movX - 1)]
															.getOcupada()) {
														if (contadorPossibilidades <= 0) {
															posicoes[contadorPossibilidades] = origemX;
															posicoes[contadorPossibilidades + 1] = origemY;
															posicoes[contadorPossibilidades + 2] = movX;
															posicoes[contadorPossibilidades + 3] = movY;
														} else if (contadorPossibilidades > 0) {
															posicoes[contadorPossibilidades + 3] = origemX;
															posicoes[contadorPossibilidades + 4] = origemY;
															posicoes[contadorPossibilidades + 5] = movX;
															posicoes[contadorPossibilidades + 6] = movY;
														} else if (contadorPossibilidades > 1) {
															posicoes[contadorPossibilidades + 6] = origemX;
															posicoes[contadorPossibilidades + 7] = origemY;
															posicoes[contadorPossibilidades + 8] = movX;
															posicoes[contadorPossibilidades + 9] = movY;
														}
														contadorPossibilidades++;
													}
												}
											}
										}
									} else {
										try {
											int[] array = movimentoDama(movX, movY, origemX, origemY);
											if (array[0] == 3) {
												if (array[2] < origemY) {
													if (array[1] > origemX) {
														if (!((array[2] - 1) < 0 || (array[1] + 1) > 7)) {
															if (!getTabuleiro().getGrid()[(array[2] - 1)][(array[1]
																	+ 1)].getOcupada()) {
																if (contadorPossibilidades <= 0) {
																	posicoes[contadorPossibilidades] = origemX;
																	posicoes[contadorPossibilidades + 1] = origemY;
																	posicoes[contadorPossibilidades + 2] = array[1];
																	posicoes[contadorPossibilidades + 3] = array[2];
																} else if (contadorPossibilidades > 0) {
																	posicoes[contadorPossibilidades + 3] = origemX;
																	posicoes[contadorPossibilidades + 4] = origemY;
																	posicoes[contadorPossibilidades + 5] = array[1];
																	posicoes[contadorPossibilidades + 6] = array[2];
																} else if (contadorPossibilidades > 1) {
																	posicoes[contadorPossibilidades + 6] = origemX;
																	posicoes[contadorPossibilidades + 7] = origemY;
																	posicoes[contadorPossibilidades + 8] = array[1];
																	posicoes[contadorPossibilidades + 9] = array[2];
																}
																contadorPossibilidades++;
															}
														}
													} else if (array[1] < origemX) {
														if (!((array[2] - 1) < 0 || (array[1] - 1) < 0)) {
															if (!getTabuleiro().getGrid()[(array[2] - 1)][(array[1]
																	- 1)].getOcupada()) {
																if (contadorPossibilidades <= 0) {
																	posicoes[contadorPossibilidades] = origemX;
																	posicoes[contadorPossibilidades + 1] = origemY;
																	posicoes[contadorPossibilidades + 2] = array[1];
																	posicoes[contadorPossibilidades + 3] = array[2];
																} else if (contadorPossibilidades > 0) {
																	posicoes[contadorPossibilidades + 3] = origemX;
																	posicoes[contadorPossibilidades + 4] = origemY;
																	posicoes[contadorPossibilidades + 5] = array[1];
																	posicoes[contadorPossibilidades + 6] = array[2];
																} else if (contadorPossibilidades > 1) {
																	posicoes[contadorPossibilidades + 6] = origemX;
																	posicoes[contadorPossibilidades + 7] = origemY;
																	posicoes[contadorPossibilidades + 8] = array[1];
																	posicoes[contadorPossibilidades + 9] = array[2];
																}
																contadorPossibilidades++;
															}
														}
													}
												} else if (array[2] > origemY) {
													if (array[1] > origemX) {
														if (!((array[2] + 1) > 7 || (array[1] + 1) > 7)) {
															if (!getTabuleiro().getGrid()[(array[2] + 1)][(array[1]
																	+ 1)].getOcupada()) {
																if (contadorPossibilidades <= 0) {
																	posicoes[contadorPossibilidades] = origemX;
																	posicoes[contadorPossibilidades + 1] = origemY;
																	posicoes[contadorPossibilidades + 2] = array[1];
																	posicoes[contadorPossibilidades + 3] = array[2];
																} else if (contadorPossibilidades > 0) {
																	posicoes[contadorPossibilidades + 3] = origemX;
																	posicoes[contadorPossibilidades + 4] = origemY;
																	posicoes[contadorPossibilidades + 5] = array[1];
																	posicoes[contadorPossibilidades + 6] = array[2];
																} else if (contadorPossibilidades > 1) {
																	posicoes[contadorPossibilidades + 6] = origemX;
																	posicoes[contadorPossibilidades + 7] = origemY;
																	posicoes[contadorPossibilidades + 8] = array[1];
																	posicoes[contadorPossibilidades + 9] = array[2];
																}
																contadorPossibilidades++;
															}
														}
													} else if (array[1] < origemX) {
														if (!((array[2] + 1) > 7 || (array[1] - 1) < 0)) {
															if (!getTabuleiro().getGrid()[(array[2] + 1)][(array[1]
																	- 1)].getOcupada()) {
																if (contadorPossibilidades <= 0) {
																	posicoes[contadorPossibilidades] = origemX;
																	posicoes[contadorPossibilidades + 1] = origemY;
																	posicoes[contadorPossibilidades + 2] = array[1];
																	posicoes[contadorPossibilidades + 3] = array[2];
																} else if (contadorPossibilidades > 0) {
																	posicoes[contadorPossibilidades + 3] = origemX;
																	posicoes[contadorPossibilidades + 4] = origemY;
																	posicoes[contadorPossibilidades + 5] = array[1];
																	posicoes[contadorPossibilidades + 6] = array[2];
																} else if (contadorPossibilidades > 1) {
																	posicoes[contadorPossibilidades + 6] = origemX;
																	posicoes[contadorPossibilidades + 7] = origemY;
																	posicoes[contadorPossibilidades + 8] = array[1];
																	posicoes[contadorPossibilidades + 9] = array[2];
																}
																contadorPossibilidades++;
															}
														}
													}
												}
											}
										} catch (MovimentoInvalidoException ex) {
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (contadorPossibilidades > 0) {
			if (contadorPossibilidades == 1) {
				posicoes[contadorPossibilidades + 3] = -1;
			} else if (contadorPossibilidades == 2) {
				posicoes[contadorPossibilidades + 6] = -1;
			}
		} else {
			posicoes[contadorPossibilidades] = -1;
		}
		return posicoes;
	}

	private void atualizador() {
		if (getTabuleiro().getPeca1() != null && getTabuleiro().getPeca1().getJogador() != null
				&& !getTabuleiro().getPeca1().getJogador().getNome().equalsIgnoreCase(getJogadorAtual().getNome())) {
			setJogadorAtual(getTabuleiro().getPeca1().getJogador());
		} else if (getTabuleiro().getPeca2() != null && getTabuleiro().getPeca2().getJogador() != null
				&& !getTabuleiro().getPeca2().getJogador().getNome().equalsIgnoreCase(getJogadorAtual().getNome())) {
			setJogadorAtual(getTabuleiro().getPeca2().getJogador());
		}
	}

	private void executaMovimentoDama(int movX, int movY, int origemX, int origemY) throws MovimentoInvalidoException {
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

	public int[] capturar(int movX, int movY, int origemX, int origemY) {
		int[] vetor = new int[2];
		if (movY < origemY) {
			if (movX > origemX) {
				this.tabuleiro.getGrid()[(movY - 1)][(movX + 1)]
						.setPeca(this.tabuleiro.getGrid()[origemY][origemX].getPeca());
				this.tabuleiro.getGrid()[(movY - 1)][(movX + 1)].setOcupada(true);

				vetor[0] = movX + 1;
				vetor[1] = movY - 1;

			} else if (movX < origemX) {
				this.tabuleiro.getGrid()[(movY - 1)][(movX - 1)]
						.setPeca(this.tabuleiro.getGrid()[origemY][origemX].getPeca());
				this.tabuleiro.getGrid()[(movY - 1)][(movX - 1)].setOcupada(true);

				vetor[0] = movX - 1;
				vetor[1] = movY - 1;

			}
			setContadorJogadas(0);
			this.tabuleiro.getGrid()[origemY][origemX].setPeca(null);
			this.tabuleiro.getGrid()[origemY][origemX].setOcupada(false);
			this.tabuleiro.getGrid()[movY][movX].setPeca(null);
			this.tabuleiro.getGrid()[movY][movX].setOcupada(false);

			return vetor;

		} else if (movY > origemY) {
			if (movX > origemX) {
				this.tabuleiro.getGrid()[(movY + 1)][(movX + 1)]
						.setPeca(this.tabuleiro.getGrid()[origemY][origemX].getPeca());
				this.tabuleiro.getGrid()[(movY + 1)][(movX + 1)].setOcupada(true);

				vetor[0] = movX + 1;
				vetor[1] = movY + 1;

			} else if (movX < origemX) {
				this.tabuleiro.getGrid()[(movY + 1)][(movX - 1)]
						.setPeca(this.tabuleiro.getGrid()[origemY][origemX].getPeca());
				this.tabuleiro.getGrid()[(movY + 1)][(movX - 1)].setOcupada(true);

				vetor[0] = movX - 1;
				vetor[1] = movY + 1;
			}
			setContadorJogadas(0);
			this.tabuleiro.getGrid()[origemY][origemX].setPeca(null);
			this.tabuleiro.getGrid()[origemY][origemX].setOcupada(false);
			this.tabuleiro.getGrid()[movY][movX].setPeca(null);
			this.tabuleiro.getGrid()[movY][movX].setOcupada(false);

			return vetor;
		}
		return vetor;
	}

	private void atualizadorPossibilidades(int movX, int movY) throws MovimentoInvalidoException {
		int[] array = possibilidadeCapturar();
		if (array[0] == -1) {
			verificarCriacaoDama(movX, movY);
			atualizador();
		}
	}

	private boolean isFimJogo() {
		PecaDama comparadorDama = new PecaDama();
		int contadorDamaClara = 0;
		int contadorDamaEscura = 0;
		int contadorPecaClara = 0;
		int contadorPecaEscura = 0;
		int contadorAtual = 0;
		Jogador vencedorAtual = null;
		int retorno = 0;
		if (!(getContadorJogadas() == 20)) {
			for (int i = 0; i < getTabuleiro().getGrid().length; i++) {
				for (Casa[] grid : getTabuleiro().getGrid()) {
					if (grid[i].getPeca() != null) {
						if (grid[i].getPeca().getCor() == CorPeca.CLARA) {
							if (grid[i].getPeca().getJogador() == getJogadorAtual()) {
								contadorAtual++;
							} else {
								vencedorAtual = grid[i].getPeca().getJogador();
							}
							if (comparadorDama.comparar(grid[i].getPeca())) {
								contadorDamaClara++;
							} else {
								contadorPecaClara++;
							}
						} else {
							if (grid[i].getPeca().getJogador() == getJogadorAtual()) {
								contadorAtual++;
							} else {
								vencedorAtual = grid[i].getPeca().getJogador();
							}
							if (comparadorDama.comparar(grid[i].getPeca())) {
								contadorDamaEscura++;
							} else {
								contadorPecaEscura++;
							}
						}
					}
				}
			}
			if (contadorAtual == 0) {
				setResultado(Resultados.COMVENCEDOR);
				setVencedor(vencedorAtual);
				return true;
			}
			if (contadorDamaClara == 2 && contadorDamaEscura == 2) {
				retorno = 1;
			} else if ((contadorDamaClara == 2 && contadorDamaEscura == 1)
					|| (contadorDamaEscura == 2 && contadorDamaClara == 1)) {
				retorno = 1;
			} else if ((contadorDamaClara == 2 && contadorDamaEscura == 1 && contadorPecaEscura == 1)
					|| (contadorDamaEscura == 2 && contadorDamaClara == 1 && contadorPecaClara == 1)) {
				retorno = 1;
			} else if ((contadorDamaClara == 2 && contadorDamaEscura == 2 && contadorPecaEscura == 1)
					|| (contadorDamaEscura == 2 && contadorDamaClara == 2 && contadorPecaClara == 1)) {
				retorno = 1;
			}
			if (retorno == 1 && getContadorJogadas() == 20) {
				setResultado(Resultados.EMPATE);
				return true;
			}
		} else {
			setResultado(Resultados.EMPATE);
			return true;
		}
		return false;
	}

	public void jogar(int movX, int movY, int origemX, int origemY) throws MovimentoInvalidoException {
		if (!isFimJogo()) {
			int[] array = possibilidadeCapturar();
			if (array[0] != -1) {
				if (array[4] == -1) {
					if ((origemX == array[0] && origemY == array[1]) && (movX == array[2] && movY == array[3])) {
						int[] cap = capturar(array[2], array[3], array[0], array[1]);
						atualizadorPossibilidades(cap[0], cap[1]);
					}
				} else if (array[8] == -1) {
					if ((origemX == array[0] && origemY == array[1]) && (movX == array[2] && movY == array[3])) {
						int[] cap = capturar(array[2], array[3], array[0], array[1]);
						atualizadorPossibilidades(cap[0], cap[1]);
					} else if ((origemX == array[4] && origemY == array[5]) && (movX == array[6] && movY == array[7])) {
						int[] cap = capturar(array[6], array[7], array[4], array[5]);
						atualizadorPossibilidades(cap[0], cap[1]);
					}
				} else {
					if ((origemX == array[0] && origemY == array[1]) && (movX == array[2] && movY == array[3])) {
						int[] cap = capturar(array[2], array[3], array[0], array[1]);
						atualizadorPossibilidades(cap[0], cap[1]);
					} else if ((origemX == array[4] && origemY == array[5]) && (movX == array[6] && movY == array[7])) {
						int[] cap = capturar(array[6], array[7], array[4], array[5]);
						atualizadorPossibilidades(cap[0], cap[1]);
					} else if ((origemX == array[8] && origemY == array[9]) && (movX == array[8] && movY == array[9])) {
						int[] cap = capturar(array[10], array[11], array[8], array[9]);
						atualizadorPossibilidades(cap[0], cap[1]);
					}
				}
			} else {
				if (movimentoPossivel(movX, movY, origemX, origemY) == 2) {
					int[] vetor = movimentoDama(movX, movY, origemX, origemY);
					if (vetor[0] == 1) {
						executaMovimentoDama(vetor[1], vetor[2], origemX, origemY);
						setContadorJogadas(getContadorJogadas() + 1);
						atualizador();
					} else if (vetor[0] == 2) {
						getTabuleiro().executarMovimento(movX, movY, origemX, origemY);
						setContadorJogadas(getContadorJogadas() + 1);
						atualizador();
					}
				} else if (movimentoPossivel(movX, movY, origemX, origemY) == 1) {
					getTabuleiro().executarMovimento(movX, movY, origemX, origemY);
					verificarCriacaoDama(movX, movY);
					setContadorJogadas(getContadorJogadas() + 1);
					atualizador();
				}
			}
		}
		isFimJogo();
	}

	public void statusTabuleiro() {
		String espacoBranco = "[ ]";
		String espacoPreto = "[/]";
		String claro = "[O]";
		String escuro = "[X]";
		System.out.printf("\n");
		if (!isFimJogo()) {
			System.out.println(getJogadorAtual().getNome());
		} else if (getResultado() == Resultados.COMVENCEDOR) {
			System.out.println("O VENCEDOR EH: " + getVencedor().getNome());
		} else if (getResultado() == Resultados.EMPATE) {
			System.out.println("EMPATE");
		}
		for (int posicaoCasa2 = 0; posicaoCasa2 < getTabuleiro().getGrid().length; posicaoCasa2++) {
			System.out.print("| " + posicaoCasa2);
		}
		System.out.printf("\n");
		for (int posicaoCasa1 = 0; posicaoCasa1 < getTabuleiro().getGrid().length; posicaoCasa1++) {
			for (int posicaoCasa2 = 0; posicaoCasa2 < getTabuleiro().getGrid().length; posicaoCasa2++) {
				if (getTabuleiro().getGrid()[posicaoCasa1][posicaoCasa2].getOcupada()) {
					if (getTabuleiro().getGrid()[posicaoCasa1][posicaoCasa2].getPeca().getCor() == CorPeca.CLARA) {
						System.out.print(claro);
					} else {
						System.out.print(escuro);
					}
				} else {
					if (getTabuleiro().getGrid()[posicaoCasa1][posicaoCasa2].getCor() == CorCasa.BRANCA) {
						System.out.print(espacoBranco);
					} else {
						System.out.print(espacoPreto);
					}
				}
			}
			System.out.print("_" + posicaoCasa1);
			System.out.printf("\n");
		}
	}
}
