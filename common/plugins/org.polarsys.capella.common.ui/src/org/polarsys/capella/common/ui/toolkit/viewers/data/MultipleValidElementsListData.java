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
package org.polarsys.capella.common.ui.toolkit.viewers.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * {@link ListData} implementation that supports to have the same object several times as valid elements.
 */
public class MultipleValidElementsListData extends ListData implements IMoveableData {
  /**
   * Constructor.
   * @param displayedElements_p
   * @param context_p
   */
  public MultipleValidElementsListData(List<? extends Object> displayedElements_p, Object context_p) {
    super(displayedElements_p, context_p);
  }

  /**
   * @see org.polarsys.capella.common.ui.toolkit.viewers.data.AbstractData#initializeRootElementCollection()
   */
  @Override
  protected Collection<Object> initializeRootElementCollection() {
    return new ArrayList<Object>(0);
  }

  /**
   * @see org.polarsys.capella.common.ui.toolkit.viewers.data.AbstractData#initializeValidElementCollection(java.util.List)
   */
  @Override
  protected Collection<Object> initializeValidElementCollection(Collection<? extends Object> displayedElements_p) {
    return new ArrayList<Object>(displayedElements_p);
  }

  /**
   * @see org.polarsys.capella.common.ui.toolkit.viewers.data.IMoveableData#swap(java.lang.Object, int, int)
   */
  public void swap(Object child_p, int index_p, int newIndex_p) {
    // Swap elements in list.
    Collections.swap((List<?>) _rootElements, index_p, newIndex_p);
    Collections.swap((List<?>) _validElements, index_p, newIndex_p);
  }
}
