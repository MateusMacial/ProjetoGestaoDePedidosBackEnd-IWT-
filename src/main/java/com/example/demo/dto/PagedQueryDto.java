package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PagedQueryDto {
    private List<?> list;
    private int page;
    private int rowsPerPage;
    private long rowsNumber;
    private boolean descending;
    private String sortBy;
    private Map<String, Object> filter;

    @JsonIgnore
    public PageRequest getPageRequest(){
        if(StringUtils.isNotBlank(this.sortBy)){
            return PageRequest.of(this.page, this.rowsPerPage, descending ? Sort.Direction.DESC : Sort.Direction.ASC, this.sortBy);
        }
        else{
            return PageRequest.of(this.page, this.rowsPerPage);
        }
    }

    @JsonIgnore
    public PageRequest getPageRequest(List<String> parametros){
        return PageRequest.of(this.page, (int)this.rowsPerPage, this.getSort(parametros));
    }

    @JsonIgnore
    public Sort getSort(List<String> defaultParametroSortList) {
        String[] parametros;
        List<String> listaParam;
        if (StringUtils.isBlank(this.sortBy)) {
            listaParam = defaultParametroSortList;
        } else {
            listaParam = new ArrayList<>();
            listaParam.add(this.sortBy);
        }
        parametros = listaParam.toArray(new String[0]);

        return Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, parametros);
    }

    @JsonIgnore
    public Sort getSort(){
        if(StringUtils.isBlank(this.sortBy)){
            return null;
        }
        return Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, this.sortBy);
    }

    @JsonIgnore
    public Sort getSort(String field, Sort.Direction direction){
        Sort s = getSort();
        if(s == null){
            s = Sort.by(direction, field);
        }
        return s;
    }

    @JsonIgnore
    public Sort getSort(String field){
        Sort s = getSort();
        if(s == null){
            s = Sort.by(Sort.Direction.ASC, field);
        }
        return s;
    }
}
