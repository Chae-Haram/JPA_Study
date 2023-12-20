package Ex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Long id;

    private Address address;

    public AddressEntity(String city, String street, String zipcode) {
        address = new Address(city, street, zipcode);
    }
}
