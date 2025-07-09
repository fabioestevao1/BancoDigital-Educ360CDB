package br.com.bootcamp.bancodigital.service;

import br.com.bootcamp.bancodigital.repository.ClienteRepository;
import br.com.bootcamp.bancodigital.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente addCliente( String nome, long cpf){
        //validar nome e cpf

        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(Long id){return clienteRepository.findById(id).orElse(null);}

    public Cliente atualizarCliente(Long id,Cliente clienteAtt){

        Cliente cliente = getClienteById(id);
        if (clienteAtt.getNome() != null){
            cliente.setNome(clienteAtt.getNome());
        }
        if (clienteAtt.getCpf() != null){
            cliente.setCpf(clienteAtt.getCpf());
        }
        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Long id){
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o id:" + id);
        }
        clienteRepository.deleteById(id);
    }
}
