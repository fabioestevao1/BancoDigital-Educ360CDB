package br.com.bootcamp.bancodigital.controller;

import br.com.bootcamp.bancodigital.entity.Conta;
import br.com.bootcamp.bancodigital.exception.ExceptionPadrao;
import br.com.bootcamp.bancodigital.service.ContaService;
import org.apache.coyote.Response;
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

           String mensagemOk = "Conta de Número: " + novaConta.getNumeroConta() + "para o cliente: " +novaConta.getCliente().getNome() + "foi criada com sucesso!";
           return new ResponseEntity<>(mensagemOk, HttpStatus.CREATED);
       } catch (ExceptionPadrao e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       } catch (IllegalArgumentException e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id){
        Conta conta = contaService.buscarPorId(id);

        if (conta != null) {
            return new ResponseEntity<>(conta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
