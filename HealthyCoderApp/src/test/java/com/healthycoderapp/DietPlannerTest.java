package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class DietPlannerTest {
	
	private DietPlanner dietPlanner;
	
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}
	
	@AfterEach
	void afterEach() {
		System.out.println("A unit test was finished.");
	}

	@RepeatedTest(value = 10, name = RepeatedTest.DISPLAY_NAME_PLACEHOLDER)
	void should_ReturnCorrectDietPlan_When_CorrectCoder() {
		
		// given
		Coder coder = new Coder(1.72, 75.0, 34, Gender.MALE);
		DietPlan expected = new DietPlan(2076, 104, 69, 260);
		
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		// then
		assertAll(
			() -> assertEquals(expected.getCalories(), actual.getCalories()),
			() -> assertEquals(expected.getProtein(), actual.getProtein()),
			() -> assertEquals(expected.getFat(), actual.getFat()),
			() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
		);
		
	}

}
