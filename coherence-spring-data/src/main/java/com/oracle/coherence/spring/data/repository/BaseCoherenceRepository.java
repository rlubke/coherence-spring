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
package com.oracle.coherence.spring.data.repository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.oracle.coherence.repository.AbstractRepository;
import com.oracle.coherence.spring.data.core.mapping.CoherencePersistentEntity;
import com.oracle.coherence.spring.data.core.mapping.CoherencePersistentProperty;
import com.tangosol.net.NamedMap;
import com.tangosol.util.Base;
import com.tangosol.util.filter.KeyFilter;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * TODO(rlubke) docs.
 *
 * @param <T> the entity type
 * @param <ID> the id type
 *
 * @author Ryan Lubke
 * @since 3.0.0
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public class BaseCoherenceRepository<T, ID>
		extends AbstractRepository<ID, T>
		implements Repository<T, ID> {

	private final NamedMap<ID, T> namedMap;
	private final MappingContext<CoherencePersistentEntity<T>, CoherencePersistentProperty> mappingContext;
	private final Class<? extends T> domainType;

	public BaseCoherenceRepository(NamedMap<ID, T> namedMap,
			MappingContext<CoherencePersistentEntity<T>, CoherencePersistentProperty> mappingContext,
			Class<? extends T> domainType) {
		this.namedMap = namedMap;
		this.mappingContext = mappingContext;
		this.domainType = domainType;
	}

	@Override
	protected NamedMap<ID, T> getMap() {
		return this.namedMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ID getId(T t) {
		CoherencePersistentEntity<T> entity =
				this.mappingContext.getPersistentEntity(this.domainType);

		if (entity == null) {
			throw new IllegalStateException("Unable to obtain PersistentEntity for type "
					+ this.domainType.getName());
		}

		CoherencePersistentProperty idProp = entity.getRequiredIdProperty();

		try {
			return (ID) (idProp.usePropertyAccess()
					? idProp.getGetter().invoke(t)
					: idProp.getField().get(t));
		}
		catch (Exception ex) {
			throw Base.ensureRuntimeException(ex);
		}
	}

	@Override
	protected Class<? extends T> getEntityType() {
		return this.domainType;
	}

	// We can't implement CrudRepository due to signature conflicts in save(), therefore
	// to prevent spring from passing crud repository methods to the query runtime,
	// we provide the crud implementations here.

	/**
	 * Deletes a given entity.
	 * @param entity must not be {@literal null}
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	public void delete(T entity) {
		remove(entity);
	}

	/**
	 * Deletes the entity with the given id.
	 * @param id must not be {@literal null}
	 * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
	 */
	public void deleteById(ID id) {
		removeById(id, false);
	}

	/**
	 * Deletes the given entities.
	 * @param entities must not be {@literal null}. Must not contain {@literal null} elements
	 * @throws IllegalArgumentException in case the given {@literal entities} or one of its entities is {@literal null}.
	 */
	public void deleteAll(Iterable<? extends T> entities) {
		removeAll(StreamSupport.stream(entities.spliterator(), false));
	}

	/**
	 * Saves all given entities.
	 * @param entities must not be {@literal null} nor must it contain {@literal null}
	 * @param <S> entity type
	 * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
	 *         as the {@literal Iterable} passed as an argument.
	 * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
	 *           {@literal null}.
	 */
	public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
		saveAll(StreamSupport.stream(entities.spliterator(), false));
		return entities;
	}

	/**
	 * Returns all instances of the type {@code T} with the given IDs.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 * @param ids must not be {@literal null} nor contain any {@literal null} values
	 * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
	 *         {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
	 */
	public Iterable<T> findAllById(Iterable<ID> ids) {
		return findAll(new KeyFilter<>(
				StreamSupport.stream(ids.spliterator(), false).collect(Collectors.toSet())));
	}

	/**
	 * Returns whether an entity with the given id exists.
	 * @param id must not be {@literal null}
	 * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
	public boolean existsById(ID id) {
		return getMap().containsKey(id);
	}

	/**
	 * Deletes all entities managed by the repository.
	 */
	public void deleteAll() {
		getMap().truncate();
	}
}
