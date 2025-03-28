package com.example.rewardsCalculatePoints.dto;

import java.time.YearMonth;
import java.util.Map;

public class RewardsResponseDTO {
	private Long customerId;
	private Map<YearMonth, Integer> monthlyPoints;
	private int totalPoints;

	public RewardsResponseDTO() {
	}

	public RewardsResponseDTO(Long customerId, Map<YearMonth, Integer> monthlyPoints, int totalPoints) {
		this.customerId = customerId;
		this.monthlyPoints = monthlyPoints;
		this.totalPoints = totalPoints;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Map<YearMonth, Integer> getMonthlyPoints() {
		return monthlyPoints;
	}

	public void setMonthlyPoints(Map<YearMonth, Integer> monthlyPoints) {
		this.monthlyPoints = monthlyPoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
}
