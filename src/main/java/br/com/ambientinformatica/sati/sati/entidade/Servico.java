/**************************************************
 * Propriedade Intelectual da Ambient Informática 
 * (www.ambientinformatica.com.br). 
 * 
 * PROIBIDA A CÓPIA OU UTILIZAÇÃO POR TERCEIROS SEM
 * PRÉVIA AUTORIZAÇÃO.
 **************************************************
 */
package br.com.ambientinformatica.sati.sati.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.SequenceGenerator;

@Entity
public class Servico {
   
   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "geradorServico")
   @SequenceGenerator(name="geradorServico", sequenceName = "gerador_servico", allocationSize=1, initialValue=1)
   private Integer id = 0;
   
   private String descricao;
   
   @Column(precision=10,scale=2)
   private BigDecimal valor = BigDecimal.ZERO;
   
   private BigDecimal remuneracaoTecnico = BigDecimal.ZERO;
   
   private int qtdDiasRemuneracaoTecnico = 30;
   
   public String getDescricao() {
      return descricao;
   }
   public void setDescricao(String descricao) {
      this.descricao = descricao;
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
   public BigDecimal getRemuneracaoTecnico() {
      return remuneracaoTecnico;
   }
   public void setRemuneracaoTecnico(BigDecimal remuneracaoTecnico) {
      this.remuneracaoTecnico = remuneracaoTecnico;
   }
   public int getQtdDiasRemuneracaoTecnico() {
      return qtdDiasRemuneracaoTecnico;
   }
   public void setQtdDiasRemuneracaoTecnico(int qtdDiasRemuneracaoTecnico) {
      this.qtdDiasRemuneracaoTecnico = qtdDiasRemuneracaoTecnico;
   }

   
}
