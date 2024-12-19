package com.sagarsubedi.litcord.model;

import com.sagarsubedi.litcord.enums.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    //Id of the admin of the channel i.e user who created it
    @Column(name = "adminId", nullable = false)
    private Long adminId;

    private ChannelType type;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    public Channel(String name, Long adminId, Server server, ChannelType type){
        this.name = name;
        this.adminId = adminId;
        this.server = server;
        this.type = type;
    }
}
