package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class OrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ordemServico_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ordemServico_seq", sequenceName = "ordemServico_seq", allocationSize = 1, initialValue = 1)
	private long id;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Tecnico tecnico;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEmissao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFechamento;

	private String descricaoProblema;

	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ordemservicoid")
	private Set<ItemServico> itensServicos = new HashSet<ItemServico>();

	@OneToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ordemservicoid")
	private Set<ItemEquipamento> itensEquipamentos = new HashSet<ItemEquipamento>();

	@Enumerated(EnumType.STRING)
	private EnumEstadoOrdemServico estado;

	// /CONTRUTOR
	public OrdemServico() {
		super();
		cliente = new Cliente();
	}

	// //////METODOS

	// ADD ITEM DE SERVIÇO
	public void addItemServico(ItemServico item) {
		if (!itensServicos.contains(item)) {
			itensServicos.add(item);
		} else {
			itensServicos.remove(item);
			itensServicos.add(item);
		}
	}

	// SETA A LISTA DE EQUIPAMENTOS
	public Set<ItemEquipamento> getItensEquipamentos() {
		return itensEquipamentos;
	}

	// ADD ITEM DE EQUIPAMENTO
	public void addItemEquipamento(ItemEquipamento itemequip) {
		if (!itensEquipamentos.contains(itemequip)) {
			itensEquipamentos.add(itemequip);
		} else {
			itensEquipamentos.remove(itemequip);
			itensEquipamentos.add(itemequip);
		}
	}

	// SETA A LISTA DE SERVIÇOS
	public Set<ItemServico> getItensServicos() {
		return itensServicos;
	}

	// REMOVE UM ITEN SELECIONADO DA LISTA DE EQUIPAMENTOS
	public void removeItemEquipamento(ItemEquipamento item) {
		itensEquipamentos.remove(item);
	}

	// REMOVE UM ITEM SELECIONADO DA LISTA DE SERVIÇOS
	public void removeItemServico(ItemServico item) {
		itensServicos.remove(item);
	}

	// LISTA DE ITENS DE SERVICO NA O.S
	public List<ItemServico> getItensServicoList() {
		return new ArrayList<ItemServico>(itensServicos);
	}

	// LISTA DE ITENS DE EQUIPAMENTO NA O.S
	public List<ItemEquipamento> getItensEquipamentoList() {
		return new ArrayList<ItemEquipamento>(itensEquipamentos);
	}

	// CANCELA A ORDEM DE SERVIÇO
	public void cancelarOs() {
		estado = EnumEstadoOrdemServico.CANCELADA;
	}

	// DESABILITA A LISTAGEM DE ORDEM CANCELADA
	public boolean isCancelada() {
		return estado == EnumEstadoOrdemServico.CANCELADA;

	}

	// EMITE A OS COM O STATUS ATENDENDO
	public void emitirOs() {
		dataEmissao = new Date(System.currentTimeMillis());
		estado = EnumEstadoOrdemServico.ATENDENDO;
	}

	// PERMITE A EDICAO DA O.S QUANDO EM ATENDIMENTO
	public boolean isEditavel() {
		return estado == EnumEstadoOrdemServico.ATENDENDO;
	}

	// PERMITE A IMPRESSAO QUANDO CONCLUIDA
	public boolean isImprimivel() {
		return estado == EnumEstadoOrdemServico.ATENDIDA;
	}

	// Fechar ordem de Serviço
	public void fecharOrdemServico() {
		dataFechamento = new Date(System.currentTimeMillis());
		estado = EnumEstadoOrdemServico.ATENDIDA;
	}

	// DESABILITA A LISGEM DA ORDEM DE SERVIÇO
	public boolean isFechada() {
		return estado == EnumEstadoOrdemServico.ATENDIDA;
	}

	// GTT E STT
	public void setItensEquipamentos(Set<ItemEquipamento> itensEquipamentos) {
		this.itensEquipamentos = itensEquipamentos;
	}

	/*
	 * Equals e HashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * Getters and Setters
	 */
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public long getId() {
		return id;
	}

	// GET ENUM ESTADO ORDEM DE SERVICO
	public EnumEstadoOrdemServico getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoOrdemServico estado) {
		this.estado = estado;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

}
//by Silas A.