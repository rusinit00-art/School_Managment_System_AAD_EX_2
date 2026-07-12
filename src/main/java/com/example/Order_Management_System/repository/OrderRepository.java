package com.example.Order_Management_System.repository;

import com.example.Order_Management_System.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders o " +
            "JOIN user u ON o.user_user_id = u.user_id " +
            "WHERE (?1 IS NULL OR u.name LIKE %?1%) " +
            "AND (u.role = 'CUSTOMER')",
            nativeQuery = true)
    List<Order> filterOrders(String customerName);

    @Query(value = "SELECT * FROM orders o " +
            "JOIN user u ON o.user_user_id = u.user_id " +
            "WHERE (?1 IS NULL OR u.user_id = ?1) " +
            "AND (u.role = 'CUSTOMER')",
            nativeQuery = true)
    List<Order> getCustomerOrders(Long userId);

    @Query("SELECT o FROM Order o " +
            "JOIN o.user u " +
            "WHERE (:userId IS NULL OR u.id = :userId) " +
            "AND u.role = 'CUSTOMER'")
    List<Order> getCustomerOrdersJPQL(@Param("userId") Long userId);

    // 4. Advanced JPQL
    @Query("SELECT o FROM Order o " +
            "WHERE (:customerId = 0 OR o.user.id = :customerId)")
    List<Order> filterOrdersJPQLAdvance(@Param("customerId") Long customerId);

}