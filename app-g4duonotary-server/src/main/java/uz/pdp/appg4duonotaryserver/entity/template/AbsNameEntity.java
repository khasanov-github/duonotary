package uz.pdp.appg4duonotaryserver.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbsNameEntity extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String name;

    private boolean active;

    @Column(columnDefinition = "text")
    private String description;
}
