package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String codigoPedido;
	private String cliente;
	private Date dataEntrega;
	private String observacao;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true)
	private List<ProdutoPedido> produtosDoPedido = new ArrayList<>();
}
