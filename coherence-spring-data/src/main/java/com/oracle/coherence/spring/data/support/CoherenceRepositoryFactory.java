/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oracle.coherence.spring.data.support;

import java.lang.reflect.Method;
import java.util.Optional;

import com.oracle.coherence.spring.data.core.mapping.CoherenceMappingContext;
import com.oracle.coherence.spring.data.core.mapping.CoherencePersistentEntity;
import com.oracle.coherence.spring.data.repository.BaseCoherenceRepository;
import com.oracle.coherence.spring.data.repository.query.CoherenceRepositoryQuery;
import com.tangosol.net.Coherence;
import com.tangosol.net.NamedMap;
import com.tangosol.net.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.PersistentEntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.lang.Nullable;

/**
 * Coherence implementation of {@link RepositoryFactorySupport}.
 *
 * @author Ryan Lubke
 * @since 3.0.0
 */
public class CoherenceRepositoryFactory extends RepositoryFactorySupport {

	private final ApplicationContext applicationContext;
	private final CoherenceMappingContext mappingContext;
	private final String sessionName;
	private final String mapName;
	private Session session;
	@SuppressWarnings("rawtypes")
	private NamedMap namedMap;

	public CoherenceRepositoryFactory(ApplicationContext applicationContext,
			CoherenceMappingContext mappingContext,
			String sessionName, String mapName) {

		this.applicationContext = applicationContext;
		this.mappingContext = mappingContext;
		this.sessionName = sessionName;
		this.mapName = mapName;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {

		CoherencePersistentEntity<?> entity = this.mappingContext.getRequiredPersistentEntity(domainClass);
		return new PersistentEntityInformation(entity);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	protected Object getTargetRepository(RepositoryInformation metadata) {

		return new BaseCoherenceRepository(ensureNamedMap(), this.mappingContext, metadata.getDomainType());
	}

	private Session ensureSession() {

		if (this.session == null) {
			Coherence coherence = this.applicationContext.getBean("coherence", Coherence.class);
			this.session = coherence.getSession(this.sessionName);
		}
		return this.session;
	}

	@SuppressWarnings("rawtypes")
	private NamedMap ensureNamedMap() {

		if (this.namedMap == null) {
			Session session = ensureSession();
			this.namedMap = session.getMap(this.mapName);
		}
		return this.namedMap;
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

		return BaseCoherenceRepository.class;
	}

	@Override
	protected Optional<QueryLookupStrategy> getQueryLookupStrategy(@Nullable QueryLookupStrategy.Key key,
			QueryMethodEvaluationContextProvider evaluationContextProvider) {

		return Optional.of(new CoherenceLookupStrategy(evaluationContextProvider));
	}

	private final class CoherenceLookupStrategy implements QueryLookupStrategy {

		private final QueryMethodEvaluationContextProvider evaluationContextProvider;

		private CoherenceLookupStrategy(QueryMethodEvaluationContextProvider evalContextProvider) {
			this.evaluationContextProvider = evalContextProvider;
		}

		@Override
		public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory, NamedQueries namedQueries) {

			return new CoherenceRepositoryQuery(CoherenceRepositoryFactory.this.ensureNamedMap(),
					method, metadata, factory);
		}
	}
}
