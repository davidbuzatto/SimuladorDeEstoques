package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Monitor.java
 *
 * Esta classe é a responsável por manter
 * a monitoração dos agentes do simulador
 *
 * @author David Buzatto
 */


public class Monitor {
	
	// array de estados dos agentes
	// FornecedorPe, FornecedorAssento, Produtor, Consumidor1, Consumidor2
	private boolean[] estado;
	
	private boolean allowed;	// execução permitida ou negada
	
	// construtor. executável por padrão
	public Monitor() {
		
		allow();
		estado = new boolean[ 5 ];
		
	}
	
	// as threads devem parar
	public void kill() {
		
		allowed = false;
		
	}
	
	// permite que as threads executem
	public void allow() {
		
		allowed = true;
		
	}
	
	// verifica se a execução das threads está permitida
	public boolean isAllowed() {
		
		return allowed;
		
	}
	
	public void setEstado( int indice, boolean valor ) {
		
		if ( indice > 4 || indice < 0 )
			indice = 0;
		else	
			estado[ indice ] = valor;
		
	}
	
	public boolean getEstado( int indice ) {
		
		if ( indice > 4 || indice < 0 )
			indice = 0;
			
		return estado[ indice ];
			
	}
	
	public void resetEstado() {
		
		estado[ 0 ] = false;
		estado[ 1 ] = false;
		estado[ 2 ] = false;
		estado[ 3 ] = false;
		estado[ 4 ] = false;
			
	}
	
	public boolean isEstadoTrue() {
		
		if ( estado[ 0 ] &&
			 estado[ 1 ] &&
			 estado[ 2 ] &&
			 estado[ 3 ] &&
			 estado[ 4 ] )
			return true;
		
		return false;
		
	}

}