package org.neutrinocms.core.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.neutrinocms.core.bean.SearchCriteria;
import org.neutrinocms.core.model.IdProvider;
import org.springframework.data.jpa.domain.Specification;

public class IdProviderSpecification<T extends IdProvider> implements Specification<IdProvider>{

	private SearchCriteria criteria;

	public IdProviderSpecification() {
		super();
	}

	public IdProviderSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<IdProvider> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}
	
	public static <T> Specification<T> idIn(List<Integer> ids) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return root.get("id").in(ids);
			}
		};
	}
	
	public static <T> Specification<T> idEqualsTo(Integer id) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get("id"), id);
			}
		};
	}
	
	
	public static <T> Specification<T> itsNameContains(String searchValue) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.get("name"), "%" + searchValue + "%");
			}
		};
	}
	
	public static <T> Specification<T> isNotAffected(String fieldName) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.isNull(root.get(fieldName));
			}
		};
	}
	
	public static <T> Specification<T> itsFieldIsAffectedTo(String fieldName, Integer id) {
		return new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(fieldName).get("id"), id);
			}
		};
	}

}
