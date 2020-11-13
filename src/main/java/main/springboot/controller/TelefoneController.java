package main.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import main.springboot.model.Pessoa;
import main.springboot.model.Telefone;
import main.springboot.repository.PessoaRepository;
import main.springboot.repository.TelefoneRepository;

@Controller
public class TelefoneController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addFonePessoa(@Valid Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		
		if (telefone != null && telefone.getNumero() != null 
			&& telefone.getNumero().isEmpty() || telefone.getNumero() == null) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			List<String> msg = new ArrayList<String>();
			msg.add("Numero deve ser informado");
			modelAndView.addObject("msg", msg);

			return modelAndView;
		}
		
		
		telefone.setPessoa(pessoa);
		
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));

		return modelAndView;
	}
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone) {	
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));

		return modelAndView;
	}

}
