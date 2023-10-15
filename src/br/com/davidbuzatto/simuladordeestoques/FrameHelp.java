package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FrameHelp.java
 *
 * Classe que implementa um frame que exibe
 * a ajuda do simulador
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.net.*;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;


public class FrameHelp extends JFrame
	implements ActionListener {
	
	private ArrayList< URL > linksVisitados;
	
	private int cont;
	
	private JButton btnVoltar,
					btnAvancar;
	
	private JEditorPane areaConteudo;
					
	public FrameHelp() {
		
		super( "Usando o Simulador" );
		
		setLayout( new FlowLayout() );
		
		linksVisitados = new ArrayList< URL >();
		
		btnVoltar = new JButton( new ImageIcon(
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconVoltar.png" ) ) );
		btnAvancar = new JButton( new ImageIcon(
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconAvancar.png" ) ) );
			
		btnVoltar.setToolTipText( "Voltar" );
		btnAvancar.setToolTipText( "Avançar" );
		
		btnVoltar.setEnabled( false );
		btnAvancar.setEnabled( false );
		
		btnVoltar.addActionListener( this );
		btnAvancar.addActionListener( this );
		
		areaConteudo = new JEditorPane();
		areaConteudo.setEditable( false );
		areaConteudo.addHyperlinkListener(
			new HyperlinkListener() {
				
				// se o usuário clicou no hyperlink,
				// vai p/ a página especificada
				public void hyperlinkUpdate( HyperlinkEvent e ) {
					
					if ( e.getEventType() ==
						HyperlinkEvent.EventType.ACTIVATED ) {
							
						carregarDocumento( e.getURL().toString() );
						linksVisitados.add( e.getURL() );
						cont = linksVisitados.size() - 1;
						
						if ( !btnVoltar.isEnabled() )
							btnVoltar.setEnabled( true );
						
						if ( btnAvancar.isEnabled() )
							btnAvancar.setEnabled( false );
						
					}
						
					
				}
				
			}
			
		);
		
		add( btnVoltar );
		add( btnAvancar );
		add( new ScrollPaneSimulador( areaConteudo, 680, 467 ) );
		
		setIconImage( new ImageIcon( 
			getClass().getResource( 
			"imagens/iconHelp.png" ) ).getImage() );
		
		setDefaultCloseOperation( 
			JFrame.DISPOSE_ON_CLOSE );
			
		setSize( 700, 545 );
 		
 		// centraliza o componente na tela, usando um
 		// deslocamento vertical
 		Utilidades.centralizaComponenteNaTela( this, -5 );
		
		setUndecorated( true );
		
    	getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
    	
    	setVisible( true );
		
		setResizable( false );
		
		// carrega página inicial
		carregarDocumento( getClass().getResource( 
			"/br/com/davidbuzatto/simuladordeestoques/help/index.html" ).toString() );
		linksVisitados.add( getClass().getResource( 
			"/br/com/davidbuzatto/simuladordeestoques/help/index.html" ) );
		
	}
	
	// carrega o documento
	public void carregarDocumento( String local ) {
		
		try {
			
			areaConteudo.setPage( local );
			
		} catch ( IOException exc ) {
		}
		
	}
	
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == btnVoltar ) {
			
			if ( cont >= 1  ) {
				carregarDocumento(
					linksVisitados.get( --cont ).toString() );
			
				if ( !btnAvancar.isEnabled() )
					btnAvancar.setEnabled( true );
					
				if ( cont == 0 ) 
					btnVoltar.setEnabled( false );
					
			} 
			
			
		} else if ( e.getSource() == btnAvancar ) {
			
			if ( cont < linksVisitados.size() - 1  ) {
				carregarDocumento(
					linksVisitados.get( ++cont ).toString() );
			
				if ( !btnVoltar.isEnabled() )
					btnVoltar.setEnabled( true );
					
				if ( cont == linksVisitados.size() - 1 ) 
					btnAvancar.setEnabled( false );
					
			}
			
		}
		
	}
	
}