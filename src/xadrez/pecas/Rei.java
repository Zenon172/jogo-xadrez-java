package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{
	
	private PartidaXadrez partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}
	
	@Override
	public String toString(){
		return "K";
	}
	
	private boolean podeMover (Posicao posicao ){
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testRook(Posicao posicao){
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
		
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
		
		// #Movimento Especial Rook
		if (getContadorMovimentos() == 0 && !partida.getCheck()){
			// Rook pequeno
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() +3);
			if (testRook(posT1)){
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
					mat[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
			// Rook grande
				Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() +4);
				if (testRook(posT2)){
					Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
					Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
					Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() +3);
					if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
						mat[posicao.getLinha()][posicao.getColuna()+2] = true;
					}
				}
		}
		
		
		
		return mat;
	}

}
