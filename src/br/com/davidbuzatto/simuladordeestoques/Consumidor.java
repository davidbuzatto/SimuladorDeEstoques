package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Consumidor.java
 *
 * Classe que define o consumidor do estoque.
 *
 * @author David Buzatto
 */


import java.io.*;

import java.util.*;


public class Consumidor extends AgenteSimulador {
	
	private ArmazemDados pA,	// armazem de PA
						 est;	// armazem do estoque do cons.					 
	
	private Monitor monitor;	// monitor dos agentes
	
	private int pAt,  			// pedidos atendidos
				pNAt; 			// pedidos não atendidos
	
	private int qtde, 	    	// quantidade de unidades pedidas
				totalPed;		// total de pedidos feitos (atendidos ou não)
				
	private int tempoCompra;	// tempo de compra
				
	private int numero;			// número do consumidor
	
	private double nivelAt;		// nível de atendimento
	
	private Random gerador;		// gerador de números aleatórios
	
	private ConfConsumidor conf;	// armazena os dados de tempo e quantidade
									// que o consumidor deve utilizar
  	
  	// construtor
	public Consumidor( String nome, int n, ArmazemDados p,
		ArmazemDados e, Monitor m, ConfConsumidor c )
		throws NegativeNumberException {
			
		// chamada ao construtor da superclasse
    	super( nome );
    	
    	pA = p;		// define o armazém de dados dos produtos acabados
    	est = e;	// define o armazém de dados do estoque do consumidor
    	
    	numero = n;	// define o número do consumidor
    	
    	monitor = m;	// define o Monitor do agente
    	
    	pAt = 0;	// pedidos atendidos
    	pNAt = 0;	// pedidos não atendidos
    	
    	conf = c;	// define o configurador do consumidor
    	
    	gerador = new Random();	// instancia o gerador de numeros aleatórios
    	
    	try {
			setLeadTime( calculaTempoCompra() );
		} catch ( NegativeNumberException exc ) {
		}
    	
    }

	public void executa() {
		
		if ( !monitor.getEstado( numero + 2 ) 
			&& getLeadTime() == 0 ) {
		
			corpo();
			
			monitor.setEstado( numero + 2, true );
			
			try {
				setLeadTime( calculaTempoCompra() );
			} catch ( NegativeNumberException e ) {
			}
			
		} else if ( !monitor.getEstado( numero + 2 )
			&& getLeadTime() != 0) {
				
			monitor.setEstado( numero + 2, true );
			
			try {
				setLeadTime( getLeadTime() - 1 );
			} catch ( NegativeNumberException e ) {
			}
			
		}
		
	} // fim do método executa
    
    // retorna o estoque
	public int getEstoque() {
		
		return est.getQuant();
		
	}
	
	// gera o nível de atendimento
	public double getNivelAt() {
		
		return nivelAt;
		
	}
	
	// gera a String de nível de atendimento
	public String getStringNivelAt() {
		
		return String.format( "%.1f%%", getNivelAt() );
		
	}
	
	// calcula o tempo de compra
	private int calculaTempoCompra() {
		
		// calcula novo tempo de compra
		int i = conf.getTMax() - conf.getTMin();	// intervado
		
		if ( i != 0 )
			return 1 + conf.getTMin() + gerador.nextInt( i );
		else
			return conf.getTMin();	// ou getTMax
		
	}
	
    // método corpo
	private void corpo() {
		
		// quantidade de unidades pedidas		
		int i = conf.getQMax() - conf.getQMin();	// intervado
		
		if ( i != 0 )
			qtde = 1 + conf.getQMin() + gerador.nextInt( i );
		else
			qtde = conf.getQMin();	// ou getQMax
		
		// verificando a quantidade disponivel em estoque
		// decidindo a compra
	    if ( pA.getQuant() >= qtde ) {
			
			// comprando o estoque
			est.setQuant( est.getQuant() + qtde );
			
			// removendo do estoque
			pA.setQuant( pA.getQuant() - qtde );
			
			// exibe mensagem
			//System.out.println( getNome() + " retirando " + qtde 
			//	+ " bancos do estoque" );
			
			pAt++;	// pedido atendido

		}
		
	    else {
	    	
			//System.out.println( getNome() + " não encontrou " + qtde 
			//	+ " bancos em estoque" );
			
			pNAt++;	// pedido não atendido

		}
		
		// total de pedidos
		totalPed = pAt + pNAt;
		
		// calcula o nível de atendimento
		nivelAt = ( ( double ) pAt / ( double ) totalPed ) * 100;
		
	}
	
}