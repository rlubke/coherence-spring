/*
 * Copyright (c) 2013, 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.spring.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.oracle.coherence.spring.CoherenceServer;
import com.oracle.coherence.spring.configuration.session.AbstractSessionConfigurationBean;
import com.oracle.coherence.spring.event.CoherenceEventListenerMethodProcessor;
import com.tangosol.net.Coherence;
import com.tangosol.net.CoherenceConfiguration;
import com.tangosol.net.SessionConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * Default implementation of the {@link CoherenceConfigurer} interface.
 *
 * @author Gunnar Hillert
 * @since 3.0
 */
@Configuration
public class DefaultCoherenceConfigurer implements CoherenceConfigurer {

	private static final Log logger = LogFactory.getLog(DefaultCoherenceConfigurer.class);

	private final Collection<SessionConfiguration> sessionConfigurations = new ArrayList<>(0);

	private Collection<Coherence.LifecycleListener> lifecycleListeners;

	private CoherenceConfiguration coherenceConfiguration;

	private Coherence coherence;

	private CoherenceServer coherenceServer;

	private final ConfigurableApplicationContext context;

	private boolean initialized = false;

	public DefaultCoherenceConfigurer(ConfigurableApplicationContext context) {
		this.context = context;
	}

	@Override
	public Coherence getCoherence() {
		return this.coherence;
	}

	@Override
	public CoherenceServer getCoherenceServer() {
		return this.coherenceServer;
	}

	@Override
	public CoherenceConfiguration getCoherenceConfiguration() {
		return this.coherenceConfiguration;
	}

	@PostConstruct
	public void initialize() {
		if (this.initialized) {
			return;
		}

		if (this.context != null) {
			final Collection<AbstractSessionConfigurationBean> sessionConfigurationBeans =
					this.context.getBeansOfType(AbstractSessionConfigurationBean.class).values();

			if (!CollectionUtils.isEmpty(sessionConfigurationBeans)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("%s sessionConfigurationBean(s) found.", sessionConfigurationBeans.size()));
				}
				this.sessionConfigurations.addAll(
					sessionConfigurationBeans
						.stream()
						.map((sp) -> sp.getConfiguration().orElseThrow(
								() -> new IllegalStateException("Empty sessionConfigurations found.")))
						.collect(Collectors.toList()));
			}

			final Collection<Coherence.LifecycleListener> lifecycleListeners =
					this.context.getBeansOfType(Coherence.LifecycleListener.class).values();

			if (!CollectionUtils.isEmpty(lifecycleListeners)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("%s lifecycleListener(s) found.", lifecycleListeners.size()));
				}
				this.lifecycleListeners = lifecycleListeners;
			}
		}

		if (this.coherenceConfiguration == null) {
			logger.warn("No Coherence configuration was provided...using default.");
			this.coherenceConfiguration = this.createCoherenceConfiguration();
		}
		if (this.coherence == null) {
			logger.warn("No Coherence instance was provided...creating a default instance.");
			this.coherence = this.createCoherence();
		}
		if (this.coherenceServer == null) {
			logger.warn("No Coherence server defined...creating default server.");
			this.coherenceServer = this.createCoherenceServer();
		}
		this.initialized = true;
	}

	/**
	 * Create the {@link CoherenceConfiguration}.
	 * @return the Configuration for a Coherence instance.
	 */
	protected CoherenceConfiguration createCoherenceConfiguration() {
		final CoherenceConfiguration.Builder builder = CoherenceConfiguration.builder();

		if (!CollectionUtils.isEmpty(this.sessionConfigurations)) {
			builder.withSessions(this.sessionConfigurations);
		}

		if (!CollectionUtils.isEmpty(this.lifecycleListeners)) {
			builder.withEventInterceptors(this.lifecycleListeners);
		}
		builder.named("default"); //TODO
		builder.withEventInterceptors(this.context.getBean(CoherenceEventListenerMethodProcessor.class).getInterceptors());
		return builder.build();
	}

	protected Coherence createCoherence() {
		return Coherence.clusterMember(this.getCoherenceConfiguration());
	}

	protected CoherenceServer createCoherenceServer() {
		return new CoherenceServer(this.getCoherence());
	}
}
