package br.com.ambientinformatica.sati.sati.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(generator = "ordemServico_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ordemServico_seq", sequenceName = "ordemServico_seq", allocationSize = 1, initialValue = 1)
	private long id;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Cliente tecnico;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioAtendimento = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFechamento = new Date();

	private String descricaoProblema;

	@ManyToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE })
	private List<ItemServico> itensServicos = new ArrayList<ItemServico>();

	@ManyToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE })
	private List<ItemEquipamento> itensEquipamentos = new ArrayList<ItemEquipamento>();

	@Enumerated(EnumType.STRING)
	private EnumEstadoOrdemServico estado = EnumEstadoOrdemServico.NOVA;

	public void addItemEquipamento(ItemEquipamento itemEquipamento) {
		itensEquipamentos.add(itemEquipamento);
	}

	public List<ItemEquipamento> getItensEquipamentos() {
		return itensEquipamentos;
	}

	public void addItemServico(ItemServico item) {
		itensServicos.add(item);
	}

	public List<ItemServico> getItensServico() {
		return itensServicos;
	}

	public void atenderOrdemServico() {
		estado = EnumEstadoOrdemServico.ATENDENDO;
	}

	public void fecharOrdemServico() {
		estado = EnumEstadoOrdemServico.ATENDIDA;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getTecnico() {
		return tecnico;
	}

	public void setTecnico(Cliente tecnico) {
		this.tecnico = tecnico;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataInicioAtendimento() {
		return dataInicioAtendimento;
	}

	public void setDataInicioAtendimento(Date dataInicioAtendimento) {
		this.dataInicioAtendimento = dataInicioAtendimento;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
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

	public EnumEstadoOrdemServico getEstado() {
		return estado;
	}

}
