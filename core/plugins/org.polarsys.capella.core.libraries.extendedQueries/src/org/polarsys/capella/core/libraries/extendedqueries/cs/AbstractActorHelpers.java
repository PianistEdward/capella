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
package org.polarsys.capella.core.libraries.extendedqueries.cs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.libraries.IAbstractLibrary;
import org.polarsys.capella.common.libraries.IAbstractModel;
import org.polarsys.capella.common.libraries.ILibraryManager;
import org.polarsys.capella.common.queries.filters.IQueryFilter;
import org.polarsys.capella.common.queries.filters.MultiFilter;
import org.polarsys.capella.common.queries.interpretor.QueryInterpretor;
import org.polarsys.capella.common.queries.queryContext.QueryContext;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.libraries.capellaModel.CapellaLibrary;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.queries.QueryIdentifierConstants;
import org.polarsys.capella.core.model.helpers.queries.filters.PreviousInterfacesForImplementationFilter;
import org.polarsys.capella.core.model.helpers.queries.filters.PreviousInterfacesForUseFilter;
import org.polarsys.capella.core.model.helpers.queries.filters.RemoveAllocatedInterfacesFilter;
import org.polarsys.capella.core.model.utils.ListExt;
import org.polarsys.capella.core.queries.helpers.QueryExt;

public class AbstractActorHelpers {

  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  public static List<CapellaElement> getAvailableElements_Actor_ImplementedInterface(CapellaElement element_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>();
    EObject input = element_p;
    BlockArchitecture block = BlockArchitectureExt.getRootBlockArchitecture(element_p);
    IAbstractModel currentProject = ILibraryManager.INSTANCE.getAbstractModel(input);

    final Component component = (Component) input;
    MultiFilter filter =
        new MultiFilter(new IQueryFilter[] { new RemoveAllocatedInterfacesFilter(), new PreviousInterfacesForImplementationFilter(component) });

    Collection<IAbstractLibrary> libraries = ILibraryManager.INSTANCE.getAllReferencedLibraries(currentProject, true);
    for (IAbstractLibrary library : libraries) {
      EObject correspondingInput = QueryExt.getCorrespondingElementInLibrary(block, (CapellaLibrary) library);
      List<Interface> interfaces =
          QueryInterpretor.executeQuery(QueryIdentifierConstants.GET_ALL_INTERFACES_FOR_ACTOR, correspondingInput, new QueryContext(), filter);
      availableElements.addAll(interfaces);

      if (correspondingInput instanceof PhysicalArchitecture) {
        BlockArchitecture logicalBlock = BlockArchitectureExt.getPreviousBlockArchitecture((BlockArchitecture) correspondingInput).get(0);
        List<Interface> logicalInterfaces =
            QueryInterpretor.executeQuery(QueryIdentifierConstants.GET_ALL_INTERFACES_FOR_ACTOR, logicalBlock, new QueryContext(), filter);
        availableElements.addAll(logicalInterfaces);
      }
    }

    availableElements = ListExt.removeDuplicates(availableElements);
    return availableElements;
  }

  /**
   * @see org.polarsys.capella.core.business.queries.core.business.queries.IBusinessQuery#getAvailableElements(org.polarsys.capella.core.data.capellacore.CapellaElement)
   */
  public static List<CapellaElement> getAvailableElements_Actor_UsedInterface(CapellaElement element_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>();
    EObject input = element_p;
    BlockArchitecture block = BlockArchitectureExt.getRootBlockArchitecture(element_p);
    IAbstractModel currentProject = ILibraryManager.INSTANCE.getAbstractModel(input);

    final Component component = (Component) input;
    MultiFilter filter = new MultiFilter(new IQueryFilter[] { new RemoveAllocatedInterfacesFilter(), new PreviousInterfacesForUseFilter(component) });

    Collection<IAbstractLibrary> libraries = ILibraryManager.INSTANCE.getAllReferencedLibraries(currentProject, true);
    for (IAbstractLibrary library : libraries) {
      EObject correspondingInput = QueryExt.getCorrespondingElementInLibrary(block, (CapellaLibrary) library);
      List<Interface> interfaces =
          QueryInterpretor.executeQuery(QueryIdentifierConstants.GET_ALL_INTERFACES_FOR_ACTOR, correspondingInput, new QueryContext(), filter);
      availableElements.addAll(interfaces);

      if (correspondingInput instanceof PhysicalArchitecture) {
        BlockArchitecture logicalBlock = BlockArchitectureExt.getPreviousBlockArchitecture((BlockArchitecture) correspondingInput).get(0);
        List<Interface> logicalInterfaces =
            QueryInterpretor.executeQuery(QueryIdentifierConstants.GET_ALL_INTERFACES_FOR_ACTOR, logicalBlock, new QueryContext(), filter);
        availableElements.addAll(logicalInterfaces);
      }
    }

    availableElements = ListExt.removeDuplicates(availableElements);
    return availableElements;
  }

}
