package com.example.rewardsCalculatePoints.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rewardsCalculatePoints.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
}
