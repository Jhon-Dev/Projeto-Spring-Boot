package main.springbot.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		//qualquer coisa cadastro tudo mais
		return "index";
	}
}
