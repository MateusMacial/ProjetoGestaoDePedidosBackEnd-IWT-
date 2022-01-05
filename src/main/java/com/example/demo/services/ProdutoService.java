package com.example.demo.services;

import com.example.demo.dao.ProdutoDao;
import com.example.demo.dao.specifications.ProdutoSpecification;
import com.example.demo.dto.PagedQueryDto;
import com.example.demo.dto.ProdutoDTO;
import com.example.demo.entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// import org.apache.commons.lang3.StringUtils;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoDao produtoDao;

	private Produto find(long id) {
		Optional<Produto> obj = produtoDao.findById(id);
		return obj.orElse(null);
	}

	public Produto save(ProdutoDTO produtoDto) {
		Produto produto;

		if(produtoDto.getId() > 0) {
			produto = produtoDao.getById(produtoDto.getId());
		}
		else {
			produto = new Produto();
		}
		/*if(StringUtils.isBlank(produtoDto.getCodigoProduto())) {
			//ToDo
		}
		if(StringUtils.isBlank(produtoDto.getDescricaoProduto())) {
			//ToDo
		}*/

		produto.setCodigoProduto(produtoDto.getCodigoProduto());
		produto.setDescricaoProduto(produtoDto.getDescricaoProduto());

		return produtoDao.save(produto);
	}

	/*public Produto update(Produto obj) {
		Produto newObj = find(obj.getId());
		updateData(newObj, obj);
		return produtoDao.save(newObj);
	}*/

	public void delete(List<Long> idsProdutosParaDeletar) {
		for (long produtoId : idsProdutosParaDeletar
			 ) {
			find(produtoId);
			produtoDao.deleteById(produtoId);
		}
	}

	public List<Produto> findAll(){
		return produtoDao.findAll();
	}

	public Page<Produto> getPage(PagedQueryDto pagedQueryDto) {
		if (pagedQueryDto.getRowsPerPage() == 0) {
			List<Produto> list = null;
			Sort sort = pagedQueryDto.getSort();

			if (sort == null) {
				list = produtoDao.findAll(ProdutoSpecification.search(pagedQueryDto.getFilter()));
			}
			else {
				list = produtoDao.findAll(ProdutoSpecification.search(pagedQueryDto.getFilter()), sort);
			}
			return new PageImpl<>(list);
		}
		else {
			return produtoDao.findAll(ProdutoSpecification.search(pagedQueryDto.getFilter()), pagedQueryDto.getPageRequest());
		}
	}


	/*public Produto fromDTO(ProdutoDTO objDto) {
		return new Produto(objDto.getId(), objDto.getCodigoProduto(), objDto.getDescricaoProduto());
	}*/

	/*private void updateData(Produto newObj, Produto obj) {
		newObj.setCodigoProduto(obj.getCodigoProduto());
		newObj.setDescricaoProduto(obj.getDescricaoProduto());
	}*/
}
