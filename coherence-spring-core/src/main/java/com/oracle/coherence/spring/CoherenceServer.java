/*
 * Copyright (c) 2013, 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.spring;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.tangosol.coherence.config.Config;
import com.tangosol.net.Coherence;
import com.tangosol.net.NamedCache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;

/**
 * Responsible for starting the default
 * {@link com.tangosol.net.Coherence} instance.
 *
 * @author Gunnar Hillert
 * @since 3.0
 */
public class CoherenceServer implements InitializingBean, DisposableBean, Lifecycle, ApplicationContextAware {

	/**
	 * Start up default of 15 seconds.
	 */
	private static final long DEFAULT_STARTUP_TIMEOUT_MILLIS = 15000;

	/**
	 * The bean context.
	 */
	private ApplicationContext applicationContext;

	/**
	 * The {@link com.tangosol.net.Coherence} instance to run.
	 */
	private final Coherence coherence;

	/**
	 * {@link Coherence} startup timeout in milliseconds.
	 */
	private final long startupTimeout = Config.getLong("coherence.spring.server.startup-timeout-millis",
			DEFAULT_STARTUP_TIMEOUT_MILLIS);

	/**
	 * Create a {@link CoherenceServer}.
	 *
	 * @param coherence the {@link com.tangosol.net.Coherence} instance to run
	 */
	public CoherenceServer(Coherence coherence) {
		this.coherence = coherence;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	public <K, V> NamedCache<K, V> getCache(String cacheName) {
		return this.coherence.getSession().getCache(cacheName);
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public Coherence getCoherence() {
		return this.coherence;
	}

	@Override
	public boolean isRunning() {
		return this.coherence != null && this.coherence.isStarted();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void start() {
		try {
			this.coherence.start().get(this.startupTimeout, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException | ExecutionException | TimeoutException ex) {
			throw new IllegalStateException(String.format("Oracle Coherence did not start "
					+ "successfully within within the specified timeout period of %sms", this.startupTimeout), ex);
		}
	}

	@Override
	public void stop() {
		if (this.coherence != null) {
			this.coherence.close();
		}
		Coherence.closeAll();
	}
}
