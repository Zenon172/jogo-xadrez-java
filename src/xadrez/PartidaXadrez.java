package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	
	public PartidaXadrez(){
		tabuleiro = new Tabuleiro (8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		IniciarPartida();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck(){
		return check;
	}
	
	public boolean getCheckMate(){
		return checkMate;
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
	
	public boolean[][] possiveisMovimentos(XadrezPosicao posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		ValidarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez MovimentoXadrez(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino){
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		ValidarPosicaoOrigem(origem);
		ValidarPosicaoDestino(origem, destino);
		Peca pecaCapturada = movimentacao(origem, destino);
		
		if(testCheck(jogadorAtual)){
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Você não pode se colocar em Check!");
		}
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testCheckMate(jogadorAtual)){
			checkMate = true;
		} else {
			trocarTurno();
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca movimentacao(Posicao origem, Posicao destino){
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(origem);
		p.incrementarContadorMovimentos();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.ColocarPeca(p, destino);
		if(pecaCapturada != null){
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.decrementarContadorMovimentos();
		tabuleiro.ColocarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.ColocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void ValidarPosicaoOrigem(Posicao posicao){
		if(!tabuleiro.TemUmaPeca(posicao)){
			throw new XadrezException("Não existe peça na posição origem");
		}
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()){
			throw new XadrezException("A peça escolhida não é sua");
		}
		if(!tabuleiro.peca(posicao).possuiMovimentoPossivel()){
			throw new XadrezException("Não existe movimento possível para peça selecionada.");
		}
	}
	
	private void ValidarPosicaoDestino(Posicao origem, Posicao destino){
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)){
			throw new XadrezException("A peça selecionada não pode se mover para a posição de destino.");
		}
	}
	
	
	private void ColocarNovaPeca(char coluna, int linha, PecaXadrez peca){
		tabuleiro.ColocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void trocarTurno(){
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor){
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei (Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list){
			if(p instanceof Rei){
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe um Rei " + cor + " no Tabuleiro");
	}
	
	private boolean testCheck(Cor cor){
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponetes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponetes){
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor){
		if(!testCheck(cor)){
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list){
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++){
				for (int j = 0; j < tabuleiro.getColunas(); j++){
					if (mat[i][j]){
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = movimentacao(origem, destino);
						boolean testCheck = testCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if(!testCheck){
							return false;
						}
					}
				}
			}
			
		}
		return true;
	}
	
	private void IniciarPartida(){
		ColocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		
		
		ColocarNovaPeca('e', 8, new Rei(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('c', 8, new Bispo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('f', 8, new Bispo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('b', 8, new Cavalo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('g', 8, new Cavalo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
		
	}

}
