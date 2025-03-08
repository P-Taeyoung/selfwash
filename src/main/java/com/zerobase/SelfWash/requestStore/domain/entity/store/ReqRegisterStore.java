package com.zerobase.SelfWash.requestStore.domain.entity.store;

import static com.zerobase.SelfWash.requestStore.domain.type.ReqStatus.PENDING;

import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import com.zerobase.SelfWash.requestStore.domain.entity.machine.ReqRegisterMachine;
import com.zerobase.SelfWash.requestStore.domain.form.store.ReqRegisterStoreForm;
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
public class ReqRegisterStore extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  private String reqStatus;

  @OneToMany(mappedBy = "requestRegisterStore", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ReqRegisterMachine> reqRegisterMachines;

  public static ReqRegisterStore reqFrom(ReqRegisterStoreForm form) {
    ReqRegisterStore reqRegisterStore = ReqRegisterStore.builder()
        .ownerId(form.getOwnerId())
        .address(form.getAddress())
        .latitude(form.getLatitude())
        .longitude(form.getLongitude())
        .reqStatus(PENDING.toString())
        .build();

    List<ReqRegisterMachine> machines = form.getReqRegisterMachineForms().stream()
        .map(machineForm -> {
          ReqRegisterMachine machine = ReqRegisterMachine.requestFrom(machineForm);
          machine.setReqRegisterStore(reqRegisterStore); // 연관관계 설정
          return machine;
        })
        .collect(Collectors.toList());

    reqRegisterStore.setReqRegisterMachines(machines); // 자식 엔티티 설정
    return reqRegisterStore;
  }

}
