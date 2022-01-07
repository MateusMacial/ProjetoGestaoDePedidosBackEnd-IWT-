package com.example.demo.services;

import com.example.demo.dao.PedidoDao;
import com.example.demo.dao.ProdutoDao;
import com.example.demo.dao.ProdutoPedidoDao;
import com.example.demo.dao.specifications.PedidoSpecification;
import com.example.demo.dao.specifications.ProdutoSpecification;
import com.example.demo.dto.PagedQueryDto;
import com.example.demo.dto.PedidoDTO;
import com.example.demo.dto.ProdutoPedidoDTO;
import com.example.demo.entidades.Pedido;
import com.example.demo.entidades.Produto;
import com.example.demo.entidades.ProdutoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// import org.apache.commons.lang3.StringUtils;

@Service
public class PedidoService {

	@Autowired
	private PedidoDao pedidoDao;

	@Autowired
	private ProdutoDao produtoDao;

	@Autowired
	private ProdutoPedidoDao produtoPedidoDao;

	public Pedido find(long id) {
		Optional<Pedido> obj = pedidoDao.findById(id);
		return obj.orElse(null);
	}

	public Pedido save(PedidoDTO pedidoDto) {
		Pedido pedido;

		if(pedidoDto.getId() > 0) {
			pedido = pedidoDao.getById(pedidoDto.getId());
		}
		else {
			pedido = new Pedido();
		}

		/*if(StringUtils.isBlank(pedidoDto.getCodigoPedido())) {
			//ToDo
		}
		if(StringUtils.isBlank(pedidoDto.getCliente())) {
			//ToDo
		}
		if(pedidoDto.getDataEntrega() == null) {
			//ToDo
		}
		if(pedidoDto.getProdutosDoPedido() == null) {
			//ToDo
		}*/

		pedido.setCodigoPedido(pedidoDto.getCodigoPedido());
		pedido.setCliente(pedidoDto.getCliente());
		pedido.setDataEntrega(pedidoDto.getDataEntrega());
		pedido.setObservacao(pedidoDto.getObservacao());

		if(pedido.getProdutosDoPedido() == null) {
			pedido.setProdutosDoPedido(new ArrayList<>());
		}
		pedido.getProdutosDoPedido().clear();

		for (ProdutoPedidoDTO produtoPedidoDTO : pedidoDto.getProdutosDoPedido()) {

			ProdutoPedido produtoPedido;

			if(produtoPedidoDTO.getId() > 0) {
				produtoPedido = produtoPedidoDao.getById(produtoPedidoDTO.getId());
			}
			else {
				produtoPedido = new ProdutoPedido();
			}

			Produto produto = produtoDao.getById(produtoPedidoDTO.getProdutoId());

			produtoPedido.setPedido(pedido);
			produtoPedido.setProduto(produto);

			pedido.getProdutosDoPedido().add(produtoPedido);
		}

		return pedidoDao.save(pedido);
	}

	/*public Pedido update(Pedido obj) {
		Pedido newObj = find(obj.getId());
		updateData(newObj, obj);
		return pedidoDao.save(newObj);
	}*/

	public void delete(List<Long> idsPedidosParaDeletar) {
		for (long pedidoId: idsPedidosParaDeletar
			 ) {
			find(pedidoId);
			pedidoDao.deleteById(pedidoId);
		}
	}

	/*public void insertProduto(Pedido pedido, Produto produtoInserir) {
		pedido.adicionarProduto(produtoInserir);
		produtoInserir.setPedido(pedido);
	}*/

	public List<Pedido> findAll(){
		return pedidoDao.findAll();
	}

	public Page<Pedido> getPage(PagedQueryDto pagedQueryDto) {
		if (pagedQueryDto.getRowsPerPage() == 0) {
			List<Pedido> list = null;
			Sort sort = pagedQueryDto.getSort();
			if (sort == null) {
				list = pedidoDao.findAll(PedidoSpecification.search(pagedQueryDto.getFilter()));
			}
			else {
				list = pedidoDao.findAll(PedidoSpecification.search(pagedQueryDto.getFilter()), sort);
			}
			return new PageImpl<>(list);
		}
		else {
			return pedidoDao.findAll(PedidoSpecification.search(pagedQueryDto.getFilter()), pagedQueryDto.getPageRequest());
		}
	}

	/*public Pedido fromDTO(PedidoDTO objDto) {
		return new Pedido(objDto.getId(), objDto.getCodigoPedido(), objDto.getCliente(), objDto.getDataEntrega(), objDto.getObservacao());
	}*/

	/*private void updateData(Pedido newObj, Pedido obj) {
		newObj.setCodigoPedido(obj.getCodigoPedido());
		newObj.setCliente(obj.getCliente());
		newObj.setDataEntrega(obj.getDataEntrega());
		newObj.setObservacao(obj.getObservacao());

		for (Produto produto : newObj.getProdutosDoPedido()) {
			produto.setPedido(null);
		}

		newObj.getProdutosDoPedido().clear();

		for (Produto produto : obj.getProdutosDoPedido()) {
			produto.setPedido(newObj);
			newObj.adicionarProduto(produto);
		}
	}*/
}
