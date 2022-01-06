package com.example.demo.dao.specifications;

import com.example.demo.entidades.Produto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;

public class ProdutoSpecification {

    public static Specification<Produto> search(Map<String, Object> filters) {
        return ((root, query, criteriaBuilder) -> {
           if(filters.containsKey("filter") && StringUtils.isNotBlank((String)filters.get("filter"))) {
               List<Predicate> predicates = new ArrayList<>();
               predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoProduto")), "%" + (String)((String) filters.get("filter")).toLowerCase() + "%"));
               predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("descricaoProduto")), "%" + (String)((String) filters.get("filter")).toLowerCase() + "%"));
               return query.where(criteriaBuilder.or(predicates.toArray(new Predicate[0]))).getRestriction();
           }
           else {
               return criteriaBuilder.greaterThan(root.get("id"), 0);
           }
        });
    }
}
