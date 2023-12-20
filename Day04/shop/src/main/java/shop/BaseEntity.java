package shop;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   // 상속관계 매핑X, 엔티티X, 테이블과 매핑X
                    // 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용
public abstract class BaseEntity {  // 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
                                    // @Entity 클래스는 엔티티나 @MappedSuperclass로 지정한 클래스만 상속 가능
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
