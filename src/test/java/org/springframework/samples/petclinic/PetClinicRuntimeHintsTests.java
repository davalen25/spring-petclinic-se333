package org.springframework.samples.petclinic;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.vet.Vet;

import static org.assertj.core.api.Assertions.assertThat;

class PetClinicRuntimeHintsTests {

	@Test
	void registerHintsRegistersResourcePatternsAndSerializableTypes() {
		RuntimeHints hints = new RuntimeHints();
		PetClinicRuntimeHints runtimeHints = new PetClinicRuntimeHints();

		runtimeHints.registerHints(hints, getClass().getClassLoader());

		assertThat(RuntimeHintsPredicates.resource().forResource("db/schema.sql").test(hints)).isTrue();
		assertThat(RuntimeHintsPredicates.resource().forResource("messages/messages.properties").test(hints)).isTrue();
		assertThat(RuntimeHintsPredicates.resource().forResource("mysql-default-conf").test(hints)).isTrue();
		assertThat(RuntimeHintsPredicates.serialization().onType(BaseEntity.class).test(hints)).isTrue();
		assertThat(RuntimeHintsPredicates.serialization().onType(Person.class).test(hints)).isTrue();
		assertThat(RuntimeHintsPredicates.serialization().onType(Vet.class).test(hints)).isTrue();
	}

}
