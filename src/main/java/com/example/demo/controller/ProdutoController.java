package com.example.demo.controller;

import com.example.demo.dto.PagedQueryDto;
import com.example.demo.dto.ProdutoDTO;
import com.example.demo.dto.ProdutosParaDeletarDTO;
import com.example.demo.entidades.Produto;
import com.example.demo.services.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping(value="/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public void save(@Valid @RequestBody ProdutoDTO produtoDto){
		produtoService.save(produtoDto);
	}

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public ResponseEntity<Void> delete(@RequestBody ProdutosParaDeletarDTO produtosParaDeletarDTO){
		produtoService.delete(produtosParaDeletarDTO.getIdsProdutosParaDeletar());
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/get-page", method = RequestMethod.POST)
	public PagedQueryDto getPageProdutos(@RequestBody PagedQueryDto pagedQueryDto) {
		Page<Produto> page = produtoService.getPage(pagedQueryDto);
		ModelMapper modelMapper = new ModelMapper();
		pagedQueryDto.setList(page.getContent().stream().map(a -> modelMapper.map(a, ProdutoDTO.class)).collect(Collectors.toList()));
		pagedQueryDto.setRowsNumber(page.getTotalElements());
		return pagedQueryDto;
	}
}
