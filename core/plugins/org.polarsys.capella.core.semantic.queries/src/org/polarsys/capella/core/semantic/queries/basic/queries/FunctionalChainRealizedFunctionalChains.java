/*******************************************************************************
 * Copyright (c) 2006, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.core.semantic.queries.basic.queries;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.oa.OperationalProcess;

/**
 * Return realized functional chains of current functional chains
 * 
 * 
 */
public class FunctionalChainRealizedFunctionalChains extends AbsFunctionalChainRealizedFunctionalChains {

	/**
	 * 
	 */
	public FunctionalChainRealizedFunctionalChains() {
		// do nothing
	}

	@Override
	public boolean isValidInstanceOf(EObject element_p) {
		if (null != element_p && !(element_p instanceof OperationalProcess)) {
			return true;
		}
		return false;
	}
}
