package src.edu.ves.lojavirtual.controller;

import java.util.List;

import src.edu.ves.lojavirtual.model.Produto;

public interface ProdutoController {
	public void adicionarProduto(Produto produto);
	public void atualizarProduto(long id, Produto produto);
	public void removerProduto(long id);
	public List<Produto> pesquisarProdutosPorNome(String nome);
}
