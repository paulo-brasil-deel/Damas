package br.com.poli.ui;

import br.com.poli.model.executor.Jogo;
import br.com.poli.view.ViewLogic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application  {
	private Stage window;
    private ViewLogic jogo = new Jogo();

    @Override
    public void start(Stage primaryStage) throws Exception {
    	window = primaryStage;
    	

        CenaAbertura cenaAbertura = new CenaAbertura();
        CenaJogo cenaJogo = new CenaJogo(jogo, window);
        
        Scene cenaAberturaScene = new Scene(cenaAbertura);
        
        cenaAbertura.setCenaJogo(cenaJogo);
        cenaJogo.setCenaAbertura(cenaAberturaScene);
        
        window.setTitle("Damas");
        window.setScene(cenaAberturaScene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

	}
