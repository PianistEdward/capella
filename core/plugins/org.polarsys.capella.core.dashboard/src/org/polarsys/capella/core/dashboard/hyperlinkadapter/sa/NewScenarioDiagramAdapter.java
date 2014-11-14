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
package org.polarsys.capella.core.dashboard.hyperlinkadapter.sa;

import org.eclipse.sirius.business.api.session.Session;

import org.polarsys.capella.core.dashboard.hyperlinkadapter.AbstractNewDiagramHyperlinkAdapter;
import org.polarsys.capella.core.dashboard.hyperlinkadapter.ModelCreationHelper;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.interaction.ScenarioKind;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.sirius.analysis.IDiagramNameConstants;
import org.polarsys.capella.core.model.helpers.ModelQueryHelper;
import org.polarsys.capella.common.data.modellingcore.ModelElement;

/**
 * New Interface scenario for SA.
 */
public class NewScenarioDiagramAdapter extends AbstractNewDiagramHyperlinkAdapter {
  /**
   * Constructor.
   * @param capellaProject_p
   * @param session_p
   */
  public NewScenarioDiagramAdapter(Project capellaProject_p, Session session_p) {
    super(capellaProject_p, session_p);
  }

  /**
   * @see org.polarsys.capella.core.dashboard.hyperlinkadapter.AbstractNewDiagramHyperlinkAdapter#getDiagramName()
   */
  @Override
  protected String getDiagramName() {
    return IDiagramNameConstants.INTERFACE_SCENARIO_DIAGRAM_NAME;
  }

  /**
   * @see org.polarsys.capella.core.dashboard.hyperlinkadapter.AbstractHyperlinkAdapter#getModelElement(org.polarsys.capella.core.data.capellamodeller.Project)
   */
  @Override
  protected ModelElement getModelElement(Project project_p) {
    SystemAnalysis systemAnalysis = ModelQueryHelper.getSystemAnalysis(project_p);
    return ModelCreationHelper.selectCapabilityAndCreateScenario(project_p, systemAnalysis, ScenarioKind.INTERFACE);
  }
}
