package shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;
}
