package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.Order;
import uz.pdp.appg4duonotaryserver.entity.enums.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@JaversSpringDataAuditable
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "select * from orders where client_id=:client_id and created_at >=:month_from and created_at<= :month_to and order_status=:order_status ", nativeQuery = true)
    List<Order> findAllByClientIdAndOrderStatus(UUID client_id, Timestamp month_from, Timestamp month_to, OrderStatus order_status);

//    @Query(nativeQuery = true, value = "select ldt.active,ldg.amount,ldt.min_amount,order_status from orders join loyalty_discount_given ldg on\n" +
//            "    orders.client_id = ldg.client_id  join loyalty_discount_tariff ldt on orders.amount<ldt.min_amount  and ldt.active")
//    List<Object> findByClientAndAmountAndAmountDiscountAndOrderStatus();

    @Query(value = "select * from orders where client_id=:client_id and order_status=:order_status", nativeQuery = true)
    List<Order> findAllByClientIdAndOrderStatus(UUID client_id ,OrderStatus order_status);

    List<Order> findAllByAgentId(UUID agent_id);

}
