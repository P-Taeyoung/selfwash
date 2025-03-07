package com.zerobase.SelfWash.requestStore.domain.repository.store;

import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqRegisterStoreRepository extends JpaRepository<ReqRegisterStore, Long> {

}
