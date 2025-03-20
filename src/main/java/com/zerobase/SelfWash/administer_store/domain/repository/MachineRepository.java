package com.zerobase.SelfWash.administer_store.domain.repository;

import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

  @Query("SELECT m FROM Machine m WHERE (:time IS NOT NULL AND m.endTime <= :time)")
  Page<Machine> findMachinesEndingBefore(@Param("time")  LocalDateTime endTime, Pageable pageable);

  List<Machine> findAllByStoreId(Long storeId);
}
