package br.com.ambientinformatica.sati.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Status;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface OrdemServicoDao extends Persistencia<OrdemServico>{
   
   public OrdemServico consultarPorId(Long idOs) throws SatiException;
   
   public List<OrdemServico> listarPorDataEmissaoSemTecnico( long id,  Date dataHoraInicio, Date dataHoraFim, Status estadoOsSelecionado) throws SatiException;
   
   public List<OrdemServico> listarPorDataEmissao( long id,  Date dataHoraInicio, Date dataHoraFim, Status estadoOsSelecionado, int idTecnico) throws SatiException;

   public List<OrdemServico> listarPorOsAtendimento(Status estadoOsSelecionado,int idTecnico) throws SatiException;
   
   public List<OrdemServico> listarPorOsAtendimentoAdmin(Status estadoOsSelecionado) throws SatiException;
   
   public List<OrdemServico> listarPorTecnico(Integer id) throws SatiException;
}
