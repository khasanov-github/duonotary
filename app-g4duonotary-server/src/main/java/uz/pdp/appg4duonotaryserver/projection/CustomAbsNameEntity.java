package uz.pdp.appg4duonotaryserver.projection;

import java.util.UUID;

public interface CustomAbsNameEntity {

    UUID getId();

    String getName();

    Boolean getActive();

    String getDescription();
}
