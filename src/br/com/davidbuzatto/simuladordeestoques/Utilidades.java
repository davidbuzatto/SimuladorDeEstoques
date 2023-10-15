package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Utilidades.java
 *
 * Classe implementa métodos utilitários estáticos.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.print.*;

import java.io.*;

import java.text.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;


public class Utilidades {
	
	// salva a representação gráfica de um painel
	public static void salvaImagemPainel( BufferedImage img ) {
			
		// cria o diálogo de escolha de arquivos
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle( "Salvar Gráfico em Forma de Imagem" );
		
		// seta propriedades do diálogo
		chooser.setApproveButtonText( "Salvar" );
		chooser.setApproveButtonMnemonic( 'S' );
		chooser.setApproveButtonToolTipText( "Salvar" );
		
		// seta diretório corrente
		chooser.setCurrentDirectory( new File( "." ) );
		
		// seta o filtro de arquivos, passando um FileFilter
		chooser.setFileFilter(
			
			new FileFilter() {
				
				public boolean accept( File f ) {
					
					return f.getName().toLowerCase().
								endsWith( ".bmp" ) ||
						   f.getName().toLowerCase().
						   		endsWith( ".jpg" ) ||
						   f.getName().toLowerCase().
								endsWith( ".png" ) ||
						   f.isDirectory();
						   
				}

				public String getDescription() {
					
					return "Arquivos de Imagem (*.bmp;*.jpg;*.jpeg;*.png)";
					
				}
				
			}
			
		);
		
		// mostra o diálogo de salvar
		int r = chooser.showSaveDialog( null );

		if ( r == JFileChooser.APPROVE_OPTION ) {

			// retorna o nome do arquivo selecionado
			// ou o nome do arquivo digitado em File Name
			// o parêmtro de File é p/ forçar a colocar a extensão
			// automaticamente
			File arquivo = chooser.getSelectedFile();

			if ( arquivo == null || arquivo.getName().equals("") )
				Mensagens.mensagemNomeArquivoInvalido();

			else {
				
				try {
					
					String nome = arquivo.toString();
					int n = nome.lastIndexOf( "." );
					String ext = "";
					
					// testa se extensão foi colocada
					if ( n < 0 ) {
						
						ext = "jpg";
						arquivo = new File( arquivo.toString() + ".jpg" );
						
					} else {
						
						ext = nome.substring( n + 1, nome.length() );
						
					}
					
					// testa extensão válida
					if ( !ext.equals( "bmp" ) &&
						 !ext.equals( "jpg" ) &&
						 !ext.equals( "jpeg" ) &&
						 !ext.equals( "png" ) ) {
						
						ext = "jpg";
						arquivo = new File( arquivo.toString() + ".jpg" );
						
					}
					
					// passa o bufferedImage, a extensão e o arquivo
					ImageIO.write( img, ext, arquivo );							
					
				} catch ( IOException exc ) {
					
					Mensagens.mensagemErroCriarArquivo();
						
				}

			}
			
		}
		
	}
	
	// imprime a representação de um PainelImprimível
	public static void imprimePainel( PainelImprimivel painel ) {
		
		// Passo 1: iniciando as configurações iniciais da impressão
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		
		// configurando a página para que nao seja necessário o 
		// aparecimento do diálogo de página
		aset.add( OrientationRequested.PORTRAIT );
		aset.add( new Copies( 1 ) );
		aset.add( new JobName( "Gráfico", null ) );

		// Passo 2: Obtendo um print job
		PrinterJob pj = PrinterJob.getPrinterJob();
		// setando o objeto a ser impresso
		pj.setPrintable( painel );
		
		// Passo 3: Buscando print services
		PrintService []services = PrinterJob.lookupPrintServices();
		
		if ( services.length > 0 ) {

			try {
				
				// setando o print service
				pj.setPrintService( services[ 0 ] ); 
				
				// Passo 2: passando as configurações p/ o diálogo 
				// de página. Seria necessário esta instrução se 
				// as opções desejadas não estivessem sido condiguradas
				// no passo 1
				// pj.pageDialog( aset );
				
				// Passo 2: passando as configurações p/ 
				// o diálogo de impressão
				if ( pj.printDialog( aset ) ) {
					
					// Passo 4: atualizando as configurações passadas
					// pelo usuário através dos diálogos 
					
					// Passo 5: passando as configuraçoes finais 
					// para a requisição de impressão
					pj.print( aset );
					
				}
				
			} catch ( PrinterException exc ) {
				
				Mensagens.mensagemErroImpressora();
				
			}
			
		}

	}
	
	// faz a conversão de tempo 
	public static int converteTempo( double valor ) {
		
		return ( int ) ( valor * 1000 );
		
	}
	
	// organiza os focos dos componentes de texto
	// seleciona texto dos campos de texto ao
	// receberem o foco e registra ouvinte
	// de ação
	public static void resolveFocus( JTextField... c ) {
		
		// final p/ poder ser acessado dentro da classe interna
		final JTextField[] comp = c;
		
		for ( int e = 0; e < comp.length; e++ ) {
			
			// final p/ poder ser acessado dentro da classe interna
			final int i = e;
			
			// quanto teclar enter
			comp[ i ].addActionListener(
				new ActionListener() {
					
					// troca o foco dos componentes
					public void actionPerformed( ActionEvent e ) {
						if ( i < comp.length - 1 )
							comp[ i + 1 ].requestFocus();
						else
							comp[ 0 ].requestFocus();
					}
					
				}
			);
			
			// quando ganhar/perder foco
			comp[ i ].addFocusListener(
				new FocusListener() {
					
					// quando ganhar o foco, selecionar o texto
					public void focusGained( FocusEvent e ) {
						String t = comp[ i ].getText();
						if ( t != "" ) {
							comp[ i ].setSelectionStart( 0 );
							comp[ i ].setSelectionEnd( t.length() );
						}
					}
					
					public void focusLost( FocusEvent e ) {
					}
					
				}
			);
			
		}
		
	}
	
	public static void centralizaComponenteNaTela( 
		Component c, int deslocamentoVertical ) {
		
		// armazena as dimensões da tela
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		// coloca no centro da tela
		int cX = ( int ) ( ( d.getWidth() / 2 ) - ( c.getWidth() / 2 ) );
		int cY = ( int ) ( ( d.getHeight() / 2 ) - ( c.getHeight() / 2 ) );
		
		c.setLocation( cX, cY + deslocamentoVertical );
		
	}
	
}