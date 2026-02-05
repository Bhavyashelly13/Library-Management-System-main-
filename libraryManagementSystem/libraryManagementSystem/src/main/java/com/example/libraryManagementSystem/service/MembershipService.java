package com.example.libraryManagementSystem.service;


import com.example.libraryManagementSystem.entity.Membership;
import com.example.libraryManagementSystem.repo.MembershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class MembershipService {

    @Autowired
    private MembershipRepository membershipRepository;

    public List<Membership> findAll() {
        return membershipRepository.findAll();
    }

    public Membership findById(Long id) {
        return membershipRepository.findById(id).orElse(null);
    }

    public Membership save(Membership membership) {
        if (membership.getMembershipNumber() == null) {
            membership.setMembershipNumber(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        }
        if (membership.getStatus() == null) {
            membership.setStatus("ACTIVE");
        }
        log.info("Membership saved: {} - {}", membership.getMembershipNumber(), membership.getMemberName());
        return membershipRepository.save(membership);
    }

    public void delete(Long id) {
        membershipRepository.deleteById(id);
        log.info("Membership deleted: {}", id);
    }

    public List<Membership> findByStatus(String status) {
        return membershipRepository.findByStatus(status);
    }

    public Membership addMembership(String memberName, String memberEmail, String memberPhone, 
                                   String membershipType, Double membershipFee) {
        Membership m = new Membership();
        m.setMemberName(memberName);
        m.setMemberEmail(memberEmail);
        m.setMemberPhone(memberPhone);
        m.setMembershipType(membershipType);
        m.setMembershipFee(membershipFee);
        m.setStatus("ACTIVE");
        m.setStartDate(LocalDate.now());
        
        LocalDate expiry = LocalDate.now();
        if ("6_MONTHS".equals(membershipType)) {
            expiry = expiry.plusMonths(6);
        } else if ("1_YEAR".equals(membershipType)) {
            expiry = expiry.plusYears(1);
        } else if ("2_YEARS".equals(membershipType)) {
            expiry = expiry.plusYears(2);
        }
        m.setExpiryDate(expiry);
        
        return save(m);
    }

    public Membership updateMembership(Long id, String membershipType) {
        Membership m = findById(id);
        if (m != null) {
            LocalDate newExpiry = LocalDate.now();
            if ("6_MONTHS".equals(membershipType)) {
                newExpiry = newExpiry.plusMonths(6);
            } else if ("1_YEAR".equals(membershipType)) {
                newExpiry = newExpiry.plusYears(1);
            } else if ("2_YEARS".equals(membershipType)) {
                newExpiry = newExpiry.plusYears(2);
            }
            m.setExpiryDate(newExpiry);
            m.setMembershipType(membershipType);
            return save(m);
        }
        return null;
    }
}
