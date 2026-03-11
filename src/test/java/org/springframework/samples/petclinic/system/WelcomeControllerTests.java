package org.springframework.samples.petclinic.system;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WelcomeControllerTests {

	private final WelcomeController controller = new WelcomeController();

	@Test
	void welcomeReturnsViewName() {
		assertThat(this.controller.welcome()).isEqualTo("welcome");
	}

}
