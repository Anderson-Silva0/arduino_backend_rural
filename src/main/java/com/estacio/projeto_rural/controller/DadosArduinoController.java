package com.estacio.projeto_rural.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacio.projeto_rural.dto.DataDuracaoDTO;
import com.estacio.projeto_rural.entity.DadosArduino;
import com.estacio.projeto_rural.repository.DadosArduinoRepository;

@RestController
@RequestMapping("/obterDadosArduino")
public class DadosArduinoController {

	@Autowired
	private DadosArduinoRepository repository;

	@GetMapping
	public ResponseEntity<List<DadosArduino>> getAll() {
		List<DadosArduino> findAll = repository.findAll();
		return ResponseEntity.ok().body(findAll);
	}

	@GetMapping("/dataDuracaoPresencas")
	public ResponseEntity<List<DataDuracaoDTO>> getDataDuracaoPresencas() {
		List<DataDuracaoDTO> result = new LinkedList<>();
		List<DadosArduino> todosValores = repository.findAll();

		LocalDateTime inicio = null;
		LocalDateTime fim = null;
		for (int i = 0; i < todosValores.size(); i++) {
			DadosArduino presenca = todosValores.get(i);
			Long valor = presenca.getValor();
			DadosArduino proximaPresenca = null;
			DadosArduino presencaAnterior = null;
			Long duracaoPresenca = 0L;
			Long proximoValor = null;
			Long valorAnterior = null;

			if (i > 0 && i < todosValores.size() - 2) {
				proximaPresenca = todosValores.get(i + 1);
				presencaAnterior = todosValores.get(i - 1);

				proximoValor = proximaPresenca.getValor();
				if (valor == 0 && proximoValor == 1 && inicio == null) {
					inicio = presenca.getDataHora();
				}

				valorAnterior = presencaAnterior.getValor();
				if (valor == 0 && valorAnterior == 1 && fim == null) {
					fim = presenca.getDataHora();
				}
				if (inicio != null && fim != null) {
					duracaoPresenca = Duration.between(inicio, fim).getSeconds();
					result.add(new DataDuracaoDTO(inicio, duracaoPresenca));
					inicio = null;
					fim = null;
				}
			}
		}

		return ResponseEntity.ok().body(result);
	}

}
