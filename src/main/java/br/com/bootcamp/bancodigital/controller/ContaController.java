package br.com.bootcamp.bancodigital.controller;

import br.com.bootcamp.bancodigital.dto.request.TransacaoRequest;
import br.com.bootcamp.bancodigital.entity.Conta;
import br.com.bootcamp.bancodigital.exception.ExceptionPadrao;
import br.com.bootcamp.bancodigital.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contas")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarConta( @RequestBody Conta conta){
        try {
            Conta novaConta = contaService.criarConta(conta);
            String mensagemOk = "Conta de Número: " + novaConta.getNumeroConta() + " para o cliente: " + novaConta.getCliente().getNome() + " foi criada com sucesso!";
            return new ResponseEntity<>(mensagemOk, HttpStatus.CREATED);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            Conta conta = contaService.buscarPorId(id);
            return ResponseEntity.ok(conta);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<?> consultarSaldo(@PathVariable Long id) {
        try {
            BigDecimal saldo = contaService.consultarSaldo(id);
            return ResponseEntity.ok(saldo);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deposito/{id}")
    public ResponseEntity<?> depositar(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        try {
            Conta contaAtt = contaService.realizarDeposito(id, request.valor());
            return ResponseEntity.ok(contaAtt);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saque/{id}")
    public ResponseEntity<?> sacar(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        try {
            Conta contaAtt = contaService.realizarSaque(id, request.valor());
            return ResponseEntity.ok(contaAtt);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferir(@RequestBody TransacaoRequest request) {
        try {
            String confirmacao = contaService.realizarTransferencia(
                    request.idContaOrigem(), request.idContaDestino(), request.valor());
            return ResponseEntity.ok(confirmacao);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/taxa/{id}")
    public ResponseEntity<?> aplicarTaxa(@PathVariable Long id) {
        try {
            Conta contaAtt = contaService.aplicarTaxa(id);
            return ResponseEntity.ok(contaAtt);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/rendimento/{id}")
    public ResponseEntity<?> aplicarRendimento(@PathVariable Long id) {
        try {
            Conta contaAtt = contaService.aplicarRendimentoPoupanca(id);
            return ResponseEntity.ok(contaAtt);
        } catch (ExceptionPadrao e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}