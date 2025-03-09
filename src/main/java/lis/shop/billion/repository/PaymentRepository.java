package lis.shop.billion.repository;

import lis.shop.billion.entity.OrderItem;
import lis.shop.billion.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
}