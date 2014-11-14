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
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.polarsys.capella.core.data.ctx.OperationalActorRealization;
import org.polarsys.capella.core.data.ctx.OperationalEntityRealization;
import org.polarsys.capella.core.data.ctx.System;
import org.polarsys.capella.core.data.oa.Entity;
import org.polarsys.capella.core.data.oa.OperationalActor;
import org.polarsys.capella.common.data.modellingcore.TraceableElement;
import org.polarsys.capella.common.helpers.adapters.MDEAdapterFactory;
import org.polarsys.capella.common.helpers.query.IQuery;
import org.polarsys.capella.common.platform.sirius.tig.ef.SemanticEditingDomainFactory.SemanticEditingDomain;

/**
 * This query allows to get the System(s) refining the current Entity(i.e OperationalActor also).
 */
public class Entity_RefiningSystem implements IQuery {

  /**
   * @see org.polarsys.capella.common.helpers.query.IQuery#compute(java.lang.Object)
   */
  public List<Object> compute(Object object_p) {
    List<Object> result = new ArrayList<Object>();
    // gets the Semantic Editing Domain
    SemanticEditingDomain semEditDomain = (SemanticEditingDomain) MDEAdapterFactory.getEditingDomain();
    // Gets the Cross Referencer
    ECrossReferenceAdapter crossReferencer = semEditDomain.getCrossReferencer();
    Collection<Setting> inverseReferences = crossReferencer.getInverseReferences((EObject) object_p);

    for (Setting setting : inverseReferences) {
      EObject eObject = setting.getEObject();
      if ((object_p instanceof OperationalActor) && (eObject instanceof OperationalActorRealization)) {
        OperationalActorRealization sar = (OperationalActorRealization) eObject;
        TraceableElement sourceElement = sar.getSourceElement();
        if (sourceElement instanceof System) {
          result.add(sourceElement);
        }
      } else if ((object_p instanceof Entity) && (eObject instanceof OperationalEntityRealization)) {
        OperationalEntityRealization sar = (OperationalEntityRealization) eObject;
        TraceableElement sourceElement = sar.getSourceElement();
        if (sourceElement instanceof System) {
          result.add(sourceElement);
        }
      }
    }

    return result;
  }
}