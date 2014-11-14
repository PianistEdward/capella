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
package org.polarsys.capella.core.model.helpers.queries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.polarsys.capella.common.queries.AbstractQuery;
import org.polarsys.capella.common.queries.exceptions.QueryException;
import org.polarsys.capella.common.queries.queryContext.IQueryContext;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.ComponentExt;

/**
 */
public class GetAllSubDefinedComponents extends AbstractQuery {

  @Override
  public List<Object> execute(Object input_p, IQueryContext context_p) throws QueryException {
    BlockArchitecture block = (BlockArchitecture) input_p;
    return (List) getAllSubDefinedComponents(block);
  }

  public List<Component> getAllSubDefinedComponents(BlockArchitecture architecture_p) {
    List<Component> comps = new ArrayList<Component>();
    LinkedList<Component> subs = new LinkedList<Component>();
    List<Component> internal = new ArrayList<Component>();

    subs.addAll(ComponentExt.getSubDefinedComponents(architecture_p));
    while (subs.size() > 0) {
      Component sub = subs.removeFirst();
      comps.add(sub);
      internal = ComponentExt.getSubDefinedComponents(sub);
      comps.addAll(internal);
      subs.addAll(internal);
    }
    return comps;
  }

}
