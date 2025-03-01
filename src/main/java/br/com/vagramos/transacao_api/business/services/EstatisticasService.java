package br.com.vagramos.transacao_api.business.services;

import br.com.vagramos.transacao_api.controller.dtos.EstatisticasResponseDTO;
import br.com.vagramos.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    private static final Logger log = LoggerFactory.getLogger(EstatisticasService.class);
    public final TransacaoService transacaoService = new TransacaoService();

    public EstatisticasResponseDTO calcularEstatisticasTransacoes(Integer intervaloBusca){
        log.info("Iniciada a busca de estatísticas de transações pelo período de tempo" + intervaloBusca);
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransaçoes(intervaloBusca);

        if(transacoes.isEmpty()){
            return new EstatisticasResponseDTO(0L,0.0,0.0,0.0,0.0);
        }

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();
        log.info("Estatísticas retornadas com sucesso.");
        return new EstatisticasResponseDTO(
                estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax());
    }
}
