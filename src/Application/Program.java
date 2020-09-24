package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		
		while (true){
			UI.printTabuleiro(partida.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			XadrezPosicao origem = UI.LerPosicao(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			XadrezPosicao destino = UI.LerPosicao(sc);
			
			PecaXadrez pecaCapturada = partida.MovimentoXadrez(origem, destino);
			
		}
	}

}
