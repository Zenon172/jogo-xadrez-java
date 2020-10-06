package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while (!partida.getCheckMate()){
			try{
				UI.clearScreen();
				UI.printPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				XadrezPosicao origem = UI.LerPosicao(sc);
				
				boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
				UI.clearScreen();
				UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);
				System.out.println();
				System.out.print("Destino: ");
				XadrezPosicao destino = UI.LerPosicao(sc);
				
				PecaXadrez pecaCapturada = partida.MovimentoXadrez(origem, destino);
				if (pecaCapturada != null){
					capturadas.add(pecaCapturada);
				}
			}
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printPartida(partida, capturadas);
	}

}
