package br.com.rodrigosnr.jogoforca.game;

import java.util.HashSet;
import java.util.Set;

import br.com.rodrigosnr.jogoforca.core.Config;
import br.com.rodrigosnr.jogoforca.core.Dictionary;
import br.com.rodrigosnr.jogoforca.core.InvalidCharacterException;
import br.com.rodrigosnr.jogoforca.core.Word;
import br.com.rodrigosnr.jogoforca.ui.UI;

public class Game {
	
	public void start() {
		
		UI.print("***Bem vindo ao Jogo da Forca***");
		
		Dictionary dictionary = Dictionary.getInstance();
		Word word = dictionary.nextWord();
		
		UI.print("A palavra tem " + word.size() + " letras");
		
		Set<Character> usedChars = new HashSet<>();
		int errorCount = 0;
		
		int maxErrors = Integer.parseInt(Config.get("maxErrors"));
		UI.print("Voc� pode errar at� " + maxErrors + " vezes");
		
		while (true) {
			UI.print(word);
			UI.printNewLine();
			
			char c;
			try {
				c = UI.readChar("Digite uma letra:");
				
				if (usedChars.contains(c)) {
					throw new InvalidCharacterException("Esta letra j� foi digitada");
				}
				usedChars.add(c);
				
				if (word.hasChar(c)) {
					UI.print("Voc� acertou uma letra!");
				} else {
				 	errorCount++;
				 	
				 	if (errorCount < maxErrors) {
				 		UI.print("Voc� errou! Voc� ainda pode errar " + (maxErrors - errorCount) + " vez(es)");
				 	}
				}
				
				UI.printNewLine();
				
				if (word.discovered()) {
					UI.print("PARAB�NS!!! Voc� acertou a palavra completa: " + word.getOriginalWord());
					UI.print("Fim do jogo!");
					break;
				}
				
				if (errorCount == maxErrors) {
					UI.print("Voc� perdeu o jogo!!! A palavra correta era: " + word.getOriginalWord());
					UI.print("Fim do jogo!");
					break;
				}
			
			} catch (InvalidCharacterException e) {
				UI.print("Erro: " + e.getMessage());
				UI.printNewLine();
			}
			
			
		
		}

	}
}
