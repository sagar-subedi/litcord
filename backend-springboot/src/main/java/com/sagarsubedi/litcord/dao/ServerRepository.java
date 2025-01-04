package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findServerByNameAndUserId(String name, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
    Optional<Server> findByInviteCode(String inviteCode);
    //Try to refactor it, if we insert admin in membership table, the query can be simplified
    @Query("SELECT DISTINCT s FROM Server s  LEFT JOIN Membership m on s.id = m.serverId WHERE s.userId = :userId OR m.userId = :userId")
    List<Server> findAllByUserId(Long userId);
}
