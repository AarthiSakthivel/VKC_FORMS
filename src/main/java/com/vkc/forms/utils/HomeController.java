package com.vkc.forms.utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(value= {"","/"})
	public String HomePage() {
		return "VKC-S4 Forms!!";
	}
	
}
