package uz.pdp.appg4duonotaryserver.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appg4duonotaryserver.entity.LoyaltyDiscountTariff;

import javax.xml.crypto.Data;

@Projection(name = "customLoyaltyDiscountTariff", types = LoyaltyDiscountTariff.class)
public interface CustomLoyaltyDiscountTariff {

    Data getStartDate();

    Double getPercent();

    boolean isActive();

    Data getMonth();

    Double getMinAmount();
}
