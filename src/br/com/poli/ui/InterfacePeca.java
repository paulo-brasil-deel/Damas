package br.com.poli.ui;

import br.com.poli.controller.MovimentoInvalidoException;
import br.com.poli.model.pojo.JogadorAutonomo;
import br.com.poli.view.ViewLogic;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class InterfacePeca extends Circle {
	private static final int RAIO = InterfaceCasa.CASA_SIZE / 2 - InterfaceCasa.CASA_SIZE / 10;

	private boolean jogadorPreto;
	private int originX;
	private int originY;

	public InterfacePeca(boolean jogadorPreto, int x, int y, ViewLogic jogo, CenaJogo cenaJogo, boolean isDama) {
		this.jogadorPreto = jogadorPreto;

		setRadius(RAIO);
		setCenterX(x * InterfaceCasa.CASA_SIZE + InterfaceCasa.CASA_SIZE / 2);
		setCenterY(y * InterfaceCasa.CASA_SIZE + InterfaceCasa.CASA_SIZE / 2);
		if (isDama) {
			Image img;
			if (jogadorPreto) {
				img = new Image("/imagens/preta.jpeg");
			} else {
				img = new Image("/imagens/branca.jpeg");
			}

			setFill(new ImagePattern(img));
		} else {
			setFill(jogadorPreto ? Color.BLACK : Color.WHITE);

		}
		if (!(jogo.getJogadorAtual() instanceof JogadorAutonomo)) {
			setOnMousePressed(event -> {
				this.originX = (int) (event.getX() / InterfaceCasa.CASA_SIZE);
				this.originY = (int) (event.getY() / InterfaceCasa.CASA_SIZE);
				System.out.println(originX + "," + originY);
			});

			setOnMouseDragged(event -> {
				// se o jogador atual eh da mesma cor que a peca
				if (jogo.isJogadorAtualPreto() && this.jogadorPreto
						|| !jogo.isJogadorAtualPreto() && !this.jogadorPreto) {
					setCenterX(event.getX());
					setCenterY(event.getY());
				}
			});

			setOnMouseReleased(event -> {
				// se o jogador atual eh da mesma cor que a peca
				if (jogo.isJogadorAtualPreto() && this.jogadorPreto
						|| !jogo.isJogadorAtualPreto() && !this.jogadorPreto) {
					int newX = (int) (event.getX() / InterfaceCasa.CASA_SIZE);
					int newY = (int) (event.getY() / InterfaceCasa.CASA_SIZE);

					try {
						cenaJogo.limparMensagemDeMovimentoInvalido();
						if (!(jogo.getJogadorAtual() instanceof JogadorAutonomo)) {
							jogo.jogar(newX, newY, originX, originY);
						}
						if (jogo.getResultado() != null) {
							if (jogo.getVencedor() != null) {
								Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
								dialogoInfo.setTitle("VENCEDOR");
								dialogoInfo.setHeaderText("PARABENS");
								dialogoInfo.setContentText(jogo.getVencedor().getNome());
								dialogoInfo.showAndWait();
								cenaJogo.desistir();

							} else {
								Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
								dialogoInfo.setTitle("EMPATE");
								dialogoInfo.setHeaderText("Nao foi dessa vez");
								dialogoInfo.setContentText("");
								dialogoInfo.showAndWait();
								cenaJogo.desistir();
							}
						}

					} catch (MovimentoInvalidoException e) {
						cenaJogo.mostrarMensagemDeMovimentoInvalido();
					}
					cenaJogo.atualizarPecas();
				}
			});
		}

	}

}
