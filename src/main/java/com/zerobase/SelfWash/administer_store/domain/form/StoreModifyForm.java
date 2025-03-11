package com.zerobase.SelfWash.administer_store.domain.form;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreModifyForm {

  private String address;
  private float latitude;
  private float longitude;
  private String notes;

}
