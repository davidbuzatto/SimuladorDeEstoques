package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Constantes.java
 *
 * Enum que define as constantes do simulador.
 *
 * @author David Buzatto
 */
 
 
 public enum Constantes {
 	
 	// versão do simulador
 	VERSAO( "Versão", 1.00 ),
 	// número máximo de cenários
 	MAX_CEN( "Cenários", 60 );
 	
 	private final String tipo;
 	private final double valor;
 	
 	// construtor enum
 	Constantes( String t, double v ) {
		
		tipo = t;;
		valor = v;
		
	}
	
	// método de acesso p/ o tipo do enum
	public String getTipo() {
		
		return tipo;
		
	}
	
	// método de acesso p/ o campo valor do enum
	public double getValor() {
		
		return valor;
		
	}
 	
 }