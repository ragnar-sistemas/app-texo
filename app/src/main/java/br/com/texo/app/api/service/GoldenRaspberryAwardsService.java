package br.com.texo.app.api.service;

import java.util.Collections;
import java.util.Comparator;
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
import br.com.texo.app.util.TexoLinkedList;

@Service
public class GoldenRaspberryAwardsService {

	@Autowired
	private GoldenRaspberryAwardsRepository repository;
	private List<GoldenRaspberryAwardsDTO> dtos;

	public GoldenRaspberryAwardsService() {
		dtos = new TexoLinkedList<>();
	}

	public List<GoldenRaspberryAwardsDTO> getGoldenRaspberryAwards() {
		this.agrupaProdutores();
		return dtos;
	}

	private void agrupaProdutores() {
		List<GoldenRaspberryAwards> awards = repository.findAll();
		Map<String, Agrupado> mapaDeAgrupados = new TreeMap<>();
		for (GoldenRaspberryAwards award : awards) {
			String[] produtores = award.getProducers().split(",", -1);
			for (int i = 0; i < produtores.length; i++) {
				String produtor = produtores[i];
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

	private void addMin(Map<String, Agrupado> mapaDeAgrupados) {
		GoldenRaspberryAwardsDTO min = new GoldenRaspberryAwardsDTO();
		min.setType("min");
		for (Entry<String, Agrupado> entry : mapaDeAgrupados.entrySet()) {
			if (entry.getValue().getMin() == 1) {
				min.getIntervals().add(interval(entry.getValue().getProdutor(), entry.getValue().getMin(), entry.getValue().getMinYear(), entry.getValue().getNextYear()));
			}
		}
		dtos.add(min);
	}

	private void addMax(Map<String, Agrupado> mapaDeAgrupados) {
		GoldenRaspberryAwardsDTO max = new GoldenRaspberryAwardsDTO();
		max.setType("max");
		for (Entry<Integer, TexoLinkedList<Agrupado>> entry : getMapMaxYear(mapaDeAgrupados).entrySet()) {
			for (Agrupado agrupado : entry.getValue()) {
				max.getIntervals().add(interval(agrupado.getProdutor(), agrupado.getMax(), agrupado.getMinYear(), agrupado.getMaxYear()));
			}
		}
		dtos.add(max);
	}

	private Map<Integer, TexoLinkedList<Agrupado>> getMapMaxYear(Map<String, Agrupado> mapaDeAgrupados) {
		Map<Integer, TexoLinkedList<Agrupado>> mapMaxYear = new TreeMap<>();
		int diff = 0;
		for (Entry<String, Agrupado> entry : mapaDeAgrupados.entrySet()) {
			if (entry.getValue().getMax() > diff) {
				TexoLinkedList<Agrupado> texoLinkedList = mapMaxYear.get(entry.getValue().getMax());
				if (texoLinkedList == null) {
					mapMaxYear.put(entry.getValue().getMax(), new TexoLinkedList<Agrupado>(entry.getValue()));
				} else {
					texoLinkedList.add(entry.getValue());
				}
				mapMaxYear.remove(diff);
				diff = entry.getValue().getMax();
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

	private class Agrupado {

		private String produtor;
		private TexoLinkedList<GoldenRaspberryAwards> awards;

		public Agrupado(String produtor) {
			awards = new TexoLinkedList<>();
			this.produtor = produtor;
		}

		public String getProdutor() {
			return produtor;
		}

		public void addAwards(GoldenRaspberryAwards award) {
			awards.add(award.isWinner() ? award : null);
		}

		public int getMin() {
			if (awards.isEmpty() || awards.size() == 1) {
				return 0;
			}
			ordenaPeloAno();
			return awards.get(1).getYear() - awards.getFirst().getYear();
		}

		public int getMinYear() {
			if (awards.isEmpty() || awards.size() == 1) {
				return 0;
			}
			ordenaPeloAno();
			return awards.getFirst().getYear();
		}

		public int getNextYear() {
			if (awards.isEmpty() || awards.size() == 1) {
				return 0;
			}
			ordenaPeloAno();
			return awards.get(1).getYear();
		}

		public int getMaxYear() {
			if (awards.isEmpty() || awards.size() == 1) {
				return 0;
			}
			ordenaPeloAno();
			return awards.getLast().getYear();
		}

		public int getMax() {
			if (awards.isEmpty() || awards.size() == 1) {
				return 0;
			}
			ordenaPeloAno();
			return awards.getLast().getYear() - awards.getFirst().getYear();
		}

		private void ordenaPeloAno() {
			Collections.sort(awards, new Comparator<GoldenRaspberryAwards>() {
				public int compare(GoldenRaspberryAwards awards1, GoldenRaspberryAwards awards2) {
					return awards1.getYear().compareTo(awards2.getYear());
				}
			});
		}

	}

}
