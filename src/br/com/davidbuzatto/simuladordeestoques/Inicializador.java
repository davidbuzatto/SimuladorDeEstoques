package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Inicializador.java
 *
 * Classe que inicializa todos os 
 * componentes do simulador.
 *
 * @author David Buzatto
 */


import java.awt.*;

import java.util.concurrent.*;

import javax.swing.*;


public final class Inicializador implements Runnable {
	
	public void run() {
		
		// frame inicializacao
		FrameInicializacao fIni = new FrameInicializacao();
 		// frame tabela
		FrameTabela fT = new FrameTabela();
		// frame gráfico
		FrameGraficoConsumidor fG = new FrameGraficoConsumidor();
		// frame principal (frame tabela e frame grafico são seus "filhos")
		FramePrincipal fP = new FramePrincipal( fT, fG );
		
		// operações de fechamento
		fIni.setDefaultCloseOperation(
			JFrame.EXIT_ON_CLOSE );
		fP.setDefaultCloseOperation(
			JFrame.DO_NOTHING_ON_CLOSE );
		fT.setDefaultCloseOperation(
			JFrame.HIDE_ON_CLOSE );
		fG.setDefaultCloseOperation(
			JFrame.HIDE_ON_CLOSE );
		
		// tamanho
		fIni.setSize( 452, 254 );
		fT.setSize( 720, 265 );
		fG.setSize( 670, 550 );
		fP.setSize( 765, 570 );
 		
		// muda o modo visualizaçao dos jframes
		fIni.setUndecorated( true );
		fT.setUndecorated( true );
		fG.setUndecorated( true );
		fP.setUndecorated( true );
		
    	fIni.getRootPane().setWindowDecorationStyle( JRootPane.NONE );
    	fT.getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
    	fG.getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
    	fP.getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
    	
    	// centraliza o componente na tela, usando um
 		// deslocamento vertical
 		Utilidades.centralizaComponenteNaTela( fIni, 0 );
 		Utilidades.centralizaComponenteNaTela( fP, -15 );
 		Utilidades.centralizaComponenteNaTela( fT, 0 );
 		Utilidades.centralizaComponenteNaTela( fG, -5 );
		
		// visibilidade e redimensionamento
		fIni.setVisible( true );
		fIni.setResizable( false );
		
		// adormece p/ exibir por um tempo o logotipo
		try {
			
			Thread.sleep( 5000 );
			
		} catch ( InterruptedException exc ) {
		}
		
		// libera fIni
		fIni.dispose();
		
		fT.setVisible( false );
		fT.setResizable( false );
		fG.setVisible( false );
		fG.setResizable( false );
		fP.setVisible( true );
		fP.setResizable( false );
		
		// pede o fóco
		fP.requestFocus();
		
	}
	
	public static void inicializa() {
		
		// executor de threads
		ExecutorService threadExecutor =
			Executors.newFixedThreadPool( 1 );
		
		// cria um novo Inicializador
		threadExecutor.execute( new Inicializador() );
		
		// quando finalizado o método run de inicializador, finaliza
		// o ExecutorService
		threadExecutor.shutdown();
		
	}
	
}