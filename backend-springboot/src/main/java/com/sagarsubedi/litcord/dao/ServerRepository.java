package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findServerByNameAndUserId(String name, Long userId);
}
