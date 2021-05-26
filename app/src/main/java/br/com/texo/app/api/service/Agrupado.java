package br.com.texo.app.api.service;

import java.util.Collections;
import java.util.Comparator;

import br.com.texo.app.model.GoldenRaspberryAwards;
import br.com.texo.app.util.TexoLinkedList;

public class Agrupado {

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
		return awards.get(1).getYear() - awards.getFirst().getYear();
	}

	public int getMinYear() {
		if (awards.isEmpty() || awards.size() == 1) {
			return 0;
		}
		return awards.getFirst().getYear();
	}

	public int getNextYear() {
		if (awards.isEmpty() || awards.size() == 1) {
			return 0;
		}
		return awards.get(1).getYear();
	}

	public int getMaxYear() {
		if (awards.isEmpty() || awards.size() == 1) {
			return 0;
		}
		return awards.getLast().getYear();
	}

	public int getMax() {
		if (awards.isEmpty() || awards.size() == 1) {
			return 0;
		}
		return awards.getLast().getYear() - awards.getFirst().getYear();
	}

	public void ordenaPeloAno() {
		Collections.sort(awards, new Comparator<GoldenRaspberryAwards>() {
			public int compare(GoldenRaspberryAwards awards1, GoldenRaspberryAwards awards2) {
				return awards1.getYear().compareTo(awards2.getYear());
			}
		});
	}

	@Override
	public String toString() {
		return "Agrupado [produtor=" + produtor + ", awards=" + awards + "]";
	}

}
