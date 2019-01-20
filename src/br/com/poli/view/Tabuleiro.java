package br.com.poli.view;

import br.com.poli.model.pojo.Casa;
import br.com.poli.model.pojo.Peca;
import br.com.poli.model.pojo.enums.CorCasa;

public class Tabuleiro {

	private final Casa[][] grid = new Casa[8][8];
	private Peca peca1;
	private Peca peca2;

	
	public Tabuleiro(Peca peca1, Peca peca2) {
		this.peca1 = peca1; 
		this.peca2 = peca2;
	}


	public Casa[][] getGrid() {
		return this.grid;
	}

	public Peca getPeca1() {
		return this.peca1;
	}

	public void setPeca1(Peca peca1) {
		this.peca1 = peca1;
	}

	public void setPeca2(Peca peca2) {
		this.peca2 = peca2;
	}

	public Peca getPeca2() {
		return this.peca2;
	}

	public void gerarTabuleiro() {
		for (int posicaoCasa1 = 0; posicaoCasa1 <= 7; posicaoCasa1++) {
			for (int posicaoCasa2 = 0; posicaoCasa2 <= 7; posicaoCasa2++) {
				this.grid[posicaoCasa1][posicaoCasa2] = new Casa();
				if ((posicaoCasa1 + posicaoCasa2) % 2 == 0) {
					this.grid[posicaoCasa1][posicaoCasa2].setCor(CorCasa.PRETA);
				} else {
					this.grid[posicaoCasa1][posicaoCasa2].setCor(CorCasa.BRANCA);
				}

				if ((posicaoCasa1 >= 0 && posicaoCasa1 <= 2) && ((posicaoCasa1 + posicaoCasa2) % 2 == 0)) {
					this.grid[posicaoCasa1][posicaoCasa2].setOcupada(true);
					this.grid[posicaoCasa1][posicaoCasa2].setPeca(getPeca2());

				} else if ((posicaoCasa1 >= 5 && posicaoCasa1 <= 7) && ((posicaoCasa1 + posicaoCasa2) % 2 == 0)) {
					this.grid[posicaoCasa1][posicaoCasa2].setOcupada(true);
					this.grid[posicaoCasa1][posicaoCasa2].setPeca(getPeca1());
				}
			}
		}
	}

        public void TabuleiroTestes(){
            for (int posicaoCasa1 = 0; posicaoCasa1 <= 7; posicaoCasa1++) {
			for (int posicaoCasa2 = 0; posicaoCasa2 <= 7; posicaoCasa2++) {
				this.grid[posicaoCasa1][posicaoCasa2] = new Casa();
				if ((posicaoCasa1 + posicaoCasa2) % 2 == 0) {
					this.grid[posicaoCasa1][posicaoCasa2].setCor(CorCasa.PRETA);
				} else {
					this.grid[posicaoCasa1][posicaoCasa2].setCor(CorCasa.BRANCA);
				}
                        }       
                    }
                                        this.grid[0][2].setOcupada(true);
					this.grid[0][2].setPeca(getPeca2());
                                        this.grid[0][4].setOcupada(true);
					this.grid[0][4].setPeca(getPeca2());
                                        
                                        this.grid[7][1].setOcupada(true);
					this.grid[7][1].setPeca(getPeca1());
                                        this.grid[7][3].setOcupada(true);
					this.grid[7][3].setPeca(getPeca1());
                                        this.grid[7][5].setOcupada(true);
					this.grid[7][5].setPeca(getPeca1());
        }
        
	public void executarMovimento(int movX, int movY, int origemX, int origemY) {
		this.grid[movY][movX].setPeca(this.grid[origemY][origemX].getPeca());
		this.grid[movY][movX].setOcupada(true);
                
                this.grid[origemY][origemX].setOcupada(false);
		this.grid[origemY][origemX].setPeca(null);
		
	}
}
