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
package org.polarsys.capella.core.data.information.properties.fields;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import org.polarsys.capella.core.data.information.ParameterDirection;
import org.polarsys.capella.core.data.information.properties.Messages;
import org.polarsys.capella.core.ui.properties.fields.AbstractSemanticKindGroup;

/**
 */
public class ParameterDirectionGroup extends AbstractSemanticKindGroup {
  private Button _directionBtnIn;
  private Button _directionBtnOut;
  private Button _directionBtnInOut;
  private Button _directionBtnReturn;
  private Button _directionBtnException;

  /**
   * Constructor.
   * @param parent_p
   * @param widgetFactory_p
   */
  public ParameterDirectionGroup(Composite parent_p, TabbedPropertySheetWidgetFactory widgetFactory_p) {
    super(parent_p, widgetFactory_p, Messages.getString("ParameterDirection.Label"), 5); //$NON-NLS-1$

    _directionBtnIn = createButton(ParameterDirection.IN);
    _directionBtnOut = createButton(ParameterDirection.OUT);
    _directionBtnInOut = createButton(ParameterDirection.INOUT);
    _directionBtnReturn = createButton(ParameterDirection.RETURN);
    _directionBtnReturn.addSelectionListener(new SelectionListener() {
      public void widgetDefaultSelected(SelectionEvent evt_p) {
        // do nothing
      }
      public void widgetSelected(SelectionEvent evt_p) {
        returnDirectionSelected(((Button) evt_p.widget).getSelection());
      }
    });
    _directionBtnException = createButton(ParameterDirection.EXCEPTION);
  }

  /**
   * This method will be called when 'return' choice is selected (or unselected).
   * Shall be overloaded if a custom behavior is required.
   * @param selected_p
   */
  protected void returnDirectionSelected(boolean selected_p) {
    // by default, do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Button> getSemanticFields() {
    List<Button> fields = new ArrayList<Button>();

    fields.add(_directionBtnIn);
    fields.add(_directionBtnOut);
    fields.add(_directionBtnInOut);
    fields.add(_directionBtnReturn);
    fields.add(_directionBtnException);

    return fields;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Button getDefaultSemanticField() {
    return _directionBtnIn;
  }
}
