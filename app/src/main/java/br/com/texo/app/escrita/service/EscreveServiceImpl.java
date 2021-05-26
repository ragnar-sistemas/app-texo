package br.com.texo.app.escrita.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.texo.app.escrita.provider.EscreveProvider;
import br.com.texo.app.leitura.Row;
import br.com.texo.app.model.GoldenRaspberryAwards;

@Service
public class EscreveServiceImpl implements EscreveService {

	@Autowired
	private EscreveProvider provider;

	public EscreveServiceImpl() {
	}

	public EscreveServiceImpl(EscreveProvider provider) {
		this.provider = provider;
	}

	public void escreve(String arquivo) {
		provider.insere(getGoldenRaspberryAwards(provider.getRows(arquivo)));
	}
	
	public void deleteAll() {
		provider.deleteAll();
	}

	private List<GoldenRaspberryAwards> getGoldenRaspberryAwards(List<Row> rows) {
		List<GoldenRaspberryAwards> list = new LinkedList<>();
		for (Row row : rows) {
			list.add(goldenRaspberryAwards(row));
		}
		return list;
	}

	private GoldenRaspberryAwards goldenRaspberryAwards(Row row) {
		GoldenRaspberryAwards awards = new GoldenRaspberryAwards();
		awards.setProducers(row.getProducers());
		awards.setStudios(row.getStudios());
		awards.setTitle(row.getTitle());
		awards.setWinner(row.getWinner());
		awards.setYear(row.getYear());
		return awards;
	}

}
