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
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentArchitecture;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.capellacore.Structure;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;

/**
 * Structure helpers
 */
public class StructureExt {

  /**
   * Gets the root component from an element container
   * @param elementContainer_p
   *          the element container
   * @return the root component
   */
  static public Component getRootComponent(Structure elementContainer_p) {
    Component comp = null;
    if (null != elementContainer_p) {
      Object container = elementContainer_p.eContainer();
      if (container instanceof Component) {
        comp = (Component) container;
      } else if (container instanceof Structure) {
        comp = getRootComponent((Structure) container);
      }
    }
    return comp;
  }

  /**
   * Gets the root component architecture from the element container
   * @param elementContainer_p
   *          the element container
   * @return the component architecture
   */
  static public ComponentArchitecture getRootComponentArchitecture(Structure elementContainer_p) {
    ComponentArchitecture compArch = null;
    if (null != elementContainer_p) {
      Object container = elementContainer_p.eContainer();
      if (container instanceof ComponentArchitecture) {
        compArch = (ComponentArchitecture) container;
      } else if (container instanceof Structure) {
        compArch = getRootComponentArchitecture((Structure) container);
      } else if (container instanceof Component) {
        compArch = ComponentExt.getRootComponentArchitecture((Component) container);
      }
    }
    return compArch;
  }

  /**
   * Gets the root block architecture from the element container
   * @param elementContainer_p
   *          the element container
   * @return the block architecture
   */
  static public BlockArchitecture getRootBlockArchitecture(Structure modelElement_p) {
	  return BlockArchitectureExt.getRootBlockArchitecture(modelElement_p);
  }

  
  /**
   * Gets all the DataPkgs from the Parent Hierarchy of the root component/component architecture of the current structure according to layer visibility and
   * multiple decomposition rules
   * @param structure_p
   *          the structure
   * @return list of DataPkgs
   */
  static public List<DataPkg> getDataPkgsFromParentHierarchy(Structure structure_p) {
    List<DataPkg> list = new ArrayList<DataPkg>(1);
    if (null != structure_p) {
      BlockArchitecture compArch = getRootBlockArchitecture(structure_p);
      if (null != compArch) {
        DataPkg dataPkg = DataPkgExt.getDataPkgOfBlockArchitecture(compArch);
        if (null != dataPkg) {
          list.add(dataPkg);
        }
        // TODO if the layer visibility is there uncomment the code
        if (compArch instanceof SystemEngineering)
          return list; // return if SystemEngineering
        list.addAll(DataPkgExt.getDataPkgsFromBlockArchitectureParent(compArch));
      }
      Component parentComp = getRootComponent(structure_p);
      if (null != parentComp) {
	      DataPkg dataPkg = parentComp.getOwnedDataPkg();
	      if (null != dataPkg) {
	        list.add(dataPkg);
	      }
	      list.addAll(DataPkgExt.getDataPkgsFromComponentParent(parentComp));
      }
    }
    return list;
  }
  
  /**
   * Gets all the Interfaces from the Parent Hierarchy of the root component/component architecture of the current operation according to layer visibility and
   * multiple decomposition rules
   * @param classifier_p the Classifier
   * @return list of Interfaces
   */
  static public List<Interface> getOwnedInterfacesFromParentHierarchy(Structure classifier_p) {
    List<Interface> list = new ArrayList<Interface>(1);
    if (null != classifier_p) {
      BlockArchitecture compArch = getRootBlockArchitecture(classifier_p);
      if (null != compArch) {
        list.addAll(InterfacePkgExt.getAllInterfaces(compArch.getOwnedInterfacePkg()));
        // Layer visibility is there
        if (compArch instanceof SystemEngineering)
          return list; // return if SystemEngineering
        list.addAll(InterfacePkgExt.getOwnedInterfacesFromBlockArchitectureParent(compArch));
      }
      Component parentComp = getRootComponent(classifier_p);
      if (null != parentComp) {
        list.addAll(InterfacePkgExt.getAllInterfaces(parentComp.getOwnedInterfacePkg()));
        list.addAll(InterfacePkgExt.getOwnedInterfacesFromComponentParent(parentComp));
      }
    }
    return list;
  }
  

  public static String getNewStructureName(Structure interfacePkg, EObject container) {
    return CapellaElementExt.getName(interfacePkg) +
    ICommonConstants.WHITE_SPACE_CHARACTER + ICommonConstants.PARENTHESIS_OPEN_CHARACTER +
    "from" +  //$NON-NLS-1$
    ICommonConstants.WHITE_SPACE_CHARACTER + 
    CapellaElementExt.getName(container) +
    ICommonConstants.PARENTHESIS_CLOSE_CHARACTER;
  }
}
