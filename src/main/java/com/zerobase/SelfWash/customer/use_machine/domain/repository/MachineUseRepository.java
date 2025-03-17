package com.zerobase.SelfWash.customer.use_machine.domain.repository;

import com.zerobase.SelfWash.customer.use_machine.domain.entity.MachineUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineUseRepository extends JpaRepository<MachineUse, Long> {

}
