package com.zerobase.SelfWash.administer_store.domain.repository;

import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}
