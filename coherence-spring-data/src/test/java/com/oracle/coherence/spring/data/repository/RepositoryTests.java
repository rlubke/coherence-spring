package com.oracle.coherence.spring.data.repository;

import javax.inject.Inject;

import com.oracle.coherence.spring.configuration.annotation.EnableCoherence;
import com.oracle.coherence.spring.data.AbstractDataTest;
import com.oracle.coherence.spring.data.config.EnableCoherenceRepositories;
import com.oracle.coherence.spring.data.model.repositories.CoherenceBookRepository;
import com.tangosol.util.Filters;
import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(RepositoryTests.Config.class)
@DirtiesContext
public class RepositoryTests extends AbstractDataTest {

	@Inject
	protected CoherenceBookRepository bookRepository;

	@Test
	public void ensureSimpleUsage() {
		assertThat(this.bookRepository.count(Filters.equal("author", FRANK_HERBERT))).isEqualTo(2);
	}

	@Configuration
	@EnableCoherence
	@EnableCoherenceRepositories("com.oracle.coherence.spring.data.model.repositories")
	public static class Config {
	}
}
