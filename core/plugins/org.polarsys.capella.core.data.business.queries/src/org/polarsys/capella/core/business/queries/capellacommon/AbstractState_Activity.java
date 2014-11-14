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
package org.polarsys.capella.core.business.queries.capellacommon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.interaction.Event;
import org.polarsys.capella.core.data.capellacommon.CapellacommonPackage;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.common.data.behavior.AbstractEvent;

/**
 */
public abstract class AbstractState_Activity implements IBusinessQuery {

  /**
   * @param arch_p
   * @param state_p
   * @return
   */
  private List<CapellaElement> getElementsFromBlockArchitecture(BlockArchitecture arch_p, State state_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);

    if (arch_p != null) {
      TreeIterator<Object> allContents = EcoreUtil.getAllContents(arch_p, false);
      while (allContents.hasNext()) {
        Object object = allContents.next();
        if (object instanceof AbstractEvent && !(object instanceof Event)) {
          availableElements.add((CapellaElement) object);
        }
      }
    }

    // remove existing from the availableElements
    for (CapellaElement elt : getCurrentElements(state_p, false)) {
      availableElements.remove(elt);
    }

    return availableElements;
  }

  /**
   * same level Visibility Layer
   * @param state_p
   */
  private List<CapellaElement> getRule_MQRY_StateTransition_AvailableEvents_11(State state_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);

    BlockArchitecture arch = SystemEngineeringExt.getRootBlockArchitecture(state_p);
    if (null != arch) {
      availableElements.addAll(getElementsFromBlockArchitecture(arch, state_p));
    }

    return availableElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  public List<CapellaElement> getAvailableElements(CapellaElement element_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>();

    if (element_p instanceof State) {
      availableElements.addAll(getRule_MQRY_StateTransition_AvailableEvents_11((State) element_p));
    }

    return availableElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.IBusinessQuery#getCurrentElements(org.polarsys.capella.core.data.capellacore.CapellaElement, boolean)
   */
  public List<CapellaElement> getCurrentElements(CapellaElement element_p, boolean onlyGenerated_p) {
    List<CapellaElement> currentElements = new ArrayList<CapellaElement>();
    if (element_p instanceof State) {
      AbstractEvent evt = ((State) element_p).getDoActivity();
      if (evt != null) {
        currentElements.add((CapellaElement) evt);
      }
    }
    return currentElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.IBusinessQuery#getEStructuralFeature()
   */
  public List<EReference> getEStructuralFeatures() {
    return Collections.singletonList(CapellacommonPackage.Literals.STATE__DO_ACTIVITY);
  }
}
