package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelSimulador.java
 *
 * Classe que define os painéis utilizados
 * no Simulador.
 *
 * @author David Buzatto
 */


import java.awt.*;

import javax.swing.*;


public class PainelSimulador extends JPanel {
	
	private int l, 	// largura preferida
				a;	// altura preferida
	
	// construtor que recebe como parâmetro a 
	// largura e altura do painel
	public PainelSimulador( int larg, int alt ) {
		
		setLargura( larg );
		setAltura( alt );
		
	}
	
	// seta largura
	public void setLargura( int larg ) {
		
		if ( larg < 10 ) {
			l = 10;
			return;
		}
		
		l = larg;
		
	}
	
	// seta altura
	public void setAltura( int alt ) {
		
		if ( alt < 10 ) {
			a = 10;
			return;
		}
		
		a = alt;
		
	}
	
	// obtém largura
	public int getLargura() {
		
		return l;
		
	}
	
	// obtém altura
	public int getAltura() {
		
		return a;
		
	}
	
	// método que retorna o tamanho preferido do painel
	public Dimension getPreferredSize() {
		
		return new Dimension( l, a );
		
	}
	
	// método que retorna o tamanho mínimo do painel
	public Dimension getMinimumSize() {
		
		return getPreferredSize();
		
	}
	
}