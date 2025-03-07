package com.zerobase.SelfWash.requestStore.service.impl;

import com.zerobase.SelfWash.requestStore.domain.dto.store.ReqRegisterStoreDto;
import com.zerobase.SelfWash.requestStore.domain.entity.store.ReqRegisterStore;
import com.zerobase.SelfWash.requestStore.domain.form.store.ReqRegisterStoreForm;
import com.zerobase.SelfWash.requestStore.domain.repository.store.ReqRegisterStoreRepository;
import com.zerobase.SelfWash.requestStore.service.ReqStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReqRegisterStoreServiceImpl implements ReqStoreService {

  private final ReqRegisterStoreRepository reqRegisterStoreRepository;


  @Override
  public Object create(Object form) {
    return ReqRegisterStoreDto.from(
        reqRegisterStoreRepository.save(ReqRegisterStore.reqFrom((ReqRegisterStoreForm) form)));
  }

  @Override
  public Object modify(Object form) {
    return null;
  }

  @Override
  public Object read(Long id) {
    return null;
  }

  @Override
  public void remove(Long id) {

  }
}
