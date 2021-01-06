package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.MainService;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin
//@RepositoryRestResource(path = "mainService",exported = false, collectionResourceRel = "list",excerptProjection = CustomMainService.class)
public interface MainServiceRepository extends JpaRepository<MainService, UUID> {
//
    boolean existsByFromTimeAndTillTimeAndName(LocalTime fromTime, LocalTime tillTime, String name);

    boolean existsByName(String name);

    MainService findByFromTimeAndTillTimeAndName(LocalTime fromTime, LocalTime tillTime, String name);

    Optional<MainService> findByActiveTrueAndOnlineFalse();
//
//    MainService findByFromTimeAndTillTime(LocalTime fromTime, LocalTime tillTime);
//
//    Optional<MainService> findByActiveTrueAndOnlineFalse();
}
