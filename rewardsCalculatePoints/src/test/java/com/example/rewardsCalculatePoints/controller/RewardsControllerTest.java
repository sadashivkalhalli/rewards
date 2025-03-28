package com.example.rewardsCalculatePoints.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rewardsCalculatePoints.dto.RewardsResponseDTO;
import com.example.rewardsCalculatePoints.service.RewardsService;

@WebMvcTest(RewardsController.class) // Load only the controller
@ExtendWith(SpringExtension.class)
class RewardsControllerTest {

	@Autowired
	private MockMvc mockMvc; // Mock the HTTP requests

	@MockBean
	private RewardsService rewardsService; // Mock service

	@Test
	void testGetRewards() throws Exception {
		// Mock service response
		Map<YearMonth, Integer> pointsMap = new HashMap<>();
		pointsMap.put(YearMonth.of(2024, 3), 100);
		pointsMap.put(YearMonth.of(2024, 2), 200);

		RewardsResponseDTO mockResponse = new RewardsResponseDTO(1L, pointsMap, 300);

		when(rewardsService.calculateRewards()).thenReturn(Arrays.asList(mockResponse));

		// Perform GET request and validate response
		mockMvc.perform(get("/api/rewardsPoints") // Change the endpoint as per your controller
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) // Expect HTTP 200
				.andExpect(jsonPath("$.size()").value(1)) // Check list size
				.andExpect(jsonPath("$[0].customerId").value(1)) // Validate customer ID
				.andExpect(jsonPath("$[0].totalPoints").value(300)); // Validate total points
	}
}
