package br.com.texo.app.api.service;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.texo.app.api.controller.dto.GoldenRaspberryAwardsDTO;
import br.com.texo.app.model.GoldenRaspberryAwards;
import br.com.texo.app.model.repository.GoldenRaspberryAwardsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class GoldenRaspberryAwardsServiceTest {

	private GoldenRaspberryAwardsService service;
	@Mock
	private GoldenRaspberryAwardsRepository repository;

	@Before
	public void antes_teste() {
		service = new GoldenRaspberryAwardsService();
		ReflectionTestUtils.setField(service, "repository", repository);
	}

	@Test
	public void nao_deve_ser_nulo() {
		Assert.assertNotNull("Não deveria ser nulo", service);
	}

	@Test
	public void testa_intervalos() {
		Mockito.when(repository.findAll()).thenReturn(awards());
		List<GoldenRaspberryAwardsDTO> awards = service.getGoldenRaspberryAwards();
		Assert.assertEquals("GoldenRaspberryAwardsDTO [type=min, intervals=[IntervalDTO [producer=Produtor 1, interval=1, previousWin=2002, followingWin=2003]]]", awards.get(0).toString());
		Assert.assertEquals("GoldenRaspberryAwardsDTO [type=max, intervals=[IntervalDTO [producer=Produtor 2, interval=20, previousWin=2000, followingWin=2020]]]", awards.get(1).toString());
	}

	private List<GoldenRaspberryAwards> awards() {
		List<GoldenRaspberryAwards> awards = new LinkedList<>();
		awards.add(award(1l, "Filme 1", "Estudio 1", "Produtor 0", null, 2000));
		awards.add(award(2l, "Filme 2", "Estudio 1", "Produtor 0", null, 2000));
		awards.add(award(3l, "Filme 1", "Estudio 3", "Produtor 1", "yes", 2002));
		awards.add(award(4l, "Filme 2", "Estudio 3", "Produtor 1", "yes", 2003));
		awards.add(award(5l, "Filme 1", "Estudio 2", "Produtor 2", "yes", 2000));
		awards.add(award(6l, "Filme 2", "Estudio 4", "Produtor 2", "yes", 2020));
		return awards;
	}

	private GoldenRaspberryAwards award(Long id, String title, String studios, String producers, String winner, Integer year) {
		GoldenRaspberryAwards award = new GoldenRaspberryAwards();
		award.setId(id);
		award.setProducers(producers);
		award.setStudios(studios);
		award.setTitle(title);
		award.setWinner(winner);
		award.setYear(year);
		return award;
	}

}
