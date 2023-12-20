package shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;
    private String actor;
}
