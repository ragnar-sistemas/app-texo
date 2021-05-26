package br.com.texo.app.leitura.service;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.texo.app.leitura.Row;

@RunWith(MockitoJUnitRunner.class)
public class LeituraServiceTest {

	private static final String CAMINHO_DO_CSV = "src/main/resources/movielisttest.csv";

	private LeituraService service;

	@Before
	public void antes_teste() {
		service = new LeituraCSVService();
	}

	@Test
	public void nao_deve_ser_nulo() {
		Assert.assertNotNull("Não deveria ser nulo", service);
	}

	@Test
	public void testa_ler_arquivo() {
		List<Row> linhas = service.getLinhas(CAMINHO_DO_CSV);
		Assert.assertFalse("Não deveria estar vazio", linhas.isEmpty());
		Assert.assertThat(comparar(linhas), Matchers.containsInAnyOrder(new String[] {
				"Row [year=1980, title=Can't Stop the Music, studios=Associated Film Distribution, producers=Allan Carr, winner=yes]",
				"Row [year=1980, title=Cruising, studios=Lorimar Productions, United Artists, producers=Jerry Weintraub, winner=]",
				"Row [year=1980, title=The Formula, studios=MGM, United Artists, producers=Steve Shagan, winner=]" 
				}));
	}

	private List<String> comparar(List<Row> linhas) {
		List<String> comparar = new LinkedList<>();
		for (Row row : linhas) {
			comparar.add(row.toString());
		}
		return comparar;
	}

}
