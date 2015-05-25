package br.com.ambientinformatica.sati.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface OrdemServicoDao extends Persistencia<OrdemServico>{
   
   public OrdemServico consultarPorId(Long idOs) throws SatiException;
   
   public List<OrdemServico> listarPorDataEmissaoSemTecnico( long id,  Date dataHoraInicio, Date dataHoraFim, EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException;
   
   public List<OrdemServico> listarPorDataEmissao( long id,  Date dataHoraInicio, Date dataHoraFim, EnumEstadoOrdemServico estadoOsSelecionado, int idTecnico) throws SatiException;

   public List<OrdemServico> listarPorOsAtendimento(EnumEstadoOrdemServico estadoOsSelecionado,int idTecnico) throws SatiException;
   
   public List<OrdemServico> listarPorOsAtendimentoAdmin(EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException;
}
