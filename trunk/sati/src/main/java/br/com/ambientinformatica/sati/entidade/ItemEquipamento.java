package br.com.ambientinformatica.sati.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class ItemEquipamento {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "geradorItemEquipamento")
	@SequenceGenerator(name = "geradorItemEquipamento", sequenceName = "gerador_item_equipamento", allocationSize = 1, initialValue = 1)
	private int id;

	@ManyToOne
	private Equipamento equipamento;

	private Integer quantidade = 1;

	@Transient
	public BigDecimal getValorTotal() {
		return equipamento.getValor().multiply(BigDecimal.valueOf(quantidade));
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public int getId() {
		return id;
	}

	// EQUALS E HASCODE
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((equipamento == null) ? 0 : equipamento.hashCode());
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
		ItemEquipamento other = (ItemEquipamento) obj;
		if (equipamento == null) {
			if (other.equipamento != null)
				return false;
		} else if (!equipamento.equals(other.equipamento))
			return false;
		return true;
	}

}
