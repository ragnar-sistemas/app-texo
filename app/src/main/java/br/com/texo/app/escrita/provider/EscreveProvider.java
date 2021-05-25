package br.com.texo.app.escrita.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.texo.app.leitura.Row;
import br.com.texo.app.leitura.service.LeituraService;
import br.com.texo.app.model.GoldenRaspberryAwards;
import br.com.texo.app.model.repository.GoldenRaspberryAwardsRepository;

@Repository
public class EscreveProvider {
	@Autowired
	private LeituraService leituraService;
	@Autowired
	private GoldenRaspberryAwardsRepository repository;

	public List<Row> getRows(String caminhoDoCsv) {
		return leituraService.getLinhas(caminhoDoCsv);
	}

	public void insere(List<GoldenRaspberryAwards> goldenRaspberryAwards) {
		repository.saveAll(goldenRaspberryAwards);
	}

}
