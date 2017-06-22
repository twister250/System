package br.com.player.test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
	
	public static void main(String[] args) {
		
		
		
		Path path = Paths.get("G:/Video/Filmes/ant_man/homem_formiga.mkv");
		
		System.out.println(path.getFileName()); //retorna o último elemento em relação à raiz do diretório informado
		
		System.out.println(path.getFileSystem().getSeparator());
		
		System.out.println(path.getName(1)); //retorna o elemento de acordo com o índice 
		
	}	
}
