package com.example.rewardsCalculatePoints.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rewardsCalculatePoints.dto.RewardsResponseDTO;
import com.example.rewardsCalculatePoints.service.RewardsService;

@RestController
@RequestMapping("api/rewardsPoints")
public class RewardsController {
	private final RewardsService rewardsService;

	public RewardsController(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
	}

	@GetMapping
	public List<RewardsResponseDTO> getRewards() {
		return rewardsService.calculateRewards();
	}

}
