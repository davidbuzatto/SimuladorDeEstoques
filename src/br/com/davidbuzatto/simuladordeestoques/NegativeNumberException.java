package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)NegativeNumberException.java
 *
 * Classe que define uma exceção verificada para erros 
 * pelo uso de números negativos.
 *
 * @author David Buzatto
 */
 
public class NegativeNumberException extends Exception {
	
	public NegativeNumberException() {
		
		super( "Número negativo." );
		
	}
	
}