package com.zerobase.SelfWash.requestStore.domain.form.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqRemoveStoreForm {

  private long ownerId;
  private long storeId;
  private String reason;

}
