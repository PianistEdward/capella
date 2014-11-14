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
package org.polarsys.capella.core.platform.sirius.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.business.queries.fa.ComponentExchange_Categories;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeCategory;
import org.polarsys.capella.core.data.oa.CommunicationMean;

public class UpdateCECategoriesController extends UpdateCategoriesController {
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateCategories(List<EObject> selectedElements_p, List<EObject> categoriesToAdd_p, List<EObject> categoriesToRemove_p) {
    for (EObject e : selectedElements_p) {
      if (e instanceof ComponentExchange) {
        EList<ComponentExchangeCategory> categories = ((ComponentExchange) e).getCategories();
        categories.addAll((Collection<? extends ComponentExchangeCategory>) categoriesToAdd_p);
        categories.removeAll(categoriesToRemove_p);
      }
    }
    if (!selectedElements_p.isEmpty() && (selectedElements_p.get(0) instanceof CommunicationMean)) {
      logResults(Messages.UpdateCMCategories_add_msg, categoriesToAdd_p);
      logResults(Messages.UpdateCMCategories_remove_msg, categoriesToRemove_p);

    } else {
      logResults(Messages.UpdateCECategories_add_msg, categoriesToAdd_p);
      logResults(Messages.UpdateCECategories_remove_msg, categoriesToRemove_p);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<EObject> getAvailableCategories(List<EObject> element_p) {
    List<EObject> result = new ArrayList<EObject>();
    // get all CE categories
    for (EObject fe : element_p) {
      if (fe instanceof ComponentExchange) {
        List<CapellaElement> categories = new ComponentExchange_Categories().getAvailableElements((ComponentExchange) fe);
        result.addAll(categories);
      }
    }
    return result;

  }

  /**
   * Compute intersection of all CE assigned categories {@inheritDoc}
   */
  @Override
  public List<EObject> getCommonCategories(List<EObject> selection_p) {
    List<EObject> result = new ArrayList<EObject>();
    if (selection_p.isEmpty()) {
      return result;
    }
    EObject first = selection_p.get(0);
    if (first instanceof ComponentExchange) {
      result.addAll(((ComponentExchange) first).getCategories());
    }
    for (EObject eObject : selection_p) {
      if (eObject instanceof ComponentExchange) {
        result.retainAll(((ComponentExchange) eObject).getCategories());
      }
    }

    return result;
  }
}
