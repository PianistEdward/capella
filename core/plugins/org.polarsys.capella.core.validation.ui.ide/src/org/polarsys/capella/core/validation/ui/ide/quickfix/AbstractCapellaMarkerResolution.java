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
package org.polarsys.capella.core.validation.ui.ide.quickfix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;
import org.polarsys.capella.common.helpers.validation.IValidationConstants;
import org.polarsys.capella.common.tools.report.appenders.reportlogview.MarkerViewHelper;
import org.polarsys.capella.core.validation.ui.ide.internal.quickfix.MarkerResolutionCache;

abstract public class AbstractCapellaMarkerResolution extends WorkbenchMarkerResolution {

  protected String _label = null;
  protected String _desc = null;
  protected String _imgKey = null;

  protected String _contributorId = null;
  protected final static String[] noRuleIds = new String[0];

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {
    return _label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return _desc;
  }

  // "quickfixAll-repository.png"

  /**
   * {@inheritDoc}
   */
  @Override
  public Image getImage() {

    if ((_contributorId != null) && (_imgKey != null)) {
      ImageDescriptor imgDesc = AbstractUIPlugin.imageDescriptorFromPlugin(_contributorId, _imgKey);
      return imgDesc.createImage();
    }

    return null;
  }

  /** Write accessor on label */
  public final void setLabel(String label_p) {
    _label = label_p;
    return;
  }

  /** Write accessor on description */
  public final void setDescription(String desc_p) {
    _desc = desc_p;
    return;
  }

  /** Write accessor on imageKey */
  public final void setImgKey(String key_p) {
    _imgKey = key_p;
    return;
  }

  /** Write accessor on contributorId */
  public final void setContributorId(String id_p) {
    _contributorId = id_p;
    return;
  }

  public final String getContributorId() {
    return _contributorId;
  }

  public List<EObject> getModelElements(IMarker marker_p) {
    return MarkerViewHelper.getModelElementsFromMarker(marker_p);
  }

  @Override
  /**
   * Subclasses may override if the default mechanism via canResolve/getResolvableRuleIds is not applicable for this resolution.
   * {@inheritDoc}
   */
  public IMarker[] findOtherMarkers(IMarker[] markers) {

    // this is for backwards compatibility
    if (markers.length == 1) {
      return new IMarker[] { markers[0] };
    }

    Collection<IMarker> otherMarkers = new ArrayList<IMarker>();
    for (IMarker marker : markers) {
      if (canResolve(marker)) {
        otherMarkers.add(marker);
      }
    }
    return otherMarkers.toArray(new IMarker[0]);
  }

  /**
   * Check if this resolution can resolve the given marker. Used to compute the resolvable markers during findOtherMarkers. This implementation checks if the
   * ruleId stored in the marker is one of the ids that's returned by getResolvableRuleIds.
   * @param marker
   * @return
   */
  protected boolean canResolve(IMarker marker) {
    String ruleId = marker.getAttribute(IValidationConstants.TAG_RULE_ID, null);
    if (isEMFRule(ruleId)) {
      return true;
    }
    String fqnRule[] = ruleId.split("\\.");
    String shortRuleId = fqnRule.length > 0 ? fqnRule[fqnRule.length - 1] : null;
    if (shortRuleId != null) {
      for (String id : getResolvableRuleIds()) {
        if (shortRuleId.equals(id)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isEMFRule(String ruleId) {
    return ruleId.startsWith("org.eclipse.emf.ecore.");
  }

  /**
   * Used to compute resolvable markers. Override this method to enable quickfix resolution if multiple markers are selected. The default implementation returns
   * null which effectively disables multi-selection quick fix resolution, unless 'canResolve' or 'findOtherMarkers' is overridden.
   * @return an array of _fully qualified_ validation rule id's that this resolver can fix
   */
  protected String[] getResolvableRuleIds() {

    Map<AbstractCapellaMarkerResolution, Set<String>> resolverRuleMap = MarkerResolutionCache.INSTANCE.getResolverRuleMap();
    Set<String> ruleIds = resolverRuleMap.get(this);
    if (null == ruleIds) {
      return noRuleIds;
    }
    return ruleIds.toArray(new String[0]);
  }

  public boolean isMultipleMarkersResolver() {
    return !(getResolvableRuleIds().length == noRuleIds.length);

  }

}
