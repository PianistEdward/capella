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

package org.polarsys.capella.core.platform.sirius.ui.project;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.polarsys.capella.common.tools.report.EmbeddedMessage;
import org.polarsys.capella.common.tools.report.config.registry.ReportManagerRegistry;
import org.polarsys.capella.common.tools.report.util.IReportManagerDefaultComponents;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.model.skeleton.EngineeringDomain;
import org.polarsys.capella.core.platform.sirius.ui.project.operations.CreateCapellaModelOperation;

/**
 * The new Capella model wizard page.
 */
public class NewModelWizardPage extends WizardPage {
  // Log4j reference logger.
  private static final Logger __logger = ReportManagerRegistry.getInstance().subscribe(IReportManagerDefaultComponents.UI);
  // constants
  private static final int SIZING_TEXT_FIELD_WIDTH = 150;

  // The current resource selection
  private IStructuredSelection _selection;
  // The model name text field.
  private Text _modelNameField;
  // The model name.
  private String _modelName;

  // The MDE project.
  private Project _mdeProject;

  private boolean _opaSelected;
  private boolean _epbsSelected;

  // The name field listener.
  private ModifyListener _nameModifyListener = new ModifyListener() {
    public void modifyText(ModifyEvent event_p) {
      boolean valid = validatePage();
      setPageComplete(valid);
    }
  };

  /**
   * Constructs the page of the new CAPELLA model wizard.
   * @param pageName_p The page name.
   * @param selection_p The selection.
   */
  public NewModelWizardPage(String pageName_p, IStructuredSelection selection_p) {
    super(pageName_p);
    setPageComplete(false);
    _selection = selection_p;
    _opaSelected = true;
    _epbsSelected = true;
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    if (!_modelNameField.isDisposed()) {
      _modelNameField.removeModifyListener(_nameModifyListener);
    }
    super.dispose();
  }

  /**
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl(Composite parent_p) {
    // 1 - Initialize the wizard page.
    initializeDialogUnits(parent_p);

    Composite composite = new Composite(parent_p, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    composite.setLayout(layout);
    GridData gData = new GridData();
    gData.horizontalAlignment = SWT.FILL;
    gData.verticalAlignment = SWT.FILL;
    composite.setLayoutData(gData);

    // 2 - Add a label and a text field to get model name from user input.
    Label modelLabel = new Label(composite, SWT.NONE);
    modelLabel.setText(Messages.getString("NewModelWizardPage.model.name")); //$NON-NLS-1$
    modelLabel.setFont(parent_p.getFont());

    _modelNameField = new Text(composite, SWT.BORDER);
    gData = new GridData();
    gData.horizontalAlignment = SWT.FILL;
    gData.grabExcessHorizontalSpace = true;
    gData.minimumWidth = SIZING_TEXT_FIELD_WIDTH;
    gData.minimumWidth = SIZING_TEXT_FIELD_WIDTH;
    _modelNameField.setLayoutData(gData);
    _modelNameField.setFont(parent_p.getFont());

    _modelNameField.addModifyListener(_nameModifyListener);

    // 3 - Add checkboxes to select the optionals Operational Analysis & EPBS Architecture.
    Label architectureOptionsLabel = new Label(composite, SWT.NONE);
    architectureOptionsLabel.setText(Messages.getString("NewModelWizardPage.architectures.options.label")); //$NON-NLS-1$

    Composite container = new Composite(composite, SWT.BORDER);
    GridData containerData = new GridData();
    containerData.horizontalAlignment = SWT.FILL;
    containerData.grabExcessHorizontalSpace = true;
    containerData.verticalAlignment = SWT.FILL;
    containerData.grabExcessVerticalSpace = true;
    container.setLayoutData(containerData);

    GridLayout buttonsLayout = new GridLayout(1, true);
    container.setLayout(buttonsLayout);

    // 3.1 - The "Operational Analysis" check box.
    Button opaCheckBox = createCheckBox(container, Messages.getString("NewModelWizardPage.operationalAnalysis.name"), true, _opaSelected); //$NON-NLS-1$
    opaCheckBox.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent event_p) {
        doOpaSelected(event_p);
      }
    });
    // 3.2 - The "System Analysis" check box.
    createCheckBox(container, Messages.getString("NewModelWizardPage.contextArchitecture.name"), false, true); //$NON-NLS-1$
    // 3.3 - The "Logical Architecture" check box.
    createCheckBox(container, Messages.getString("NewModelWizardPage.logicalArchitecture.name"), false, true); //$NON-NLS-1$
    // 3.4 - The "Physical Architecture" check box.
    createCheckBox(container, Messages.getString("NewModelWizardPage.physicalArchitecture.name"), false, true); //$NON-NLS-1$
    // 3.5 - The "EPBS Architecture" check box.
    Button epbsCheckBox = createCheckBox(container, Messages.getString("NewModelWizardPage.epbsArchitecture.name"), true, _epbsSelected); //$NON-NLS-1$
    epbsCheckBox.addSelectionListener(new SelectionAdapter() {
      /**
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected(SelectionEvent event_p) {
        doEpbsSelected(event_p);
      }
    });
    setPageComplete(validatePage());
    setControl(composite);
  }

  /**
   * Create a check button with given parameter.
   * @param parent_p
   * @param label_p
   * @param enabled <code>true</code> means you can interact with the button.
   * @param defaultSelection_p <code>true</code> means checked.
   */
  protected Button createCheckBox(Composite parent_p, String label_p, boolean enabled_p, boolean defaultSelection_p) {
    Button checkBox = new Button(parent_p, SWT.CHECK);
    checkBox.setText(label_p);
    GridData ctxGridData = new GridData();
    ctxGridData.horizontalAlignment = SWT.FILL;
    checkBox.setLayoutData(ctxGridData);
    checkBox.setSelection(defaultSelection_p);
    checkBox.setEnabled(enabled_p);
    return checkBox;
  }

  /**
   * @param event_p
   */
  protected void doEpbsSelected(SelectionEvent event_p) {
    _epbsSelected = ((Button) event_p.widget).getSelection();
  }

  /**
   * Returns whether this page's controls currently all contain valid values.
   * @return <code>true</code> if all controls are valid, and <code>false</code> if at least one is invalid
   */
  protected boolean validatePage() {
    _modelName = null;

    // Check the selection.
    if ((null == _selection) || _selection.isEmpty()) {
      _mdeProject = null;
      if (null == getPreviousPage()) {
        setErrorMessage("No resource selected."); //$NON-NLS-1$
        return false;
      }
    }

    // Check if the current selection is a MDE project.
    Object selectedObject = null;
    if (null != _selection) {
      selectedObject = _selection.getFirstElement();
      if (!(selectedObject instanceof Project)) {
        _mdeProject = null;
        setErrorMessage("Selected resource isn't a Capella project."); //$NON-NLS-1$
        return false;
      }
    }

    // Check the model name field content.
    if ("".equals(_modelNameField.getText().trim())) { //$NON-NLS-1$
      _mdeProject = null;
      setErrorMessage("Model name must be specified"); //$NON-NLS-1$
      return false;
    }

    // Erase error message & message.
    setErrorMessage(null);
    setMessage(null);

    // Update UML model and Capella project references.
    _mdeProject = (Project) selectedObject;
    if (null == _mdeProject && null == getPreviousPage()) {
      setErrorMessage("Model must be specified"); //$NON-NLS-1$
      return false;
    }
    _modelName = _modelNameField.getText().trim();
    return true;
  }

  /**
   * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean visible_p) {
    super.setVisible(visible_p);
    if (visible_p) {
      _modelNameField.setFocus();
    }
  }

  /**
   * Creates and returns the new Capella model.
   * @param engineeringDomain_p The engineering domain.
   * @param name_p The engineering name.
   */
  public void createNewModel(EngineeringDomain engineeringDomain_p, String name_p, IProgressMonitor monitor_p) {
    CreateCapellaModelOperation modelOp = new CreateCapellaModelOperation(_mdeProject, name_p, engineeringDomain_p, _opaSelected);
    try {
      modelOp.run(monitor_p);
    } catch (InvocationTargetException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("NewModelWizardPage.createNewModel(..) _ "); //$NON-NLS-1$
      loggerMessage.append(exception_p.getMessage());
      __logger.warn(new EmbeddedMessage(loggerMessage.toString(), IReportManagerDefaultComponents.UI), exception_p);
    } catch (InterruptedException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("NewModelWizardPage.createNewModel(..) _ "); //$NON-NLS-1$
      loggerMessage.append(exception_p.getMessage());
      __logger.warn(new EmbeddedMessage(loggerMessage.toString(), IReportManagerDefaultComponents.UI), exception_p);
    }
  }

  /**
   * Gets the model name.
   * @return The model name.
   */
  public String getModelName() {
    return _modelName;
  }

  /**
   * Sets the MDE project.
   * @param mdeProject_p The MDE project.
   */
  public void setMdeProject(Project mdeProject_p) {
    _mdeProject = mdeProject_p;
  }

  /**
   * Gets the MDE project.
   * @return The MDE project.
   */
  public Project getMdeProject() {
    return _mdeProject;
  }

  /**
   * Checks if the operational analysis button is selected.
   * @return <code>True</code> if the Operational analysis is selected else <code>false</code>.
   */
  public boolean isOpaSelected() {
    return _opaSelected;
  }

  /**
   * Checks if the EPBS architecture button is selected.
   * @return <code>True</code> if the Operational analysis is selected else <code>false</code>.
   */
  public boolean isEpbsSelected() {
    return _epbsSelected;
  }

  /**
   * Sets the value_p of the model name field.
   * @param value_p The value to set.
   */
  public void setModelNameFieldValue(String value_p) {
    _modelNameField.setText(value_p);
  }

  /**
   * @param event_p
   */
  protected void doOpaSelected(SelectionEvent event_p) {
    _opaSelected = ((Button) event_p.widget).getSelection();
  }
}
