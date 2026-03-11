package org.springframework.samples.petclinic.system;

import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CacheConfigurationTests {

	@Test
	void customizerCreatesVetsCacheWithStatisticsEnabled() {
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		JCacheManagerCustomizer customizer = cacheConfiguration.petclinicCacheConfigurationCustomizer();
		CacheManager cacheManager = mock(CacheManager.class);
		when(cacheManager.createCache(eq("vets"), any(Configuration.class))).thenReturn(null);

		customizer.customize(cacheManager);

		verify(cacheManager).createCache(eq("vets"), any(Configuration.class));
		assertThat(cacheManager.getCache("vets")).isNull();
	}

}
