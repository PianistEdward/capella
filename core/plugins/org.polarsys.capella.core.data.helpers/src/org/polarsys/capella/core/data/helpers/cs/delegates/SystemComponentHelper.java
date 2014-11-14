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
package org.polarsys.capella.core.data.helpers.cs.delegates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.SystemComponent;
import org.polarsys.capella.core.data.cs.SystemComponentCapabilityRealizationInvolvement;
import org.polarsys.capella.core.data.helpers.capellacommon.delegates.CapabilityRealizationInvolvedElementHelper;
import org.polarsys.capella.core.data.capellacommon.CapabilityRealizationInvolvement;

public class SystemComponentHelper {
	private static SystemComponentHelper instance;

	private SystemComponentHelper() {
    // do nothing
	}

	public static SystemComponentHelper getInstance() {
		if (instance == null)
			instance = new SystemComponentHelper();
		return instance;
	}
	
	public Object doSwitch(SystemComponent element_p,
			EStructuralFeature feature_p) {
		Object ret = null;
		
		if (feature_p
				.equals(CsPackage.Literals.SYSTEM_COMPONENT__PARTICIPATIONS_IN_CAPABILITY_REALIZATIONS)) {

			ret = getParticipationsInCapabilityRealizations(element_p);
		} 
		
		// no helper found... searching in super classes...
		if(null == ret) {
			ret = CapabilityRealizationInvolvedElementHelper.getInstance().doSwitch(element_p, feature_p);
		}
		if(null == ret) {
			ret = ComponentHelper.getInstance().doSwitch(element_p, feature_p);
		}
		
		return ret;
	}

	protected List<SystemComponentCapabilityRealizationInvolvement> getParticipationsInCapabilityRealizations(SystemComponent element_p) {
		List<CapabilityRealizationInvolvement> involvements = element_p
				.getInvolvingCapabilityRealizationInvolvements();
		List<SystemComponentCapabilityRealizationInvolvement> ret = new ArrayList<SystemComponentCapabilityRealizationInvolvement>();

		for (CapabilityRealizationInvolvement involvement : involvements) {
			if(involvement instanceof SystemComponentCapabilityRealizationInvolvement)
			ret.add((SystemComponentCapabilityRealizationInvolvement) involvement);
		}

		return ret;
	}

}
