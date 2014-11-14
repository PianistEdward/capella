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
package org.polarsys.capella.core.data.interaction;

import org.polarsys.capella.core.data.capellacore.Allocation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scenario Realization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.polarsys.capella.core.data.interaction.ScenarioRealization#getRealizedScenario <em>Realized Scenario</em>}</li>
 *   <li>{@link org.polarsys.capella.core.data.interaction.ScenarioRealization#getRealizingScenario <em>Realizing Scenario</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.polarsys.capella.core.data.interaction.InteractionPackage#getScenarioRealization()
 * @model annotation="http://www.polarsys.org/kitalpha/ecore/documentation description='an allocation link between a scenario, and the scenario that it realizes' usage\040guideline='this link is typically generated by the Capella tool during automated transitions between design levels' used\040in\040levels='operational,system,logical,physical' usage\040examples='n/a' constraints='none' comment/notes='none' reference\040documentation='none'"
 *        annotation="http://www.polarsys.org/capella/MNoE/CapellaLike/Mapping UML/SysML\040semantic\040equivalences='' base\040metaclass\040in\040UML/SysML\040profile\040='uml::Realization' explanation='none' constraints='none'"
 * @generated
 */
public interface ScenarioRealization extends Allocation {





	/**
	 * Returns the value of the '<em><b>Realized Scenario</b></em>' reference.

	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Realized Scenario</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Realized Scenario</em>' reference.
	 * @see org.polarsys.capella.core.data.interaction.InteractionPackage#getScenarioRealization_RealizedScenario()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.polarsys.org/kitalpha/ecore/documentation description='the scenario that is being realized by/from the other scenario' constraints='none' comment/notes='none'"
	 *        annotation="http://www.polarsys.org/capella/MNoE/CapellaLike/Mapping UML/SysML\040semantic\040equivalences='keyword::none' explanation='Derived and transient' constraints='none'"
	 * @generated
	 */

	Scenario getRealizedScenario();







	/**
	 * Returns the value of the '<em><b>Realizing Scenario</b></em>' reference.

	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Realizing Scenario</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Realizing Scenario</em>' reference.
	 * @see org.polarsys.capella.core.data.interaction.InteractionPackage#getScenarioRealization_RealizingScenario()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.polarsys.org/kitalpha/ecore/documentation description='the scenario that realizes (to) the other scenario' constraints='none' comment/notes='none'"
	 *        annotation="http://www.polarsys.org/capella/MNoE/CapellaLike/Mapping UML/SysML\040semantic\040equivalences='keyword::none' explanation='Derived and transient' constraints='none'"
	 * @generated
	 */

	Scenario getRealizingScenario();





} // ScenarioRealization
