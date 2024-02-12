package com.nttdata.bootcamp.clientmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientMessage {
    private String cellPhoneNumber;
    private String imei;
    private String email;
}
