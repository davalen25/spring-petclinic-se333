package org.springframework.samples.petclinic.owner;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OwnerControllerUnitTests {

	private final OwnerRepository owners = mock(OwnerRepository.class);

	private final OwnerController controller = new OwnerController(this.owners);

	@Test
	void showOwnerThrowsWhenOwnerMissing() {
		given(this.owners.findById(999)).willReturn(Optional.empty());

		assertThatThrownBy(() -> this.controller.showOwner(999)).isInstanceOf(IllegalArgumentException.class);
	}

}
