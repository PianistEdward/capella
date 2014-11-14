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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationChainImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * An event process that stores ADD and REMOVE notifications and enable to clear them.
 */
public class MoveElementEventProcessor implements EventProcessor {

  /**
   * stores ADD element notifications
   */
  EObjectNotificationMap newValueNotificationMap = new EObjectNotificationMap();
  /**
   * stores REMOVE element notifications
   */
  EObjectNotificationMap oldValueNotificationMap = new EObjectNotificationMap();

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(Notification notif_p) {
    if (notif_p.getEventType() == Notification.ADD) {
      Object newValue = notif_p.getNewValue();
      if (newValue instanceof EObject) {
        newValueNotificationMap.add((EObject) newValue, notif_p);
      }
    }
    if (notif_p.getEventType() == Notification.REMOVE) {
      Object oldValue = notif_p.getOldValue();
      if (oldValue instanceof EObject) {
        oldValueNotificationMap.add((EObject) oldValue, notif_p);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearConsumed() {
    for (EObject clazz : oldValueNotificationMap.keySet()) {
      clearNotificationChains(oldValueNotificationMap.get(clazz), newValueNotificationMap.get(clazz));
    }
    clearMaps();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void process() {
    // do nothing

  }

  private void clearNotificationChains(NotificationChainImpl notifChain1, NotificationChainImpl notifChain2) {
    if ((null == notifChain1) || (null == notifChain2)) {
      return;
    }
    if (notifChain1.size() == notifChain2.size()) {
      notifChain1.clear();
      notifChain2.clear();
    } else {
      List<Notification> toRemoveFrom1 = new ArrayList<Notification>();
      List<Notification> toRemoveFrom2 = new ArrayList<Notification>();
      for (Notification n1 : notifChain1) {
        for (Notification n2 : notifChain2) {
          if (n1.getNotifier() == n2.getNotifier()) {
            toRemoveFrom1.add(n1);
            toRemoveFrom2.add(n2);
          }
        }
      }
      notifChain1.removeAll(toRemoveFrom1);
      notifChain1.removeAll(toRemoveFrom2);
    }

  }

  private void clearMaps() {
    Set<EObject> toRemoveFromNew = new HashSet<EObject>();
    Set<EObject> toRemoveFromOld = new HashSet<EObject>();

    for (EObject c1 : newValueNotificationMap.keySet()) {
      NotificationChainImpl chain = newValueNotificationMap.get(c1);
      if ((null == chain) || chain.isEmpty()) {
        toRemoveFromNew.add(c1);
      }
    }
    for (EObject c2 : oldValueNotificationMap.keySet()) {
      NotificationChainImpl chain = oldValueNotificationMap.get(c2);
      if ((null == chain) || chain.isEmpty()) {
        toRemoveFromNew.add(c2);
      }
      for (EObject c : toRemoveFromOld) {
        oldValueNotificationMap.remove(c);

      }
      for (EObject c : toRemoveFromNew) {
        newValueNotificationMap.remove(c);

      }
    }

  }

}
