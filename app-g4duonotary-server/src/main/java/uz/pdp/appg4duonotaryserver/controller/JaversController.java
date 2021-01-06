package uz.pdp.appg4duonotaryserver.controller;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.entity.State;

import java.util.List;

@RestController
@RequestMapping("/api/javers")
public class JaversController {

    private final Javers javers;

    public JaversController(Javers javers) {
        this.javers = javers;
    }

    @GetMapping("/state /snapshots")
    public String getStoresSnapshots() {
        QueryBuilder jqlQuery = QueryBuilder.byClass(State.class);
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
        return javers.getJsonConverter().toJson(snapshots);
    }
}
