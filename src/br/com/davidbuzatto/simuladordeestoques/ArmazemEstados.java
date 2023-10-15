package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)ArmazemEstados.java
 *
 * Classe que armazena todas as informações que
 * devem ser compartilhadas entre várias classes
 * do simulador.
 *
 * @author David Buzatto
 */


import java.awt.*;

public class ArmazemEstados {
	
	// variáveis de simulação
	private static double tempoPasso;			// tempo do passo

	private static String unidadePasso;			// id da unidade de passo
				  
	private static boolean tempoRealGrafCons;	// plotagem aotumática
					
	// inicializa todos os campos
	public static void inicializa() {
		
		setTempoPasso( 1 );
		setUnidadePasso( "semana" );

		setTempoRealGrafCons( false );
		
	}
	
	// seta o tempo de passo
	public static void setTempoPasso( double n ) {
		
		tempoPasso = n;
		
	}
	
	// define o id da unidade de passo
	public static void setUnidadePasso( String s ) {
		
		unidadePasso = s;
		
	}
	
	// ativa/desativa plotagem automática dos gráficos dos consumidores
	public static void setTempoRealGrafCons( boolean b ) {
		
		tempoRealGrafCons = b;
		
	}
	
	// obtém o tempo de passo
	public static double getTempoPasso() {
		
		return tempoPasso;
		
	}
	
	// obtém o id da unidade de passo
	public static String getUnidadePasso() {
		
		return unidadePasso;
		
	}
	
	// verifica se a plotagem automática do gráfico dos consumidores
	// está ativada
	public static boolean isTempoRealGrafCons() {
		
		return tempoRealGrafCons;
		
	}
	
}