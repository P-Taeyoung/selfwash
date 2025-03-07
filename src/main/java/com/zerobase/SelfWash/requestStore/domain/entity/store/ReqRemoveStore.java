package com.zerobase.SelfWash.requestStore.domain.entity.store;

import com.zerobase.SelfWash.requestStore.domain.form.store.ReqRemoveStoreForm;
import com.zerobase.SelfWash.requestStore.domain.type.ReqStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqRemoveStore {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long ownerId;
  private long storeId;
  private String reason;
  private String reqStatus;

  public static ReqRemoveStore reqFrom(ReqRemoveStoreForm form) {
    return ReqRemoveStore.builder()
        .ownerId(form.getOwnerId())
        .storeId(form.getStoreId())
        .reason(form.getReason())
        .reqStatus(ReqStatus.PENDING.toString())
        .build();
  }

}
