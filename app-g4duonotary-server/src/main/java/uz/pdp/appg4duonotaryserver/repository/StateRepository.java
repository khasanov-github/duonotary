package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.State;
import uz.pdp.appg4duonotaryserver.payload.StateDto;

import java.util.List;
import java.util.UUID;

@JaversSpringDataAuditable
public interface StateRepository extends JpaRepository<State, UUID> {

}