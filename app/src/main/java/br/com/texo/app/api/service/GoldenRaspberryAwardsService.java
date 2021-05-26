package br.com.texo.app.api.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.texo.app.api.controller.dto.GoldenRaspberryAwardsDTO;
import br.com.texo.app.api.dto.IntervalDTO;
import br.com.texo.app.model.GoldenRaspberryAwards;
import br.com.texo.app.model.repository.GoldenRaspberryAwardsRepository;
import br.com.texo.app.util.ListUtil;
import br.com.texo.app.util.TexoLinkedList;

@Service
public class GoldenRaspberryAwardsService {

	@Autowired
	private GoldenRaspberryAwardsRepository repository;
	private GoldenRaspberryAwardsDTO dto;

	public GoldenRaspberryAwardsDTO getGoldenRaspberryAwards() {
		dto = new GoldenRaspberryAwardsDTO();
		this.agrupaProdutores();
		return dto;
	}

	private void agrupaProdutores() {
		List<GoldenRaspberryAwards> awards = repository.findAll();
		Map<String, Agrupado> mapaDeAgrupados = new TreeMap<>();
		for (GoldenRaspberryAwards award : awards) {
			for (String produtor : getProdutores(award.getProducers())) {
				Agrupado agrupado = mapaDeAgrupados.get(produtor);
				if (agrupado == null) {
					agrupado = new Agrupado(produtor);
					mapaDeAgrupados.put(produtor, agrupado);
				}
				agrupado.addAwards(award);
			}
		}
		addMin(mapaDeAgrupados);
		addMax(mapaDeAgrupados);
	}

	public TexoLinkedList<String> getProdutores(String producers) {
		TexoLinkedList<String> produtores = new TexoLinkedList<>();
		String[] produtoresDaString = producers.split(",|and ", -1);
		for (int i = 0; i < produtoresDaString.length; i++) {
			produtores.add(produtoresDaString[i].trim().isEmpty() ? null : produtoresDaString[i].trim());
		}
		return ListUtil.removeDuplicates(produtores);
	}

	private void addMin(Map<String, Agrupado> mapaDeAgrupados) {
		for (Entry<String, Agrupado> entry : mapaDeAgrupados.entrySet()) {
			entry.getValue().ordenaPeloAno();
			int minYear = entry.getValue().getMin();
			if (minYear == 1) {
				dto.addMin(interval(entry.getValue().getProdutor(), minYear, entry.getValue().getMinYear(), entry.getValue().getNextYear()));
			}
		}
	}

	private void addMax(Map<String, Agrupado> mapaDeAgrupados) {
		for (Entry<Integer, TexoLinkedList<Agrupado>> entry : getMapMaxYear(mapaDeAgrupados).entrySet()) {
			for (Agrupado agrupado : entry.getValue()) {
				agrupado.ordenaPeloAno();
				dto.addMax(interval(agrupado.getProdutor(), agrupado.getMax(), agrupado.getMinYear(), agrupado.getMaxYear()));
			}
		}
	}

	private Map<Integer, TexoLinkedList<Agrupado>> getMapMaxYear(Map<String, Agrupado> mapaDeAgrupados) {
		Map<Integer, TexoLinkedList<Agrupado>> mapMaxYear = new TreeMap<>();
		int diff = 0;
		for (Entry<String, Agrupado> entry : mapaDeAgrupados.entrySet()) {
			int maxYear = entry.getValue().getMax();
			if (maxYear > diff) {
				TexoLinkedList<Agrupado> texoLinkedList = mapMaxYear.get(maxYear);
				if (texoLinkedList == null) {
					mapMaxYear.put(maxYear, new TexoLinkedList<Agrupado>(entry.getValue()));
				} else {
					texoLinkedList.add(entry.getValue());
				}
				mapMaxYear.remove(diff);
				diff = maxYear;
			} else if (maxYear != 0 && maxYear == diff) {
				mapMaxYear.get(maxYear).add(entry.getValue());
			}
		}
		return mapMaxYear;
	}

	private IntervalDTO interval(String producer, Integer interval, Integer previousWin, Integer followingWin) {
		IntervalDTO dto = new IntervalDTO();
		dto.setProducer(producer);
		dto.setInterval(interval);
		dto.setPreviousWin(previousWin);
		dto.setFollowingWin(followingWin);
		return dto;
	}

}
