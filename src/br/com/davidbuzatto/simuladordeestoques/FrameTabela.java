package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FrameTabela.java
 *
 * Classe que define a tabela para exibir os dados.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

import java.io.*;

import java.text.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;


public class FrameTabela extends JFrame
	implements ActionListener {
	
	private int cenario;				// armazena a quantidade de cenários
	
	private String[][] cont;			// armazena o conteúdo da tabela
	
	private String[] colunas;			// armazena as colunas da tabela
	
	private TabelaSimulador tabela;		// tabela de informações
	
	private PainelTitulado p;			// painel da tabela
	
	private JScrollPane scroll;			// scroll pane para a tabela
	
	private JMenuItem itemAbreTab,  	// itens do menu de tabela
					  itemSalvaTab,
					  itemLimpaTab,	
					  itemRetiraTab,
				      itemImprimeTab,
				      itemLegAjuda;		// itens do menu ajuda
	
	// construtor
	public FrameTabela() {
		
		// chama o construtor da superclasse que aceita
		// uma String como parâmetro para setar
		// o título da janela
		super( "Tabela de Informações" );
		
		// seta o layout
		setLayout( new FlowLayout() );
		
		// declara e instancia um ImageIcon para o ícone
		// do JFrame
		ImageIcon img = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconTab.png" ) );
		// seta o ícone do JFrame
		setIconImage( img.getImage() );
		
		// tabela ------
		// populando a tabela
		String[] c = { "Cenário", 
			"LT Pés", "Lote Pés",
			"LT Ass", "Lote Ass",
			"LT PA", "Lote PA",
			"Est. C1", "NA C1",
			"Est. C2", "NA C2" };
		
		colunas = c;
			
		String[][] cont = 
			new String[ ( int ) Constantes.MAX_CEN.getValor() ][ colunas.length ];

		// instanciando a tabela
		tabela = new TabelaSimulador( cont, colunas );
		tabela.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		tabela.doLayout();
		
		p = new PainelTitulado( null, 700, 200 );
		p.setLayout( new BorderLayout() );
		
		scroll = new JScrollPane( tabela );
		p.add( scroll, BorderLayout.CENTER );
		
		add( p );
		
		// menu do JFrame
		JMenuBar menuBar = new JMenuBar();
		
		// menu tabela
		JMenu menuTab = new JMenu( "Tabela" );
		menuTab.setMnemonic( 'T' );
		
		// itens do menu tabela
		// imagens dos itens
		ImageIcon imgAbre = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconAbre.png" ) );
		ImageIcon imgSalva = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconSalva.png" ) );
		ImageIcon imgLimpa = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconLimpa.png" ) );
		ImageIcon imgRetira = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconRetira.png" ) );
		ImageIcon imgImprime = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconImprime.png" ) );
			
		itemAbreTab = new JMenuItem( "Abrir Tabela", imgAbre );
		itemSalvaTab = new JMenuItem( "Salvar Tabela", imgSalva );
		itemLimpaTab = new JMenuItem( "Limpar Tabela", imgLimpa );
		itemRetiraTab = new JMenuItem( "Exclui Último Cenário", imgRetira );
		itemImprimeTab = new JMenuItem( "Imprimir Tabela", imgImprime );

		itemAbreTab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O,
			InputEvent.CTRL_MASK ) );
		itemSalvaTab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S,
			InputEvent.CTRL_MASK ) );
		itemLimpaTab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_C,
			InputEvent.ALT_MASK ) );
		itemRetiraTab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X,
			InputEvent.ALT_MASK ) );
		itemImprimeTab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_P,
			InputEvent.CTRL_MASK ) );
		
		itemAbreTab.setMnemonic( 'A' );
		itemSalvaTab.setMnemonic( 'S' );
		itemLimpaTab.setMnemonic( 'L' );
		itemRetiraTab.setMnemonic( 'E' );
		itemImprimeTab.setMnemonic( 'I' );
		
		itemAbreTab.addActionListener( this );
		itemSalvaTab.addActionListener( this );
		itemLimpaTab.addActionListener( this );
		itemRetiraTab.addActionListener( this );
		itemImprimeTab.addActionListener( this );
		
		menuTab.add( itemAbreTab );
		menuTab.add( itemSalvaTab );
		menuTab.addSeparator();
		menuTab.add( itemLimpaTab );
		menuTab.add( itemRetiraTab );
		menuTab.addSeparator();
		menuTab.add( itemImprimeTab );
		
		menuBar.add( menuTab );
		
		// menu de ajuda
		JMenu menuAjuda = new JMenu( "Ajuda" );
		menuAjuda.setMnemonic( 'A' );
		
		ImageIcon imgLegAjuda = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconAjuda.png" ) );
		
		itemLegAjuda = new JMenuItem( "Legendas", imgLegAjuda );
		itemLegAjuda.addActionListener( this );
		
		itemLegAjuda.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_L,
			InputEvent.CTRL_MASK + InputEvent.ALT_MASK ) );
			
		itemLegAjuda.setMnemonic( 'L' );
		
		menuAjuda.add( itemLegAjuda );
		
		menuBar.add( menuAjuda );
		 
		// seta a barra de menus
		setJMenuBar( menuBar );

	} // fim do construtor
	
	// controla o evento de clique dos botões
	public void actionPerformed( ActionEvent e ) {
		
		try {
			
			if ( e.getSource() == itemSalvaTab ) {
				
				// salva tabela
				salvaTabela();
				
			} else if ( e.getSource() == itemAbreTab ) {
				
				// abre uma tabela
				abreTabela();
				
			} else if ( e.getSource() == itemLimpaTab ) {
				
				int opc = Mensagens.mensagemOpcaoSimNao(
					"Deseja mesmo limpar esta tabela?",
					"Limpar Tabela" );
				
				switch ( opc ) {
					
					case 0:
						limparTabela();
						
				}
				
			} else if ( e.getSource() == itemRetiraTab ) {
				
				int opc = Mensagens.mensagemOpcaoSimNao(
					"Deseja mesmo excluir o último cenário?",
					"Excluir Último Cenário" );
				
				switch ( opc ) {
					
					case 0:
						// limpa última linha da tabela
						limpaLinhaTabela( tabela.getCenario() - 1 );
					
						// decrementa o cenário
						tabela.setCenario( tabela.getCenario() - 1 );
						
				}
				
			} else if ( e.getSource() == itemImprimeTab ) {
				
				try {
					
					tabela.print( JTable.PrintMode.NORMAL,
						new MessageFormat( "Cenários" ),
						new MessageFormat( "Simulador de Estoques" ) );
					return;
					
				} catch ( PrinterException exc ) {
					
					Mensagens.mensagemErroImpressora();
					
				}
				
			} else if ( e.getSource() == itemLegAjuda ) {

				Mensagens.mensagemExibeLegendas();
				
			}
			
		} catch( Exception exc ) { // depuração
			
			Depurador.gerarStackTrace( exc );
			
		}
		
	} // fim do método actionPerformed

	// retorna a tabela atual
	public TabelaSimulador getTabela() {
		
		return tabela;
		
	}
	
	// limpa tabela setando todos os elementos como ""
	private void limparTabela() {
		
		for ( int i = 0; i < ( int ) Constantes.MAX_CEN.getValor(); i++ )
			for ( int j = 0; j < colunas.length; j++ )
				tabela.setValueAt( "", i, j );
				
		tabela.setCenario( 1 );
		
	}
	
	// limpa linha baseada no cenário
	private void limpaLinhaTabela( int c ) {
		
		if ( c > 0 )
			for ( int i = 0; i < colunas.length; i++ )
					tabela.setValueAt( "", c - 1, i );
			
	} // fim do método limpaLinhaTabela
	
	// salva a tabela corrente
	private void salvaTabela() {
		
		// cria o diálogo de escolha de arquivos
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle( "Salvar Tabela" );
		
		// seta propriedades do diálogo
		chooser.setApproveButtonText( "Salvar" );
		chooser.setApproveButtonMnemonic( 'S' );
		chooser.setApproveButtonToolTipText( "Salva a Tabela" );
		
		// seta diretório corrente
		chooser.setCurrentDirectory( new File( "." ) );
		
		// seta o filtro de arquivos, passando um FileFilter
		chooser.setFileFilter(
			
			new FileFilter() {
				
				public boolean accept( File f ) {
					
					return f.getName().toLowerCase().
								endsWith( ".tab" ) ||
						   f.isDirectory();
						   
				}

				public String getDescription() {
					
					return "Arquivos de Tabela (*.tab)";
					
				}
				
			}
			
		);
		
		// mostra o diálogo de salvar
		int r = chooser.showSaveDialog( this );

		if ( r == JFileChooser.APPROVE_OPTION ) {

			// retorna o nome do arquivo selecionado
			// ou o nome do arquivo digitado em File Name
			// o parêmtro de File é p/ forçar a colocar a extensão
			// automaticamente
			File arquivo = new File(
				chooser.getSelectedFile().toString() + 
				".tab" );

			if ( arquivo == null || arquivo.getName().equals("") )
				Mensagens.mensagemNomeArquivoInvalido();

			else {
				
				try {
					
					// cria stream de objeto
					ObjectOutputStream saida =
						new ObjectOutputStream(
							new FileOutputStream( arquivo ) );
							
					// escreve o objeto tabela no arquivo
					saida.writeObject( tabela );
					
					// fecha o stream
					saida.close();
					
				} catch ( IOException exc ) {
					
					Mensagens.mensagemErroCriarArquivo();
						
				}

			}

		}
		
	} // fim do método salvaTabela
	
	// abre uma tabela
	private void abreTabela() {
		
		// cria o diálogo de escolha de arquivos
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle( "Abrir Tabela" );
		
		// seta propriedades do diálogo
		chooser.setApproveButtonText( "Abrir" );
		chooser.setApproveButtonMnemonic( 'A' );
		chooser.setApproveButtonToolTipText( "Abre uma Tabela" );
		
		// seta diretório corrente
		chooser.setCurrentDirectory( new File( "." ) );
		
		// seta o filtro de arquivos, passando um FileFilter
		chooser.setFileFilter(
			
			new FileFilter() {
				
				public boolean accept( File f ) {
					
					return f.getName().toLowerCase().
								endsWith( ".tab" ) ||
						   f.isDirectory();
						   
				}

				public String getDescription() {
					
					return "Arquivos de Tabela (*.tab)";
					
				}
				
			}
			
		);
		
		// mostra o diálogo de abrir
		int r = chooser.showOpenDialog( this );

		if ( r == JFileChooser.APPROVE_OPTION ) {

			// retorna o nome do arquivo selecionado
			// ou o nome do arquivo digitado em File Name
			File arquivo = chooser.getSelectedFile();

			if ( arquivo == null || arquivo.getName().equals("") )
				Mensagens.mensagemNomeArquivoInvalido();

			else {
				
				try {
					
					// cria stream de objeto
					ObjectInputStream entrada =
						new ObjectInputStream(
							new FileInputStream( arquivo ) );
					
					tabela = 
						( TabelaSimulador ) entrada.readObject();
					tabela.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
					tabela.doLayout();

					// remove o scrollbar do painel p
					p.remove( scroll );
					
					// cria novamente o scroll com a nova tabela
					scroll = new JScrollPane( tabela );
					// adiciona no painel
					p.add( scroll, BorderLayout.CENTER );
					
					// torna o frame invisivel depois visível novamente
					// para forçar a repintura dos componentes
					setVisible( false );
					setVisible( true );
					
				} catch ( IOException exc ) {
					
					Mensagens.mensagemErroVersao();
						
				} catch ( ClassNotFoundException exc ) {
					
					//System.out.println( "Erro interno" );
					
				}

			}

		}
		
	} // fim do método abreTabela
	    
} 