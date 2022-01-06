package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

	private long id;
	private String codigoPedido;
	private String cliente;
	private LocalDate dataEntrega;
	private String observacao;

	private List<ProdutoPedidoDTO> produtosDoPedido;
}
