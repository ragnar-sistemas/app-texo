package br.com.texo.app.escrita.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.texo.app.escrita.provider.EscreveProvider;
import br.com.texo.app.leitura.Row;
import br.com.texo.app.model.GoldenRaspberryAwards;

@RunWith(MockitoJUnitRunner.class)
public class EscreveServiceTest {

	private static final String FAKE = "";

	private EscreveService service;
	@Mock
	private EscreveProvider provider;
	@Captor
	private ArgumentCaptor<List<GoldenRaspberryAwards>> argument;

	@Before
	public void antes_teste() {
		service = new EscreveServiceImpl(provider);
	}

	@Test
	public void nao_deve_ser_nulo() {
		Assert.assertNotNull("Não deveria ser nulo", service);
	}

	@Test
	public void testa_salvar_com_conta_mae() {
		Mockito.when(provider.getRows(Mockito.anyString())).thenReturn(Arrays.asList(row()));
		service.escreve(FAKE);
		Mockito.verify(provider, VerificationModeFactory.times(1)).insere(argument.capture());
		List<GoldenRaspberryAwards> awards = argument.getValue();
		Assert.assertEquals("Deveria ter somente um registro", 1, awards.size());
		Assert.assertEquals("GoldenRaspberryAwards [id=null, year=1980, title=Can't Stop the Music, studios=Associated Film Distribution, producers=Allan Carr, winner=yes]", awards.get(0).toString());
	}

	private Row row() {
		Row row = new Row();
		row.setProducers("Allan Carr");
		row.setStudios("Associated Film Distribution");
		row.setTitle("Can't Stop the Music");
		row.setWinner("yes");
		row.setYear(1980);
		return row;
	}
}
