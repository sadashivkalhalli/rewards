package com.example.rewardsCalculatePoints.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.rewardsCalculatePoints.dto.RewardsResponseDTO;
import com.example.rewardsCalculatePoints.model.Transaction;
import com.example.rewardsCalculatePoints.repository.TransactionRepository;

@Service
public class RewardsService {
	private final TransactionRepository transactionRepository;

	public RewardsService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public List<RewardsResponseDTO> calculateRewards() {
		List<Transaction> list = new ArrayList<>();
		list.add(new Transaction(1l, 500d, LocalDateTime.now()));
		list.add(new Transaction(1l, 650, LocalDateTime.now().minusMonths(1)));
		list.add(new Transaction(1l, 450, LocalDateTime.now().minusMonths(2)));

		list.add(new Transaction(2l, 300, LocalDateTime.now()));
		list.add(new Transaction(2l, 500d, LocalDateTime.now().minusMonths(1)));
		list.add(new Transaction(2l, 600, LocalDateTime.now().minusMonths(2)));

		list.add(new Transaction(3l, 1000, LocalDateTime.now()));
		list.add(new Transaction(3l, 500d, LocalDateTime.now().minusMonths(1)));
		list.add(new Transaction(3l, 100, LocalDateTime.now().minusMonths(2)));

		transactionRepository.saveAll(list);
		LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
		List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(threeMonthsAgo,
				LocalDateTime.now());

		Map<Long, Map<YearMonth, Integer>> customerPoints = new HashMap<>();

		for (Transaction transaction : transactions) {
			int points = calculatePoints(transaction.getAmount());
			YearMonth month = YearMonth.from(transaction.getTransactionDate());

			customerPoints.computeIfAbsent(transaction.getCustomerId(), k -> new HashMap<>()).merge(month, points,
					Integer::sum);
		}

		return customerPoints.entrySet().stream().map(entry -> {
			int totalPoints = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
			return new RewardsResponseDTO(entry.getKey(), entry.getValue(), totalPoints);
		}).collect(Collectors.toList());
	}

	private int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100;
		}
		if (amount > 50) {
			points += (amount - 50);
		}
		return points;
	}
}
