/**************************************************
 * Propriedade Intelectual da Ambient Informática 
 * (www.ambientinformatica.com.br). 
 * 
 * PROIBIDA A CÓPIA OU UTILIZAÇÃO POR TERCEIROS SEM
 * PRÉVIA AUTORIZAÇÃO.
 **************************************************
 */
package br.com.ambientinformatica.sati.sati.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.ambientinformatica.sati.sati.entidade.Servico;

@Entity
public class ItemServico {

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "geradorItemServico")
   @SequenceGenerator(name="geradorItemServico", sequenceName = "gerador_item_servico", allocationSize=1, initialValue=1)
   private int id;
   
   @ManyToOne
   private Servico servico;
   
   private Integer quantidade = 1;

   @Transient
   public BigDecimal getValorTotal(){
      return servico.getValor().multiply(BigDecimal.valueOf(quantidade));
   }
   
   public Servico getServico() {
      return servico;
   }
   public void setServico(Servico servico) {
      this.servico = servico;
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
}
