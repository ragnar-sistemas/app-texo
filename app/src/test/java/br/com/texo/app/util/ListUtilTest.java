package br.com.texo.app.util;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ListUtilTest {

	@Test
	public void testa_sem_remover() {
		Assert.assertEquals("[Produtor1, Produtor2, Produtor3]", ListUtil.removeDuplicates(Arrays.asList("Produtor1", "Produtor2", "Produtor3")).toString());
	}

	@Test
	public void testa_removendo() {
		Assert.assertEquals("[Produtor1, Produtor2]", ListUtil.removeDuplicates(Arrays.asList("Produtor1", "Produtor2", "Produtor2")).toString());
	}
}
