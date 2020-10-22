package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{
	
	private PartidaXadrez partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
		
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
			//#Movimento especial enPassant (Peça Branca)
			if(posicao.getLinha() == 3){
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().PosicaoExiste(esquerda) && possuiPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partida.getVulneravelEnPassant()){
					mat[esquerda.getLinha() -1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().PosicaoExiste(direita) && possuiPecaAdversaria(direita) && getTabuleiro().peca(direita) == partida.getVulneravelEnPassant()){
					mat[direita.getLinha() -1][direita.getColuna()] = true;
				}
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
			//#Movimento especial enPassant (Peça Preta)
			if(posicao.getLinha() == 4){
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() -1);
				if(getTabuleiro().PosicaoExiste(esquerda) && possuiPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partida.getVulneravelEnPassant()){
					mat[esquerda.getLinha() +1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				if(getTabuleiro().PosicaoExiste(direita) && possuiPecaAdversaria(direita) && getTabuleiro().peca(direita) == partida.getVulneravelEnPassant()){
					mat[direita.getLinha() +1][direita.getColuna()] = true;
				}
			}
		}
		
		return mat;
	}
	
	@Override
	public String toString(){
		return "P";
	}

}
