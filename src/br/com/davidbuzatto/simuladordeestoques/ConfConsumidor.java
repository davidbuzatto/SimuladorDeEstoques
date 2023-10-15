package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)ConfConsumidor.java
 *
 * Classe que define a configuração de tempo e 
 * quantidade de um consumidor.
 *
 * @author David Buzatto
 */


public class ConfConsumidor {
	
	private int tMin,		// tempo mínimo (unidade de simulação)
				tMax; 		// tempo máximo (unidade de simulação)
	private int	qMin,		// quantidade mínima
				qMax;		// quantidade máxima
	
	// construtor de quatro argumentos
	public ConfConsumidor( int t1, int t2, int q1, int q2 ) {
		
		setTMin( t1 );
		setTMax( t2 );
		setQMin( q1 );
		setQMax( q2 );
		
	}
	
	// métodos set
	public void setTMin( int t ) {
		
		if ( t <= 0 )
			tMin = 1;
		else
			tMin = t;
		
	}
	
	public void setTMax( int t ) {
		
		if ( t < getTMin() )
			tMax = getTMin() + 1;
		else
			tMax = t;
		
	}
	
	public void setQMin( int q ) {
		
		if ( qMin <= 0 )
			qMin = 1;
		else
			qMin = q;
	}
	
	public void setQMax( int q ) {
		
		if ( q < getQMin() )
			qMax = getQMin() + 1;
		else
			qMax = q;
		
	}
	
	// métodos get
	public int getTMin() {
		
		return tMin;
		
	}
	
	public int getTMax() {
		
		return tMax;
		
	}
	
	public int getQMin() {
		
		return qMin;
		
	}
	
	public int getQMax() {
		
		return qMax;
		
	}
	
}