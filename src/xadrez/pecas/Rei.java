package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString(){
		return "K";
	}
	
	private boolean podeMover (Posicao posicao ){
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Mover para cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Mover para Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().PosicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		return mat;
	}

}
