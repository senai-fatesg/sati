package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "cliente_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1, initialValue = 1)
	private long id;
	private String nomecliente;
	private String razaoSocial;
	private String telefone;
	private String celular;
	private String email ;
	private String cpfCnpj;
	@OneToOne
	private Endereco endereco;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastramento = new Date();
	
	//Const
	public Cliente() {
		super();
		endereco = new Endereco();
	}

	public Cliente(long id, String razaoSocial, String telefone,
			String celular, String email, String cpfCnpj,
			br.com.ambientinformatica.sati.entidade.Endereco endereco,
			Date dataCadastramento) {
		super();
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.telefone = telefone;
		this.celular = celular;
		this.email = email;
		this.cpfCnpj = cpfCnpj;
		this.endereco = endereco;
		this.dataCadastramento = dataCadastramento;
	}

	
	//GTT E STT
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getNomecliente() {
		return nomecliente;
	}

	public void setNomecliente(String nomecliente) {
		this.nomecliente = nomecliente;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataCadastramento() {
		return dataCadastramento;
	}

	public void setDataCadastramento(Date dataCadastramento) {
		this.dataCadastramento = dataCadastramento;
	}




}
//by Silas A.