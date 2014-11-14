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
package org.polarsys.capella.core.projection.actors.la2pa.rules;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.fa.ComponentFunctionalAllocation;
import org.polarsys.capella.core.tiger.ITransfo;
import org.polarsys.capella.core.transfo.logicalfunction.TransformLogicalFunction;
import org.polarsys.capella.common.data.modellingcore.TraceableElement;

public class Rule_ComponentFunctionalAllocation extends org.polarsys.capella.core.projection.common.rules.cs.Rule_ComponentFunctionalAllocation {

  /**
   * @see org.polarsys.capella.core.projection.common.CommonRule#runSubTransition(org.eclipse.emf.ecore.EObject, org.polarsys.capella.core.tiger.ITransfo)
   */
  @Override
  protected void runSubTransitionBeforeTransform(EObject element_p, ITransfo transfo_p) {
    ComponentFunctionalAllocation componentFuncAlloc = (ComponentFunctionalAllocation) element_p;
    TraceableElement target = componentFuncAlloc.getTargetElement();

    // Call LogicalFunction to PhysicalFunction engine transformation
    TransformLogicalFunction transformLogicalFunction = new TransformLogicalFunction();
    transformLogicalFunction.setContext(target);
    transformLogicalFunction.execute();
  }

}
