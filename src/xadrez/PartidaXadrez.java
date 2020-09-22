package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaXadrez(){
		tabuleiro = new Tabuleiro (8, 8);
		IniciarPartida();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++){
			for (int j=0; j<tabuleiro.getColunas(); j++){
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void ColocarNovaPeca(char coluna, int linha, PecaXadrez peca){
		tabuleiro.ColocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	private void IniciarPartida(){
		ColocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('e', 8, new Rei(tabuleiro,  Cor.PRETO));
	}

}
