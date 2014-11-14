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
package org.polarsys.capella.core.model.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.data.cs.AbstractDeploymentLink;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.ctx.System;
import org.polarsys.capella.core.data.epbs.ConfigurationItem;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.epbs.PhysicalArtifactRealization;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.information.Partition;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.pa.LogicalComponentRealization;
import org.polarsys.capella.core.data.pa.PaFactory;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalArchitecturePkg;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.data.pa.deployment.DeploymentFactory;
import org.polarsys.capella.core.data.pa.deployment.TypeDeploymentLink;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.data.modellingcore.TraceableElement;

/**
 * PhysicalComponent helpers
 */
public class PhysicalComponentExt {

  /**
   * @param location target location of deployment
   * @param deployedElement element to be deployed
   */

  public static void addDeployedElement(PhysicalComponent location, PhysicalComponent deployedElement) {
    // FIXME
    TypeDeploymentLink link = DeploymentFactory.eINSTANCE.createTypeDeploymentLink();
    CapellaElement cont = (CapellaElement) location.eContainer();
    if (cont != null) {
      ((PhysicalComponentPkg) cont).getOwnedDeployments().add(link);
    } else {
      cont = (CapellaElement) location.eContainer();
      ((PhysicalArchitecture) cont).getOwnedDeployments().add(link);
    }

    link.setLocation(location);
    link.setDeployedElement(deployedElement);
  }

  /**
   * @param deployedElement element to be deployed
   * @param location target location of deployment
   */
  public static void addDeployerElement(PhysicalComponent deployedElement, PhysicalComponent location) {
    PhysicalComponentExt.addDeployedElement(location, deployedElement);

  }

  /**
   * This method adds a logical component implementation.
   * @param physicalComponent_p component implementing
   * @param logicalComponent_p component to be implemented
   */
  public static void addImplementedLogicalComponent(PhysicalComponent physicalComponent_p, LogicalComponent logicalComponent_p) {
    LogicalComponentRealization impl = PaFactory.eINSTANCE.createLogicalComponentRealization();
    impl.setTargetElement(logicalComponent_p);
    impl.setSourceElement(physicalComponent_p);
    physicalComponent_p.getOwnedLogicalComponentRealizations().add(impl);
  }

  /**
   * @param sourceDeployedElements_p
   * @param targetDeployedElements_p
   * @return
   */
  public static Collection<ComponentExchange> findConnectionsBetweenPhysicalComponentes(List<Component> sourceDeployedElements_p,
      List<Component> targetDeployedElements_p) {
    List<ComponentExchange> result = new ArrayList<ComponentExchange>(1);

    for (Component component : sourceDeployedElements_p) {
      Collection<ComponentExchange> allRelatedConnection = ComponentExt.getAllRelatedComponentExchange(component);
      for (ComponentExchange connection : allRelatedConnection) {
        Component targetComponent = ComponentExchangeExt.getTargetComponent(connection);
        if (targetDeployedElements_p.contains(targetComponent)) {
          result.add(connection);
        }
      }
    }
    return result;
  }

  public static List<PhysicalComponent> getDeployedElements(PhysicalComponent location_p) {
    List<PhysicalComponent> deployedElements = new ArrayList<PhysicalComponent>(1);
    List<AbstractDeploymentLink> deployments = location_p.getDeploymentLinks();

    for (AbstractDeploymentLink abstractDeployment : deployments) {
      deployedElements.add((PhysicalComponent) abstractDeployment.getDeployedElement());
    }
    return deployedElements;
  }

  public static List<PhysicalComponent> getDeploymentTargets(PhysicalComponent element_p) {
    List<PhysicalComponent> deploymentTargets = new ArrayList<PhysicalComponent>();
    List<AbstractDeploymentLink> deployments = element_p.getDeployingLinks();

    for (AbstractDeploymentLink abstractDeployment : deployments) {
      deploymentTargets.add((PhysicalComponent) abstractDeployment.getLocation());
    }
    return deploymentTargets;
  }

  /**
   * This method retrieves all epbs component which implement the specified physical component.
   * @param component_p The source physical component.
   * @return The configuration item list.
   */
  public static ConfigurationItem getImplementor(PhysicalComponent component_p, EPBSArchitecture epbsArchitecture_p) {
    ConfigurationItem implementorCI = null;
    for (ConfigurationItem ci : getImplementors(component_p)) {
      if (EcoreUtil2.isContainedBy(ci, epbsArchitecture_p)) {
        implementorCI = ci;
      }
    }
    return implementorCI;
  }

  /**
   * This method retrieves all epbs component which implement the specified physical component.
   * @param component_p The source physical component.
   * @return The configuration item list.
   */
  public static List<ConfigurationItem> getImplementors(PhysicalComponent component_p) {
    List<ConfigurationItem> configurationItemsList = new ArrayList<ConfigurationItem>(1);

    EList<AbstractTrace> incomingTraces = component_p.getIncomingTraces();
    for (AbstractTrace incomingTrace : incomingTraces) {
      if (incomingTrace instanceof PhysicalArtifactRealization) {
        TraceableElement source = ((PhysicalArtifactRealization) incomingTrace).getSourceElement();
        if (source instanceof ConfigurationItem) {
          configurationItemsList.add((ConfigurationItem) source);
        }
      }
    }
    return configurationItemsList;
  }

  /**
   * Gets all the lcs from LogicalArchitecture
   * @param logArch_p the {@link LogicalArchitecture}
   * @param currentPC_p the current {@link PhysicalComponent}
   * @param isFilterRequired_p flag for checking filters
   * @return list of LCs
   */
  public static List<CapellaElement> getLCsFromLogicalArchitecture(LogicalArchitecture logArch_p, PhysicalComponent currentPC_p, boolean isFilterRequired_p) {
    List<CapellaElement> list = new ArrayList<CapellaElement>(1);
    if (null != logArch_p) {
      for (LogicalComponent lc : LogicalArchitectureExt.getAllLCsFromLogicalArchitectureLayer(logArch_p)) {
        if (isFilterRequired_p) {
          if (hasImplementedLC(currentPC_p, lc)) {
            continue;
          }
        }
        list.add(lc);
      }
    }
    return list;
  }

  /**
   * @param component_p the current PhysicalComponent
   * @return returns the first containing PhysicalArchitecture
   */
  public static PhysicalArchitecture getOwningPhysicalArchitecture(PhysicalComponent component_p) {
    return (PhysicalArchitecture) EcoreUtil2.getFirstContainer(component_p, PaPackage.Literals.PHYSICAL_ARCHITECTURE);
    // return getRecursiveParentArchitecture(component_p);
  }

  /**
   * @param component_p the current Physical Component
   * @return returns the Component containing the given Physical Component (can be either a Physical Component, or a Physical Node)
   */
  public static Component getParentContainer(PhysicalComponent component_p) {
    return getRecursiveParentContainer(component_p);
  }

  /**
   * Retrieves the Physical component container of Scenario given in parameter. (For Scenario under the PhysicalArchitecture, return Root PhysicalComponent
   * @return
   */
  public static PhysicalComponent getPhysicalComponentContainerFromScenario(Scenario scenario_p) {
    PhysicalComponent containerPc = null;

    containerPc = (PhysicalComponent) EcoreUtil2.getFirstContainer(scenario_p, PaPackage.Literals.PHYSICAL_COMPONENT);
    if (containerPc == null) {
      // Case : Scenario contained by PhysicalArchitecture (not under a Physical Component)
      // Retrieve the Root Physical Component
      PhysicalArchitecture pa = (PhysicalArchitecture) EcoreUtil2.getFirstContainer(scenario_p, PaPackage.Literals.PHYSICAL_ARCHITECTURE);
      containerPc = SystemEngineeringExt.getRootPhysicalComponent(pa);
    }

    return containerPc;
  }

  /**
   * Returns ALL provided interfaces including implemented interfaces and all the provided interfaces trough standard ports
   * @param component_p current component
   * @return returns all the provided interfaces of the current component
   */
  public static List<Interface> getProvidedInterfaces(PhysicalComponent component_p) {
    List<Interface> providedItfList = new ArrayList<Interface>(1);
    List<Partition> exposedPorts = component_p.getOwnedPartitions();

    providedItfList.addAll(component_p.getImplementedInterfaces());

    for (Partition port : exposedPorts) {
      if (port instanceof ComponentPort) {
        ComponentPort stdPort = (ComponentPort) port;
        providedItfList.addAll(PortExt.getProvidedInterfaces(stdPort));
      }
    }

    return providedItfList;
  }

  /**
   * @param component_p
   * @return
   */
  private static PhysicalArchitecture getRecursiveParentArchitecture(PhysicalComponent component_p) {

    EObject container = component_p.eContainer();

    if (container instanceof PhysicalArchitecture) {
      return (PhysicalArchitecture) container;
    } else if (container instanceof PhysicalComponentPkg) {
      return getRecursiveParentArchitecture((PhysicalComponentPkg) container);
    } else if (container instanceof PhysicalComponent) {
      return getRecursiveParentArchitecture((PhysicalComponent) container);
    }
    return null;
  }

  private static PhysicalArchitecture getRecursiveParentArchitecture(PhysicalComponentPkg comppkg_p) {
    EObject container = comppkg_p.eContainer();

    if (container instanceof PhysicalArchitecture) {
      return (PhysicalArchitecture) container;
    } else if (container instanceof PhysicalComponentPkg) {
      return getRecursiveParentArchitecture((PhysicalComponentPkg) container);
    } else if (container instanceof PhysicalComponent) {
      return getRecursiveParentArchitecture((PhysicalComponent) container);
    }
    return null;
  }

  /**
   * @param component_p
   * @return
   */
  private static Component getRecursiveParentContainer(PhysicalArchitecture component_p) {
    Component cpnt = null;
    EObject container = component_p.eContainer();

    if (container instanceof System) {
      cpnt = (System) container;
    } else if (container instanceof PhysicalComponent) {
      // added here if PhysicalComponent is decomposed into LCDcmps, which
      // is a PhysicalArchitecture
      cpnt = (PhysicalComponent) container;
    } else if (container instanceof PhysicalArchitecturePkg) {
      cpnt = getRecursiveParentContainer((PhysicalArchitecturePkg) container);
    }

    return cpnt;
  }

  /**
   * @param component_p
   * @return
   */
  private static Component getRecursiveParentContainer(PhysicalArchitecturePkg component_p) {
    Component cpnt = null;
    EObject container = component_p.eContainer();

    if (container instanceof System) {
      cpnt = (System) container;
    }

    return cpnt;
  }

  /**
   * @param component_p
   * @return
   */
  private static Component getRecursiveParentContainer(PhysicalComponent component_p) {
    Component cpnt = null;
    EObject container = component_p.eContainer();

    if (container instanceof PhysicalArchitecture) {
      cpnt = getRecursiveParentContainer((PhysicalArchitecture) container);
    } else if (container instanceof PhysicalComponentPkg) {
      cpnt = getRecursiveParentContainer((PhysicalComponentPkg) container);
    } else if (container instanceof PhysicalComponent) {
      cpnt = (PhysicalComponent) container;
    }

    return cpnt;
  }

  /**
   * @param componentPkg_p
   * @return
   */
  private static Component getRecursiveParentContainer(PhysicalComponentPkg componentPkg_p) {
    Component cpnt = null;
    EObject container = componentPkg_p.eContainer();

    if (container instanceof PhysicalArchitecture) {
      cpnt = getRecursiveParentContainer((PhysicalArchitecture) container);
    } else if (container instanceof PhysicalComponentPkg) {
      cpnt = getRecursiveParentContainer((PhysicalComponentPkg) container);
    } else if (container instanceof PhysicalComponent) {
      cpnt = (PhysicalComponent) container;
    }

    return cpnt;
  }

  /**
   * Returns ALL required interfaces including used interfaces and all the required interfaces trough standard ports
   * @param component_p current component
   * @return returns all the required interfaces of the current component
   */
  public static List<Interface> getRequiredInterfaces(PhysicalComponent component_p) {
    List<Interface> requiredItfList = new ArrayList<Interface>(1);
    List<Partition> exposedPorts = component_p.getOwnedPartitions();

    requiredItfList.addAll(component_p.getUsedInterfaces());

    for (Partition port : exposedPorts) {
      if (port instanceof ComponentPort) {
        ComponentPort stdPort = (ComponentPort) port;
        requiredItfList.addAll(PortExt.getRequiredInterfaces(stdPort));
      }
    }

    return requiredItfList;
  }

  /**
   * Checks whether the PhysicalComponent has implemented the logical component
   * @param currentPC_p the PhysicalComponent
   * @param lc_p the LogicalComponent
   * @return true if the PhysicalComponent has implemented the logical component
   */
  static public boolean hasImplementedLC(PhysicalComponent currentPC_p, LogicalComponent lc_p) {
    boolean flag = false;
    for (LogicalComponentRealization lcImpl : currentPC_p.getOwnedLogicalComponentRealizations()) {
      if (lcImpl.getAllocatedComponent().equals(lc_p)) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  /**
   * This method checks if the two specified physical components are implemented by the same epbs component.
   * @param PC1_p
   * @param PC2_p
   * @return
   */
  public static boolean haveSameImplementor(PhysicalComponent PC1_p, PhysicalComponent PC2_p, EPBSArchitecture epbsArchitecture_p) {
    ConfigurationItem CI1 = getImplementor(PC1_p, epbsArchitecture_p);
    ConfigurationItem CI2 = getImplementor(PC2_p, epbsArchitecture_p);

    if ((CI1 == null) || (CI2 == null)) {
      return false;
    }

    return CI1.equals(CI2);
  }

  public static boolean isDeployedOn(PhysicalComponent location_p, PhysicalComponent deployedElement_p) {

    List<AbstractDeploymentLink> deployments = location_p.getDeploymentLinks();

    for (AbstractDeploymentLink abstractDeployment : deployments) {
      if (abstractDeployment.getDeployedElement().equals(deployedElement_p)) {
        return true;
      }
    }

    return false;
  }

  /*
   * Return true if the the PhysicalComponent given in parameter is the PhysicalComponent Root
   */
  public static boolean isPhysicalComponentRoot(EObject element_p) {
    if (!(element_p instanceof PhysicalComponent)) {
      return false;
    }

    // Last PhysicalComponent hierarchy detection
    return (!EcoreUtil2.isContainedBy(element_p, PaPackage.Literals.PHYSICAL_COMPONENT));
  }

  /**
   * This method removes a logical component implementation.
   * @param physicalComponent_p the physical component who implements the logical component
   * @param logicalComponent_p the implemented logical component
   */
  public static void removeImplementedLogicalComponent(PhysicalComponent physicalComponent_p, LogicalComponent logicalComponent_p) {
    LogicalComponentRealization implementLink = null;
    ListIterator<LogicalComponentRealization> it = physicalComponent_p.getLogicalComponentRealizations().listIterator();
    while (it.hasNext()) {
      LogicalComponentRealization lnk = it.next();
      if (lnk.getAllocatedComponent().equals(logicalComponent_p)) {
        implementLink = lnk;
      }
    }
    if (implementLink != null) {
      physicalComponent_p.getLogicalComponentRealizations().remove(implementLink);
      physicalComponent_p.getOwnedLogicalComponentRealizations().remove(implementLink);
      implementLink.destroy();
    }
  }

  public static void undeployElement(PhysicalComponent location_p, PhysicalComponent deployedElement_p) {
    List<AbstractDeploymentLink> elementsToDelete = new ArrayList<AbstractDeploymentLink>();
    List<AbstractDeploymentLink> deployements = location_p.getDeploymentLinks();

    for (AbstractDeploymentLink abstractDeployment : deployements) {
      if (abstractDeployment.getDeployedElement().equals(deployedElement_p)) {
        elementsToDelete.add(abstractDeployment);
      }
    }

    for (AbstractDeploymentLink toDelete : elementsToDelete) {
      toDelete.destroy();
    }
  }

  /**
   * One can't deploy physical component of nature 'NODE'... in Physical Component with nature 'BEHAVIOUR'
   * @param sourcePart
   * @param targetPart
   * @return true, if deployment possible, false other wise
   */
  public static boolean isDeploymentPossible(Part sourcePart, Part targetPart) {
    AbstractType sourceType = sourcePart.getAbstractType();
    AbstractType targetType = targetPart.getAbstractType();
    if ((null != sourceType) && (null != targetType) && (sourceType instanceof PhysicalComponent) && (targetType instanceof PhysicalComponent)) {
      PhysicalComponent sourcePC = (PhysicalComponent) sourceType;
      PhysicalComponent targetPC = (PhysicalComponent) targetType;
      if ((sourcePC.getNature() == PhysicalComponentNature.BEHAVIOR) && (targetPC.getNature() == PhysicalComponentNature.NODE)) {
        return false;
      }
    }

    return true;
  }

  public static boolean isNode(PhysicalComponent physicalComponent_p) {
    if (PhysicalComponentNature.NODE == physicalComponent_p.getNature()) {
      return true;
    }
    return false;
  }

  public static boolean isBehaviour(PhysicalComponent physicalComponent_p) {
    if (PhysicalComponentNature.BEHAVIOR == physicalComponent_p.getNature()) {
      return true;
    }
    return false;
  }
}
