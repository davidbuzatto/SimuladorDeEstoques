package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FramePrincipal.java
 *
 * Classe que implementa a interface gráfica do Simulador
 * e integra todas as demais classes.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.concurrent.*;

import javax.swing.*;


public class FramePrincipal extends JFrame
	implements ActionListener {
	
	// componentes da GUI
	private JButton bIni, 
					bFim, 
					bTabela,
					bGrafico;
	
	private JLabel lLtPes, 
				   lLPes,
				   lEstPes,
				   lLtAss,
				   lLAss,
				   lEstAss,
				   lLtPA,
				   lLPA,
				   lEstPA,
				   lC1Est,
				   lC1NAt,
				   lC2Est,
				   lC2NAt,
				   
				   // montagem do status
				   labelTempoPasso,
				   labelUnidadePasso,
				   
				   // cor dos gráficos de estoque
				   labelCorGrafPes,
				   labelCorGrafAss,
				   labelCorGrafPA;
				   
	private JTextField fLtPes,
					   fLPes,
					   fEstPes,
					   fLtAss,
					   fLAss,
					   fEstAss,
					   fLtPA,
					   fLPA,
					   fEstPA,
					   fC1Est,
					   fC1NAt,
				   	   fC2Est,
				   	   fC2NAt;
	
	// painél do gráfico de estoque
	private PainelGraficoEstoque pGrafEst;
	
	// scroll pane do gráfico de estoque
	private ScrollPaneSimulador scrollPaneGrafEst;
	
	// painéis de cores dos gráficos de estoque
	private PainelSimulador pCorGrafPes,
							pCorGrafAss,
							pCorGrafPA;
				   	   
	// componentes de menu
	JMenuItem itemSairSim,
			  itemConfTempo,
			  itemConfGraf,
			  itemConfGrafEst,
			  itemConfCons1,
			  itemConfCons2,
			  itemSobreAjuda,
			  itemComoUsarAjuda,
			  itemPopConfCons1,
			  itemPopConfGCons1,
			  itemPopConfCons2,
			  itemPopConfGCons2,
			  itemSalvarGEst,
			  itemImprimirGEst,
			  itemLimparGEst,
			  itemConfGEst,
			  itemPopConfTempo;
	
	// menus popup
	private JPopupMenu popGEst,
					   popCons1,
					   popCons2,
					   popTempo;
			  
	// menu de configuração
	JMenu menuConf;
			  
	// thread do Simulador
	private GerenteDePasso gerentePasso;
	
	// agentes do simulador
	private Produtor agP;
	private FornecedorPe agFP;
	private FornecedorAssento agFA;
	private Consumidor agCon1, agCon2;
	
	// executor de threads
	ExecutorService threadExecutor;
	
	// monitor dos agentes
	private Monitor monitor;
	
	// armazéns p/ informações
	private ArmazemDados aPes, 
						 aAss, 
						 aPA, 
						 aCon1, 
						 aCon2;
	
	// frame da tabela de informações
	private FrameTabela fT;
	
	// frame dos gráficos
	private FrameGraficoConsumidor fG;
	
	// tabela de informações do sistema
	private TabelaSimulador tabela;
	
	private ConfConsumidor confC1,	// armazenam configuracao dos consumidores
						   confC2;
	
	private Random gerador;		// gerador de números aleatórios
	
	// construtor
	public FramePrincipal( FrameTabela f, FrameGraficoConsumidor g ) {
		
		// chama o construtor da superclasse que aceita
		// uma String como parâmetro para setar
		// o título da janela
		super( "Simulador de Estoques" );
		
		// registrando o ouvinte para fechamento
		addWindowListener(
			new WindowAdapter() {
				
				public void windowClosing( WindowEvent e ) {
					
					int opc = Mensagens.mensagemOpcaoSimNao(
						"Deseja mesmo sair do simulador?", "Sair" );
					
					switch ( opc ) {
						
						case 0:
							System.exit( 0 );
							
					}
					
				}
				
			}
			
		);
		
		// seta o layout
		setLayout( new FlowLayout() );
		
		// declara e instancia um ImageIcon para o ícone
		// do JFrame
		ImageIcon img = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconPrin.png" ) );
		// seta o ícone do JFrame
		setIconImage( img.getImage() );
		
		// setando todos os campos de ArmazemEstados
		ArmazemEstados.inicializa();
		
		// instancia novo Monitor
		monitor = new Monitor();
		
		// recebe o frame tabela
		fT = f;
		
		// recebe o frame gráfico
		fG = g;
		
		// instancia os configuradores de consumidores
		confC1 = new ConfConsumidor( 1, 1, 1, 50 );
		confC2 = new ConfConsumidor( 1, 1, 1, 50 );
		
		// construção do menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuSimulador = new JMenu( "Simulador" );
		menuSimulador.setMnemonic( 'S' );
		
		ImageIcon imgSair = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconSair.png" ) );
		itemSairSim = new JMenuItem( "Sair", imgSair );	
		itemSairSim.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4,
			InputEvent.ALT_MASK ) );
		menuSimulador.add( itemSairSim );
		itemSairSim.setMnemonic( 'r' );
		itemSairSim.addActionListener( this );
		
		menuBar.add( menuSimulador );
		
		menuConf = new JMenu( "Configurações" );
		menuConf.setMnemonic( 'C' );
		
		ImageIcon imgConfTempo = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconConfTempo.png" ) );
		ImageIcon imgConfGraf = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconConfGraf.png" ) );
		ImageIcon imgConfCons1 = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconConfCons1.png" ) );
		ImageIcon imgConfCons2 = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconConfCons2.png" ) );
		ImageIcon imgInicia = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconInicia.png" ) );
		ImageIcon imgFinaliza = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconFinaliza.png" ) );
		ImageIcon imgTabela = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconTabela.png" ) );
		ImageIcon imgGraf = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconGrafico.png" ) );
		
		itemConfTempo = new JMenuItem( "Relações de Tempo", imgConfTempo );
		itemConfGraf = new JMenuItem( "Gráficos dos Consumidores", imgConfGraf );
		itemConfGrafEst = new JMenuItem( "Gráfico de Estoque", imgConfGraf );
		itemConfCons1 = new JMenuItem( "Consumidor 1", imgConfCons1 );
		itemConfCons2 = new JMenuItem( "Consumidor 2", imgConfCons2 );
		
		menuConf.add( itemConfTempo );
		menuConf.add( itemConfGraf );
		menuConf.add( itemConfGrafEst );
		menuConf.addSeparator();
		menuConf.add( itemConfCons1 );
		menuConf.add( itemConfCons2 );
		
		itemConfTempo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_T,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfGraf.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfGrafEst.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfCons1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfCons2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
			
		itemConfTempo.setMnemonic( 'm' );
		itemConfGraf.setMnemonic( 'G' );
		itemConfGrafEst.setMnemonic( 'E' );
		itemConfCons1.setMnemonic( '1' );
		itemConfCons2.setMnemonic( '2' );
		
		itemConfTempo.addActionListener( this );
		itemConfGraf.addActionListener( this );
		itemConfGrafEst.addActionListener( this );
		itemConfCons1.addActionListener( this );
		itemConfCons2.addActionListener( this );
			
		menuBar.add( menuConf );
		
		// menu ajuda
		JMenu menuAjuda = new JMenu( "Ajuda" );
		menuAjuda.setMnemonic( 'A' );
		
		ImageIcon imgAjuda = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconAjuda.png" ) );
		
		itemComoUsarAjuda = new JMenuItem( "Usando o Simulador",
			imgAjuda );	
		itemSobreAjuda = new JMenuItem( "Sobre...", imgAjuda );
		
		itemComoUsarAjuda.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F1,
			0 ) );
		itemSobreAjuda.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.CTRL_MASK ) );
		
		itemComoUsarAjuda.setMnemonic( 'U' );	
		itemSobreAjuda.setMnemonic( 'o' );
		
		itemComoUsarAjuda.addActionListener( this );
		itemSobreAjuda.addActionListener( this );
		
		menuAjuda.add( itemComoUsarAjuda );
		menuAjuda.addSeparator();
		menuAjuda.add( itemSobreAjuda );
		
		menuBar.add( menuAjuda );
		
		setJMenuBar( menuBar );
		
		// contrução da interface para Fornecedor de pés
		lLtPes = new JLabel( "Lead Time" );
		lLPes = new JLabel( "Lote" );
		fLtPes = new JTextField( "1", 10 );
		fLPes = new JTextField( "300", 10 );
		
		PainelTitulado pFPe = new PainelTitulado(
			"Fornecedor de Pés", 160, 80 );
		pFPe.setLayout( new GridLayout( 2, 2, 5, 5 ) );
		pFPe.add( lLtPes );	pFPe.add( fLtPes );
		pFPe.add( lLPes );	pFPe.add( fLPes );
		
		
		// contrução da interface para Fornecedor de assentos
		lLtAss = new JLabel( "Lead Time" );
		lLAss = new JLabel( "Lote" );
		fLtAss = new JTextField( "1", 10 );
		fLAss = new JTextField( "100", 10 );
		
		PainelTitulado pFAss = new PainelTitulado(
			"Fornecedor de Assentos", 160, 80 );
		pFAss.setLayout( new GridLayout( 2, 2, 5, 5 ) );
		pFAss.add( lLtAss );	pFAss.add( fLtAss );
		pFAss.add( lLAss );		pFAss.add( fLAss );
		
		// painel p/ agrupar os fornecedores de pés e assentos
		PainelTitulado pForn = new PainelTitulado(
			"Fornecedores", 170, 210 );
		pForn.setLayout( new FlowLayout() );
		pForn.add( pFPe );
		pForn.add( pFAss);
		
		
		
		// contrução da interface para Produtor de bancos
		lLtPA = new JLabel( "Lead Time" );
		lLPA = new JLabel( "Lote" );
		lEstPes = new JLabel( "Estoque Pés" );
		lEstAss = new JLabel( "Estoque Assentos" );
		lEstPA = new JLabel( "Estoque Bancos" );
		fLtPA = new JTextField( "1", 10 );
		fLPA = new JTextField( "100", 10 );
		fEstPes = new JTextField( 10 );
		fEstPes.setEditable( false );
		fEstPes.setFocusable( false );
		fEstAss = new JTextField( 10 );
		fEstAss.setEditable( false );
		fEstAss.setFocusable( false );
		fEstPA = new JTextField( 10 );
		fEstPA.setEditable( false );
		fEstPA.setFocusable( false );
		
		PainelTitulado pPA = new PainelTitulado( 
			null, 230, 160 );
		pPA.setLayout( new GridLayout( 5, 2, 5, 5 ) );
		pPA.add( lLtPA );	pPA.add( fLtPA );
		pPA.add( lLPA );	pPA.add( fLPA );
		pPA.add( lEstPes );	pPA.add( fEstPes );	
		pPA.add( lEstAss ); pPA.add( fEstAss );	
		pPA.add( lEstPA );	pPA.add( fEstPA );	
		
		// painel que desenha o gráfico dos estoques de pés e assentos
		pGrafEst = new PainelGraficoEstoque( this, 400, 122 );
		pGrafEst.setToolTipText(
			"Gráfico de estoque do produtor de bancos" );
		scrollPaneGrafEst = new ScrollPaneSimulador( pGrafEst, 355, 140 );
		
		PainelTitulado pFPA = new PainelTitulado(
			"Produtor de Bancos", 380, 375 );
		pFPA.setLayout( new FlowLayout() );
		pFPA.add( pPA );
		pFPA.add( scrollPaneGrafEst );
		
		// painéis com as cores dos gráficos
		pCorGrafPes = new PainelSimulador( 10, 10 );
		pCorGrafAss = new PainelSimulador( 10, 10 );
		pCorGrafPA = new PainelSimulador( 10, 10 );
		pCorGrafPes.setBackground( pGrafEst.getCorGrafPes() );
		pCorGrafAss.setBackground( pGrafEst.getCorGrafAss() );
		pCorGrafPA.setBackground( pGrafEst.getCorGrafPA() );
		
		// label dos paineis
		labelCorGrafPes = new JLabel( "Pés" );
		labelCorGrafAss = new JLabel( "Assentos" );
		labelCorGrafPA = new JLabel( "Bancos" );
		
		JPanel painelCores = new JPanel();
		painelCores.setLayout( new GridLayout( 1, 3, 5, 5 ) );
		JPanel pAux1 = new JPanel();
		pAux1.add( pCorGrafPes );
		pAux1.add( labelCorGrafPes );
		JPanel pAux2 = new JPanel();
		pAux2.add( pCorGrafAss );
		pAux2.add( labelCorGrafAss );
		JPanel pAux3 = new JPanel();
		pAux3.add( pCorGrafPA );
		pAux3.add( labelCorGrafPA );
		
		painelCores.add( pAux1 );
		painelCores.add( pAux2 );
		painelCores.add( pAux3 );
		
		pFPA.add( painelCores );
		
		// construindo menuPopup do gráfico de estoque
		popGEst = new JPopupMenu();
		
		ImageIcon imgSalva = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconSalva.png" ) );
		ImageIcon imgImprime = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconImprime.png" ) );
		ImageIcon imgLimpa = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconLimpa.png" ) );
		
		itemSalvarGEst = new JMenuItem( "Salvar", imgSalva );
		itemSalvarGEst.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_W,
			InputEvent.CTRL_MASK ) );
		itemImprimirGEst = new JMenuItem( "Imprimir", imgImprime );
		itemImprimirGEst.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_W,
			InputEvent.ALT_MASK ) );
		itemLimparGEst = new JMenuItem( "Limpar", imgLimpa );
		itemLimparGEst.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_W,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemConfGEst = new JMenuItem( "Configurar Gráfico", imgConfGraf );
		itemConfGEst.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		
		itemSalvarGEst.addActionListener( this );
		itemImprimirGEst.addActionListener( this );
		itemLimparGEst.addActionListener( this );
		itemConfGEst.addActionListener( this );
		
		popGEst.add( itemSalvarGEst );
		popGEst.addSeparator();
		popGEst.add( itemImprimirGEst );
		popGEst.add( itemLimparGEst );
		popGEst.addSeparator();
		popGEst.add( itemConfGEst );
		
		// adiciona os menus popup nos graficos
		pGrafEst.setComponentPopupMenu( popGEst );
		
		// contrução da interface para Consumidor 1
		lC1Est = new JLabel( "Estoque" );
		lC1NAt = new JLabel( "Nível Atend." );
		fC1Est = new JTextField( 10 );
		fC1Est.setEditable( false );
		fC1Est.setFocusable( false );
		fC1NAt = new JTextField( 10 );
		fC1NAt.setEditable( false );
		fC1NAt.setFocusable( false );
		
		PainelTitulado pC1 = new PainelTitulado(
			"Consumidor 1", 160, 80 );
		pC1.setToolTipText( "Consumidor 1" );
		pC1.setLayout( new GridLayout( 2, 2, 5, 5 ) );
		pC1.add( lC1Est );	pC1.add( fC1Est );
		pC1.add( lC1NAt );	pC1.add( fC1NAt );
		
		// construção do menu popup do Consumidor 1
		popCons1 = new JPopupMenu();
		
		itemPopConfCons1 = new JMenuItem( "Configurar", 
			imgConfCons1 );
		itemPopConfCons1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemPopConfGCons1 = new JMenuItem( 
			"Configurar Gráfico", imgConfGraf );
		itemPopConfGCons1.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		
		itemPopConfCons1.addActionListener( this );
		itemPopConfGCons1.addActionListener( this );
		
		popCons1.add( itemPopConfCons1 );
		popCons1.addSeparator();
		popCons1.add( itemPopConfGCons1 );
		
		// adiciona os menus popup no painel
		pC1.setComponentPopupMenu( popCons1 );
		
		
		// contrução da interface para Consumidor 2
		lC2Est = new JLabel( "Estoque" );
		lC2NAt = new JLabel( "Nível Atend." );
		fC2Est = new JTextField( 10 );
		fC2Est.setEditable( false );
		fC2Est.setFocusable( false );
		fC2NAt = new JTextField( 10 );
		fC2NAt.setEditable( false );
		fC2NAt.setFocusable( false );
		
		PainelTitulado pC2 = new PainelTitulado(
			"Consumidor 2", 160, 80 );
		pC2.setToolTipText( "Consumidor 2" );
		pC2.setLayout( new GridLayout( 2, 2, 5, 5 ) );
		pC2.add( lC2Est );	pC2.add( fC2Est );
		pC2.add( lC2NAt );	pC2.add( fC2NAt );
		
		// construção do menu popup do Consumidor 2
		popCons2 = new JPopupMenu();
		
		itemPopConfCons2 = new JMenuItem( "Configurar", 
			imgConfCons2 );
		itemPopConfCons2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_2,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		itemPopConfGCons2 = new JMenuItem( 
			"Configurar Gráfico", imgConfGraf );
		itemPopConfGCons2.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		
		itemPopConfCons2.addActionListener( this );
		itemPopConfGCons2.addActionListener( this );
		
		popCons2.add( itemPopConfCons2 );
		popCons2.addSeparator();
		popCons2.add( itemPopConfGCons2 );
		
		// adiciona os menus popup no painel
		pC2.setComponentPopupMenu( popCons2 );
		
		// painel p/ agrupar os consumidores 1 e 2
		PainelTitulado pCon = new PainelTitulado(
			"Consumidores", 170, 210 );
		pCon.setLayout( new FlowLayout() );
		pCon.add( pC1 );
		pCon.add( pC2 );
		
		
		// painel geral, agrupa os painéis de forncedores, pa e consumidores
		PainelTitulado pCima = new PainelTitulado(
			null, 750, 395 );
		pCima.setLayout( new FlowLayout() );
		pCima.add( pForn );
		pCima.add( pFPA );
		pCima.add( pCon );
		
		// adiciona o painel cima no JFrame
		add( pCima );
		
		// botões
		bIni = new JButton( "Iniciar", imgInicia );
		bIni.setMnemonic( 'I' );
		bIni.setToolTipText(
			"Inicia a simulação" );
		bFim = new JButton( "Finalizar", imgFinaliza );
		bFim.setMnemonic( 'F' );
		bFim.setToolTipText(
			"Finaliza a simulalação" );
		bTabela = new JButton( "Exibir Tabela", imgTabela );
		bTabela.setMnemonic( 'T' );
		bTabela.setToolTipText(
			"Exibe os dados das simulações em forma de tabela" );
		bGrafico = new JButton( "Exibir Gráficos", imgGraf );
		bGrafico.setMnemonic( 'G' );
		bGrafico.setToolTipText(
			"Exibe os gráficos de nível de atendimento dos consumidores da "
			+ "simulação atual" );
		
		bIni.addActionListener( this );
		bFim.addActionListener( this );
		bTabela.addActionListener( this );
		bGrafico.addActionListener( this );
		
		bFim.setEnabled( false );
		
		JPanel pBot = new JPanel();
		pBot.setLayout( new FlowLayout() );
		pBot.add( bIni );
		pBot.add( bFim );
		pBot.add( bTabela );
		pBot.add( bGrafico );
		
		PainelTitulado pBaixo = new PainelTitulado(
			null, 750, 60 );
		pBaixo.setLayout( new FlowLayout() );
		pBaixo.add( pBot );
		
		add( pBaixo );
		
		// painel de status
		PainelTitulado pRelTempo = new PainelTitulado(
			 null, 750, 40 );
		pRelTempo.setToolTipText( "Relações de Tempo" );
		
		labelTempoPasso = new JLabel();
		labelUnidadePasso = new JLabel();
		
		labelTempoPasso.setForeground( new Color( 0, 51, 102 ) );
		labelUnidadePasso.setForeground( new Color( 0, 153, 204 ) );
		
		pRelTempo.add( labelTempoPasso );
		pRelTempo.add( labelUnidadePasso );
		
		// construção do menu popup do painel de relações
		// de tempo
		popTempo = new JPopupMenu();
		
		itemPopConfTempo = new JMenuItem( "Configurar", 
			imgConfTempo );
		itemPopConfTempo.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_T,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
		
		itemPopConfTempo.addActionListener( this );
		
		popTempo.add( itemPopConfTempo );
		
		// adiciona os menus popup no painel
		pRelTempo.setComponentPopupMenu( popTempo );
		
		// atualiza o status
		atualizaStatus();
		
		add( pRelTempo );
		
		// seleciona texto dos campos de texto ao
		// receberem o foco e registra ouvinte
		// de ação
		Utilidades.resolveFocus(
			fLtPes, fLPes, fLtAss, fLAss, fLtPA, fLPA );
		
		// instancia gerador de números aleatórios
		gerador = new Random();
		
	} // fim do construtor
	
	// método tratador de eventos de clique dos botões
	public void actionPerformed( ActionEvent e ) {
		
		Object obj = e.getSource();
		
		if ( obj == bIni ) {
			
			// inicia a simulação
			inicia(); 
			
		} else if ( obj == bFim ) {
			
			// finaliza a simualação
			finaliza();
			
		} else if ( obj == bTabela ) {
			
			if ( ! ( fT.isVisible() ) )
				fT.setVisible( true );
				
		} else if ( obj == bGrafico ) {
			
			if ( ! ( fG.isVisible() ) )
				fG.setVisible( true );
				
		} else if ( obj == itemSairSim ) {
			
			int opc = Mensagens.mensagemOpcaoSimNao(
				"Deseja mesmo sair do simulador?", "Sair" );
			
			switch ( opc ) {
				
				case 0:
					System.exit( 0 );
					
			}
			
		} else if ( obj == itemConfTempo
			|| obj == itemPopConfTempo ) {
			
			// cria e exibe o diálogo de configuração de tempo
			new DialogoTempo( this, "Relações de Tempo", true );
			
		} else if ( obj == itemConfGraf 
			|| obj == itemPopConfGCons1
			|| obj == itemPopConfGCons2 ) {
			
			// cria e exibe o diálogo de configuração dos gráficos
			new DialogoGraficoConsumidor( this, fG, 
				"Gráficos dos Consumidores", true );
			
		} else if ( obj == itemConfGrafEst 
			|| obj == itemConfGEst ) {
			
			// cria e exibe o diálogo de configuração 
			// dos gráficos de estoque
			new DialogoGraficoEstoque( this, pGrafEst,
				"Gráfico de Estoque", true );
			
		} else if ( obj == itemConfCons1
			|| obj == itemPopConfCons1 ) {
			
			// cria e exibe o diálogo de configuração do consumidor 1
			new DialogoConsumidor( this, "Consumidor 1", true, confC1 );
			
		} else if ( obj == itemConfCons2
			|| obj == itemPopConfCons2 ) {
			
			// cria e exibe o diálogo de configuração do consumidor 2
			new DialogoConsumidor( this, "Consumidor 2", true, confC2 );
			
		} else if ( obj == itemComoUsarAjuda ) {
			
			new FrameHelp();
			
		} else if ( obj == itemSobreAjuda ) {
			
			// exibe diálogo sobre
			Mensagens.mensagemSobre();
			
		} else if ( obj == itemSalvarGEst ) {
			
			Utilidades.salvaImagemPainel( pGrafEst.getBufferedImage() );
			
		} else if ( obj == itemImprimirGEst ) {
			
			Utilidades.imprimePainel( pGrafEst );
			
		} else if ( obj == itemLimparGEst ) {
			
			int opc = Mensagens.mensagemOpcaoSimNao( 
				"Deseja mesmo limpar o gráfico?", "Limpar" );
			
			switch ( opc ) {
				
				case 0:
					pGrafEst.limpaCoords();
					pGrafEst.repaint();
					
			}
			
		}
		
	} // fim do método actionPerformed
	
	// inicia a simulacao
	private void inicia() {
		
		// desabilita a interface
		switchInterface();
		
		// reseta o tamanho do gráfico de estoques
		pGrafEst.setLargura( 400 );
		pGrafEst.setAltura( 122 );
		resetScrollPaneGrafEst();
		
		// reseta o tamando dos gráficos dos consumidores
		fG.getPainelGrafico1().setLargura( 600 );
		fG.resetScrollPaneGrafCons( 1 );
		fG.getPainelGrafico2().setLargura( 600 );
		fG.resetScrollPaneGrafCons( 2 );
		
		// "joga" o foco para o botao finalizar
		bFim.requestFocus();
		
		// cria as threads que serão executadas, e inicia a execução
		try {
			
			geraThreads();
			
		} catch ( NegativeNumberException exc ) {
			
			Mensagens.mensagemNumeroNegativo();
				
			// habilita a interface
			switchInterface();
		
		} catch ( NumberFormatException exc ) {
			
			Mensagens.mensagemFormatoIncorreto();
			
			// habilita a interface
			switchInterface();
				
		}
			
	}
	
	// finaliza a simulação
	private void finaliza() {

		// finaliza as threads
		mataThreads();
		
		// recebe a tabela do FrameTabela
		tabela = fT.getTabela();
		
		// aguarda até que todas as threads estejam mortas
		while ( !threadExecutor.isTerminated() ) {
		}
			
		// verifica quantidade de cenários	
		if ( tabela.getCenario() <= ( int ) Constantes.MAX_CEN.getValor() ) {
			
			// preenche uma nova linha na tabela
			preencheLinhaTabela();
			
			// incrementa o cenário
			tabela.setCenario( tabela.getCenario() + 1 );
			
		} else {
			
			Mensagens.mensagemMaximoCenarios();
			
		}		
		
		// reseta os estados
		monitor.resetEstado();
		
		// habilita a interface
		switchInterface();
		
		// "joga" o foco para o primeiro campo
		fLtPes.requestFocus();
		
	}
		
	// gera as threads do Simulador
	private void geraThreads() 
		throws NegativeNumberException  {
		
		// permite a execução das threads
		monitor.allow();
		
		// instancia os armazéns
		aPes = new ArmazemDados();
		aAss = new ArmazemDados();
		aPA = new ArmazemDados();
		aCon1 = new ArmazemDados();
		aCon2 = new ArmazemDados();
		
		// instancia cada agente do Simulador
		// estão juntos aqui pois pode haver algum
		// erro na construção como parâmteros inválidos
		// assim nenhuma das threads é iniciada antes
		// que todas se encontrem num estado consistente
		
		// fornecedor de pes				
		agFP = new FornecedorPe( 
			"FornecedorPe", 					// nome da thread
			Integer.parseInt(					// lead time
				fLtPes.getText() ),
			Integer.parseInt(					// lote
				fLPes.getText() ),
			aPes,								// armazém de dados
			monitor 							// monitor de threads
		);
		
		// fornecedor de assentos
		agFA = new FornecedorAssento( 
			"FornecedorAssento", 				// nome da thread
			Integer.parseInt(					// lead time
				fLtAss.getText() ),
			Integer.parseInt(					// lote
				fLAss.getText() ),
			aAss,								// armazém de dados
			monitor								// monitor de threads
		);
      
		// produtor de bancos
		agP = new Produtor( 
			"Produtor", 						// nome da thread
			Integer.parseInt(					// lead time
				fLtPA.getText() ),
			Integer.parseInt(					// lote
				fLPA.getText() ),
			aPes,								// armazém de pes
			aAss,								// armazém de assentos
			aPA,								// armazém de PA
			monitor								// monitor de threads
		);
		
		// consumidor 1
		agCon1 = new Consumidor( 
			"Consumidor1",		// nome da thread
			1,					// número do consumidor
			aPA,				// armazem de PA
			aCon1,				// armazem consumidor 1
			monitor,			// monitor de threads
			confC1				// configurador do consumidor
		); 
		
		// consumidor 2
		agCon2 = new Consumidor( 
			"Consumidor2",		// nome da thread
			2,					// número do consumidor
			aPA,				// armazem de PA
			aCon2,				// armazem consumidor 2
			monitor,			// monitor de threads
			confC2				// configurador do consumidor
		);
	
		// cria o gerente de passo
		// sempre segue a unidade de simulação como 1
		gerentePasso = new GerenteDePasso( 
			Utilidades.converteTempo( 
				ArmazemEstados.getTempoPasso() ) );
		
		// cria ExecutorService para gerenciar threads
		threadExecutor = Executors.newFixedThreadPool( 1 );
		
		// inicia a execução de todas as threads criadas
		threadExecutor.execute( gerentePasso );
		
	} // fim do método geraThreads
	
	// faz com q o ThreadKiller não permita a execução das Threads
	private void mataThreads() {
		
		// sinaliza o fim das threads
		monitor.kill();
		
		// envia mensagem de finalização para as threads
		threadExecutor.shutdownNow();
		
	}
	
	// preenche uma linha na tabela baseado no cenário
	private void preencheLinhaTabela() {
		
		tabela.setValueAt( Integer.toString( 
			tabela.getCenario() ), tabela.getCenario() - 1, 0 );
			
		tabela.setValueAt( fLtPes.getText() 
			+ " " + ArmazemEstados.getUnidadePasso(), 
			tabela.getCenario() - 1, 1 );
		tabela.setValueAt( fLPes.getText(), tabela.getCenario() - 1, 2 );
			
		tabela.setValueAt( fLtAss.getText() 
			+ " " + ArmazemEstados.getUnidadePasso(), 
			tabela.getCenario() - 1, 3 );
		tabela.setValueAt( fLAss.getText(), tabela.getCenario() - 1, 4 );
			
		tabela.setValueAt( fLtPA.getText() 
			+ " " + ArmazemEstados.getUnidadePasso(),
			tabela.getCenario() - 1, 5 );
		tabela.setValueAt( fLPA.getText(), tabela.getCenario() - 1, 6 );
			
		tabela.setValueAt( Integer.toString( 
			agCon1.getEstoque() ), tabela.getCenario() - 1, 7 );
		tabela.setValueAt( agCon1.getStringNivelAt(),
			tabela.getCenario() - 1, 8 );
		tabela.setValueAt( Integer.toString( 
			agCon2.getEstoque() ), tabela.getCenario() - 1, 9 );
		tabela.setValueAt( agCon2.getStringNivelAt(),
			tabela.getCenario() - 1, 10 );
			
	} // fim do método preencheLinhaTabela
	
	// altera o estado da interface
	private void switchInterface() {
		
		bIni.setEnabled( !bIni.isEnabled() );
		bFim.setEnabled( !bFim.isEnabled() );
		
		fLtPes.setEditable( !fLtPes.isEditable() );
		fLPes.setEditable( !fLPes.isEditable() );
		fLtAss.setEditable( !fLtAss.isEditable() );
		fLAss.setEditable( !fLAss.isEditable() );
		fLtPA.setEditable( !fLtPA.isEditable() );
		fLPA.setEditable( !fLPA.isEditable() );
		
		menuConf.setEnabled( !menuConf.isEnabled() );
		
		itemPopConfCons1.setEnabled( !itemPopConfCons1.isEnabled() );
		itemPopConfGCons1.setEnabled( !itemPopConfGCons1.isEnabled() );
		itemPopConfCons2.setEnabled( !itemPopConfCons2.isEnabled() );
		itemPopConfGCons2.setEnabled( !itemPopConfGCons2.isEnabled() );
		itemSalvarGEst.setEnabled( !itemSalvarGEst.isEnabled() );
		itemImprimirGEst.setEnabled( !itemImprimirGEst.isEnabled() );
		itemLimparGEst.setEnabled( !itemLimparGEst.isEnabled() );
		itemConfGEst.setEnabled( !itemConfGEst.isEnabled() );
		itemPopConfTempo.setEnabled( !itemPopConfTempo.isEnabled() );
		
		fG.switchPopupMenu();
			
	}
	
	// atualiza o painel de status
	public void atualizaStatus() {
		
		labelTempoPasso.setText( 
			"Tempo da unidade de simulação em segundos: "
			+ ArmazemEstados.getTempoPasso() + "  " );
			
		labelUnidadePasso.setText( "Unidade do passo da simulação: " 
			+ ArmazemEstados.getUnidadePasso() );
		
	}
	
	// repinta os gráficos dos consumidores
	public void repintaGrafCons() {
		
		fG.getPainelGrafico1().repaint();
		fG.getPainelGrafico2().repaint();
		
	}
	
	// repinta o gráfico de estoques
	// e suas colorações
	public void repintaGrafEstoque() {
		
		pGrafEst.repaint();
		
		pCorGrafPes.setBackground( pGrafEst.getCorGrafPes() );
		pCorGrafAss.setBackground( pGrafEst.getCorGrafAss() );
		pCorGrafPA.setBackground( pGrafEst.getCorGrafPA() );
		
	}
	
	// reseta o scrollPane para repintar o gráfico de novo tamanho
	public void resetScrollPaneGrafEst() {
		
		scrollPaneGrafEst.setViewportView( pGrafEst );
		
	}
	
	// classe interna privada p/ gerenciamento de passo
	// e atualização da interface gráfica
	private class GerenteDePasso implements Runnable {
		
		private int sleep;		// tempo de dormencia
		private int cont;		// contador de tempo
		
		// estoques
		private int ePes,
					eAss,
					ePA;
		
		public GerenteDePasso( int s ) {
			
			setSleep( s );
			
			// limpa os gráficos
			fG.getPainelGrafico1().limpaCoords();
			fG.getPainelGrafico2().limpaCoords();
			pGrafEst.limpaCoords();
			
			// repinta os gráficos
			repintaGrafCons();
			repintaGrafEstoque();
			
		}
		
		// atualiza os campos de texto e gráficos
		public void run() {
			
			// se as threads estão executando
			while ( monitor.isAllowed() ) {
				
				// executa os agentes
				agFP.executa();
				agFA.executa();
				agP.executa();
				
				if ( gerador.nextInt( 2 ) == 1 ) {
					agCon1.executa();
					agCon2.executa();
				} else {
					agCon2.executa();
					agCon1.executa();
				}
					
				atualizaInterface();
				adicionaCoordenadas( cont );
				cont++;
				
				// repinta o gráfico de estoques
				repintaGrafEstoque();
				
				// verifica plotagem automatica
				if ( ArmazemEstados.isTempoRealGrafCons() ) {
					
					// repinta os gráficos
					repintaGrafCons();
					
				}
					
				if ( monitor.isEstadoTrue() )
					monitor.resetEstado();
					
				try {
					Thread.sleep( getSleep() );
				} catch ( InterruptedException exc ) {
				}
				
			}

			// garante q a interface será limpa
			limpaInterface();
			
			// repinta os gráficos
			repintaGrafCons();
			repintaGrafEstoque();
				
		}
		
		private void atualizaInterface() {
			
			// adquire os estoques
			ePes = agFP.getEstoque();
			eAss = agFA.getEstoque();
			ePA = agP.getEstoque();
			
			fEstPes.setText( 
				Integer.toString( ePes ) );
			fEstAss.setText( 
				Integer.toString( eAss ) );
			fEstPA.setText( 
				Integer.toString( ePA ) );
			fC1Est.setText( 
				Integer.toString( agCon1.getEstoque() ) );
			fC1NAt.setText( agCon1.getStringNivelAt() );
			fC2Est.setText( 
				Integer.toString( agCon2.getEstoque() ) );
			fC2NAt.setText( agCon2.getStringNivelAt() );
			
		}
		
		private void limpaInterface() {
			
			// limpa campos dos estoques e níveis de atendimento
			fEstPes.setText( "" );			
			fEstAss.setText( "" ); 			
			fEstPA.setText( "" );		
			fC1Est.setText( "" );		
			fC1NAt.setText( "" );
			fC2Est.setText( "" );
			fC2NAt.setText( "" );
			
		}
		
		private void adicionaCoordenadas( int tempo ) {
			
			fG.getPainelGrafico1().addCoord( tempo, agCon1.getNivelAt() );
			fG.getPainelGrafico2().addCoord( tempo, agCon2.getNivelAt() );
			pGrafEst.addCoord( tempo, ePes, eAss, ePA );
			
		}
		
		public void setSleep( int s ) {
			
			if ( s < 0 )
				sleep = 1;
			else
				sleep = s;
			
		}
		
		public int getSleep() {
			
			return sleep;
			
		}
		
	} // fim da classe interna privada GerenteDePasso
	
} // fim da classe FramePrincipal