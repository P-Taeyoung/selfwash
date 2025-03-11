package com.zerobase.SelfWash.owner_inquiry.domain.repository;

import com.zerobase.SelfWash.owner_inquiry.domain.entity.OwnerInquiry;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerInquiryRepository extends JpaRepository<OwnerInquiry, Long> {
  List<OwnerInquiry> findAllByOwnerId(Long ownerId);
}
