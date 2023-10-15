package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FrameGraficoConsumidor.java
 *
 * Classe que implementa um frame com os gráficos 
 * dos níveis de atendimento.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.imageio.*;
import javax.imageio.stream.*;

import javax.swing.*;


public class FrameGraficoConsumidor extends JFrame
	implements ActionListener {
	
	private PainelGraficoConsumidor pG1,
									pG2;
	
	private JPopupMenu popG1,
					   popG2;
	
	private JMenuItem itemSalvarG1,
					  itemSalvarG2,
					  itemImprimirG1,
					  itemImprimirG2,
					  itemLimparG1,
					  itemLimparG2,
					  itemConfG1,
					  itemConfG2;
					  
	private ScrollPaneSimulador scrollPaneGrafCons1,
								scrollPaneGrafCons2;
					  
	// contrutor
	public FrameGraficoConsumidor() {
		
		super( "Gráficos dos Consumidores" );
		
		setLayout( new FlowLayout() );
		
		// declara e instancia um ImageIcon para o ícone
		// do JFrame
		ImageIcon img = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconGraf.png" ) );
		// seta o ícone do JFrame
		setIconImage( img.getImage() );
		
		PainelTitulado pGraf1 = new PainelTitulado(
			"Consumidor 1", 615, 305 );
		pGraf1.setLayout( new FlowLayout() );
		PainelTitulado pGraf2 = new PainelTitulado(
			"Consumidor 2", 615, 305 );
		pGraf2.setLayout( new FlowLayout() );
		
		pG1 = new PainelGraficoConsumidor( this, 600, 250, Color.RED, 1 );
		pG2 = new PainelGraficoConsumidor( this, 600, 250, Color.BLUE, 2 );
		pG1.setToolTipText( "Para opções, clique com o botão direito" );
		pG2.setToolTipText( "Para opções, clique com o botão direito" );
		
		// construindo menus popUp
		popG1 = new JPopupMenu();
		popG2 = new JPopupMenu();
		
		ImageIcon imgSalva = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconSalva.png" ) );
		ImageIcon imgImprime = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconImprime.png" ) );
		ImageIcon imgLimpa = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconLimpa.png" ) );
		ImageIcon imgConfGraf = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconConfGraf.png" ) );
		
		itemSalvarG1 = new JMenuItem( "Salvar", imgSalva );
		itemSalvarG1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.CTRL_MASK ) );
		itemImprimirG1 = new JMenuItem( "Imprimir", imgImprime );
		itemImprimirG1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.ALT_MASK ) );
		itemLimparG1 = new JMenuItem( "Limpar", imgLimpa );
		itemLimparG1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfG1 = new JMenuItem( "Configurar Gráfico", imgConfGraf );
		itemConfG1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemSalvarG2 = new JMenuItem( "Salvar", imgSalva );
		itemSalvarG2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2,
			InputEvent.CTRL_MASK ) );
		itemImprimirG2 = new JMenuItem( "Imprimir", imgImprime );
		itemImprimirG2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2,
			InputEvent.ALT_MASK ) );
		itemLimparG2 = new JMenuItem( "Limpar", imgLimpa );
		itemLimparG2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfG2 = new JMenuItem( "Configurar Gráfico", imgConfGraf );
		itemConfG2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		
		itemSalvarG1.addActionListener( this );
		itemImprimirG1.addActionListener( this );
		itemLimparG1.addActionListener( this );
		itemConfG1.addActionListener( this );
		itemSalvarG2.addActionListener( this );
		itemImprimirG2.addActionListener( this );
		itemLimparG2.addActionListener( this );
		itemConfG2.addActionListener( this );
		
		popG1.add( itemSalvarG1 );
		popG1.addSeparator();
		popG1.add( itemImprimirG1 );
		popG1.add( itemLimparG1 );
		popG1.addSeparator();
		popG1.add( itemConfG1 );
		
		popG2.add( itemSalvarG2 );
		popG2.addSeparator();
		popG2.add( itemImprimirG2 );
		popG2.add( itemLimparG2 );
		popG2.addSeparator();
		popG2.add( itemConfG2 );
		
		// adiciona os menus popup nos graficos
		pG1.setComponentPopupMenu( popG1 );
		pG2.setComponentPopupMenu( popG2 );
		
		scrollPaneGrafCons1 = new ScrollPaneSimulador( pG1, 600, 268 );
		scrollPaneGrafCons2 = new ScrollPaneSimulador( pG2, 600, 268 );
		
		pGraf1.add( scrollPaneGrafCons1 );
		pGraf2.add( scrollPaneGrafCons2 );
		
		PainelSimulador pGrafs = new PainelSimulador( 630, 630 );
		pGrafs.add( pGraf1 );
		pGrafs.add( pGraf2 );
		
		add( new ScrollPaneSimulador( pGrafs, 650, 510 ) );	
		
	}
	
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == itemSalvarG1 ) {
			
			Utilidades.salvaImagemPainel( pG1.getBufferedImage() );
			
		} else if ( e.getSource() == itemImprimirG1 ) {
			
			Utilidades.imprimePainel( pG1 );
			
		} else if ( e.getSource() == itemLimparG1 ) {
			
			int opc = Mensagens.mensagemOpcaoSimNao( 
				"Deseja mesmo limpar o gráfico?", "Limpar" );
			
			switch ( opc ) {
				
				case 0:
					pG1.limpaCoords();
					pG1.repaint();
					
			}
			
		} else if ( e.getSource() == itemSalvarG2 ) {
			
			Utilidades.salvaImagemPainel( pG2.getBufferedImage() );
			
		} else if ( e.getSource() == itemImprimirG2 ) {
			
			Utilidades.imprimePainel( pG2 );

		} else if ( e.getSource() == itemLimparG2 ) {
			
			int opc = Mensagens.mensagemOpcaoSimNao( 
				"Deseja mesmo limpar o gráfico?", "Limpar" );
			
			switch ( opc ) {
				
				case 0:
					pG2.limpaCoords();
					pG2.repaint();
					
			}
			
		} else if ( e.getSource() == itemConfG1
			|| e.getSource() == itemConfG2 ) {
				
			// cria e exibe o diálogo de configuração dos gráficos
			new DialogoGraficoConsumidor( this, this,
				"Gráficos dos Consumidores", true );
				
		}
		
	}
	
	public void switchPopupMenu() {
		
		itemSalvarG1.setEnabled( !itemSalvarG1.isEnabled() );
		itemImprimirG1.setEnabled( !itemImprimirG1.isEnabled() );
		itemLimparG1.setEnabled( !itemLimparG1.isEnabled() );
		itemConfG1.setEnabled( !itemConfG1.isEnabled() );
		itemSalvarG2.setEnabled( !itemSalvarG2.isEnabled() );
		itemImprimirG2.setEnabled( !itemImprimirG2.isEnabled() );
		itemLimparG2.setEnabled( !itemLimparG2.isEnabled() );
		itemConfG2.setEnabled( !itemConfG2.isEnabled() );
		
	}
	
	// reseta o scrollPane para repintar o gráfico de novo tamanho
	public void resetScrollPaneGrafCons( int n ) {
		
		if ( n == 1 ) {
			scrollPaneGrafCons1.setViewportView( pG1 );
		} else if ( n == 2 ) {
			scrollPaneGrafCons2.setViewportView( pG2 );
		} else {
			scrollPaneGrafCons2.setViewportView( pG2 );
		}
		
	}
	
	public PainelGraficoConsumidor getPainelGrafico1() {
		
		return pG1;
		
	}
	
	public PainelGraficoConsumidor getPainelGrafico2() {
		
		return pG2;
		
	}
	
}