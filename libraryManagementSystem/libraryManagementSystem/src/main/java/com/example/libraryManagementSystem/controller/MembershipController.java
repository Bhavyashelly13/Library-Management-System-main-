package com.example.libraryManagementSystem.controller;

import com.example.libraryManagementSystem.dto.ResponseWrapper;
import com.example.libraryManagementSystem.entity.Membership;
import com.example.libraryManagementSystem.service.MembershipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/membership")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Membership>>> getAll() {
        List<Membership> memberships = membershipService.findAll();
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Memberships retrieved", memberships));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Membership>> getById(@PathVariable Long id) {
        Membership membership = membershipService.findById(id);
        if (membership == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Membership retrieved", membership));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<Membership>> addMembership(@RequestBody Membership membership) {
        Membership saved = membershipService.save(membership);
        log.info("✓ Membership added: {}", membership.getMembershipNumber());
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Membership added", saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ResponseWrapper<Membership>> updateMembership(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String membershipType = request.get("membershipType");
        Membership updated = membershipService.updateMembership(id, membershipType);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("✓ Membership updated: {}", id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Membership updated", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deleteMembership(@PathVariable Long id) {
        membershipService.delete(id);
        log.info("✓ Membership deleted: {}", id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Membership deleted", null));
    }
}
