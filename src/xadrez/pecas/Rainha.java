package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez{

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		//Mover para cima
				p.setValores(posicao.getLinha() -1, posicao.getColuna());
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() - 1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para baixo
				p.setValores(posicao.getLinha() +1, posicao.getColuna());
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() + 1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para esquerda
				p.setValores(posicao.getLinha(), posicao.getColuna() -1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() -1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para direita
				p.setValores(posicao.getLinha(), posicao.getColuna() +1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() +1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				//Mover para Noroeste
				p.setValores(posicao.getLinha() -1, posicao.getColuna() -1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() -1, p.getColuna() -1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para Nordeste
				p.setValores(posicao.getLinha() -1, posicao.getColuna() +1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() -1, p.getColuna() +1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para Sudoeste
				p.setValores(posicao.getLinha() +1, posicao.getColuna() -1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() +1, p.getColuna() -1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				//Mover para Sudeste
				p.setValores(posicao.getLinha() +1, posicao.getColuna() +1);
				while (getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() +1, p.getColuna() +1);
				}
				if (getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		return mat;
	}
	
	@Override
	public String toString(){
		return "Q";
	}

}
