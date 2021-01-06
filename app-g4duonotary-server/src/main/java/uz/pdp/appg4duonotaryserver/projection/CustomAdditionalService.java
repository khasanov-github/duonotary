package uz.pdp.appg4duonotaryserver.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appg4duonotaryserver.entity.AdditionalService;
import uz.pdp.appg4duonotaryserver.entity.Service;

import java.util.List;
import java.util.UUID;

@Projection(name = "customAdditionalService", types = AdditionalService.class)
public interface CustomAdditionalService {
    UUID getId();

    String getName();

    Boolean getActive();

    String getDescription();

    List<Service> getServices();
}
