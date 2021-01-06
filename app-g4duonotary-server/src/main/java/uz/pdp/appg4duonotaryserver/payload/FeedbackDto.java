package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackDto {
    private UUID id;

    private UUID orderDto;// qaysi zakazligi

    private UUID zipCodeId;//qaysi maxallagaligi

    private UUID userId;//client yoki agent

//    private UserDto userDto;// client yoki agenta

    private boolean agent;//agentligini bilish

    private int rate; //client va agent uchun

    private String text; //agar rate 3dan pas bo'lsa comment yoziwi wart
}