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
import br.com.texo.app.util.TexoLinkedList;

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
		GoldenRaspberryAwardsDTO awards = service.getGoldenRaspberryAwards();
		Assert.assertEquals("Lista e minimos deveria ter 2", 2, awards.getMin().size());
		Assert.assertEquals("Lista de máximos deveria ter 2", 2, awards.getMax().size());
		Assert.assertEquals("IntervalDTO [producer=Produtor 23, interval=1, previousWin=2019, followingWin=2020]", awards.getMin().get(0).toString());
		Assert.assertEquals("IntervalDTO [producer=Produtor 6, interval=1, previousWin=2002, followingWin=2003]", awards.getMin().get(1).toString());
		Assert.assertEquals("IntervalDTO [producer=Produtor 2, interval=21, previousWin=2000, followingWin=2021]", awards.getMax().get(0).toString());
		Assert.assertEquals("IntervalDTO [producer=Produtor 22, interval=21, previousWin=2000, followingWin=2021]", awards.getMax().get(1).toString());
	}

	@Test
	public void testa_split_com_1_produtor() {
		String frase = "Produtor1";
		TexoLinkedList<String> split = service.getProdutores(frase);
		Assert.assertEquals(1, split.size());
		Assert.assertEquals("Produtor1", split.get(0));
	}

	@Test
	public void testa_split_com_2_produtores() {
		TexoLinkedList<String> split = service.getProdutores("Produtor1 and Produtor2");
		Assert.assertEquals("Deveria ter 2", 2, split.size());
		Assert.assertEquals("Produtor1", split.get(0));
		Assert.assertEquals("Produtor2", split.get(1));
	}

	@Test
	public void testa_split_com_3_produtores() {
		TexoLinkedList<String> split = service.getProdutores("Produtor1, Produtor2 and Produtor3");
		Assert.assertEquals("Deveria ter 3", 3, split.size());
		Assert.assertEquals("Produtor1", split.get(0));
		Assert.assertEquals("Produtor2", split.get(1));
		Assert.assertEquals("Produtor3", split.get(2));
	}

	@Test
	public void testa_split_com_2_produtores_um_com_and_no_nome() {
		TexoLinkedList<String> split = service.getProdutores("Produtor1, AandProdutor2");
		Assert.assertEquals("Deveria ter 2", 2, split.size());
		Assert.assertEquals("Produtor1", split.get(0));
		Assert.assertEquals("AandProdutor2", split.get(1));
	}

	private List<GoldenRaspberryAwards> awards() {
		List<GoldenRaspberryAwards> awards = new LinkedList<>();
		awards.add(award(1l, "Filme 1", "Estudio 1", "Produtor 0 and Produtor 1", null, 2000));
		awards.add(award(2l, "Filme 2", "Estudio 1", "Produtor 0", null, 2000));
		awards.add(award(3l, "Filme 1", "Estudio 3", "Produtor 1, Produtor 2 and Produtor 6", "yes", 2002));
		awards.add(award(4l, "Filme 2", "Estudio 3", "Produtor 1", null, 2003));
		awards.add(award(5l, "Filme 1", "Estudio 2", "Produtor 2", "yes", 2000));
		awards.add(award(6l, "Filme 2", "Estudio 4", "Produtor 2", "yes", 2021));
		awards.add(award(7l, "Filme 1", "Estudio 5", "Produtor 5", "yes", 2020));
		awards.add(award(8l, "Filme 1", "Estudio 6", "Produtor 6", null, 2000));
		awards.add(award(9l, "Filme 2", "Estudio 6", "Produtor 6", "yes", 2003));
		awards.add(award(10l, "Filme 1", "Estudio 7", "Produtor 7", "yes", 2010));
		awards.add(award(11l, "Filme 2", "Estudio 7", "Produtor 7", "yes", 2019));
		awards.add(award(12l, "Filme 1", "Estudio 22", "Produtor 22", "yes", 2000));
		awards.add(award(13l, "Filme 2", "Estudio 22", "Produtor 22", "yes", 2021));
		awards.add(award(14l, "Filme 1", "Estudio 23", "Produtor 23 and Produtor 23", "yes", 2019));
		awards.add(award(14l, "Filme 2", "Estudio 23", "Produtor 23", "yes", 2020));
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
