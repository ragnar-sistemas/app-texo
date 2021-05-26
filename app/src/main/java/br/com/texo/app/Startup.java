package br.com.texo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.texo.app.escrita.service.EscreveService;

@Component
public class Startup {

	private static final String CAMINHO_DO_CSV = "src/main/resources/movielist.csv";

	@Autowired
	private EscreveService escreveService;

	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		escreveService.deleteAll();
		escreveService.escreve(CAMINHO_DO_CSV);
	}

}
