package br.com.poli.controller;

import br.com.poli.model.executor.Jogo;


public class Benchmark {

    public static void main(String[] args) throws MovimentoInvalidoException, NomesInvalidosException {
        Jogo jogo = new Jogo();
        
        jogo.iniciarPartida("Jair", "Paulo"); 

        jogo.statusTabuleiro();
        
        jogo.jogar(4, 6, 5, 7);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(5, 1, 4, 0);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(3, 5, 4, 6);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(6, 2, 5, 1);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(4, 6, 3, 7);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(3, 1, 2, 0);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(2, 6, 1, 7);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(4, 2, 3, 1);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(1, 5, 2, 6);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(5, 3, 6, 2);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(0, 4, 1, 5);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(4, 4, 5, 3);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(4, 4, 3, 5);
        
        jogo.statusTabuleiro();
        
        jogo.jogar(4, 2, 5, 3);
        
        jogo.statusTabuleiro();
    }
} 
 