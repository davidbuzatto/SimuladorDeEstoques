package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)ArmazemDados.java
 *
 * Classe que define um armazém de dados para as threads.
 *
 * @author David Buzatto
 */


public class ArmazemDados {
	
	// armazena a quantidade
	private int quantidade;
	
	// construtor
	public ArmazemDados() {
		
		setQuant( 0 );
		
	}
	
	// método set
	public void setQuant( int q ) {
		
		quantidade = q;
		
	}
	
	// método get
	public int getQuant() {
		
		return quantidade;
		
	}
	
}