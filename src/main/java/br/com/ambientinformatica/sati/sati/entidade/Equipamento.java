package br.com.ambientinformatica.sati.sati.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Equipamento {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geradorEquipamento")
	@SequenceGenerator(name = "geradorEquipamento", sequenceName = "gerador_equipamento", allocationSize = 1, initialValue = 1)
	private Integer id;

	@OneToOne
	private Marca Marca;

	private String modelo;

	@Column(precision = 10, scale = 2)
	private BigDecimal valor = BigDecimal.ZERO;

	public Marca getMarca() {
		return Marca;
	}

	public void setMarca(Marca marca) {
		Marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

}
