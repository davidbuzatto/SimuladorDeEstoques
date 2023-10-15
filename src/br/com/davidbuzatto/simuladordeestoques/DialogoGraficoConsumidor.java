package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)DialogoGraficoConsumidor.java
 *
 * Classe que define um diálogo para 
 * configurações dos gráficos.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;
 
 
public class DialogoGraficoConsumidor extends JDialog
	implements ActionListener {
 	
 	private PainelSimulador painelCor1,
 							painelCor2;
 	
 	private PainelGraficoConsumidor pG1,
 									pG2;
 				   
	private JButton btnCor1,
					btnCor2,
					btnOK,
					btnCancel;

	private JCheckBox labelBox1,
					  labelBox2,
					  gradeBox1,
					  gradeBox2,
					  antiAliasBox1,
					  antiAliasBox2,
					  tempoRealBox;
					  
	private Color cor1,
				  cor2;
	
	private FrameGraficoConsumidor fG;
 					
 	// construtor
	public DialogoGraficoConsumidor( JFrame d,
		FrameGraficoConsumidor f, String titulo, boolean modal ) {
 		
 		// chama construtor da superclasse
		super( d, titulo, modal );
 		
 		fG = f;
 		
		setLayout( new FlowLayout() );
 		
 		pG1 = fG.getPainelGrafico1();
 		pG2 = fG.getPainelGrafico2();
 		
		PainelTitulado pCima = new PainelTitulado(
			null, 255, 240 );
		pCima.setLayout( new FlowLayout() );
		
		PainelTitulado pCons1 = new PainelTitulado(
			"Consumidor 1", 240, 95 );
		PainelTitulado pCons2 = new PainelTitulado(
			"Consumidor 2", 240, 95 );
		
		// recebe as cores do frame
		cor1 = pG1.getCorGraf();
		cor2 = pG2.getCorGraf();
		
		// seta as demonstracoes de cores com o padrão
		painelCor1 = new PainelSimulador( 20, 20 );
		painelCor1.setBackground( cor1 );
		painelCor1.setToolTipText( "Cor atual" );
		painelCor2 = new PainelSimulador( 20, 20 );
		painelCor2.setBackground( cor2 );
		painelCor2.setToolTipText( "Cor atual" );
		
		btnCor1 = new JButton( "Cor" );
		btnCor1.setToolTipText( "Define a cor do gráfico para o Consumidor 1" );
		btnCor1.addActionListener( this );
		btnCor2 = new JButton( "Cor" );
		btnCor2.setToolTipText( "Define a cor do gráfico para o Consumidor 2" );
		btnCor2.addActionListener( this );
		
		// verifica desenho dos valores antes de construir
		labelBox1 = new JCheckBox( "Valores",
			pG1.isDrawingLabel() );
		labelBox1.setToolTipText( "Ativa/Desativa desenho dos valores" );
		labelBox2 = new JCheckBox( "Valores",
			pG2.isDrawingLabel() );
		labelBox2.setToolTipText( "Ativa/Desativa desenho dos valores" );
		
		// verifica desenho da grade antes de construir
		gradeBox1 = new JCheckBox( "Grade",
			pG1.isDrawingGrade() );
		gradeBox1.setToolTipText( "Ativa/Desativa desenho da grade" );
		gradeBox2 = new JCheckBox( "Grade",
			pG2.isDrawingGrade() );
		gradeBox2.setToolTipText( "Ativa/Desativa desenho da grade" );
		
		// verifica antialias antes de construir
		antiAliasBox1 = new JCheckBox( "Anti-Aliasing",
			pG1.isAntialiasing() );
		antiAliasBox1.setToolTipText( "Ativa/Desativa suavisação" );
		antiAliasBox2 = new JCheckBox( "Anti-Aliasing",
			pG2.isAntialiasing() );
		antiAliasBox2.setToolTipText( "Ativa/Desativa suavisação" );
		
		tempoRealBox = new JCheckBox( "Plotagem automática",
			ArmazemEstados.isTempoRealGrafCons() );
		tempoRealBox.setToolTipText( "Ativa/Desativa plotagem automática "
			+ "dos gráficos" );
		
		pCons1.add( btnCor1 );
		pCons1.add( painelCor1 );
		pCons1.add( labelBox1 );
		pCons1.add( gradeBox1 );
		pCons1.add( antiAliasBox1 );

		pCons2.add( btnCor2 );
		pCons2.add( painelCor2 );
		pCons2.add( labelBox2 );
		pCons2.add( gradeBox2 );
		pCons2.add( antiAliasBox2 );
		
		pCima.add( pCons1 );
		pCima.add( pCons2 );
		pCima.add( tempoRealBox );
			
		PainelTitulado pBaixo = new PainelTitulado(
			null, 255, 50 );
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
 		
		setSize( 270, 335 );
		
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
					
			// seta as cores
			pG1.setCorGraf( cor1 );
			pG2.setCorGraf( cor2 );
			
			// seta valores
			pG1.setDrawLabel( labelBox1.isSelected() );
			pG2.setDrawLabel( labelBox2.isSelected() );
			
			// seta grade
			pG1.setDrawGrade( gradeBox1.isSelected() );
			pG2.setDrawGrade( gradeBox2.isSelected() );
			
			// seta a suavisação
			pG1.setAntialiasing( antiAliasBox1.isSelected() );
			pG2.setAntialiasing( antiAliasBox2.isSelected() );
				
			// seta plotagem automática
			ArmazemEstados.setTempoRealGrafCons( 
				tempoRealBox.isSelected() );
			
			pG1.repaint();
			pG2.repaint();
			
			// fecha o diálogo
			dispose();
			
		} else if ( e.getSource() == btnCancel ) {
			
			// fecha o diálogo
			dispose();
			
		} else if ( e.getSource() == btnCor1 ) {
			
			Color c = JColorChooser.showDialog( this, 
				"Cor do Gráfico do Consumidor 1", cor1 );
			
			// se c for diferente de null
			if ( c != null )
				cor1 = c;
			
			// repinta o painel de amostra
			painelCor1.setBackground( cor1 );
			
		} else if ( e.getSource() == btnCor2 ) {
			
			Color c = JColorChooser.showDialog( this, 
				"Cor do Gráfico do Consumidor 2", cor2 );
			
			// se c for diferente de null
			if ( c != null )
				cor2 = c;
			
			// repinta o painel de amostra
			painelCor2.setBackground( cor2 );
			
		}
		
	}
 	
}