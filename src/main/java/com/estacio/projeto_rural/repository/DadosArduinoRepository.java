package com.estacio.projeto_rural.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estacio.projeto_rural.entity.DadosArduino;

@Repository
public interface DadosArduinoRepository extends JpaRepository<DadosArduino, Long>{

}
