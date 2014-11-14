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

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.oa.OperationalProcess;
import org.polarsys.capella.common.helpers.query.IQuery;

/**
 * Using inverseCrossReference, find the OperationalProcess containing current exchange
 */
public class FunctionalExchangeOperationalProcess implements IQuery {

	/**
	 * 
	 */
	public FunctionalExchangeOperationalProcess() {
    // do nothing
	}

	/** 
	 * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
	 */
	public List<Object> compute(Object object_p) {
		List<Object> result = new ArrayList<Object>();
		if (object_p instanceof FunctionalExchange) {
      for (FunctionalChain chain : ((FunctionalExchange) object_p).getInvolvingFunctionalChains()) {
        if (chain instanceof OperationalProcess) {
          result.add(chain);
        }
      }
		}
		return result;
	}
}
