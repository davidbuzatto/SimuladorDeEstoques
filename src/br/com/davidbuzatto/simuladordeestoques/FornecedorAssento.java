package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FornecedorAssento.java
 *
 * Classe que define o fornecedor de assentos.
 *
 * @author David Buzatto
 */


import java.io.*;

import java.util.*;


public class FornecedorAssento extends AgenteSimulador {
	
	private ArmazemDados ass;	// armazém de assentos
	
	private Monitor monitor;	// monitor dos agentes
	
	// construtor
	public FornecedorAssento( String nome, int leadTime, 
		int lote, ArmazemDados a, Monitor m )
		throws NegativeNumberException {
		
		// chamada ao construtor da superclasse	
		super( nome, leadTime, lote );
		
		ass = a;	// define o armazém de assentos
		monitor = m;	// define o Monitor do agente
		
	}

	public void executa() {

		if ( !monitor.getEstado( 1 ) 
			&& getLeadTime() == 0 ) {
			
			corpo();
			
			monitor.setEstado( 1, true );
			
			try {
				setLeadTime( getLeadTimeConst() );
			} catch ( NegativeNumberException e ) {
			}
			
		} else if ( !monitor.getEstado( 1 )
			&& getLeadTime() != 0) {
				
			monitor.setEstado( 1, true );
			
			try {
				setLeadTime( getLeadTime() - 1 );
			} catch ( NegativeNumberException e ) {
			}
			
		}

	}
	
	// retorna o estoque
	public int getEstoque() {
		
		return ass.getQuant();
		
	}
	
  	// método corpo
	private void corpo() {
		
		// armazena a nova quantidade de pes
		ass.setQuant( ass.getQuant() + getLote() );
		
		// avisa da entrega
		//System.out.println( getNome() + " colocando " + getLote() 
		//	+ " assentos no estoque" );
			
	}
	
}