package br.com.davidbuzatto.simuladordeestoques;

/*
 * @(#)Mensagens.java
 *
 * Classe que define todas as mensagens do simulador.
 *
 * @author David Buzatto
 */
 
 
import javax.swing.*;


public final class Mensagens {
	
	// exibe a mensagem de sair
	public static int mensagemOpcaoSimNao( String mensagem, String titulo) {
		
		return JOptionPane.showConfirmDialog(
		 	null, mensagem,
			titulo, JOptionPane.YES_NO_OPTION );
		
	}
	
	// exibe a mensagem de erro por numero negativo
	public static void mensagemNumeroNegativo() {
		
		JOptionPane.showMessageDialog( null,
			"Existe algum campo com valor incorreto!\n" 
			+ "Não é permitido o uso de valores negativos!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe a mensagem de números fora de ordem
	public static void mensagemNumerosForaDeOrdem() {
		
		JOptionPane.showMessageDialog( null,
			"Existe algum campo com valor incorreto!\n" 
			+ "Um valor mínimo pode assumir um valor\n"
			+ "menor ou igual ao valor máximo!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe a mensagem de número com formato incorreto
	public static void mensagemFormatoIncorreto() {
		
		JOptionPane.showMessageDialog( null,
			"Existe algum campo com valor incorreto!\n" 
			+ "Os valores permitidos são apenas números inteiros!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe a mensagem de número com formato incorreto
	public static void mensagemFormatoIncorretoTempo() {
		
		JOptionPane.showMessageDialog( null,
			"Existe algum campo com valor incorreto!\n" 
			+ "Os valores permitidos são apenas números reais!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe a mensagem de número de cenários p/ a tabela estão no máximo
	public static void mensagemMaximoCenarios() {
		
		JOptionPane.showMessageDialog( null,
			"A tabela suporta no máximo " 
			+ ( int ) Constantes.MAX_CEN.getValor() + " cenários,\n"
			+ "limpe a tabela para adicionar mais cenários!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem de sobre
	public static void mensagemSobre() {
		
		String sobre = "Simulador de Estoques\n\n"
			+ "    Este simulador foi implementado com o intúito de ser apresentado na\n"
			+ "        Terceira Semana de Sistemas de Informação da UNIFEOB.\n\n"
			+ "    A simulação é feita baseando-se em dois produtores, um\n"
			+ "         consumidor/produtor e dois consumidores.\n\n"
			+ "    É possível a configuração de várias opções do simulador.\n\n"
			+ "    O Simulador foi desenvolvido por David Buzatto, adicionando a ele\n"
			+ "        toda a interface gráfica com o usuário, modelagem do modelo\n"
			+ "        de multithreading, configurações, desenho dos gráficos e\n"
			+ "        suporte a: impressão, gravação e recuperação de informações\n"
			+ "        obtidas.\n\n"
			+ "    Orientador: Prof. Fábio G. R. Murback.\n"
			+ "    Conceito: Fábio G. R. Murback.\n"
			+ "    Desenvolvimento: David Buzatto, aluno do 2º Ano de Sistemas de\n"
			+ "    Informação - UNIFEOB.\n\n"
			+ "    Início da Implementação do Simulador: 15/06/2005\n"
			+ "    Fim da implementação do Simulador: 27/10/2005\n"
			+ "    Versão: " + Constantes.VERSAO.getValor();
			
		JOptionPane.showMessageDialog( null,
			sobre, "Sobre...", JOptionPane.INFORMATION_MESSAGE );
		
	}
	
	// exibe mensagem de recurso não implementado
	public static void mensagemNaoImplementado() {
		
		JOptionPane.showMessageDialog( null,
			"Ainda não implementado", "Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem de impressoa não instalada
	public static void mensagemErroImpressora() {
		
		JOptionPane.showMessageDialog( null,
			"Verifique se existe alguma\n"
			+ "impressoa instalada em seu sistema"
			+ "ou se está instalada e funcionando corretamente!",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem de nome de arquivo inválido
	public static void mensagemNomeArquivoInvalido() {
		
		JOptionPane.showMessageDialog( null, "Nome de arquivo inválido",
			"Erro", JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem de erro ao tentar criar arquivo
	public static void mensagemErroCriarArquivo() {
		
		JOptionPane.showMessageDialog( null,
			"Erro ao criar arquivo.", "Erro",
			JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem ao tentar ler uma classe de versão incorreta
	public static void mensagemErroVersao() {
		
		JOptionPane.showMessageDialog( null,
			"Este arquivo é inválido ou \n" + 
			"de uma versão anterior do simulador.",
			"Erro",
			JOptionPane.ERROR_MESSAGE );
		
	}
	
	// exibe mensagem com as legendas da tabela
	public static void mensagemExibeLegendas() {
		
		String legendas = 
			"Cenário: Número do experimento\n"
			+ "LT: Lead Time\n"
			+ "Lote: Lote de Produção\n"
			+ "Est.: Estoque\n"
			+ "NA: Nível de Atendimento\n"
			+ "Pés: Fornecedor de Pés\n"
			+ "Ass: Fornecedor de Assentos\n"
			+ "C1 e C2: Consumidores 1 e 2\n";
			
		JOptionPane.showMessageDialog( null,
			legendas, "Legendas", JOptionPane.INFORMATION_MESSAGE );
		
	}
	
}