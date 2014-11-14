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
package org.polarsys.capella.core.business.queries.cs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.model.helpers.InterfacePkgExt;
import org.polarsys.capella.core.model.helpers.query.CapellaQueries;
import org.polarsys.capella.core.model.utils.ListExt;

/**
 */
public class Component_ImplementedInterfaces implements IBusinessQuery {

  /**
   * <p>
   * Gets all the interfaces implemented by...
   * </p>
   * <p>
   * The owner logical component of the current one.(Refer
   * MQRY_LogicalComponent_ImplInterfaces_11).
   * </p>
   * 
   * @param currentLC_p
   *            the current Logical Component
   * @param parentLC_p
   *            the owner Logical Component
   * @return list of interfaces
   */
  private List<CapellaElement> getRule_MQRY_LC_ImplInterfaces_11(Component currentLC_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);

    for (Component component : ComponentExt.getDirectParents(currentLC_p)) {
      availableElements.addAll(ComponentExt.getImplementedInterfacesFiltered(component, currentLC_p));
    }
    return availableElements;
  }

  /**
   * <p>
   * Gets all the interfaces contained by the interface package (and sub
   * packages) of the current logical component's parent (Refer
   * MQRY_LogicalComponent_ImplInterfaces_12).
   * </p>
   * 
   * @param currentLC_p
   *            the current Logical Component
   * @param parentLC_p
   *            the owner Logical Component
   * @return list of interfaces
   */
  private List<CapellaElement> getRule_MQRY_LC_ImplInterfaces_12(Component currentLC_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
    Object parent = currentLC_p.eContainer();
    if (null != parent) {
      if (parent instanceof Component) {
        availableElements.addAll(InterfacePkgExt.getAllInterfacesFiltered(((Component) parent).getOwnedInterfacePkg(), currentLC_p, false));
      } else if (parent instanceof BlockArchitecture) {
        availableElements.addAll(InterfacePkgExt.getAllInterfacesFiltered(((BlockArchitecture) parent).getOwnedInterfacePkg(), currentLC_p, false));
      }
    }
    return availableElements;
  }

  /**
   * <p>
   * Gets all the interfaces contained by the interface package(sub packages)
   * of the current LC's parent hierarchy((Refer
   * MQRY_LogicalComponent_ImplInterfaces_13).
   * </p>
   * 
   * @param currentLC_p
   *            the current Logical Component
   * @param parentLC_p
   *            the parent Logical Component
   * @return list of interfaces
   */
  private List<CapellaElement> getRule_MQRY_LC_ImplInterfaces_13(Component currentLC_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>(1);
    // all the interfaces in the parent hierarchy
    for (Interface inter : ComponentExt.getInterfacesFromComponentParentHierarchy(currentLC_p)) {
      if (ComponentExt.isImplementingInterface(currentLC_p, inter)) {
        continue;
      }
      availableElements.add(inter);
    }
    return availableElements;
  }

  /**
   * <p>
   * Gets all the interfaces implemented by...
   * </p>
   * <p>
   * The owner logical component of the current one.(Refer
   * MQRY_LogicalComponent_UsedInterfaces_11).
   * </p>
   * <p>
   * All the interfaces contained by the interface package (and sub packages)
   * of the current logical component's parent (Refer
   * MQRY_LogicalComponent_UsedInterfaces_12).
   * </p>
   * <p>
   * All the interfaces contained by the interface package(sub packages) of
   * the current LC's parent hierarchy((Refer
   * MQRY_LogicalComponent_UsedInterfaces_13).
   * </p>
   * <p>
   * All the interfaces used/implemented by the same level LCs((Refer
   * MQRY_LogicalComponent_UsedInterfaces_14).
   * </p>
   * <p>
   * Except the interfaces that are already used by the current LC(Refer
   * MQRY_LogicalComponent_UsedInterfaces_1Ex1)
   * </p>
   * 
   * @see org.polarsys.capella.core.business.queries.capellacore.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.common.model.CapellaElement)
   */
  public List<CapellaElement> getAvailableElements(CapellaElement element_p) {
    SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(element_p);
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>();

    if (null == systemEngineering) {
      return availableElements;
    }

    // Update Mode
    if (element_p instanceof Component) {
      Component currentLC = (Component) element_p;

      // all the interfaces implemented by owner LC
      availableElements.addAll(getRule_MQRY_LC_ImplInterfaces_11(currentLC));

      // all the interfaces in the interface pkg and sub pkgs of parentLC
      availableElements.addAll(getRule_MQRY_LC_ImplInterfaces_12(currentLC));

      // all the interfaces in the parent hierarchy
      availableElements.addAll(getRule_MQRY_LC_ImplInterfaces_13(currentLC));
    }

    // removing the duplicate entries in the list
    availableElements = ListExt.removeDuplicates(availableElements);
    availableElements.remove(element_p);

    return availableElements;
  }

  /**
   * <p>
   * Gets all the interfaces implemented by the current Logical Component
   * </p>
   * <p>
   * Refer MQRY_LogicalComponent_ImplInterfaces_1
   * </p>
   * 
   * @see org.polarsys.capella.core.business.queries.capellacore.core.business.queries.IBusinessQuery#getCurrentElements(org.polarsys.capella.core.common.model.CapellaElement,
   *      boolean)
   */
  public List<CapellaElement> getCurrentElements(CapellaElement element_p, boolean onlyGenerated_p) {
    List<CapellaElement> currentElements = new ArrayList<CapellaElement>();
    SystemEngineering systemEngineering = CapellaQueries.getInstance().getRootQueries().getSystemEngineering(element_p);
    if (null == systemEngineering) {
      return currentElements;
    }

    if (element_p instanceof Component) {
      Component currentLC = (Component) element_p;
      currentElements.addAll(ComponentExt.getImplementedInterfaces(currentLC));
    }
    return currentElements;
  }

  public EClass getEClass() {
    return CsPackage.Literals.COMPONENT;
  }

  public List<EReference> getEStructuralFeatures() {
    List<EReference> list = new ArrayList<EReference>(1);
    list.add(CsPackage.Literals.COMPONENT__IMPLEMENTED_INTERFACES);
    list.add(CsPackage.Literals.COMPONENT__OWNED_INTERFACE_IMPLEMENTATIONS);

    return list;
  }
}
