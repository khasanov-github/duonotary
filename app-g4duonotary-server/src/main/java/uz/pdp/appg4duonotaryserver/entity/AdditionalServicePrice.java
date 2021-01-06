package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdditionalServicePrice extends AbsEntity {

    @ManyToOne(optional = false)
    private AdditionalService additionalService;

    @ManyToOne(optional = false)
    private ZipCode zipCode;

    @Column(nullable = false)
    private Double price;

    private boolean active;
}
