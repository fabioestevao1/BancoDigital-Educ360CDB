package br.com.bootcamp.bancodigital.dto.request;

import java.math.BigDecimal;

public record TransacaoRequest(
        Long idContaOrigem,
        Long idContaDestino,
        BigDecimal valor
) {
}
