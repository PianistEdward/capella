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

import org.polarsys.capella.core.data.fa.FunctionPort;
import org.polarsys.capella.core.data.oa.OperationalActivity;
import org.polarsys.capella.common.data.activity.ActivityEdge;
import org.polarsys.capella.common.data.activity.ActivityNode;
import org.polarsys.capella.common.helpers.query.IQuery;

/**
 * 
 */
public class ItemQuery_Pin_sourceAndTargetOwners implements IQuery {

  /**
	 * 
	 */
  public ItemQuery_Pin_sourceAndTargetOwners() {
    // Does nothing
  }

  /**
   * 
   * (source and target).owner
   * 
   * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
   */
  public List<Object> compute(Object object_p) {
    List<Object> result = new ArrayList<Object>();
    if (object_p instanceof ActivityEdge) {
      ActivityEdge f = (ActivityEdge) object_p;
      ActivityNode source = f.getSource();
      if (null != source) {
        if (source instanceof FunctionPort) {
          result.add(source.eContainer());
        } else if (source instanceof OperationalActivity) {
          result.add(source);
        }
      }
      ActivityNode target = f.getTarget();
      if (null != target) {
        if (target instanceof FunctionPort) {
          result.add(target.eContainer());
        } else if (target instanceof OperationalActivity) {
          result.add(target);
        }
      }
    }
    return result;
  }
}
