package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)DialogoConsumidor.java
 *
 * Classe que define um diálogo para 
 * configurações de um consumidor.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;


public class DialogoConsumidor extends JDialog
	implements ActionListener {
 	
	private JLabel labelTempoMin,
				   labelTempoMax,
				   labelQuantMin,
				   labelQuantMax;
 	
	private JTextField fieldTempoMin,
					   fieldTempoMax,
					   fieldQuantMin,
					   fieldQuantMax;
 	
	private JButton btnOK,
					btnCancel;
	
	private FramePrincipal dono;
	
	private ConfConsumidor conf;
 					
 	// construtor
	public DialogoConsumidor( FramePrincipal d, String titulo, boolean modal,
		ConfConsumidor c ) {
 		
 		// chama construtor da superclasse
		super( d, titulo, modal );
 		
 		// recupera o frame dono do diálogo
 		dono = d;
 		
 		// recupera o confidurador de consumidor
 		conf = c;
 		
		setLayout( new FlowLayout() );
 		
		PainelTitulado pCima = new PainelTitulado(
			null, 230, 180 );
		pCima.setLayout( new GridLayout( 2, 1, 5, 5 ) );
		
		labelTempoMin = new JLabel( "Mínimo" );
		labelTempoMax = new JLabel( "Máximo" );
		labelQuantMin = new JLabel( "Mínimo" );
		labelQuantMax = new JLabel( "Máximo" );
 		
 		// constrói utilizando os valores do configurador
		fieldTempoMin = new JTextField( conf.getTMin() + "", 10 );
		fieldTempoMax = new JTextField( conf.getTMax() + "", 10 );
		fieldQuantMin = new JTextField( conf.getQMin() + "", 10 );
		fieldQuantMax = new JTextField( conf.getQMax() + "", 10 );
		
		PainelTitulado pTempo = new PainelTitulado(
			"Intervalo de tempo de compra", 180, 85 );
		pTempo.setLayout( new FlowLayout() );
		pTempo.add( labelTempoMin );
		pTempo.add( fieldTempoMin );
		pTempo.add( labelTempoMax );
		pTempo.add( fieldTempoMax );
		
		PainelTitulado pQuant = new PainelTitulado(
			"Intervalo de quantidade de compra", 180, 85 );
		pQuant.setLayout( new FlowLayout() );
		pQuant.add( labelQuantMin );
		pQuant.add( fieldQuantMin );
		pQuant.add( labelQuantMax );
		pQuant.add( fieldQuantMax );
		
		pCima.add( pTempo );
		pCima.add( pQuant );
			
		PainelTitulado pBaixo = new PainelTitulado(
			null, 230, 50 );
		pBaixo.setLayout( new FlowLayout() );
			
		btnOK = new JButton( "OK" );
		btnOK.setMnemonic( 'O' );
		btnOK.setToolTipText(
			"Aceita alterações" );
		btnOK.addActionListener( this );
		btnCancel = new JButton( "Cancelar" );
		btnCancel.setMnemonic( 'C' );
		btnCancel.setToolTipText(
			"Rejeita alterações" );
		btnCancel.addActionListener( this );
 		
 		pBaixo.add( btnOK );
 		pBaixo.add( btnCancel );
 		
 		add( pCima );
 		add( pBaixo );
 		
 		getRootPane().setDefaultButton( btnOK );
 		
 		setSize( 245, 275 );
		
		// centraliza o componente na tela, usando um
 		// deslocamento vertical
 		Utilidades.centralizaComponenteNaTela( this, 0 );
		
		setDefaultCloseOperation(
			DISPOSE_ON_CLOSE );

		// muda exibição
		setUndecorated( true );
    	getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
    	
    	setResizable( false );
    	setVisible( true );
 		
	}
	
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == btnOK ) {
			
			try {
				
				int tMin = Integer.parseInt( fieldTempoMin.getText() );
				int tMax = Integer.parseInt( fieldTempoMax.getText() );
				int qMin = Integer.parseInt( fieldQuantMin.getText() );
				int qMax = Integer.parseInt( fieldQuantMax.getText() );
				
				if ( tMin <= 0 || tMax <= 0 
					|| qMin <= 0 || qMax <= 0 )
					throw new NegativeNumberException();
					
				if ( tMax < tMin || qMax < qMin )
					throw new NumbersOutOfOrderException();
				
				conf.setTMin( tMin );
				conf.setTMax( tMax );
				conf.setQMin( qMin );
				conf.setQMax( qMax );
				
				// fecha o diálogo
				dispose();
				
			} catch ( NegativeNumberException exc ) {
				
				Mensagens.mensagemNumeroNegativo();
					
			} catch ( NumbersOutOfOrderException exc ) {
				
				Mensagens.mensagemNumerosForaDeOrdem();
					
			} catch ( NumberFormatException exc ) {
				
				Mensagens.mensagemFormatoIncorreto();
					
			}
			
		} else {	// botão Cancel clicado
			
			// fecha o diálogo
			dispose();
			
		}
		
	}
 	
}