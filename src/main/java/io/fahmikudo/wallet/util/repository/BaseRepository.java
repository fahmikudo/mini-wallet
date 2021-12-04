package io.fahmikudo.wallet.util.repository;

import io.fahmikudo.wallet.util.domain.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseModel> extends JpaRepository<T, String> {
}
