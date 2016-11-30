package br.com.ambientinformatica.sati.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum Status implements IEnum{
   ATENDENDO("Aberta"),
   ATENDIDA("Fechada"),
   CANCELADA("Cancelada");
   
   private String descricao;

   Status(String descricao){
   	this.descricao = descricao;
   }
   
	@Override
   public String getDescricao() {
	   return descricao;
   }

}
