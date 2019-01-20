package br.com.poli.ui;

import br.com.poli.controller.NomesInvalidosException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CenaAbertura extends GridPane {
	private CenaJogo cenaJogo;
	private Label erroMsgLabel;

	public CenaAbertura() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(25);
		this.setVgap(25);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setStyle("-fx-background-image: url('/imagens/source.gif');");
		
		Text scenetitle = new Text("Damas");
		scenetitle.setFill(Color.WHITE);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(scenetitle, 0, 0, 2, 1);

        Label jogadorClaro = new Label("Jogador Claro");
        jogadorClaro.setTextFill(Color.WHITE);
        this.add(jogadorClaro, 0, 1);

        TextField jogadorClaroTextField = new TextField();
        this.add(jogadorClaroTextField, 1, 1);

		Label jogadorEscuro = new Label("Jogador Escuro");
		jogadorEscuro.setTextFill(Color.WHITE);
		this.add(jogadorEscuro, 0, 2);

        TextField jogadorEscuroTextField = new TextField();
        this.add(jogadorEscuroTextField, 1, 2);
        
		ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList("Humano", "Computador"));
		cb.setValue("Humano");
        this.add(cb, 2, 2);
		cb.setOnAction(e -> {
			System.out.println(cb.getValue());
			if (cb.getValue().equals("Computador")) {
				jogadorEscuroTextField.setText("Computador");
				jogadorEscuroTextField.setEditable(false);
			} else {
				jogadorEscuroTextField.setText("");
				jogadorEscuroTextField.setEditable(true);

			}
		});

		Button btn = new Button();
		btn.setText("Iniciar Partida");
		btn.setOnAction(evento -> {
			try {
				cenaJogo.iniciarCena(jogadorClaroTextField.getText(), jogadorEscuroTextField.getText());
				this.erroMsgLabel.setText("");
			} catch (NomesInvalidosException e) {
				this.erroMsgLabel.setText(e.getMessage());
			}
			jogadorClaroTextField.clear();
			jogadorEscuroTextField.clear();
			
		});
		this.add(btn, 3, 2);
		
		this.erroMsgLabel = new Label();
		this.add(erroMsgLabel, 1, 3);
		this.erroMsgLabel.setTextFill(Color.RED);
	}

	public void setCenaJogo(CenaJogo cenaJogo) {
		this.cenaJogo = cenaJogo;
	}
}
