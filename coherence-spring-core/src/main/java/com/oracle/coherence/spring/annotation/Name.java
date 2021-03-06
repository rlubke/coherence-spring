/*
 * Copyright (c) 2013, 2021, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */
package com.oracle.coherence.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An annotation used when injecting Coherence resource to indicate a
 * specific resource name.
 *
 * @author Jonathan Knight  2019.10.20
 * @author Gunnar Hillert
 */
@Documented
@Retention(RUNTIME)
public @interface Name {

	/**
	 * The name used to identify a specific resource.
	 *
	 * @return the name used to identify a specific resource
	 */
	String value();
}
