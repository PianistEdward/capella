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
package org.polarsys.capella.core.transition.common.activities;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.api.IComparison;
import org.eclipse.emf.diffmerge.api.IDiffPolicy;
import org.eclipse.emf.diffmerge.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.api.IMergePolicy;
import org.eclipse.emf.diffmerge.api.Role;
import org.eclipse.emf.diffmerge.api.diff.IDifference;
import org.eclipse.emf.diffmerge.api.scopes.IFeaturedModelScope;
import org.eclipse.osgi.util.NLS;

import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.merge.ExtendedComparison;
import org.polarsys.capella.core.transition.common.policies.diff.ExtDiffPolicy;
import org.polarsys.capella.core.transition.common.policies.match.TraceabilityHandlerMatchPolicy;
import org.polarsys.capella.core.transition.common.policies.merge.ExtMergePolicy;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class DifferencesComputingActivity extends AbstractActivity implements ITransposerWorkflow {

  public static final String ID = "org.polarsys.capella.core.transition.common.activities.DifferencesComputingActivity"; //$NON-NLS-1$

  /*
   * (non-Javadoc)
   * @see org.polarsys.kitalpha.cadence.core.api.IActivity#run(org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters)
   */
  @Override
  @SuppressWarnings("unchecked")
  public IStatus _run(ActivityParameters activityParams_p) {
    IContext context = (IContext) activityParams_p.getParameter(TRANSPOSER_CONTEXT).getValue();

    computeDifferences(context);

    return Status.OK_STATUS;
  }

  /**
   * @param selection_p
   */
  public void computeDifferences(IContext context_p) {

    IFeaturedModelScope sourceScope = (IFeaturedModelScope) context_p.get(ITransitionConstants.MERGE_REFERENCE_SCOPE);
    IFeaturedModelScope targetScope = (IFeaturedModelScope) context_p.get(ITransitionConstants.MERGE_TARGET_SCOPE);

    // Defining comparison with target as TARGET and source as REFERENCE
    IComparison comparison = createComparison(sourceScope, targetScope);

    context_p.put(ITransitionConstants.MERGE_COMPARISON, comparison);

    // Computing differences
    comparison.compute(createMatchPolicy(context_p), createDiffPolicy(context_p), createMergePolicy(context_p), null);

    // Getting differences to merge: all the presences in source
    List<IDifference> toAnalyseFromSource = comparison.getDifferences(Role.REFERENCE);
    List<IDifference> toAnalyseFromTarget = comparison.getDifferences(Role.TARGET);

    context_p.put(ITransitionConstants.MERGE_REFERENCE_DIFFERENCES, toAnalyseFromSource);
    context_p.put(ITransitionConstants.MERGE_TARGET_DIFFERENCES, toAnalyseFromTarget);

    if (displayLog(context_p)) {

      // Logging
      LogHelper.getInstance().debug(NLS.bind("Differences from {0}", Role.REFERENCE.toString()), Messages.Activity_ComputingDifferenceActivity);
      for (IDifference diff : toAnalyseFromSource) {
        LogHelper.getInstance().debug(NLS.bind(" - {0}", diff.toString()), Messages.Activity_ComputingDifferenceActivity);
      }

      LogHelper.getInstance().debug(NLS.bind("Differences from {0}", Role.TARGET.toString()), Messages.Activity_ComputingDifferenceActivity);
      for (IDifference diff : toAnalyseFromTarget) {
        LogHelper.getInstance().debug(NLS.bind(" - {0}", diff.toString()), Messages.Activity_ComputingDifferenceActivity);
      }

    }

  }

  /**
   * @param context_p
   * @return
   */
  protected boolean displayLog(IContext context_p) {
    return true;
  }

  /**
   * @param context_p
   * @return
   */
  protected IMergePolicy createMergePolicy(IContext context_p) {
    return new ExtMergePolicy(context_p);
  }

  /**
   * @param context_p
   * @return
   */
  protected IDiffPolicy createDiffPolicy(IContext context_p) {
    IDiffPolicy policy = new ExtDiffPolicy(context_p);
    return policy;
  }

  protected IComparison createComparison(IFeaturedModelScope sourceScope_p, IFeaturedModelScope targetScope_p) {
    return new ExtendedComparison(targetScope_p, sourceScope_p);
  }

  /**
   * @return
   */
  protected IMatchPolicy createMatchPolicy(IContext context_p) {
    IMatchPolicy policy = new TraceabilityHandlerMatchPolicy(context_p);
    return policy;
  }

}
