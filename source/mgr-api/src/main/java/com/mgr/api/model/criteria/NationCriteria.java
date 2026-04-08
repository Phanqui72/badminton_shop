package com.mgr.api.model.criteria;

import com.mgr.api.model.Nation;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class NationCriteria {
    private Long id;
    private String name;
    private Integer kind;
    private Long parentId;
    private Integer status;

    public Specification<Nation> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (kind != null) {
                predicates.add(cb.equal(root.get("kind"), kind));
            }
            if (parentId != null) {
                predicates.add(cb.equal(root.get("parentId"), parentId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
