package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelTitulado.java
 *
 * Classe que define um painel com borda e titulado.
 *
 * @author David Buzatto
 */


import javax.swing.*;
import javax.swing.border.*;


public class PainelTitulado extends PainelSimulador {

	// construtor recebe o título, largura e altura
	public PainelTitulado( String titulo, int larg, int alt ) {
		
		// chama constturtor da classe ancestral
		super( larg, alt );

		// borda etched
		Border etched = BorderFactory.createEtchedBorder();

		// borda com titulo, usa como base a etched criada
		Border titulada = BorderFactory.createTitledBorder(
				etched, titulo );

		// seta	a borda desta classe de painel
		setBorder( titulada );
		
	}
	
}