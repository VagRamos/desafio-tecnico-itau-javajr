package br.com.vagramos.transacao_api.business.services;

import br.com.vagramos.transacao_api.controller.dtos.TransacaoRequestDTO;
import br.com.vagramos.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void cadastrarTransacao(TransacaoRequestDTO dto){

        log.info("Iniciado o processamento de gravar transações." + dto);

        if(dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data e hora maiores que a data atual.");
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atuais");
        }
        if (dto.valor() < 0 ){
            log.error("Valor não pode ser menor que zero.");
            throw new UnprocessableEntity("Valor não pode ser menor que zero.");
        }

        listaTransacoes.add(dto);
        log.info("Transação cadastrada com sucesso.");
    }

    public void limparTransacoes(){
        log.info("Iniciado processo para deletar transações.");
        listaTransacoes.clear();
        log.info("Transações deletadas com sucesso.");
    }

    public List<TransacaoRequestDTO> buscarTransaçoes(Integer intervaloBusca){
        log.info("Iniciadas as buscas de transações por tempo" + intervaloBusca);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBusca);
        log.info("Retorno de transações realizado com sucesso");
        return listaTransacoes.stream().filter(transacao -> transacao.dataHora().isAfter(dataHoraIntervalo)).toList();
    }
}
