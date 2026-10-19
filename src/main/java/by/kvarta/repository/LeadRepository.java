package by.kvarta.repository;

import by.kvarta.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    boolean existsByPhoneAndCreatedAtAfter(String phone, LocalDateTime after);
}
