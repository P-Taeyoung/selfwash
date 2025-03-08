package com.zerobase.SelfWash.requestStore.domain.repository.store;

import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRemoveStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqRemoveStoreRepository extends JpaRepository<ReqRemoveStore, Long> {

}
