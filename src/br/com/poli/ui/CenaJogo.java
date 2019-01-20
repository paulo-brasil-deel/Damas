package br.com.poli.ui;

import static br.com.poli.ui.InterfaceCasa.CASA_SIZE;

import br.com.poli.controller.NomesInvalidosException;
import br.com.poli.model.executor.Jogo;
import br.com.poli.model.pojo.Casa;
import br.com.poli.model.pojo.PecaDama;
import br.com.poli.model.pojo.enums.CorPeca;
import br.com.poli.view.ViewLogic;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CenaJogo extends Pane {
	private static final Integer ALTURA = 8;
	private static final Integer LARGURA = 8;

	private ViewLogic jogo;
	private Stage window;
	private Scene cenaAbertura;
	private Scene cenaJogo;
	private int segundo = 0;
	private int minuto = 0;
	private Timeline clocktempo;

	private Group casasGrupo = new Group();
	private Group pecasGrupo = new Group();
	private Label jogadorClaroLabel;
	private Label jogadorEscuroLabel;
	private Label temporizadorLabel;
	private Label jogadasSemCapturaLabel;
	private Label contadorClaroLabel;
	private Label contadorEscuroLabel;
	private Label erroLabel;

	public CenaJogo(ViewLogic jogo, Stage window) {
		this.jogo = jogo;
		this.window = window;
		this.cenaJogo = new Scene(this);

		int sessaoJogo = 25;
		int sessaoErro = 25;

		int sessaoJogador = 25;
		this.setPrefSize(LARGURA * CASA_SIZE, ALTURA * CASA_SIZE + 2 * sessaoJogador + sessaoJogo + sessaoErro);

		this.jogadasSemCapturaLabel = new Label();
		jogadasSemCapturaLabel.relocate(0, 0);
		this.getChildren().add(jogadasSemCapturaLabel);

		this.jogadorEscuroLabel = new Label();
		jogadorEscuroLabel.relocate(0, sessaoJogo);
		this.getChildren().add(jogadorEscuroLabel);

		this.contadorEscuroLabel = new Label();
		contadorEscuroLabel.relocate(LARGURA * CASA_SIZE / 2, sessaoJogo);
		this.getChildren().add(contadorEscuroLabel);

		this.jogadorClaroLabel = new Label();
		jogadorClaroLabel.relocate(0, sessaoJogador + sessaoJogo + ALTURA * CASA_SIZE);
		this.getChildren().add(jogadorClaroLabel);

		this.contadorClaroLabel = new Label();
		contadorClaroLabel.relocate(LARGURA * CASA_SIZE / 2, sessaoJogador + sessaoJogo + ALTURA * CASA_SIZE);
		this.getChildren().add(contadorClaroLabel);

		this.temporizadorLabel = new Label();
		temporizadorLabel.relocate(7 * CASA_SIZE, 0);
		temporizadorLabel.setText("00:00");
		this.getChildren().add(temporizadorLabel);

		this.erroLabel = new Label();
		erroLabel.relocate(0, sessaoErro + 2 * sessaoJogador + ALTURA * CASA_SIZE);
		erroLabel.setTextFill(Color.RED);
		this.getChildren().add(erroLabel);

		Button buttonDesistir = new Button();
		buttonDesistir.relocate(LARGURA * CASA_SIZE / 2, sessaoErro + 2 * sessaoJogador + ALTURA * CASA_SIZE);
		buttonDesistir.setText("Give Up");
		buttonDesistir.setOnAction(e -> desistir());
		this.getChildren().add(buttonDesistir);

		Pane tabuleiro = criarTabuleiro();
		tabuleiro.relocate(0, sessaoJogador + sessaoJogo);

		this.getChildren().add(tabuleiro);

		clocktempo = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			segundo++;
			if (segundo == 60) {
				segundo = 0;
				minuto++;
			}

			temporizadorLabel.setText(String.format("%02d", minuto) + ":" + String.format("%02d", segundo));
			
			if (jogo.getComputador() != null && jogo.getJogadorAtual() == jogo.getComputador()) {
				jogo.getComputador().jogarAuto((Jogo) jogo);
				atualizarPecas();
			}
		}), new KeyFrame(Duration.seconds(1)));
		clocktempo.setCycleCount(Animation.INDEFINITE);
	}

	public void setCenaAbertura(Scene cenaAbertura) {
		this.cenaAbertura = cenaAbertura;
	}

	public void limparMensagemDeMovimentoInvalido() {
		this.erroLabel.setText("");
	}

	public void mostrarMensagemDeMovimentoInvalido() {
		this.erroLabel.setText("MOVIMENTO INVALIDO");
	}

	public void atualizarPecas() {
		pecasGrupo.getChildren().clear();

		Casa[][] casas = jogo.getTabuleiro().getGrid();
		for (int y = 0; y < ALTURA; y++) {
			for (int x = 0; x < LARGURA; x++) {
				if (casas[y][x].getOcupada()) {

					boolean isDama = casas[y][x].getPeca() instanceof PecaDama;
					if (casas[y][x].getPeca().getCor() == CorPeca.ESCURA) {

						InterfacePeca c = new InterfacePeca(true, x, y, jogo, this, isDama);
						pecasGrupo.getChildren().add(c);

					} else {

						InterfacePeca c = new InterfacePeca(false, x, y, jogo, this, isDama);
						pecasGrupo.getChildren().add(c);

					}
				}
			}
		}
		jogadasSemCapturaLabel.setText("Jogadas sem capturas: " + jogo.getContadorJogadas());
		contadorClaroLabel.setText("Numero de Pecas: " + jogo.getNumeroPecasClaras());
		contadorEscuroLabel.setText("Numero de Pecas: " + jogo.getNumeroPecasEscuras());
	}

	public void desistir() {
		clocktempo.stop();
		window.setScene(cenaAbertura);
	}

	public void iniciarCena(String jogadorClaro, String jogadorEscuro) throws NomesInvalidosException {
		jogo.iniciarPartida(jogadorClaro, jogadorEscuro);
		jogadorClaroLabel.setText("Nome: " + jogadorClaro);
		jogadorEscuroLabel.setText("Nome: " + jogadorEscuro);

		this.segundo = 0;
		this.minuto = 0;
		clocktempo.play();

		this.atualizarPecas();
		window.setScene(cenaJogo);
	}

	private Pane criarTabuleiro() {
		Pane pane = new Pane();

		pane.getChildren().add(casasGrupo);
		pane.getChildren().add(pecasGrupo);
		pane.setPrefSize(LARGURA * CASA_SIZE, ALTURA * CASA_SIZE);

		criarGridInterface();
		// atualizarPecas();

		return pane;
	}

	private void criarGridInterface() {
		for (int y = 0; y < ALTURA; y++) {
			for (int x = 0; x < LARGURA; x++) {
				InterfaceCasa casa = new InterfaceCasa((x + y) % 2 == 0, x, y);
				casasGrupo.getChildren().add(casa);
			}
		}
	}

}
