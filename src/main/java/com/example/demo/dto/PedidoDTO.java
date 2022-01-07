package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

	private long id;
	private String codigoPedido;
	private String cliente;
	private Date dataEntrega;
	private String observacao;

	private List<ProdutoPedidoDTO> produtosDoPedido;
}
