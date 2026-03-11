package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PetControllerUnitTests {

	private final OwnerRepository owners = mock(OwnerRepository.class);

	private final PetTypeRepository types = mock(PetTypeRepository.class);

	private final PetController controller = new PetController(this.owners, this.types);

	@Test
	void findOwnerThrowsWhenOwnerMissing() {
		given(this.owners.findById(999)).willReturn(Optional.empty());

		assertThatThrownBy(() -> this.controller.findOwner(999)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void findPetThrowsWhenOwnerMissing() {
		given(this.owners.findById(999)).willReturn(Optional.empty());

		assertThatThrownBy(() -> this.controller.findPet(999, 1)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void processUpdateFormSavesWhenPetIdIsNotFoundOnOwner() {
		PetType hamster = new PetType();
		hamster.setId(3);
		hamster.setName("hamster");
		given(this.types.findPetTypes()).willReturn(List.of(hamster));

		Owner owner = new Owner();
		Pet existing = new Pet();
		existing.setName("petty");
		owner.addPet(existing);
		existing.setId(1);

		Pet petToUpdate = new Pet();
		petToUpdate.setId(99);
		petToUpdate.setName("betty");
		petToUpdate.setBirthDate(LocalDate.of(2015, 2, 12));
		petToUpdate.setType(hamster);

		BindingResult result = new BeanPropertyBindingResult(petToUpdate, "pet");
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String view = this.controller.processUpdateForm(owner, petToUpdate, result, redirectAttributes);

		assertThat(view).isEqualTo("redirect:/owners/{ownerId}");
		assertThat(redirectAttributes.getFlashAttributes()).containsKey("message");
		assertThat(redirectAttributes.getFlashAttributes().get("message")).isEqualTo("Pet details has been edited");
		verify(this.owners).save(any(Owner.class));
	}

	@Test
	void processUpdateFormRejectsDuplicatePetNameForDifferentPetId() {
		Owner owner = new Owner();

		Pet existing = new Pet();
		existing.setName("petty");
		owner.addPet(existing);
		existing.setId(1);

		Pet petToUpdate = new Pet();
		petToUpdate.setId(2);
		petToUpdate.setName("petty");
		petToUpdate.setBirthDate(LocalDate.of(2015, 2, 12));

		BindingResult result = new BeanPropertyBindingResult(petToUpdate, "pet");
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String view = this.controller.processUpdateForm(owner, petToUpdate, result, redirectAttributes);

		assertThat(view).isEqualTo("pets/createOrUpdatePetForm");
		assertThat(result.hasFieldErrors("name")).isTrue();
		assertThat(result.getFieldError("name")).isNotNull();
		assertThat(result.getFieldError("name").getCode()).isEqualTo("duplicate");
	}

}
