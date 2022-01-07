package com.example.demo.dao;

import com.example.demo.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDao extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

}
