package br.com.texo.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eGoldenRaspberryAwards")
public class GoldenRaspberryAwards implements Serializable {

	private static final long serialVersionUID = 7977210946338537178L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private Integer year;
	private String title;
	private String studios;
	private String producers;
	private String winner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public String getProducers() {
		return producers;
	}

	public void setProducers(String producers) {
		this.producers = producers;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	public boolean isWinner() {
		return "yes".equalsIgnoreCase(winner);
	}

	@Override
	public String toString() {
		return "GoldenRaspberryAwards [id=" + id + ", year=" + year + ", title=" + title + ", studios=" + studios + ", producers=" + producers + ", winner=" + winner + "]";
	}

}
