package com.example.demo.dao.specifications;

import com.example.demo.entidades.Pedido;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoSpecification {

    public static Specification<Pedido> search(Map<String, Object> filters) {
        return ((root, query, criteriaBuilder) -> {
           if(filters.containsKey("filter") && StringUtils.isNotBlank((String)filters.get("filter"))) {
               List<Predicate> predicates = new ArrayList<>();
               predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoPedido")), "%" + (String)((String) filters.get("filter")).toLowerCase() + "%"));
               predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cliente")), "%" + (String)((String) filters.get("filter")).toLowerCase() + "%"));
               return query.where(criteriaBuilder.or(predicates.toArray(new Predicate[0]))).getRestriction();
           }
           else {
               return criteriaBuilder.greaterThan(root.get("id"), 0);
           }
        });
    }
}
