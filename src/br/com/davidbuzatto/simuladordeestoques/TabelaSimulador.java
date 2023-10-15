package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)TabelaSimulador.java
 *
 * Classe que define uma tabela serializável
 * para poder gravar em arquivo.
 *
 * @author David Buzatto
 */


import java.io.*;

import javax.swing.*;


public class TabelaSimulador extends JTable
	implements Serializable {
	
	private int cenario;	// cenário atual
	
	public TabelaSimulador( Object[][] conteudo, 
		Object[] colunas ) {
			
		super( conteudo, colunas );
		
		// iniciando o cenário
		setCenario( 1 );
	}
	
	// seta cenário
	public void setCenario( int n ) {
		
		if ( n < 1 )
			cenario = 1;
		
		else
			cenario = n;
			
	}
	
	// retorna cenário
	public int getCenario() {
		
		return cenario;
		
	}
	
}