package org.neutrinocms.core.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.neutrinocms.core.model.IdProvider;
import org.springframework.data.jpa.domain.Specification;

public class IdProviderSpecification<T extends IdProvider>{

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
