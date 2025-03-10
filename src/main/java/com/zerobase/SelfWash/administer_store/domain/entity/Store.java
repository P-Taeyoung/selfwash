package com.zerobase.SelfWash.administer_store.domain.entity;

import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.form.StoreForm;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  //매장기본 정보
  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  //운영현황
  private boolean opened;
  //승인여부
  private boolean approved;
  //비고
  private String notes;
  //삭제예정데이터
  private boolean withdraw;

  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Machine> machines;

  public static Store from(StoreForm form) {
    Store store = Store.builder()
        .ownerId(form.getOwnerId())
        .address(form.getAddress())
        .latitude(form.getLatitude())
        .longitude(form.getLongitude())
        .notes(form.getNotes())
        .opened(false)
        .approved(false)
        .withdraw(false)
        .build();
    store.setMachines(machineFrom(store, form.getMachineForms())); // 자식 엔티티 설정

    return store;
  }

  public static List<Machine> machineFrom (Store store, List<MachineForm> machineForms) {
    return machineForms.stream().map(
        machineForm -> {
          Machine machine = Machine.from(machineForm);
          machine.setStore(store);
          return machine;
        }).collect(Collectors.toList());
  }

  public void addMachine (Machine machine) {
    machines.add(machine);
    machine.setStore(this);
  }

  public void removeMachine (Machine machine) {
    machines.remove(machine);
    machine.setStore(null);
  }
}
