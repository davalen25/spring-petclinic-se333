package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class PetClinicApplicationTests {

	@Test
	void mainDelegatesToSpringApplicationRun() {
		String[] args = { "--spring.main.web-application-type=none" };
		try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
			PetClinicApplication.main(args);
			springApplication.verify(() -> SpringApplication.run(PetClinicApplication.class, args));
		}
	}

}
