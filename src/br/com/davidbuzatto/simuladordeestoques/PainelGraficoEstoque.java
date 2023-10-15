package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)PainelGraficoEstoque.java
 *
 * Classe que desenha o gráfico dos 
 * estoques de pés e assentos.
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


public class PainelGraficoEstoque extends PainelImprimivel {

	private Color corGrafPes,
				  corGrafAss,
				  corGrafPA;
	
	private ArrayList< Integer > tempo,
								 pes,
								 ass,
								 pa;
								 
	private String idUnidadeSimulacao;
	
	private boolean antialias,
					drawGrade,
					drawLabel;
	
	private FramePrincipal dono;
	
	// coordenadas de referência p/ o desenhos								 
	private int origemX, 
				fimX,
				origemY,
				fimY;
	
	// construtor	
	public PainelGraficoEstoque( FramePrincipal d, int l, int a ) {
		
		super( l, a );
		
		dono = d;
		
		setCorGrafPes( new Color( 0, 51, 102 ) );
		setCorGrafAss( new Color( 0, 0, 255 ) );
		setCorGrafPA( new Color( 0, 153, 204 ) );
		setDrawLabel( true );
		setDrawGrade( false );
		setAntialiasing( false );
		
		tempo = new ArrayList< Integer >();
		pes = new ArrayList< Integer >();
		ass = new ArrayList< Integer >();
		pa = new ArrayList< Integer >();
		
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
		for ( int i = origemY - 20, q = 100; i > fimY; i -= 20 ) {
			g2d.drawLine( origemX - 5, i, origemX, i );
			g2d.drawString( q + "",
				origemX - metrica.stringWidth( q + "" ) - 6,
				i );
			q += 100;
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
		g2d.drawString( "(quantidade)", 
			- ( getAltura() / 2 ) 
			- ( metrica.stringWidth( "(quantidade)" ) / 2 ) + 12,
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
			t2 = 0,
			p1 = 0,
			p2 = 0,
			a1 = 0,
			a2 = 0,
			pa1 = 0,
			pa2 = 0;
			
		// translada o sistema de coordenadas
		g2d.translate( origemX, origemY );
		
		// seta fonte
		g2d.setFont( new Font( "SansSerif", Font.PLAIN, 10 ) );
		
		// verifica se as listas estão vazias
		if ( !isEmpty() ) {
			
			for ( int i = 1; i < tempo.size(); i++ ) {
				
				t1 = tempo.get( i - 1 ) * 20;
				p1 = pes.get( i - 1 ) / 5;
				a1 = ass.get( i - 1 ) / 5;
				pa1 = pa.get( i - 1 ) / 5;
				
				t2 = tempo.get( i ) * 20;
				p2 = pes.get( i ) / 5;
				a2 = ass.get( i ) / 5;
				pa2 = pa.get( i ) / 5;
				
				g2d.setColor( corGrafPes );
				g2d.drawLine( t1, ( int ) -p1, t2, ( int ) -p2 );
				desenhaPonto( g2d, t1, ( int ) -p1, 
					pes.get( i - 1 ) + "", corGrafPes );
				
				g2d.setColor( corGrafAss );	
				g2d.drawLine( t1, ( int ) -a1, t2, ( int ) -a2 );
				desenhaPonto( g2d, t1, ( int ) -a1, 
					ass.get( i - 1 ) + "", corGrafAss );
				
				g2d.setColor( corGrafPA );
				g2d.drawLine( t1, ( int ) -pa1, t2, ( int ) -pa2 );
				desenhaPonto( g2d, t1, ( int ) -pa1, 
					pa.get( i - 1 ) + "", corGrafPA );
					
			}
			
			// desenha o último ponto
			desenhaPonto( g2d, t2, ( int ) -p2,
				pes.get( pes.size() - 1 ) + "", corGrafPes );
			desenhaPonto( g2d, t2, ( int ) -a2,
				ass.get( ass.size() - 1 ) + "", corGrafAss );
			desenhaPonto( g2d, t2, ( int ) -pa2,
				pa.get( pa.size() - 1 ) + "", corGrafPA );
			
			// verifica estouro na largura	
			if ( t2 > fimX - 80 ) {
				setLargura( getLargura() + 200 );
				repaint();
				dono.resetScrollPaneGrafEst();
			} 
			
			// verifica estouro na altura
			if ( p2 > getAltura() - 40 ) {
				setAltura( getAltura() + 100 );
				repaint();
				dono.resetScrollPaneGrafEst();
			}
			if ( a2 > getAltura() - 40 ) {
				setAltura( getAltura() + 100 );
				repaint();
				dono.resetScrollPaneGrafEst();
			}
			if ( pa2 > getAltura() - 40 ) {
				setAltura( getAltura() + 100 );
				repaint();
				dono.resetScrollPaneGrafEst();
			}
			
		}
		
		// retorna o sistema de coordenadas a posição original
		g2d.translate( origemX, origemY );
		
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
	
	// adiciona coordenadas ao gráfico
	public void addCoord( int t, int p, int a, int prod ) {
		
		tempo.add( t );
		pes.add( p );
		ass.add( a );
		pa.add( prod );
		
	}
	
	// limpa as listas
	public void limpaCoords() {
		
		tempo.clear();
		pes.clear();
		ass.clear();
		pa.clear();
		
	}
	
	// verifica se as listas estão vazias
	private boolean isEmpty() {
		
		return ( tempo.isEmpty()
				 && pes.isEmpty()
				 && ass.isEmpty()
				 && pa.isEmpty() );
				 
	}
	
		
	// configura a cor do gráfico de pés
	public void setCorGrafPes( Color c ) {
		
		corGrafPes = c;
		
	}
	
	// configura a cor do gráfico de assentos
	public void setCorGrafAss( Color c ) {
		
		corGrafAss = c;
		
	}
	
	// configura a cor do gráfico de bancos
	public void setCorGrafPA( Color c ) {
		
		corGrafPA = c;
		
	}
	
	// configura suavização
	public void setAntialiasing( boolean b ) {
		
		antialias = b;
		
	}
	
	// configura desenho da grade
	public void setDrawGrade( boolean b ) {
		
		drawGrade = b;
		
	}
	
	// configura desenho dos valores
	public void setDrawLabel( boolean b ) {
		
		drawLabel = b;
		
	}
	
	// obtém a cor do gráfico de pés
	public Color getCorGrafPes() {
		
		return corGrafPes;
		
	}
	
	// obtém a cor do gráfico de assentos
	public Color getCorGrafAss() {
		
		return corGrafAss;
		
	}
	
	// obtém a cor do gráfico de bancos
	public Color getCorGrafPA() {
		
		return corGrafPA;
		
	}

	// verifica se está ativada/desativada a suavisação
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