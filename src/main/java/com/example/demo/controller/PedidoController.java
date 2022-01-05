package com.example.demo.controller;

import com.example.demo.dto.PedidoDTO;
import com.example.demo.dto.PedidosParaDeletarDTO;
import com.example.demo.dto.ProdutosParaDeletarDTO;
import com.example.demo.entidades.Pedido;
import com.example.demo.services.PedidoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(value="/find", method=RequestMethod.GET)
	public PedidoDTO find(Integer id) {
		Pedido obj = pedidoService.find(id);
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(obj, PedidoDTO.class);
	}

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public void save(@Valid @RequestBody PedidoDTO pedidoDto){
		pedidoService.save(pedidoDto);
	}

	/*@RequestMapping(value="/update", method=RequestMethod.POST)
	public ResponseEntity<Void> update(@Valid @RequestBody PedidoDTO objDto){
		Pedido pedido = pedidoService.fromDTO(objDto);
		for (ProdutoDTO produtoDto : objDto.getProdutosDoPedido()) {
			Produto produto = produtoService.find(produtoDto.getId());
			pedidoService.insertProduto(pedido, produto);
		}
		pedido = pedidoService.update(pedido);
		return ResponseEntity.noContent().build();
	}*/

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public ResponseEntity<Void> delete(@RequestBody PedidosParaDeletarDTO pedidosParaDeletarDTO){
		pedidoService.delete(pedidosParaDeletarDTO.getIdsPedidosParaDeletar());
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/get-page", method=RequestMethod.GET)
	public List<PedidoDTO> findAll() {
		ModelMapper modelMapper = new ModelMapper();
		List<Pedido> list = pedidoService.findAll();
		return list.stream()
				.map(obj -> modelMapper.map(obj, PedidoDTO.class))
				.collect(Collectors.toList());
	}
}
