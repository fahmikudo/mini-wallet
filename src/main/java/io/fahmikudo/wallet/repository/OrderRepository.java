package io.fahmikudo.wallet.repository;

import io.fahmikudo.wallet.domain.Order;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.util.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<Order> {

    Optional<Order> findByOrderNoAndIsDeleted(String orderNo, boolean isDeleted);

    Page<Order> findByUserAndIsDeleted(User user, Pageable pageable, boolean isDeleted);

    Optional<Order> findByOrderNoAndUserAndIsDeleted(String orderNo, User user, boolean isDeleted);

}
