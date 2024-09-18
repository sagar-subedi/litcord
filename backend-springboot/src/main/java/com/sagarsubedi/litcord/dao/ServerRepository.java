package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
}
