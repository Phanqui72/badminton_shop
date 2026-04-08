package com.mgr.api.repository.nation;

import com.mgr.api.model.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long>, JpaSpecificationExecutor<Nation> {
    List<Nation> findAllByKind(Integer kind);
    List<Nation> findAllByParentId(Long parentId);
}
