package br.com.bootcamp.bancodigital.service;

import br.com.bootcamp.bancodigital.entity.Cliente;
import br.com.bootcamp.bancodigital.entity.Conta;
import br.com.bootcamp.bancodigital.exception.ExceptionPadrao;
import br.com.bootcamp.bancodigital.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;

    public Conta criarConta(Conta conta) {
        if(conta.getCliente() == null || conta.getCliente().getId() == null){
            throw new IllegalArgumentException("O id do cliente é obrigatório para criarmos a conta");

        }

        Long clienteId = conta.getCliente().getId();
        Cliente clienteConta = clienteService.getClienteById(clienteId);

        conta.setCliente(clienteConta);
        conta.setNumeroConta(gerarNumeroContaUnico());

        return contaRepository.save(conta);
    }
    public List<Conta> listarTodas() {
        return contaRepository.findAll();
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ExceptionPadrao("Conta com ID " + id + " não encontrada."));
    }

    public void deletarPorId(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new ExceptionPadrao("Conta com ID " + id + " não encontrada para exclusão.");
        }
        contaRepository.deleteById(id);
    }

    @Transactional
    public Conta realizarDeposito(Long id, BigDecimal valor){
            Conta conta = buscarPorId(id);
            conta.setSaldo(conta.getSaldo().add(valor));
            return contaRepository.save(conta);
    }

    @Transactional
    public Conta realizarSaque(Long id, BigDecimal valor){
        Conta conta = buscarPorId(id);
        if (conta.getSaldo().compareTo(valor) < 0){
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque");
        } else {
            conta.setSaldo(conta.getSaldo().subtract(valor));
            return contaRepository.save(conta);
        }

    }

    @Transactional
    public String realizarTransferencia(Long idContaOrigem, Long idContaDestino, BigDecimal valor){
        if (idContaOrigem.equals(idContaDestino)){
            throw new IllegalArgumentException("A conta de origem não pode ser igual a conta destino");
        }
        realizarSaque(idContaOrigem, valor);
        realizarDeposito(idContaDestino,valor);
        return "Transferencia de R$"  + valor + "realizada com sucesso!";
    }


    public BigDecimal consultarSaldo(Long id) {
        Conta conta = buscarPorId(id);
        return conta.getSaldo();
    }

    @Transactional
    public Conta aplicarTaxa(Long id){
        Conta conta = buscarPorId(id);

        BigDecimal taxa;
        if(conta.getTipoConta().toUpperCase().contains("PRIME")){
            taxa = new BigDecimal("19,99");
        } else {
            taxa = new BigDecimal("9,99");
        }
        conta.setSaldo(conta.getSaldo().subtract(taxa));
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta aplicarRendimentoPoupanca(Long id){
        Conta conta = buscarPorId(id);
        BigDecimal taxaRendimento = new BigDecimal("0.0006");
        BigDecimal rendimento = conta.getSaldo().multiply(taxaRendimento);
        conta.setSaldo(conta.getSaldo().add(rendimento));
        return contaRepository.save(conta);
    }

    private String gerarNumeroContaUnico() {

        return UUID.randomUUID().toString().substring(0, 8);
    }

}