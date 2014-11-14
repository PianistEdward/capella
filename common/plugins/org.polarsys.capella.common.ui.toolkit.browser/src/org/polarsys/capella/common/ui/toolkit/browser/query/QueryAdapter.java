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
package org.polarsys.capella.common.ui.toolkit.browser.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.polarsys.kitalpha.emde.extension.ModelExtensionHelper;
import org.polarsys.kitalpha.emde.extension.ModelExtensionManager;
import org.polarsys.capella.common.helpers.adapters.MDEAdapterFactory;
import org.polarsys.capella.common.helpers.query.IQuery;
import org.polarsys.capella.common.tig.ef.command.AbstractReadOnlyCommand;

/**
 */
public class QueryAdapter {
  /**
   * Singleton.
   */
  private static QueryAdapter _instance = null;

  /**
   * Singleton constructor.
   */
  private QueryAdapter() {
    // nothing to do.
  }

  /**
   * Singleton accessor.
   * @return
   */
  public static QueryAdapter getInstance() {
    if (_instance == null) {
      _instance = new QueryAdapter();
    }
    return _instance;
  }

  /**
   * Compute a query and return its result.
   * @param currentElement_p context of the query.
   */
  @SuppressWarnings("unchecked")
  public List<Object> compute(Object currentElement_p, Object query_p) {
    QueryComputeCommand queryComputeCommand = new QueryComputeCommand(currentElement_p, query_p);
    MDEAdapterFactory.getExecutionManager().execute(queryComputeCommand);
    List<Object> result = new ArrayList<Object>((List<Object>) queryComputeCommand.getResult());
    // Trim result from useless 'null' values.
    for (Iterator<Object> iterator = result.iterator(); iterator.hasNext();) {
      if (null == iterator.next()) {
        iterator.remove();
      }
    }
    return result;
  }

  /**
   * Command for computing query.
   */
  protected class QueryComputeCommand extends AbstractReadOnlyCommand {
    protected List<Object> internalResult = null;
    protected Object currentElement = null;
    protected Object query = null;

    /**
     * Constructor
     */
    public QueryComputeCommand(Object currentElement_p, Object query_p) {
      internalResult = new ArrayList<Object>(0);
      currentElement = currentElement_p;
      query = query_p;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @SuppressWarnings("unchecked")
    public void run() {
      if (query instanceof IQuery) {
    	  ModelExtensionManager mgr = ModelExtensionHelper.getInstance();
    	  for (Object o : ((IQuery) query).compute(currentElement))
    	  {
    		  if (!mgr.isExtensionModelDisabled(o))
    				  internalResult.add(o);
    	  }
        setResult(internalResult);
      }
    }
  }
}
