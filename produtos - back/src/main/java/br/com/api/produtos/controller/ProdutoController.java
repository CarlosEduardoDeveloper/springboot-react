package br.com.api.produtos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.produtos.model.ProdutoModel;
import br.com.api.produtos.model.RespostaModel;
import br.com.api.produtos.service.ProdutoService;

@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
	
	@Autowired
	ProdutoService service;
	
	@GetMapping("/")
	public String rota() {
		return "API de produtos funcionando!";
	}
	
	@GetMapping("/listar")
	public Iterable<ProdutoModel> listar() {
		return service.listar();
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody ProdutoModel pm){
		return service.cadastrarAlterar(pm, "cadastrar");
	}
	
	@PutMapping("/alterar")
	public ResponseEntity<?> alterar(@RequestBody ProdutoModel pm){
		return service.cadastrarAlterar(pm, "alterar");
	}
	
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<RespostaModel> remover(@PathVariable Long id){
		return service.remover(id);
	}
	

}
