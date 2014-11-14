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
package org.polarsys.capella.core.model.preferences;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.information.AbstractInstance;
import org.polarsys.capella.core.data.information.Partition;
import org.polarsys.capella.core.data.information.PartitionableElement;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.model.handler.helpers.CapellaProjectHelper;
import org.polarsys.capella.core.model.handler.helpers.CapellaProjectHelper.TriStateBoolean;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.common.tig.ef.command.AbstractReadWriteCommand;

/**
 */
public class CapellaModelDataListenerForPartsAndComponents extends CapellaModelDataListener {
  /**
   * This listener will rename: <li>all the Parts typed by a PartitionableElement, according to the new PartitionableElement's name <li>the name of the part's
   * Type, according its new name <li>the name of the part, according its new Type <li>all the InstanceRoles typed by a Part, according to the new Part's name
   * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
   */
  @Override
  public void notifyChanged(Notification notification_p) {
    // pre-condition: call contributed filters
    if (filterNotification(notification_p)) {
      return;
    }

    // pre-condition: only SET notifications are wanted
    if (notification_p.getEventType() != Notification.SET) {
      return;
    }

    EStructuralFeature feature = (EStructuralFeature) notification_p.getFeature();
    if (feature != null) {
      if (feature.equals(ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME)) {
        final String value = notification_p.getNewStringValue();
        Object notifier = notification_p.getNotifier();
        // Check the project that contains given notifier is not in Reusable Components mode.
        if (!TriStateBoolean.False.equals(CapellaProjectHelper.isReusableComponentsDriven((ModelElement) notifier))) {
          return;
        }
        if (notifier instanceof PartitionableElement) {
          for (final Partition part : ((PartitionableElement) notifier).getRepresentingPartitions()) {

            if ((part != null) && !StringUtils.equals(part.getName(), value)) {
              executeCommand(new AbstractReadWriteCommand() {
                public void run() {
                  part.setName(value);
                  renameInstanceRole(part, value);
                }
              });
            }
          }
        } else if (notifier instanceof Partition) {
          final Type type = ((Partition) notifier).getType();
          if ((type != null) && !StringUtils.equals(type.getName(), value)) {
            executeCommand(new AbstractReadWriteCommand() {
              public void run() {
                type.setName(value);
              }
            });
          }
          renameInstanceRole((Partition) notifier, value);
        } else if (notifier instanceof InstanceRole) {
          final AbstractInstance instance = ((InstanceRole) notifier).getRepresentedInstance();
          if ((instance != null) && !StringUtils.equals(instance.getName(), value)) {
            executeCommand(new AbstractReadWriteCommand() {
              public void run() {
                instance.setName(value);
              }
            });
          }
        }
      } else if (feature.equals(ModellingcorePackage.Literals.ABSTRACT_TYPED_ELEMENT__ABSTRACT_TYPE)) {
        Object value = notification_p.getNewValue();
        Object notifier = notification_p.getNotifier();
        // Check the project that contains given notifier is not in Reusable Components mode.
        if (!TriStateBoolean.False.equals(CapellaProjectHelper.isReusableComponentsDriven((ModelElement) notifier))) {
          return;
        }
        if ((notifier instanceof Partition) && (value instanceof AbstractType)) {
          final Partition part = (Partition) notifier;
          final AbstractType type = (AbstractType) value;
          if ((type != null) && (part != null) && !StringUtils.equals(part.getName(), type.getName())) {
            executeCommand(new AbstractReadWriteCommand() {
              public void run() {
                part.setName(type.getName());
                renameInstanceRole(part, type.getName());
              }
            });
          }
        }
      }
    }
  }

  /**
   * @param part_p
   * @param name_p
   */
  protected void renameInstanceRole(Partition part_p, String name_p) {
    for (EObject role : EObjectExt.getReferencers(part_p, InteractionPackage.Literals.INSTANCE_ROLE,
        InteractionPackage.Literals.INSTANCE_ROLE__REPRESENTED_INSTANCE)) {
      if ((role != null) && !StringUtils.equals(((InstanceRole) role).getName(), name_p)) {
        ((InstanceRole) role).setName(name_p);
      }
    }
  }
}
