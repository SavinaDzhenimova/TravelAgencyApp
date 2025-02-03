package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Candidate;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);

    Optional<Candidate> findByPhoneNumber(String phoneNumber);
}
