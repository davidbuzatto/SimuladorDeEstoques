package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)DialogoGraficoEstoque.java
 *
 * Classe que define um diálogo para 
 * configurações do gráfico de estoque.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;
 
 
public class DialogoGraficoEstoque extends JDialog
	implements ActionListener {
 	
	private JLabel labelCorPes,
				   labelCorAss,
				   labelCorPA;
 	
 	private PainelSimulador painelCorPes,
 						  	painelCorAss,
 						  	painelCorPA;
 				   
	private JButton btnCorPes,
					btnCorAss,
					btnCorPA,
					btnOK,
					btnCancel;

	private JCheckBox gradeBox,
					  labelBox,
					  antiAliasBox;
					  
	private Color corPes,
				  corAss,
				  corPA;
	
	private FramePrincipal dono;
	
	private PainelGraficoEstoque painel;
 					
 	// construtor
	public DialogoGraficoEstoque( FramePrincipal d, 
		PainelGraficoEstoque p, String titulo, boolean modal ) {
 		
 		// chama construtor da superclasse
		super( d, titulo, modal );
 		
 		// recupera o frame dono do diálogo
 		dono = d;
 		
 		// recupera o painel de gráfico de estoque
 		painel = p;
 		
		setLayout( new FlowLayout() );
 		
		PainelTitulado pCima = new PainelTitulado(
			null, 200, 175 );
		pCima.setLayout( new FlowLayout() );
		
		labelCorPes = new JLabel( "Pés: " );
		labelCorAss = new JLabel( "Assentos: " );
		labelCorPA = new JLabel( "Bancos: " );
		
		// recebe as cores do frame
		corPes = painel.getCorGrafPes();
		corAss = painel.getCorGrafAss();
		corPA = painel.getCorGrafPA();
		
		// seta as demonstracoes de cores com o padrão
		painelCorPes = new PainelSimulador( 20, 20 );
		painelCorPes.setBackground( painel.getCorGrafPes() );
		painelCorPes.setToolTipText( "Cor atual" );
		painelCorAss = new PainelSimulador( 20, 20 );
		painelCorAss.setBackground( painel.getCorGrafAss() );
		painelCorAss.setToolTipText( "Cor atual" );
		painelCorPA = new PainelSimulador( 20, 20 );
		painelCorPA.setBackground( painel.getCorGrafPA() );
		painelCorPA.setToolTipText( "Cor atual" );
		
		JPanel painel1 = new JPanel();
		painel1.add( painelCorPes );
		JPanel painel2 = new JPanel();
		painel2.add( painelCorAss );
		JPanel painel3 = new JPanel();
		painel3.add( painelCorPA );
		
		btnCorPes = new JButton( "Cor" );
		btnCorPes.setToolTipText( "Define a cor do gráfico para o estoque de Pés" );
		btnCorPes.addActionListener( this );
		btnCorAss = new JButton( "Cor" );
		btnCorAss.setToolTipText( "Define a cor do gráfico para o estoque de Assentos" );
		btnCorAss.addActionListener( this );
		btnCorPA = new JButton( "Cor" );
		btnCorPA.setToolTipText( "Define a cor do gráfico para o estoque de Bancos" );
		btnCorPA.addActionListener( this );
		
		// verifica desenho dos valores antes de construir
		labelBox = new JCheckBox( "Valores",
			painel.isDrawingLabel() );
		labelBox.setToolTipText( "Ativa/Desativa desenho dos valores" );
		
		// verifica desenho da grade antes de construir
		gradeBox = new JCheckBox( "Grade",
			painel.isDrawingGrade() );
		gradeBox.setToolTipText( "Ativa/Desativa desenho da grade" );
		
		// verifica antialias antes de construir
		antiAliasBox = new JCheckBox( "Anti-Aliasing",
			painel.isAntialiasing() );
		antiAliasBox.setToolTipText( "Ativa/Desativa suavisação" );
		
		PainelSimulador pComp = new PainelSimulador( 190, 100 );
		pComp.setLayout( new GridLayout( 3, 3, 5, 5 ) );
		
		pComp.add( labelCorPes );
		pComp.add( btnCorPes );
		pComp.add( painel1 );
		pComp.add( labelCorAss );
		pComp.add( btnCorAss );
		pComp.add( painel2 );
		pComp.add( labelCorPA );
		pComp.add( btnCorPA );
		pComp.add( painel3 );
		
		pCima.add( pComp );
		pCima.add( labelBox );
		pCima.add( gradeBox );
		pCima.add( antiAliasBox );
			
		PainelTitulado pBaixo = new PainelTitulado(
			null, 200, 50 );
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
 		
		setSize( 215, 270 );
		
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
			painel.setCorGrafPes( corPes );
			painel.setCorGrafAss( corAss );
			painel.setCorGrafPA( corPA );
			
			// seta valores
			painel.setDrawLabel( labelBox.isSelected() );
			
			// seta grade
			painel.setDrawGrade( gradeBox.isSelected() );
			
			// seta suavisação
			painel.setAntialiasing( antiAliasBox.isSelected() );
			
			// repinta o painel com as novas configurações
			dono.repintaGrafEstoque();
			
			// fecha o diálogo
			dispose();
			
		} else if ( e.getSource() == btnCancel ) {
			
			// fecha o diálogo
			dispose();
			
		} else if ( e.getSource() == btnCorPes ) {
			
			Color c = JColorChooser.showDialog( this, 
				"Cor do Gráfico de Pés", corPes );
			
			// se c for diferente de null
			if ( c != null )
				corPes = c;
			
			// repinta o painel de amostra
			painelCorPes.setBackground( corPes );
			
		} else if ( e.getSource() == btnCorAss ) {
			
			Color c = JColorChooser.showDialog( this, 
				"Cor do Gráfico de Assentos", corAss );
			
			// se c for diferente de null
			if ( c != null )
				corAss = c;
			
			// repinta o painel de amostra
			painelCorAss.setBackground( corAss );
			
		} else if ( e.getSource() == btnCorPA ) {
			
			Color c = JColorChooser.showDialog( this, 
				"Cor do Gráfico de Bancos", corPA );
			
			// se c for diferente de null
			if ( c != null )
				corPA = c;
			
			// repinta o painel de amostra
			painelCorPA.setBackground( corPA );
			
		}
		
	}
 	
}