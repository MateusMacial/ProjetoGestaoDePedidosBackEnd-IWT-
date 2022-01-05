package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoPedidoDTO {

	private long id;
	private long produtoId;
	private long pedidoId;
	private String produtoCodigoProduto;
	private String produtoDescricaoProduto;
}
