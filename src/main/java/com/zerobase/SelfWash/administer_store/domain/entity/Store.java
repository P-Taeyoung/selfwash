package com.zerobase.SelfWash.administer_store.domain.entity;

import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.form.StoreForm;
import com.zerobase.SelfWash.administer_store.domain.form.StoreModifyForm;
import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;


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

  //위도,경도 정보
  @Column(columnDefinition = "POINT")
  private Point location;

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
        .location(createPoint(form.getLongitude(), form.getLatitude()))
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

  public void modify(StoreModifyForm form) {
    this.setAddress(form.getAddress());
    this.location = createPoint(form.getLongitude(), form.getLatitude());
    this.setNotes(form.getNotes());
  }

  // 연관관계 설정에 따라서 부모 엔티티 변경에 따른 자식 엔티티 동기화를 위한 메서드
  public Machine addMachine (Machine machine) {
    machines.add(machine);
    machine.setStore(this);
    return machine;
  }

  public void removeMachine (Machine machine) {
    machines.remove(machine);
    machine.setStore(null);
  }


  private static Point createPoint(double longitude, double latitude) {
    GeometryFactory geometryFactory = new GeometryFactory();
    return geometryFactory.createPoint(new Coordinate(longitude, latitude));
  }


}
