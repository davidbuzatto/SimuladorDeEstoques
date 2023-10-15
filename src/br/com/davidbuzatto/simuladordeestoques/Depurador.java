package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Depurador.java
 *
 * Classe que define um método p/ gerar o 
 * rastreamento de pilha em um diálogo.
 *
 * @author David Buzatto
 */


import javax.swing.*;


public final class Depurador {
	
	// exibe a pilha de chamada completa em uma area de texto
	public static void gerarStackTrace( Exception exc ) {
		
		String s = "";			
		
		// obtém a pilha de chamadas
		StackTraceElement[] ste = exc.getStackTrace();			
		
		// adiciona a String s
		for ( StackTraceElement el : ste )
			s += el.toString() + "\n";
		
		// cria uma area de texto e exibe toda a informação
		JTextArea t = new JTextArea( s, 20, 50 );
		
		JOptionPane.showMessageDialog( null, new JScrollPane( t ),
			"Rastreamento de Pilha", JOptionPane.INFORMATION_MESSAGE );
		
	}
	
}