package com.mgr.api.model.criteria;

import com.mgr.api.model.Coupon;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CouponCriteria {
    private Long id;
    private String code;
    private Integer status;

    public Specification<Coupon> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null) predicates.add(cb.equal(root.get("id"), id));
            if (status != null) predicates.add(cb.equal(root.get("status"), status));
            if (code != null) predicates.add(cb.equal(root.get("code"), code));
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
