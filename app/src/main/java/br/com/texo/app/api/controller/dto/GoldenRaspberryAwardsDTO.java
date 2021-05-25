package br.com.texo.app.api.controller.dto;

import java.util.LinkedList;
import java.util.List;

import br.com.texo.app.api.dto.IntervalDTO;

public class GoldenRaspberryAwardsDTO {

	private String type;
	private List<IntervalDTO> intervals;

	public GoldenRaspberryAwardsDTO() {
		intervals = new LinkedList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<IntervalDTO> getIntervals() {
		return intervals;
	}

	public void setIntervals(List<IntervalDTO> intervals) {
		this.intervals = intervals;
	}

	@Override
	public String toString() {
		return "GoldenRaspberryAwardsDTO [type=" + type + ", intervals=" + intervals + "]";
	}

}
