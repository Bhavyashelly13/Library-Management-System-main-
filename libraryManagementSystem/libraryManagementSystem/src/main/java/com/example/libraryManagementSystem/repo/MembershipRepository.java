package com.example.libraryManagementSystem.repo;


import com.example.libraryManagementSystem.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findByMembershipNumber(String membershipNumber);
    List<Membership> findByStatus(String status);
    List<Membership> findByMembershipType(String membershipType);
}
