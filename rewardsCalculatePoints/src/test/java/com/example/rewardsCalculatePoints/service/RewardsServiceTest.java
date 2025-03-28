package com.example.rewardsCalculatePoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.rewardsCalculatePoints.dto.RewardsResponseDTO;
import com.example.rewardsCalculatePoints.model.Transaction;
import com.example.rewardsCalculatePoints.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RewardsServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RewardsService rewardsService;

	private List<Transaction> mockTransactions;

	@BeforeEach
	void setUp() {
		// Mock transactions for testing
		mockTransactions = Arrays.asList(new Transaction(1L, 500d, LocalDateTime.now()),
				new Transaction(1L, 650, LocalDateTime.now().minusMonths(1)),
				new Transaction(1L, 450, LocalDateTime.now().minusMonths(2)),
				new Transaction(2L, 300, LocalDateTime.now()),
				new Transaction(2L, 500d, LocalDateTime.now().minusMonths(1)),
				new Transaction(2L, 600, LocalDateTime.now().minusMonths(2)),
				new Transaction(3L, 1000, LocalDateTime.now()),
				new Transaction(3L, 500d, LocalDateTime.now().minusMonths(1)),
				new Transaction(3L, 100, LocalDateTime.now().minusMonths(2)));

		// Mock repository behavior
		when(transactionRepository.findByTransactionDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
				.thenReturn(mockTransactions);

		when(transactionRepository.saveAll(anyList())).thenReturn(mockTransactions);
	}

	@Test
	void testCalculateRewards() {
		// Act
		List<RewardsResponseDTO> rewards = rewardsService.calculateRewards();

		// Assert
		assertNotNull(rewards);
		assertEquals(3, rewards.size()); // 3 customers expected

		// Verify customer 1's total points
		RewardsResponseDTO customer1 = rewards.stream().filter(r -> r.getCustomerId() == 1L).findFirst().orElse(null);
		assertNotNull(customer1);
		assertTrue(customer1.getTotalPoints() > 0);

		// Verify repository method calls
		verify(transactionRepository, times(1)).saveAll(anyList());
		verify(transactionRepository, times(1)).findByTransactionDateBetween(any(LocalDateTime.class),
				any(LocalDateTime.class));
	}
}
