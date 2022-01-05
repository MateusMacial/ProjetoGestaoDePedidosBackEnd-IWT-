package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Produto extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String codigoProduto;
	private String descricaoProduto;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
	private List<ProdutoPedido> pedidosDoProduto = new ArrayList<>();
}
