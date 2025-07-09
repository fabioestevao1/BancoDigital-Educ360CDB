package br.com.bootcamp.bancodigital.repository;

import br.com.bootcamp.bancodigital.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

}
