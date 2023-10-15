package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelInicializacao.java
 *
 * Painel com a imagem de inicialização.
 *
 * @author David Buzatto
 */
 
 
import java.awt.*;
 
import javax.swing.*;
 
 
public class PainelInicializacao extends PainelSimulador {
	
 	public PainelInicializacao() {
 		
 		super( 452, 254 );
 		
 	}
 	
 	public void paintComponent( Graphics g ) {
 		
 		super.paintComponent( g );
 		
 		// define a imagem de fundo
 		ImageIcon img = new ImageIcon( 
			getClass().getResource( "/br/com/davidbuzatto/simuladordeestoques/imagens/imagemInicializacao.png" ) );
 		
 		// pinta a imagem no componente
 		img.paintIcon( null, g, 0, 0 );
 		
 		g.setFont( new Font( "SansSerif", Font.PLAIN, 10 ) );
 		g.setColor( Color.WHITE );
 		
 		FontMetrics metrica = g.getFontMetrics();
 		String str = "Desenvolvido por David Buzatto - Versão " 
 			+ Constantes.VERSAO.getValor();
 		
 		g.drawString( str,
 			getLargura() - metrica.stringWidth( str ) - 10, 
 			getAltura() - 10 );
 		
 	}
	
}