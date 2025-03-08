package com.zerobase.SelfWash.requestStore.domain.entity.store;

import static com.zerobase.SelfWash.requestStore.domain.type.ReqStatus.PENDING;

import com.zerobase.SelfWash.requestStore.domain.entity.machine.ReqUpdateMachine;
import com.zerobase.SelfWash.requestStore.domain.form.store.ReqUpdateStoreForm;
import com.zerobase.SelfWash.requestStore.domain.type.ReqStatus;
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
@NoArgsConstructor
@AllArgsConstructor
public class ReqUpdateStore {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long storeId;
  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  private String reqStatus;

  @OneToMany(mappedBy = "requestUpdateStore", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ReqUpdateMachine> reqUpdateMachines;

  public static ReqUpdateStore reqFrom(ReqUpdateStoreForm form) {
    ReqUpdateStore updateStoreForm = ReqUpdateStore.builder()
        .storeId(form.getStoreId())
        .ownerId(form.getOwnerId())
        .address(form.getAddress())
        .latitude(form.getLatitude())
        .longitude(form.getLongitude())
        .reqStatus(PENDING.toString())
        .build();

    List<ReqUpdateMachine> machines = form.getReqUpdateMachineForms().stream()
        .map(machineForm -> {
          ReqUpdateMachine machine = ReqUpdateMachine.requestFrom(machineForm);
          machine.setReqUpdateStore(updateStoreForm); // 연관관계 설정
          return machine;
        })
        .collect(Collectors.toList());

    updateStoreForm.setReqUpdateMachines(machines); // 자식 엔티티 설정
    return updateStoreForm;
  }

}
