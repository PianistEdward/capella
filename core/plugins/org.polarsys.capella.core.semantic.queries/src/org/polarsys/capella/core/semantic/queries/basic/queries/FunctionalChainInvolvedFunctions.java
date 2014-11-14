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

import org.eclipse.emf.common.util.EList;

import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.common.helpers.query.IQuery;

/**
 * return functions involved in functionalChain
 */
public class FunctionalChainInvolvedFunctions implements IQuery {

  /**
	 *  default
	 */
  public FunctionalChainInvolvedFunctions() {
    // Does nothing
  }

  /**
   * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
   */
  public List<Object> compute(Object object_p) {
    List<Object> result = new ArrayList<Object>();
    if (object_p instanceof FunctionalChain) {
      FunctionalChain functionalChain = (FunctionalChain) object_p;
      EList<AbstractFunction> involvedFunctions = functionalChain.getInvolvedFunctions();
      if(!involvedFunctions.isEmpty())
        result.addAll(involvedFunctions);
    }
    return result;
  }
}
