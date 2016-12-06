package br.com.ambientinformatica.sati.util;

import java.util.List;

import br.com.ambientinformatica.sati.entidade.Tecnico;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioTecnicos {
	
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
	public RelatorioTecnicos() {
		System.out.println("Gerando Relatório para formato .pdf .....");
	}
	
	
	public void imprimir(List<Tecnico> tecnicos) throws Exception {
		JasperReport report = JasperCompileManager.compileReport(PATH_TO_JASPER_REPORTS_PACKAGE + "Tecnicos.jrxml");

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(tecnicos));

		JasperExportManager.exportReportToPdfFile(print, PATH_TO_REPORTS_PACKAGE + "Relatorio_de_Tecnicos.pdf");
		
		System.out.println("Relatório gerado com sucesso em " + PATH_TO_REPORTS_PACKAGE);
	}
}
