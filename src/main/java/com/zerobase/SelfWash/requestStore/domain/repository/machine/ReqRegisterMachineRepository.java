package com.zerobase.SelfWash.requestStore.domain.repository.machine;

import com.zerobase.SelfWash.requestStore.domain.entity.machine.ReqRegisterMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReqRegisterMachineRepository extends JpaRepository<ReqRegisterMachine, Long> {

}
