package com.zerobase.SelfWash.administer_store.domain.dto;

import com.zerobase.SelfWash.administer_store.domain.entity.Machine;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.type.MachineType;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineRedisDto {

  private long machineId;
  private MachineType machineType;
  private UsageStatus status;
  private LocalDateTime endTime;


  public static MachineRedisDto toDto(Machine machine) {
    return MachineRedisDto.builder()
        .machineId(machine.getId())
        .machineType(machine.getMachineType())
        .status(machine.getUsageStatus())
        .endTime(machine.getEndTime())
        .build();
  }

  public Map<String, String> toHashMap() {
    Map<String, String> map = new HashMap<>();
    map.put("machineId", String.valueOf(this.getMachineId()));
    map.put("machineType", String.valueOf(this.getMachineType()));
    map.put("status", this.getStatus().toString());
    map.put("endTime", this.getEndTime()!= null ? endTime.toString() : null);
    return map;
  }
}
