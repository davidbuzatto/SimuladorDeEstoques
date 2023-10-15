package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelImprimivel.java
 *
 * Classe que define um painel que pode ser
 * impresso.
 *
 * @author David Buzatto
 */
 
import java.awt.print.*;


public abstract class PainelImprimivel extends PainelSimulador
	implements Printable {
	
	public PainelImprimivel( int larg, int alt ) {
		
		// chama constturtor da classe ancestral
		super( larg, alt );

	}
	
	// método print de Printable é passado para as classes concretas
	
}