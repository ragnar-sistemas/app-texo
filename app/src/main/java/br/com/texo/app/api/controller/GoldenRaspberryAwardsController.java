package br.com.texo.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.texo.app.api.controller.dto.GoldenRaspberryAwardsDTO;
import br.com.texo.app.api.service.GoldenRaspberryAwardsService;

@Controller
@RequestMapping(GoldenRaspberryAwardsController.GOLDEN_RASPBERRY_AWARDS)
public class GoldenRaspberryAwardsController {

	public static final String GOLDEN_RASPBERRY_AWARDS = "v1/golden-raspberry-awards";
	@Autowired
	private GoldenRaspberryAwardsService service;

	@GetMapping
	public @ResponseBody GoldenRaspberryAwardsDTO getGoldenRaspberryAwards() {
		return service.getGoldenRaspberryAwards();
	}

}
