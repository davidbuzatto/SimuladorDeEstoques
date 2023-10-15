package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)NumbersOutOfOrderException.java
 *
 * Classe que define uma exceção verificada p/ erros pelo uso de
 * valores fora de ordem (maior antes de menor).
 *
 * @author David Buzatto
 */
 
public class NumbersOutOfOrderException extends Exception {
	
	public NumbersOutOfOrderException() {
		
		super( "Números fora de ordem." );
		
	}
	
}