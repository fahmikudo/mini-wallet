package io.fahmikudo.wallet.repository;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.util.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {
}
