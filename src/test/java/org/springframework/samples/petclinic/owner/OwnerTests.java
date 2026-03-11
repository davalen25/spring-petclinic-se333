package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTests {

	@Test
	void getPetByNameRespectsIgnoreNewFlag() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Fluffy");
		owner.addPet(pet);

		assertThat(owner.getPet("fluffy", true)).isNull();
		assertThat(owner.getPet("fluffy", false)).isSameAs(pet);
	}

	@Test
	void getPetByIdReturnsExistingPet() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Rex");
		owner.addPet(pet);
		pet.setId(7);

		assertThat(owner.getPet(7)).isSameAs(pet);
		assertThat(owner.getPet(999)).isNull();
	}

}
