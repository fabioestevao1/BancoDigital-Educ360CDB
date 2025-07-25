package br.com.bootcamp.bancodigital.repository;

import br.com.bootcamp.bancodigital.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}
