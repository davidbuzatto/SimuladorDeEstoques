package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FornecedorPe.java
 *
 * Classe que define o fornecedor de pés.
 *
 * @author David Buzatto
 */


import java.io.*;

import java.util.*;


public class FornecedorPe extends AgenteSimulador {
	
	private ArmazemDados pes;	// armazéns de pés
	
	private Monitor monitor;	// monitor dos agentes
	
	// construtor
	public FornecedorPe( String nome, int leadTime, 
		int lote, ArmazemDados p, Monitor m ) 
		throws NegativeNumberException {
		
		// chamada ao construtor da superclasse	
		super( nome, leadTime, lote );
		
		pes = p;	// define armazém de pés
		monitor = m;	// define o Monitor do agente
		
	}

	public void executa() {

		if ( !monitor.getEstado( 0 ) 
			&& getLeadTime() == 0 ) {
			
			corpo();
			
			monitor.setEstado( 0, true );
			
			try {
				setLeadTime( getLeadTimeConst() );
			} catch ( NegativeNumberException e ) {
			}
			
		} else if ( !monitor.getEstado( 0 )
			&& getLeadTime() != 0) {
				
			monitor.setEstado( 0, true );
			
			try {
				setLeadTime( getLeadTime() - 1 );
			} catch ( NegativeNumberException e ) {
			}
			
		}
			
	}
	
	// retorna o estoque
	public int getEstoque() {
		
		return pes.getQuant();
		
	}
	
	// método corpo
	private void corpo() {
		
		// armazena a nova quantidade de pes
		pes.setQuant( pes.getQuant() + getLote() );
		
		// avisa da entrega
		//System.out.println( getNome() + " colocando " + getLote() 
		//	+ " pes no estoque" );
			
	}
	
}