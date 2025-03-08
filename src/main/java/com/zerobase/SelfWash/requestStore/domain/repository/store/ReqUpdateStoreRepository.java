package com.zerobase.SelfWash.requestStore.domain.repository.store;

import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqUpdateStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqUpdateStoreRepository extends JpaRepository<ReqUpdateStore, Long> {

}
