package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;      // 주문 상품

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    // 주문

    private int orderPrice; // 주문 가격
    private int count;      // 주문 수량

//    protected OrderItem() {} == @NoArgsConstructor(access = AccessLevel.PROTECTED)

    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // == 비지니스 로직 == //
    public void cancel() {
        getItem().addStock(count);  // 재고 수량을 원복해준다
    }

    // == 조회 로직 == //
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
