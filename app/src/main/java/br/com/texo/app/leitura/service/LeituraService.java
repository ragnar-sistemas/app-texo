package br.com.texo.app.leitura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.texo.app.leitura.Row;

@Service
public interface LeituraService {

	List<Row> getLinhas(String caminhoDoCsv);

}
