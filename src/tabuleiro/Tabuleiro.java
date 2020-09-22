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
			throw new TabuleiroException("Posi��o n�o existe no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao){
		if(!PosicaoExiste(posicao)){
			throw new TabuleiroException("Posi��o n�o existe no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];		
	}
	
	public void ColocarPeca(Peca peca, Posicao posicao){
		if(TemUmaPeca(posicao)){
			throw new TabuleiroException("J� existe uma pe�a nesta posi��o: "+posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean PosicaoExiste(int linha, int coluna){
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean PosicaoExiste(Posicao posicao){
		return PosicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean TemUmaPeca(Posicao posicao){
		if (!PosicaoExiste(posicao)){
			throw new TabuleiroException("Posi��o n�o exite");
		}
		return peca(posicao) != null;
	}
	
	

}
