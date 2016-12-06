package br.com.ambientinformatica.sati.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.util.Relatorio;

public class RelatorioTeste {

	public static void main(String[] args) {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			Cliente Cliente1 = new Cliente();
			Cliente1.setNomecliente("RD Tecnologia");
			Cliente1.setRazaoSocial("Razão Social 01");
			Cliente1.setTelefone("3283-1110");
			Cliente1.setCelular("98888-5566");
			Cliente1.setEmail("teste1@email.com");

			Cliente Cliente2 = new Cliente();
			Cliente2.setNomecliente("RD Tecnologia");
			Cliente2.setRazaoSocial("Razão Social 02");
			Cliente2.setTelefone("3283-1110");
			Cliente2.setCelular("98888-5566");
			Cliente2.setEmail("teste2@email.com");

			Cliente Cliente3 = new Cliente();
			Cliente3.setNomecliente("RD Tecnologia");
			Cliente3.setRazaoSocial("Razão Social 03");
			Cliente3.setTelefone("3283-1110");
			Cliente3.setCelular("98888-5566");
			Cliente3.setEmail("teste3@email.com");

			clientes.add(Cliente1);
			clientes.add(Cliente2);
			clientes.add(Cliente3);

			Relatorio relatorio = new Relatorio();
			relatorio.imprimirRelatorioClientes(clientes);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
