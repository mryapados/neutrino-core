package org.neutrinocms.core.specification;

import java.util.ArrayList;
import java.util.List;

import org.neutrinocms.core.bean.SearchCriteria;
import org.neutrinocms.core.model.IdProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class IdProviderSpecificationBuilder {
    private final List<SearchCriteria> params;
  
    public IdProviderSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }
 
    public IdProviderSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
 
    public Specification<IdProvider> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<IdProvider>> specs = new ArrayList<Specification<IdProvider>>();
        for (SearchCriteria param : params) {
            specs.add(new IdProviderSpecification<IdProvider>(param));
        }
 
        Specification<IdProvider> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
