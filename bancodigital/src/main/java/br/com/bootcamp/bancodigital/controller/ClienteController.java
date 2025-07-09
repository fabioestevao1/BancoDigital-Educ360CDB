package br.com.bootcamp.bancodigital.controller;

import br.com.bootcamp.bancodigital.entity.Cliente;
import br.com.bootcamp.bancodigital.service.ClienteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/add")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){

        Cliente clienteAdicionado = clienteService.addCliente(cliente.getNome(), cliente.getCpf());

        if( clienteAdicionado != null)
        {
            return new ResponseEntity<>("Cliente Adicionado "+cliente.getNome()+"com sucesso", HttpStatus.CREATED);

        }
        else {
            return new ResponseEntity<>("Nome ou cpf do cliente inválido", HttpStatus.NOT_ACCEPTABLE );
        }

    }

    @GetMapping("/all")
    public ArrayList<Cliente> getAllClientes(){

        return (ArrayList<Cliente>) clienteService.getClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        Cliente cliente = clienteService.getClienteById(id);

        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente clienteAtt){
        Cliente cliente = clienteService.atualizarCliente(id, clienteAtt);

        return ResponseEntity.ok(cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);

        return ResponseEntity.ok("Cliente com id " +id+ " foi deletado com sucesso.");
    }

}
