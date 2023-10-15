package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)FrameInicializacao.java
 *
 * Classe que define o frame de inicialização.
 *
 * @author David Buzatto
 */
 
 
import java.awt.*;
 
import javax.swing.*;
 
 
public class FrameInicializacao extends JFrame {
 	
	public FrameInicializacao() {
 		
 		super( "Carregando..." );
 		
 		// declara e instancia um ImageIcon para o ícone
		// do JFrame
		ImageIcon img = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/iconPrin.png" ) );
		// seta o ícone do JFrame
		setIconImage( img.getImage() );
		
		PainelInicializacao pIni = new PainelInicializacao();
		
		add( pIni );
		
	}
 	
}