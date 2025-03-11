package com.zerobase.SelfWash.administer_store.service.impl;

import com.zerobase.SelfWash.administer_store.domain.dto.StoreDto;
import com.zerobase.SelfWash.administer_store.domain.entity.Store;
import com.zerobase.SelfWash.administer_store.domain.form.StoreForm;
import com.zerobase.SelfWash.administer_store.domain.form.StoreModifyForm;
import com.zerobase.SelfWash.administer_store.domain.repository.MachineRepository;
import com.zerobase.SelfWash.administer_store.domain.repository.StoreRepository;
import com.zerobase.SelfWash.administer_store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

  private final StoreRepository storeRepository;

  @Override
  @Transactional
  public StoreDto create(StoreForm form) {
    return StoreDto.from(storeRepository.save(Store.from(form)));
  }

  @Override
  @Transactional
  public StoreDto search(Long storeId) {
    return StoreDto.from(storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다.")));
  }

  @Override
  @Transactional
  public void modify(Long storeId, StoreModifyForm form) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    store.modify(form);
  }

  @Override
  @Transactional
  public void remove(Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    //운영 중단을 먼저 한 후 폐점 요청 가능
    if (store.isOpened()) {
      throw new RuntimeException("운영 중에는 폐점이 불가능합니다.");
    }

    store.setWithdraw(true);
  }

  @Override
  @Transactional
  public void approve(Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new RuntimeException("해당하는 매장 정보가 없습니다."));

    if (store.isWithdraw()) {
      throw new RuntimeException("등록 승인이 불가한 매장입니다.");
    }

    if (store.isApproved()) {
      throw new RuntimeException("이미 등록 승인된 매장입니다.");
    }

    store.setApproved(true);
  }

}
