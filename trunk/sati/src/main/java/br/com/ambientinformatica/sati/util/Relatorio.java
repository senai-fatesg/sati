package br.com.ambientinformatica.sati.util;

import java.util.List;

import br.com.ambientinformatica.sati.entidade.Cliente;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Relatorio {

	/**
	 * Caminho para a pasta onde estão armazenados os arquivos .jrxml
	 */
	private static final String PATH_TO_JASPER_REPORTS_PACKAGE = "./src/main/jasperreports/";
	/**
	 * Caminho para a pasta onde estão armazenados os relatorios em .pdf
	 */
	private static final String PATH_TO_REPORTS_PACKAGE = "./src/main/webapp/reports/";

	/**
	 * Recupera os caminhos para que a classe possa encontrar os relatórios
	 */
	public Relatorio() {
		System.out.println("Gerando Relatório para formato .pdf .....");
	}
	
	/**
	 * Imprime / Gera uma lista de Clientes
	 * @param clientes
	 * @throws Exception
	 */
	public void imprimir(List<Cliente> clientes) throws Exception {
		JasperReport report = JasperCompileManager.compileReport(PATH_TO_JASPER_REPORTS_PACKAGE + "Clientes.jrxml");

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(clientes));

		JasperExportManager.exportReportToPdfFile(print, PATH_TO_REPORTS_PACKAGE + "Relatorio_de_Clientes.pdf");
		
		System.out.println("Relatório gerado com sucesso em " + PATH_TO_REPORTS_PACKAGE);
	}

}
