package com.mgr.api.model.criteria;

import com.mgr.api.model.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserCriteria {
    private Long id;
    private Integer status;
    private Integer gender;
    private String fullName;
    private String phone;
    private String email;

    public Specification<User> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (gender != null) {
                predicates.add(cb.equal(root.get("gender"), gender));
            }
            if (StringUtils.hasText(fullName)) {
                predicates.add(cb.like(cb.lower(root.get("account").get("fullName")), "%" + fullName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(phone)) {
                predicates.add(cb.like(cb.lower(root.get("account").get("phone")), "%" + phone.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(cb.like(cb.lower(root.get("account").get("email")), "%" + email.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
