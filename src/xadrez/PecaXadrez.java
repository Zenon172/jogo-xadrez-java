package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
	
	private Cor cor;
	private int contadorMovimentos;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorMovimentos(){
		return contadorMovimentos;
	}
	
	public void incrementarContadorMovimentos(){
		contadorMovimentos++;
	}
	
	public void decrementarContadorMovimentos(){
		contadorMovimentos--;
	}
	
	public XadrezPosicao getPosicaoXadrez() {
		return XadrezPosicao.paraPosicao(posicao);
	}
	
	protected boolean possuiPecaAdversaria(Posicao posicao){
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
