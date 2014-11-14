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
package org.polarsys.capella.common.re.re2rpl.activities;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.capella.core.transition.common.handlers.filter.CompoundFilteringItems;
import org.polarsys.capella.common.re.handlers.filter.AvoidMergeUnmodifiableItem;
import org.polarsys.capella.common.re.handlers.filter.FilterFromTargetItem;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class DifferencesFilteringActivity extends org.polarsys.capella.common.re.activities.DifferencesFilteringActivity implements ITransposerWorkflow {

  public static final String ID = DifferencesFilteringActivity.class.getCanonicalName();

  @Override
  protected IStatus initializeFilterItemHandlers(IContext context_p, CompoundFilteringItems handler_p, ActivityParameters activityParams_p) {
    super.initializeFilterItemHandlers(context_p, handler_p, activityParams_p);
    handler_p.addFilterItem(new FilterFromTargetItem(), context_p);
    handler_p.addFilterItem(new AvoidMergeUnmodifiableItem(), context_p);

    return Status.OK_STATUS;
  }

  @Override
  @SuppressWarnings("unchecked")
  public IStatus _run(ActivityParameters activityParams_p) {
    return super._run(activityParams_p);
  }

}
