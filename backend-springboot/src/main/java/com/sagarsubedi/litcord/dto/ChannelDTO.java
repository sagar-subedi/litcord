package com.sagarsubedi.litcord.dto;

import com.sagarsubedi.litcord.enums.ChannelType;
import lombok.Data;

@Data
public class ChannelDTO {
    private Long id;
    private String name;
    private Long adminId;
    private Long serverId;
    private ChannelType type;
}
