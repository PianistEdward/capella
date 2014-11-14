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

import org.polarsys.capella.core.data.ctx.Actor;
import org.polarsys.capella.core.data.ctx.ActorCapabilityInvolvement;
import org.polarsys.capella.core.data.ctx.Capability;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.InvolverElement;
import org.polarsys.capella.common.helpers.query.IQuery;

/**
 * Returns allocating Capabilities of current Actor.
 */
public class ActorAllocatingCapabilities implements IQuery {

  /**
   * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
   */
  public List<Object> compute(Object object_p) {
    List<Object> result = new ArrayList<Object>();
    // gets the Semantic Editing Domain
    if (object_p instanceof Actor) {
      Actor actor = (Actor) object_p;
      EList<Involvement> involvingInvolvements = actor.getInvolvingInvolvements();
      for (Involvement involvement : involvingInvolvements) {
        if (involvement instanceof ActorCapabilityInvolvement) {
          InvolverElement involver = involvement.getInvolver();
          if (involver instanceof Capability) {
            result.add(involver);
          }
        }
      }
    }
    return result;
  }
}
