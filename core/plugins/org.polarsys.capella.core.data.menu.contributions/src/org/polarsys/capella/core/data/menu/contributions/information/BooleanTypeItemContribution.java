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
package org.polarsys.capella.core.data.menu.contributions.information;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.polarsys.capella.core.data.information.datatype.DatatypePackage;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.menu.dynamic.contributions.IMDEMenuItemContribution;

public class BooleanTypeItemContribution implements IMDEMenuItemContribution {

	/**
	 * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#selectionContribution()
	 */
	public boolean selectionContribution(ModelElement modelElement_p, EClass cls_p, EStructuralFeature feature_p) {
		return true;
	}

	/**
	 * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#executionContribution()
	 */
	public Command executionContribution(EditingDomain editingDomain_p, ModelElement containerElement_p, ModelElement createdElement_p, EStructuralFeature feature_p) {
		return new SetCommand(editingDomain_p, createdElement_p, DatatypePackage.Literals.DATA_TYPE__DISCRETE, Boolean.TRUE);
	}

	/**
	 * @see org.polarsys.capella.common.ui.menu.IMDEMenuItemContribution#getMetaclass()
	 */
	public EClass getMetaclass() {
		return DatatypePackage.Literals.BOOLEAN_TYPE;
	}
}
