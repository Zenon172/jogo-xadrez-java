package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if(getCor()== Cor.BRANCO) {
			
			//Mover uma casa pra frente
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover duas casas pra frente (primeira jogada de cada Peão)
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if(getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p) && getTabuleiro().PosicaoExiste(p2) && !getTabuleiro().TemUmaPeca(p2) && getContadorMovimentos() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover diagonal Noroeste (capturar peça)
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover diagonal Nordeste (capturar peça)
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		} else {
			//Mover uma casa pra frente
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover duas casas pra frente (primeira jogada de cada Peão)
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().PosicaoExiste(p) && !getTabuleiro().TemUmaPeca(p) && getTabuleiro().PosicaoExiste(p2) && !getTabuleiro().TemUmaPeca(p2) && getContadorMovimentos() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover diagonal Noroeste (capturar peça)
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
			//Mover diagonal Nordeste (capturar peça)
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().PosicaoExiste(p) && possuiPecaAdversaria(p)){
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString(){
		return "P";
	}

}
