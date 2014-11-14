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

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.SystemComponent;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.model.helpers.ComponentExchangeExt;
import org.polarsys.capella.common.data.modellingcore.AbstractInformationFlow;
import org.polarsys.capella.common.data.modellingcore.InformationsExchanger;
import org.polarsys.capella.common.helpers.query.IQuery;

/**
 *
 */
public class ItemQuery_Flow_sourceAndTargetOwners implements IQuery {

	/**
	 * 
	 */
	public ItemQuery_Flow_sourceAndTargetOwners() {
    // do nothing
	}

	/**
	 * 
	 * (source and target).owner
	 * 
	 * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
	 */
	public List<Object> compute(Object object_p) {
		List<Object> result = new ArrayList<Object>();
		if (object_p instanceof AbstractInformationFlow) {
			AbstractInformationFlow f = (AbstractInformationFlow) object_p;
			
			// connection 
			if (f instanceof ComponentExchange) {
	      Part sourcePart = ComponentExchangeExt.getSourcePart((ComponentExchange) f);
	      Part targetPart = ComponentExchangeExt.getTargetPart((ComponentExchange) f);
	      if (null != sourcePart){
	        result.add(sourcePart);
	      }else{
          InformationsExchanger source = f.getSource();
          if (null != source && source instanceof ComponentPort) {
            EObject eContainer = source.eContainer();
            if (null != eContainer && eContainer instanceof SystemComponent) {
              result.add(eContainer);    
            }
          }
	      }
	      if (null != targetPart){
	        result.add(targetPart);
	      }else{
          InformationsExchanger source = f.getTarget();
          if (null != source && source instanceof ComponentPort) {
            EObject eContainer = source.eContainer();
            if (null != eContainer && eContainer instanceof SystemComponent) {
              result.add(eContainer);    
            }
          }
	      }
	      
	      return result;
      }

			if (null != f.getSource()) result.add(f.getSource().eContainer());
			if (null != f.getTarget()) result.add(f.getTarget().eContainer());
		}
		
		return result;
	}
}
