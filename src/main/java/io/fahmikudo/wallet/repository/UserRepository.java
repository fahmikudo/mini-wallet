package io.fahmikudo.wallet.repository;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.util.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmailAndIsDeleted(String email, boolean isDeleted);

    Optional<User> findByPhoneAndIsDeleted(String phone, boolean isDeleted);

}
