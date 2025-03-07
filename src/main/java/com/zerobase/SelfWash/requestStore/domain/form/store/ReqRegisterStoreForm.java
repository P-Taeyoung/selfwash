package com.zerobase.SelfWash.requestStore.domain.form.store;

import com.zerobase.SelfWash.requestStore.domain.form.machine.ReqRegisterMachineForm;
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
public class ReqRegisterStoreForm {

  private long ownerId;
  private String address;
  private float latitude;
  private float longitude;

  private List<ReqRegisterMachineForm> reqRegisterMachineForms;

}
