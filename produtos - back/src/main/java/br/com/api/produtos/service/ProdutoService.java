package br.com.api.produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.model.ProdutoModel;
import br.com.api.produtos.model.RespostaModel;
import br.com.api.produtos.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private RespostaModel respostaModel;

	public Iterable<ProdutoModel> listar() {
		return repository.findAll();
	}

	public ResponseEntity<?> cadastrarAlterar(ProdutoModel pm, String acao) {
		if (pm.getNome().equals("")) {
			respostaModel.setMensagem("O Nome do produto é obrigatório!");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else if (pm.getMarca().equals("")) {
			respostaModel.setMensagem("O Nome da marca é obrigatório!");
			return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.BAD_REQUEST);
		} else {
			if (acao.equals("cadastrar")) {
				return new ResponseEntity<ProdutoModel>(repository.save(pm), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<ProdutoModel>(repository.save(pm), HttpStatus.OK);
			}
		}
	}

	public ResponseEntity<RespostaModel> remover(Long id) {
		repository.deleteById(id);
		respostaModel.setMensagem("Produto removido com sucesso");
		return new ResponseEntity<RespostaModel>(respostaModel, HttpStatus.OK);
	}

}
