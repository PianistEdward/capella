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
package org.polarsys.capella.common.data.helpers.activity.delegates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.common.data.activity.ActivityGroup;
import org.polarsys.capella.common.data.activity.ActivityPackage;
import org.polarsys.capella.common.data.activity.ActivityPartition;

public class ActivityPartitionHelper {

  private static ActivityPartitionHelper instance;

	public static ActivityPartitionHelper getInstance(){
		if (instance == null)
			instance = new ActivityPartitionHelper();
		return instance;
	}

	public Object doSwitch(ActivityPartition element_p, EStructuralFeature feature_p){
		Object ret = null;
		
		if (feature_p.equals(ActivityPackage.Literals.ACTIVITY_PARTITION__SUPER_PARTITION)) {
			ret = getSuperPartition(element_p);
		}
		else if (feature_p.equals(ActivityPackage.Literals.ACTIVITY_PARTITION__SUB_PARTITIONS)) {
			ret = getSubPartitions(element_p);
		}
		
		return ret;
	}
	
	public ActivityPartition getSuperPartition (ActivityPartition element_p) {
		ActivityGroup group = element_p.getSuperGroup();
		if (group instanceof ActivityPartition){
			return (ActivityPartition)group;		
		}
		return null;
	}

	public List<ActivityPartition> getSubPartitions (ActivityPartition element_p) {
		List<ActivityPartition> ret = new ArrayList<ActivityPartition>();
		for (ActivityGroup group : element_p.getSubGroups()) {
			if (group instanceof ActivityPartition){
				ret.add((ActivityPartition) group);		
			}
		}
		return ret;
	}
}
