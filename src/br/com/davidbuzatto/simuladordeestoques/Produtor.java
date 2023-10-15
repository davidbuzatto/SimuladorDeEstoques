package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Produtor.java
 *
 * Classe que define o produtor do estoque.
 *
 * @author David Buzatto
 */


import java.io.*;

import java.util.*;


public class Produtor extends AgenteSimulador {
	
	private ArmazemDados pes,	// armazém de pés
						 ass,	// armazém de assentos
						 pA;	// armazém de produtos acabados
	
	private Monitor monitor;	// monitor dos agentes
	
	// construtor
	public Produtor( String nome, int leadTime, 
		int lote, ArmazemDados p, ArmazemDados a, ArmazemDados pa,
		Monitor m )
		throws NegativeNumberException {
		
		// chamada ao construtor da superclasse
		super( nome, leadTime, lote );
		
		pes = p;	// define o armazém de pés
		ass = a;	// define o armazém de assentos
		pA = pa;	// define o armazém de produtos acabados
		
		monitor = m;	// define o Monitor do agente
		
	}

	public void executa() {
		
		if ( !monitor.getEstado( 2 ) 
			&& getLeadTime() == 0 ) {
			
			corpo();
			
			monitor.setEstado( 2, true );
			
			try {
				setLeadTime( getLeadTimeConst() );
			} catch ( NegativeNumberException e ) {
			}
			
		} else if ( !monitor.getEstado( 2 )
			&& getLeadTime() != 0) {
				
			monitor.setEstado( 2, true );
			
			try {
				setLeadTime( getLeadTime() - 1 );
			} catch ( NegativeNumberException e ) {
			}
			
		}
	  
	}
	
	// retorna o estoque
	public int getEstoque() {
		
		return pA.getQuant();
		
	}
	
	// método corpo
	private void corpo() {
		
		// inicializa variáveis de controle para fabricar ou nao
		boolean dispPe = true;	// pés disponíveis
		boolean dispAss = true;	// assentos disponíveis

		// verifica a quantidade de pés em estoque para produzir um lote de bancos
		if ( pes.getQuant() < ( getLote() * 3 ) ) {
			
			// nao pode fabricar com um numero menor de pés 
			// que o necessário para o lote mínimo
			dispPe = false;
			
		}

		// verifica a quantidade de assentos em estoque para produzir um lote de bancos
		if ( ass.getQuant() < getLote() ) {
			
			// nao pode fabricar com um numero menor de assentos 
			// que o necessário para o lote mínimo
			dispAss = false;
			
		}
		
		// fabricando os bancos
		if ( dispPe && dispAss ) {

			// armazena quantidade produzida no armazem de pA
			pA.setQuant(  pA.getQuant() + getLote() );
			
			// exibe mensagem
			//System.out.println( getNome() + " colocando " + getLote() 
			//	+ " bancos no estoque" );
			
			// retira dos armazens a quantidade utilizada de
			// pes e assentos
			pes.setQuant( pes.getQuant() - ( 3 * getLote() ) );
			ass.setQuant( ass.getQuant() - getLote() );
		}	
		
	} // fim do método corpo
						 
}