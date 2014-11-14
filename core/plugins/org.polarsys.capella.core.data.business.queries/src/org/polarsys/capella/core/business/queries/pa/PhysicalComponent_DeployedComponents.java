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
package org.polarsys.capella.core.business.queries.pa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.data.cs.AbstractDeploymentLink;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.pa.deployment.TypeDeploymentLink;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.model.preferences.CapellaModelPreferencesPlugin;

/**
 *
 */
public class PhysicalComponent_DeployedComponents implements IBusinessQuery {

  public boolean isMultipleDeploymentAllowed() {
    return CapellaModelPreferencesPlugin.getDefault().isMultipleDeploymentAllowed();
  }
  
  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  public List<CapellaElement> getAvailableElements(CapellaElement element_p) {
    PhysicalComponent currentPC = (PhysicalComponent) element_p;
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
    List<PhysicalComponent> comps = SystemEngineeringExt.getAllPhysicalComponents(currentPC);

    for (PhysicalComponent physicalComponent : comps) {
      if (! (currentPC.getNature().equals(PhysicalComponentNature.BEHAVIOR) 
          && physicalComponent.getNature().equals(PhysicalComponentNature.NODE))
          && !(!isMultipleDeploymentAllowed() && !physicalComponent.getDeployingLinks().isEmpty())
          && !physicalComponent.equals(currentPC)
          && !EcoreUtil.isAncestor(physicalComponent, currentPC)){
          availableElements.add(physicalComponent);
      }
    }
    availableElements.removeAll(getCurrentElements(element_p, false));
    return availableElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getCurrentElements(org.polarsys.capella.core.data.capellacore.CapellaElement, boolean)
   */
  public List<CapellaElement> getCurrentElements(CapellaElement element_p, boolean onlyGenerated_p) {
    List<CapellaElement> currentElements = new ArrayList<CapellaElement>();

    if (element_p instanceof PhysicalComponent) {
      PhysicalComponent pc = (PhysicalComponent) element_p;
      for (AbstractDeploymentLink abstractDeployment : pc.getDeploymentLinks()) {
        if (abstractDeployment instanceof TypeDeploymentLink) {
          currentElements.add(abstractDeployment.getDeployedElement());
        }
      }
    }

    return currentElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getEClass()
   */
  public EClass getEClass() {
    return PaPackage.Literals.PHYSICAL_COMPONENT;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getEStructuralFeatures()
   */
  public List<EReference> getEStructuralFeatures() {
    return Collections.singletonList(CsPackage.Literals.ABSTRACT_DEPLOYMENT_LINK__DEPLOYED_ELEMENT);
  }

}
