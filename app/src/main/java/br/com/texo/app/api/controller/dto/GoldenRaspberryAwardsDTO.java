package br.com.texo.app.api.controller.dto;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.texo.app.api.dto.IntervalDTO;

public class GoldenRaspberryAwardsDTO {
	@JsonInclude(Include.NON_EMPTY)
	private List<IntervalDTO> min;
	@JsonInclude(Include.NON_EMPTY)
	private List<IntervalDTO> max;

	public GoldenRaspberryAwardsDTO() {
		min = new LinkedList<IntervalDTO>();
		max = new LinkedList<IntervalDTO>();
	}

	public List<IntervalDTO> getMin() {
		return min;
	}

	public List<IntervalDTO> getMax() {
		return max;
	}

	public void addMin(IntervalDTO min) {
		this.min.add(min);
	}

	public void addMax(IntervalDTO max) {
		this.max.add(max);
	}

}
