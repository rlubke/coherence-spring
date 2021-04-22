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

import java.io.Serializable;
import java.util.Locale;

import com.oracle.coherence.spring.data.config.CoherenceMap;
import com.oracle.coherence.spring.data.core.mapping.CoherenceMappingContext;
import com.tangosol.net.Coherence;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * Factory responsible for creating Repository instances for a specific repository
 * interface.
 *
 * @param <ID> the entity's identity type
 * @param <T>  the repository type
 * @param <S>  the entity type
 * @author Ryan Lubke
 * @since 3.0.0
 */
public class CoherenceRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
		extends RepositoryFactoryBeanSupport<T, S, ID>
		implements ApplicationContextAware {

	protected ApplicationContext applicationContext;
	private CoherenceMappingContext ctx = new CoherenceMappingContext();

	public CoherenceRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
		setMappingContext(this.ctx);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory() {
		Class<?> repoClass = getObjectType();
		String mapName;
		String sessionName = Coherence.DEFAULT_NAME;
		if (repoClass.isAnnotationPresent(CoherenceMap.class)) {
			CoherenceMap repository = repoClass.getAnnotation(CoherenceMap.class);
			mapName = repository.value();
			sessionName = repository.session();
		}
		else {
			String repoClassName = repoClass.getSimpleName();
			int idx = repoClassName.lastIndexOf("Repository");
			if (idx != -1) {
				mapName = repoClassName.substring(0, idx).toLowerCase(Locale.ROOT);
			}
			else {
				throw new IllegalStateException();
			}
		}
		return new CoherenceRepositoryFactory(this.applicationContext, this.ctx, sessionName, mapName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
