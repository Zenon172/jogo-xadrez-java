package tabuleiro;

public class Tabuleiro {
	
	private int linhas, colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna){
		if(!PosicaoExiste(linha, coluna)){
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao){
		if(!PosicaoExiste(posicao)){
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];		
	}
	
	public void ColocarPeca(Peca peca, Posicao posicao){
		if(TemUmaPeca(posicao)){
			throw new TabuleiroException("Já existe uma peça nesta posição: "+posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removePeca(Posicao posicao){
		if(!PosicaoExiste(posicao)){
			throw new TabuleiroException("Posição não existe no tabuleiro");
		}
		if(peca(posicao) == null){
			return null;
		}else{
			Peca aux = peca(posicao);
			pecas[posicao.getLinha()][posicao.getColuna()] = null;
			return aux;
		}
	}
	
	private boolean PosicaoExiste(int linha, int coluna){
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean PosicaoExiste(Posicao posicao){
		return PosicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean TemUmaPeca(Posicao posicao){
		if (!PosicaoExiste(posicao)){
			throw new TabuleiroException("Posição não exite");
		}
		return peca(posicao) != null;
	}
	
	

}
