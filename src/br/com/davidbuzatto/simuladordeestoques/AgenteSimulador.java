package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)AgenteSimulador.java
 *
 * Classe abstrata que define um agente 
 * genérico para o Simulador.
 *
 * @author David Buzatto
 */


public abstract class AgenteSimulador {
	
	private String nome;		// nome da thread
	private int leadTime;		// leadTime
	private int leadTimeConst;	// lead time constante
	private int lote;			// lote
	
	//contrutores
	public AgenteSimulador() 
		throws NegativeNumberException {
		
		this( "", 0, 0 );
		
	}
	
	public AgenteSimulador( String n )
		throws NegativeNumberException {
			
		this( n, 0, 0 );
		
	}
	
	public AgenteSimulador( String n, int lT,
		int l ) throws NegativeNumberException {
		
		setNome( n );
		setLeadTime( lT );
		setLeadTimeConst( lT );
		setLote( l );
		
	}
	
	// métodos set
	public void setNome( String n ) {
		
		nome = n;
		
	}
	
	public void setLeadTime( int lT ) 
		throws NegativeNumberException {
		
		if ( lT < 0 )
			throw new NegativeNumberException();
		else {
			leadTime = lT;
		}
		
	}
	
	private void setLeadTimeConst( int lT ) {
		
		leadTimeConst = lT;
		
	}
	
	public void setLote( int l ) 
		throws NegativeNumberException {
		
		if ( l < 0 )
			throw new NegativeNumberException();
		else
			lote = l;
		
	}
	
	// métodos get
	public String getNome() {
		
		return nome;
		
	}
	
	public int getLeadTime() {
		
		return leadTime;
		
	}
	
	public int getLeadTimeConst() {
		
		return leadTimeConst;
		
	}
	
	public int getLote() {
		
		return lote;
		
	}
	
}