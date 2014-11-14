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
package org.polarsys.capella.core.data.helpers.ctx.services;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.core.data.ctx.Mission;
import org.polarsys.capella.core.data.ctx.MissionPkg;

/**
 * MissionPkg helpers.
 */
public class MissionPkgExt {

  /**
   * Get all the missions in a missionPkg (and SUB PKGS) recursively
   * @param missionPkg_p the missionPkg
   * @return list of missions
   */
  static public List<Mission> getAllMissions(MissionPkg missionPkg_p) {
    List<Mission> list = new ArrayList<Mission>(1);
    if (null != missionPkg_p) {
      list.addAll(missionPkg_p.getOwnedMissions());
      for (MissionPkg subPkg : missionPkg_p.getOwnedMissionPkgs()) {
        list.addAll(getAllMissions(subPkg));
      }
    }

    return list;
  }
}
