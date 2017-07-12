package org.neutrinocms.core.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.neutrinocms.core.model.translation.Translation;
import org.springframework.data.jpa.domain.Specification;

public class TranslationSpecification<T extends Translation> extends IdProviderSpecification<T>{

	public Specification<T> someThingToDo(String someThing) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.get("name"), "%" + someThing + "%");
			}
		};
	}

}
