package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
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
	private PecaXadrez vulneravelEnPassant;
	private PecaXadrez promocao;
	
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
	
	public PecaXadrez getVulneravelEnPassant(){
		return vulneravelEnPassant;
	}
	
	public PecaXadrez getPromocao(){
		return promocao;
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
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//Jogada especial Promoção
		promocao = null;
		if(pecaMovida instanceof Peao){
			if((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)){
				promocao = (PecaXadrez)tabuleiro.peca(destino);
				promocao = trocarPecaPromovida("Q");
			}
		}
		
		check = (testCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testCheckMate(jogadorAtual)){
			checkMate = true;
		} else {
			trocarTurno();
		}
		
		// Movimento especial en passant
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)){
			vulneravelEnPassant = pecaMovida;
		} else {
			vulneravelEnPassant = null;
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez trocarPecaPromovida(String type){
		if(promocao == null){
			throw new IllegalStateException("Não há peça para ser promovida");
		}
		if(!type.equals("Q") && !type.equals("B") && !type.equals("C") && !type.equals("T")){
			throw new InvalidParameterException("Tipo de promoção inválido!");
		}
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.ColocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String type, Cor cor){
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("Q")) return new Rainha(tabuleiro, cor);
		if (type.equals("C")) return new Cavalo(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
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
		
		// #Movimento Especial Rook pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() +2){
			Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() +3);
			Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() +1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.ColocarPeca(torre, destinoT);
			torre.incrementarContadorMovimentos();
			
		}
		// #Movimento Especial Rook grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() -2){
			Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() -4);
			Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() -1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(origemT);
			tabuleiro.ColocarPeca(torre, destinoT);
			torre.incrementarContadorMovimentos();
					
		}
		// #Movimento Especial En Passant
		if (p instanceof Peao){
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null){
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO){
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
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
		// #Movimento Especial Rook pequeno
				if (p instanceof Rei && destino.getColuna() == origem.getColuna() +2){
					Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() +3);
					Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() +1);
					PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
					tabuleiro.ColocarPeca(torre, origemT);
					torre.incrementarContadorMovimentos();
					
				}
				// #Movimento Especial Rook grande
				if (p instanceof Rei && destino.getColuna() == origem.getColuna() -2){
					Posicao origemT = new Posicao(origem.getLinha(), destino.getColuna() -4);
					Posicao destinoT = new Posicao(origem.getLinha(), destino.getColuna() -1);
					PecaXadrez torre = (PecaXadrez)tabuleiro.removePeca(destinoT);
					tabuleiro.ColocarPeca(torre, origemT);
					torre.decrementarContadorMovimentos();
							
				}
				// #Movimento Especial En Passant
				if (p instanceof Peao){
					if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulneravelEnPassant){
						PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(destino);
						Posicao peaoPosicao;
						if (p.getCor() == Cor.BRANCO){
							peaoPosicao = new Posicao(3, destino.getColuna());
						} else {
							peaoPosicao = new Posicao(4, destino.getColuna());
						}
						tabuleiro.ColocarPeca(peao, peaoPosicao);
					}
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
		ColocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		ColocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		ColocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		
		
		ColocarNovaPeca('e', 8, new Rei(tabuleiro,  Cor.PRETO, this));
		ColocarNovaPeca('d', 8, new Rainha(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('c', 8, new Bispo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('f', 8, new Bispo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('b', 8, new Cavalo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('g', 8, new Cavalo(tabuleiro,  Cor.PRETO));
		ColocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		ColocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		ColocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
		
	}

}
