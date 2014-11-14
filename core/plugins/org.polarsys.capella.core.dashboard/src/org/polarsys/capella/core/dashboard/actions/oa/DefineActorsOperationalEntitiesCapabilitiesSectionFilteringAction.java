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
package org.polarsys.capella.core.dashboard.actions.oa;

import java.util.List;

import org.polarsys.capella.common.mdsofa.common.helper.MiscHelper;
import org.polarsys.capella.core.dashboard.editor.pages.AbstractCapellaArchitectureDashboardPage;
import org.polarsys.capella.core.sirius.analysis.IDiagramNameConstants;

/**
 * Section filter for Define Actors, Operational Entities and Operational Capabilities.
 */
public class DefineActorsOperationalEntitiesCapabilitiesSectionFilteringAction extends AbstractOAViewerFilteringAction {
  /**
   * Constructor.
   * @param capellaArchitecturePage_p
   */
  public DefineActorsOperationalEntitiesCapabilitiesSectionFilteringAction(AbstractCapellaArchitectureDashboardPage capellaArchitecturePage_p) {
    super(capellaArchitecturePage_p);
  }

  /**
   * @see org.polarsys.capella.core.dashboard.actions.AbstractViewerFilteringAction#getRetainedRepresentationDescriptions()
   */
  @Override
  protected List<String> getRetainedRepresentationDescriptions() {
    String[] retainedRepresentationDescriptions = {
      IDiagramNameConstants.OPERATIONAL_ENTITY_BREAKDOWN_DIAGRAM_NAME,
      IDiagramNameConstants.OPERATIONAL_ROLE_BLANK_DIAGRAM_NAME,
      IDiagramNameConstants.OPERATIONAL_ENTITY_BLANK_DIAGRAM_NAME
    };
    return MiscHelper.asList(retainedRepresentationDescriptions);
  }
}
