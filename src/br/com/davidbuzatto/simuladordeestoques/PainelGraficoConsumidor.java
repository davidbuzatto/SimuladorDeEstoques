package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelGraficoConsumidor.java
 *
 * Classe que desenha o gráfico de nível de 
 * atendimento de um consumidor.
 *
 * @author David Buzatto
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.print.*;

import java.util.*;

import javax.swing.*;


public class PainelGraficoConsumidor extends PainelImprimivel {
	
	private ArrayList< Integer > tempo;
	private ArrayList< Double > nivelAt;
	
	private Color corGraf;
	
	private boolean antialias,
					drawGrade,
					drawLabel;
	
	FrameGraficoConsumidor dono;
	
	private int idCons;	// identificador do consumidor
	
	// coordenadas de referência p/ o desenhos								 
	private int origemX, 
				fimX,
				origemY,
				fimY;
				
	public PainelGraficoConsumidor( FrameGraficoConsumidor d, 
		int l, int a, Color cor, int id ) {
		
		super( l, a );
		setCorGraf( cor );
		setDrawLabel( true );
		setDrawGrade( false );
		setAntialiasing( false );
		
		dono = d;
		
		idCons = id;
		
		tempo = new ArrayList< Integer >();
		nivelAt = new ArrayList< Double >();
		
	}
	
	public void paintComponent( Graphics g ) {
		
		// chama paintComponent ancestral
		super.paintComponent( g );
		
		// cria um novo Graphics2D
		Graphics2D g2d = ( Graphics2D ) g;
			
		if ( isAntialiasing() )
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON );
		else
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF );
		
		// desenha estrutura e o gráfico no painel
		desenha( g2d );
		
	}
	
	private void desenha( Graphics2D g2d ) {
		
		// configura as coordenadas
		origemX = 60;
		fimX = getLargura();
		origemY = getAltura() - 40;
		fimY = 0;
		
		desenhaEstrutura( g2d );
		
		desenhaGrafico( g2d );
		
	}
	
	private void desenhaEstrutura( Graphics2D g2d ) {
		
		// desenha um retângulo branco no fundo do gráfico
		g2d.setColor( Color.WHITE );
		g2d.fillRect( 0, 0, getLargura(), getAltura() );
		
		if ( isDrawingGrade() )
			// desenha grade do gráfico
			desenhaGrade( g2d );
			
		g2d.setColor( Color.BLACK );
		
		// eixo x (tempo)
		g2d.drawLine( origemX - 20, origemY, fimX, origemY );
		
		// eixo y (quantidades)
		g2d.drawLine( origemX, origemY + 20, origemX, fimY);
		
		// seta eixo tempo
		g2d.fillPolygon( new int[] { fimX - 4, fimX, fimX - 4 },
						 new int[] { origemY - 4, origemY, origemY + 4 },
						 3 );
						 
		// seta eixo quantidades
		g2d.fillPolygon( new int[] { origemX - 4, origemX, origemX + 4 },
						 new int[] { fimY + 4, fimY, fimY + 4 },
						 3 );
		
		// seta fonte
		g2d.setFont( new Font( "SansSerif", Font.BOLD, 10 ) );
		
		// obtém a métrica da fonte atual
		FontMetrics metrica = g2d.getFontMetrics();
		
		g2d.setColor( Color.BLUE.darker().darker() );
			
		// marcas e números eixo quantidade
		for ( int i = origemY - 20, q = 10; i > fimY; i -= 20 ) {
			g2d.drawLine( origemX - 5, i, origemX, i );
			g2d.drawString( q + "%",
				origemX - metrica.stringWidth( q + "%" ) - 6,
				i );
			q += 10;
		}
		
		g2d.setColor( Color.RED.darker().darker() );
		
		// marcas eixo tempo
		for ( int i = origemX + 20; i < fimX; i += 20 )
			g2d.drawLine( i, origemY, i, origemY + 5 );
			
		// translada o contexto gráfico
		g2d.translate( origemX, origemY + 10 );
		
		// rotaciona o contexto gráfico p/ desenhar na vertical
		g2d.rotate( Math.toRadians( 90 ) );
		
		for ( int i = 20, t = 1; i < fimX; i += 20) {
			g2d.drawString( t + "", -2, -i );
			t += 1;
		}
		
		// volta o contexto gráfico ao normal
		g2d.rotate( Math.toRadians( -90 ) );
		g2d.translate( -origemX, -( origemY + 10 ) );
		
		// rotaciona o contexto gráfico p/ desenhar na vertical
		g2d.rotate( Math.toRadians( -90 ) );
		
		g2d.setFont( new Font( "SansSerif", Font.BOLD, 12 ) );
		
		g2d.setColor( Color.BLUE.darker().darker() );
		
		metrica = g2d.getFontMetrics();
		g2d.drawString( "(nível de atendimento)", 
			- ( getAltura() / 2 ) 
			- ( metrica.stringWidth( "(nível de atendimento)" ) / 2 ) + 12,
			15 );
		
		// volta o contexto gráfico ao normal
		g2d.rotate( Math.toRadians( 90 ) );
		
		g2d.setColor( Color.RED.darker().darker() );
		
		g2d.drawString( "(tempo em "
			+ ArmazemEstados.getUnidadePasso() + ")", 
			origemX + 20, origemY + 30 );
		
		g2d.setColor( Color.BLACK );
		g2d.drawString( "0", origemX - 10, origemY + 14 );
		
	}
	
	private void desenhaGrafico( Graphics2D g2d ) {
		
		// coordenadas de desenho
		int t1 = 0,
			t2 = 0;
			
		double n1 = 0,
			   n2 = 0;
			
		// translada o sistema de coordenadas
		g2d.translate( origemX, origemY );
		
		// seta fonte
		g2d.setFont( new Font( "SansSerif", Font.PLAIN, 10 ) );
		
		// verifica se as listas estão vazias
		if ( !tempo.isEmpty() && !nivelAt.isEmpty() ) {
		
			for ( int i = 1; i < tempo.size(); i++ ) {
				
				t1 = tempo.get( i - 1 ) * 20;
				n1 = nivelAt.get( i - 1 ) * 2; 
				
				t2 = tempo.get( i ) * 20;
				n2 = nivelAt.get( i ) * 2;
				
				g2d.setColor( corGraf );
				g2d.drawLine( t1, ( int ) -n1, t2, ( int ) -n2 );
				desenhaPonto( g2d, t1, ( int ) -n1, 
					String.format( "%.1f%%", n1 / 2 ), corGraf );

			}
			
			// desenha o último ponto
			desenhaPonto( g2d, t2, ( int ) -n2, 
				String.format( "%.1f%%", n2 / 2 ), corGraf );
			
			// verifica estouro na largura	
			if ( t2 > fimX - 80 ) {
				setLargura( getLargura() + 200 );
				repaint();
				dono.resetScrollPaneGrafCons( idCons );
			} 
			
		}
		
		// retorna o sistema de coordenadas a posição original
		g2d.translate( -60, -206 );
		
	}
	
	// desenha ponto no gráfico
	private void desenhaPonto( Graphics2D g2d, 
		int x, int y, String s, Color c ) {
		
		g2d.setColor( new Color(
			c.getRed(),
			c.getGreen(),
			c.getBlue(),
			110 ) );
		g2d.fillOval( x - 3, y - 3, 6, 6 );
		
		g2d.setColor( c.darker().darker() );
		g2d.drawOval( x - 3, y - 3, 6, 6 );
		
		if ( isDrawingLabel() )
			g2d.drawString( s, x + 7, y + 4 );
		
	}
	
	// desenha a grade do gráfico
	private void desenhaGrade( Graphics2D g2d ) {
		
		g2d.setColor( new Color( 153, 153, 153, 125 ) );
		
		// linhas horizontais
		for ( int i = origemY - 20; i > fimY; i -= 20 ) {
			g2d.drawLine( origemX, i, fimX, i );
		}
		
		// linhas verticais
		for ( int i = origemX + 20; i < fimX; i += 20 ) {
			g2d.drawLine( i, origemY, i, fimY );
		}
		
	}
	
	// método para impressão
	public int print( Graphics g, PageFormat pf, int pageIndex ) 
		throws PrinterException {
		
		if ( pageIndex == 0 ) {
			
			Graphics2D g2d = ( Graphics2D ) g;
			
			g2d.translate( pf.getImageableX(), pf.getImageableY() ); 
			
			// desenha o gráfico no g2d
			desenha( g2d );
			
			return Printable.PAGE_EXISTS;
			
		} else {
			
			return Printable.NO_SUCH_PAGE;
			
		}

	}
	
	// retorna a imagem buferizada do desenho do painél
	public BufferedImage getBufferedImage() {
		
		// cria um novo buffered image
		BufferedImage imagem = new BufferedImage( getLargura(), 
			getAltura(), BufferedImage.TYPE_INT_RGB );
		
		// recebe o contexto gráfico de buffered image
		Graphics2D g2d = imagem.createGraphics();
		
		if ( isAntialiasing() )
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON );
		else
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF );
		
		// desenha estrutura e o gráfico no buffered image
		desenha( g2d );
		
		return imagem;
		
	}
	
	// adiciona o tempo e o nível de atendimento
	public void addCoord( int t, double n ) {
		
		tempo.add( t );
		nivelAt.add( n );
		
	}
	
	// limpa as listas
	public void limpaCoords() {
		
		tempo.clear();
		nivelAt.clear();
		
	}
	
	public void setCorGraf( Color c ) {
		
		corGraf = c;
		
	}
	
	public void setAntialiasing( boolean a ) {
		
		antialias = a;
		
	}
	
	// configura desenho da grade
	public void setDrawGrade( boolean b ) {
		
		drawGrade = b;
		
	}
	
	// configura desenho dos valores
	public void setDrawLabel( boolean b ) {
		
		drawLabel = b;
		
	}
	
	public Color getCorGraf() {
		
		return corGraf;
		
	}
	
	public boolean isAntialiasing() {
		
		return antialias;
		
	}
	
	// verifica se desenho da grade está ativado ou não
	public boolean isDrawingGrade() {
		
		return drawGrade;
		
	}
	
	// verifica se desenho dos valores está ativado ou não
	public boolean isDrawingLabel() {
		
		return drawLabel;
		
	}
	
}