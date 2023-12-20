package Ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;



@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
