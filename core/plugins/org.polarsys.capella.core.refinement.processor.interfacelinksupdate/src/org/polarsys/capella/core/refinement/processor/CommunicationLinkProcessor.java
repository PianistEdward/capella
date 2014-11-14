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
package org.polarsys.capella.core.refinement.processor;

import java.util.List;

import org.polarsys.capella.core.data.epbs.ConfigurationItem;
import org.polarsys.capella.core.data.epbs.EpbsPackage;
import org.polarsys.capella.core.data.information.communication.CommunicationLink;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;

/**
 */
public class CommunicationLinkProcessor extends AbstractInterfaceProcessor {

  /**
   * Default constructor
   */
  public CommunicationLinkProcessor() {
    super();
  }

  /**
   * Constructor
   * 
   * @param context_p the Element on which the processing will applied
   */
  public CommunicationLinkProcessor(CapellaElement context_p) {
    super(context_p);
  }

  /**
   * @see org.polarsys.capella.core.refinement.scenarios.core.plugs.IRefinementPlug#getName()
   */
  public Object getName() {
    return "communication link update"; //$NON-NLS-1$
  }

  /**
   * @see org.polarsys.capella.core.refinement.processor.AbstractInterfaceProcessor#synchronize(org.polarsys.capella.core.data.la.LogicalComponent)
   */
  @Override
  protected void synchronize(LogicalComponent component_p) {
    List<CommunicationLink> communicationLinksToAdd   = InterfaceProcessorHelper.computeCommunicationLinksToAdd(component_p);
    List<CommunicationLink> communicationLinksToRemove = InterfaceProcessorHelper.computeCommunicationLinksToRemove(component_p, communicationLinksToAdd, LaPackage.Literals.LOGICAL_ARCHITECTURE);

    // Add and Remove Communication Links computed
    InterfaceProcessorHelper.addCommunicationLink(component_p, communicationLinksToAdd, communicationLinksToRemove);
    InterfaceProcessorHelper.removeCommunicationLink(component_p, communicationLinksToRemove);
  }

  /**
   * @see org.polarsys.capella.core.refinement.processor.AbstractInterfaceProcessor#synchronize(org.polarsys.capella.core.data.pa.PhysicalComponent)
   */
  @Override
  protected void synchronize(PhysicalComponent component_p) {
    List<CommunicationLink> communicationLinksToAdd   = InterfaceProcessorHelper.computeCommunicationLinksToAdd(component_p);
    List<CommunicationLink> communicationLinksToRemove = InterfaceProcessorHelper.computeCommunicationLinksToRemove(component_p, communicationLinksToAdd, PaPackage.Literals.PHYSICAL_ARCHITECTURE);

    // Add and Remove Communication Links computed
    InterfaceProcessorHelper.addCommunicationLink(component_p, communicationLinksToAdd, communicationLinksToRemove);
    InterfaceProcessorHelper.removeCommunicationLink(component_p, communicationLinksToRemove);
  }

  /**
   * @see org.polarsys.capella.core.refinement.processor.AbstractInterfaceProcessor#synchronize(org.polarsys.capella.core.data.epbs.ConfigurationItem)
   */
  @Override
  protected void synchronize(ConfigurationItem component_p) {
    List<CommunicationLink> communicationLinksToAdd   = InterfaceProcessorHelper.computeCommunicationLinksToAdd(component_p);
    List<CommunicationLink> communicationLinksToRemove = InterfaceProcessorHelper.computeCommunicationLinksToRemove(component_p, communicationLinksToAdd, EpbsPackage.Literals.EPBS_ARCHITECTURE);

    // Add and Remove Communication Links computed
    InterfaceProcessorHelper.addCommunicationLink(component_p, communicationLinksToAdd, communicationLinksToRemove);
    InterfaceProcessorHelper.removeCommunicationLink(component_p, communicationLinksToRemove);
  }
}
