package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate; // 주문 시간
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    // 일반적인 SQL을 사용할 때 처럼 원하는 값을 선택해서 조회
    // new 명령어를 사용해서 JPQL의 결과를 DTO로 즉시 변환
    // SELECT 절에서 원하는 데이터를 직접 선택하므로 DB 애플리케이션 네트웍 용량 최적화(생각보다 미비)
    // 리포지토리 재사용성 떨어짐, API 스펙에 맞춘 코드가 리포지토리에 들어가는 단점
}
