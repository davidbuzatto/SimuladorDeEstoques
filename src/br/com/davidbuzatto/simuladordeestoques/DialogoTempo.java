package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)DialogoTempo.java
 *
 * Classe que define um diálogo para 
 * configurações de tempo.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;
 
 
public class DialogoTempo extends JDialog
	implements ActionListener {
 	
	private JLabel labelTempo,
				   labelUniPasso;
 	
	private JTextField fieldTempo,
					   fieldUniPasso;
 	
	private JButton btnOK,
					btnCancel;
	
	private FramePrincipal dono;
 					
 	// construtor
	public DialogoTempo( FramePrincipal d, String titulo, boolean modal ) {
 		
 		// chama construtor da superclasse
		super( d, titulo, modal );
 		
 		// recupera o frame dono do diálogo
 		dono = d;
 		
		setLayout( new FlowLayout() );
 		
		PainelTitulado pCima = new PainelTitulado(
			null, 230, 70 );
		pCima.setLayout( new GridLayout( 2, 2, 5, 5 ) );
		
		labelTempo = new JLabel( "Tempo do passo" );
		labelTempo.setToolTipText( 
			"Tempo do passo da simulação em segundos" );
		labelUniPasso = new JLabel( "Unidade do passo" );
		labelUniPasso.setToolTipText( "Identificador da unidade de passo. "
			+ "Exemplos: dia, semana, mês, etc" );
		
		// seta os campos
		fieldTempo = new JTextField( ArmazemEstados.getTempoPasso() + "" );
		fieldUniPasso = new JTextField( ArmazemEstados.getUnidadePasso() );
		
		pCima.add( labelTempo );
		pCima.add( fieldTempo );
		pCima.add( labelUniPasso );
		pCima.add( fieldUniPasso );
			
		PainelTitulado pBaixo = new PainelTitulado(
			null, 230, 50 );
		pBaixo.setLayout( new FlowLayout() );
			
		btnOK = new JButton( "OK" );
		btnOK.setMnemonic( 'O' );
		btnOK.setToolTipText(
			"Aceita alterações" );
		btnOK.addActionListener( this );
		btnCancel = new JButton( "Cancelar" );
		btnCancel.setToolTipText(
			"Rejeita alterações" );
		btnCancel.setMnemonic( 'C' );
		btnCancel.addActionListener( this );
 		
 		pBaixo.add( btnOK );
 		pBaixo.add( btnCancel );
 		
 		add( pCima );
 		add( pBaixo );
 		
 		getRootPane().setDefaultButton( btnOK );
 		
 		setSize( 245, 165 );
		
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
				
				double tempo = Double.parseDouble( fieldTempo.getText() );
				
				if ( tempo <= 0 )
					throw new NegativeNumberException();
					
				ArmazemEstados.setTempoPasso( tempo );
				ArmazemEstados.setUnidadePasso( fieldUniPasso.getText() );
				
				// atualiza o status
				dono.atualizaStatus();
				
				// fecha o diálogo
				dispose();
				
			} catch ( NegativeNumberException exc ) {
				
				Mensagens.mensagemNumeroNegativo();
					
			} catch ( NumberFormatException exc ) {
				
				Mensagens.mensagemFormatoIncorretoTempo();
					
			}
			
		} else {	// botão Cancel clicado
			
			// fecha o diálogo
			dispose();
			
		}
		
	}
 	
}