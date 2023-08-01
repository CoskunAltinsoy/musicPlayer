package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType name);
    boolean existsByName(RoleType name);
}
