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
package org.polarsys.capella.core.data.helpers.fa.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 */
public abstract class BusPatternHelper extends PatternHelper {

	public Map<EClass, EndDescription> getEndsMap() {
		if (getPattern() instanceof BusPattern) {
			return ((BusPattern) getPattern()).getEndsMap();
		}
		return null;
	}

	public EClass getBusEClass() {
		if (getPattern() instanceof BusPattern) {
			return ((BusPattern) getPattern()).getBusEClass();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public Set<EObject> getAvailable(EObject from_p) {
		if (from_p == null)
			return null;
		Map<EClass, EndDescription> endsMap = getEndsMap();
		EClass busEClass = getBusEClass();
		Set<EClass> endsEClass = endsMap.keySet();
		if (endsMap == null || busEClass == null)
			return null;
		Set<EObject> available = new HashSet<EObject>();
		if (busEClass.isSuperTypeOf(from_p.eClass())) {
			return available;
		}
		for (EClass endEClass : endsEClass) {
			if (busEClass.isSuperTypeOf(from_p.eClass())) {
				return available;
			}
		}
		// found nothing...
		return available;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<EObject> getCurrent(EObject from_p) {
		if (from_p == null)
			return null;
		Set<EObject> current = new HashSet<EObject>();
		Map<EClass, EndDescription> endsMap = getEndsMap();
		EClass busEClass = getBusEClass();
		Set<EClass> endsEClass = endsMap.keySet();
		if (endsMap == null || busEClass == null)
			return null;
		if (busEClass.isSuperTypeOf(from_p.eClass())) {
			for (EClass endEClass : endsEClass) {
				EndDescription desc = endsMap.get(endEClass);
				String endName = desc.getEndName();
				if (endName != "" && !busEClass.getEAllReferences().isEmpty()) { //$NON-NLS-1$
					List<EReference> references = busEClass.getEAllReferences();
					for (EReference ref : references) {
						if (ref.getName().compareTo(endName) == 0) {
							if (ref.isMany()) {
								EList<EObject> many = (EList<EObject>) from_p.eGet(ref, true /* resolve */);
								if (many != null && many.isEmpty())
									current.addAll(many);
								return current;
							}
							EObject single = (EObject) from_p.eGet(ref, true /* resolve */);
							if (single != null)
								current.add(single);
							return current;
						}
					}
				}
			}
			return current;
		}
		for (EClass endEClass : endsEClass) {
			EndDescription desc = endsMap.get(endEClass);
			String busName = desc.getBusName();
			if (busName != "" && !endEClass.getEAllReferences().isEmpty()) { //$NON-NLS-1$
				List<EReference> references = busEClass.getEAllReferences();
				for (EReference ref : references) {
					if (ref.getName().compareTo(busName) == 0) {
						if (ref.isMany()) {
							EList<EObject> many = (EList<EObject>) from_p.eGet(ref, true /* resolve */);
							if (many != null && many.isEmpty())
								current.addAll(many);
							return current;
						}
						EObject single = (EObject) from_p.eGet(ref, true /* resolve */);
						if (single != null)
							current.add(single);
						return current;
					}
				}
			}
		}
		// found nothing...
		return current;
	}

	/**
	 * Extracts first list object and checks if it can be a Bus. Handles job to
	 * {@link #validateBusPattern(EObject, Set)}.
	 * 
	 * @return false if first list object can not be viewed as bus or
	 *         {@link #validateBusPattern(EObject, Set)} returns false
	 */
	@Override
	public boolean validatePattern(Set<EObject> objects_p) {
		if (objects_p == null || objects_p.isEmpty())
			return false;

		return false;
	}

	/**
	 * Checks that objects are currently connected by our Pattern. IE
	 * {@link #validatePattern(Set)} returns true.
	 */
	@Override
	public boolean validateDisconnection(Set<EObject> list_p) {
		return false;
	}

	/**
	 * Checks that objects are currently not connected as defined by our
	 * Pattern. IE {@link #validatePattern(Set)} returns false.
	 */
	@Override
	public boolean validateConnection(Set<EObject> list_p) {
		return false;
	}
}
