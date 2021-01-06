package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;

@Data
public class ReqSignIn {
    private String userName;
    private String password;
}
