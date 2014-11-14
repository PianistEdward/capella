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
package org.polarsys.capella.core.sequencediag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.description.filter.FilterDescription;

import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;
import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.business.queries.capellacore.BusinessQueriesProvider;
import org.polarsys.capella.core.data.cs.AbstractActor;
import org.polarsys.capella.core.data.cs.Block;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ExchangeItemAllocation;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.SystemComponent;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.AbstractFunctionalBlock;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeFunctionalExchangeAllocation;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.helpers.capellacore.services.GeneralizableElementExt;
import org.polarsys.capella.core.data.information.AbstractEventOperation;
import org.polarsys.capella.core.data.information.AbstractInstance;
import org.polarsys.capella.core.data.information.ElementKind;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.Parameter;
import org.polarsys.capella.core.data.information.ParameterDirection;
import org.polarsys.capella.core.data.interaction.AbstractEnd;
import org.polarsys.capella.core.data.interaction.AbstractFragment;
import org.polarsys.capella.core.data.interaction.CombinedFragment;
import org.polarsys.capella.core.data.interaction.Event;
import org.polarsys.capella.core.data.interaction.EventReceiptOperation;
import org.polarsys.capella.core.data.interaction.EventSentOperation;
import org.polarsys.capella.core.data.interaction.Execution;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.interaction.InteractionFragment;
import org.polarsys.capella.core.data.interaction.InteractionOperand;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.core.data.interaction.InteractionState;
import org.polarsys.capella.core.data.interaction.MessageEnd;
import org.polarsys.capella.core.data.interaction.MessageKind;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.ScenarioKind;
import org.polarsys.capella.core.data.interaction.SequenceMessage;
import org.polarsys.capella.core.data.interaction.StateFragment;
import org.polarsys.capella.core.data.interaction.TimeLapse;
import org.polarsys.capella.core.data.interaction.properties.controllers.InterfaceHelper;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.oa.ActivityAllocation;
import org.polarsys.capella.core.data.oa.Entity;
import org.polarsys.capella.core.data.oa.OperationalActivity;
import org.polarsys.capella.core.data.oa.Role;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.sirius.analysis.IMappingNameConstants;
import org.polarsys.capella.core.sirius.analysis.InformationServices;
import org.polarsys.capella.core.sirius.analysis.SequenceDiagramServices;
import org.polarsys.capella.core.libraries.extendedqueries.QueryIdentifierConstants;
import org.polarsys.capella.core.model.handler.helpers.CapellaProjectHelper;
import org.polarsys.capella.core.model.handler.helpers.CapellaProjectHelper.TriStateBoolean;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.model.helpers.ScenarioExt;
import org.polarsys.capella.core.model.helpers.SequenceMessageExt;
import org.polarsys.capella.core.model.helpers.queries.filters.OnlySharedDataOrEventOrUnsetFilter;
import org.polarsys.capella.common.data.modellingcore.AbstractExchangeItem;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.queries.filters.IQueryFilter;
import org.polarsys.capella.common.queries.interpretor.QueryInterpretor;
import org.polarsys.capella.common.queries.queryContext.QueryContext;

/**
 * Services to manipulate Capella scenario.
 */
public class ScenarioService {

  private static final String EMPTY_STRING = ICommonConstants.EMPTY_STRING;

  /**
   * Moves the end <code>toMove</code> just after the end <code>previousEnd</code>. used in common.odesign, oa.odesign, sequences.odesign
   * @param toMove the end to move
   * @param previousEnd the previous end.
   * @return the moved end.
   */
  public EObject moveEndOnScenario(final InteractionFragment toMove, final InteractionFragment previousEnd) {
    return ScenarioExt.moveEndOnScenario(toMove, previousEnd);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<ExchangeItem> getSharedDataEventForSD(DSemanticDecorator elementView) {
    IQueryFilter filter = new OnlySharedDataOrEventOrUnsetFilter();
    return (List) QueryInterpretor.executeQuery(QueryIdentifierConstants.GET_ALL_EXCHANGE_ITEMS_FOR_LIB, elementView.getTarget(), new QueryContext(), filter);
  }

  // END CODE REFACTOR

  /**
   * Move the end <code>toMove</code> just after the end <code>previousEnd</code> on the instance role of <code>toMove</code>. used in common.odesign,
   * oa.odesign, sequences.odesign
   * @param toMove the end to move.
   * @param previousEnd the previous end (it doesn't need to be on the same instance role of <code>toMove</code>).
   * @return the moved end.
   */
  public EObject moveEndOnInstanceRole(final InteractionFragment toMove, final InteractionFragment previousEnd) {
    final Scenario scenario = (Scenario) previousEnd.eContainer();
    final InstanceRole covered = toMove.getCoveredInstanceRoles().get(0);
    /*
     * First of all: move the messageEnd to the end of the list
     */
    covered.getAbstractEnds().move(covered.getAbstractEnds().size() - 1, (AbstractEnd) toMove);

    /*
     * Compute the new index and move !
     */
    final int previousEndIndex = scenario.getOwnedInteractionFragments().indexOf(previousEnd);

    AbstractEnd previousOnIR = null;
    for (final AbstractEnd end : covered.getAbstractEnds()) {
      if (end == previousEnd) {
        previousOnIR = end;
        break;
      } else if (scenario.getOwnedInteractionFragments().indexOf(end) > previousEndIndex) {
        break;
      } else {
        previousOnIR = end;
      }
    }
    int newIndex = previousOnIR == null ? 0 : covered.getAbstractEnds().indexOf(previousOnIR) + 1;
    if (newIndex >= covered.getAbstractEnds().size()) {
      newIndex = covered.getAbstractEnds().size() - 1;
    }
    covered.getAbstractEnds().move(newIndex, (AbstractEnd) toMove);
    return toMove;
  }

  /**
   * Moves the end <code>toMove</code> on the beginning of the scenario. used in common.odesign, oa.odesign, sequences.odesign
   * @param toMove the end to move
   * @return the moved end.
   */
  public EObject moveEndOnBeginingOfScenario(final InteractionFragment toMove) {
    return ScenarioExt.moveEndOnBeginingOfScenario(toMove);
  }

  /**
   * Move the end <code>toMove</code> on the begining of the instance role. used in common.odesign, oa.odesign, sequences.odesign
   * @param toMove the end to move.
   * @return the moved end.
   */
  public EObject moveEndOnBeginingOfInstanceRole(final InteractionFragment toMove) {
    if (toMove instanceof AbstractEnd) {
      toMove.getCoveredInstanceRoles().get(0).getAbstractEnds().move(0, (AbstractEnd) toMove);
    }
    return toMove;
  }

  /**
   * Moves the message <code>toMove</code> just after the message <code>previousMessage</code>. used in common.odesign, oa.odesign, sequences.odesign
   * @param toMove the message to move.
   * @param previousMessage the previous message.
   * @return the moved message.
   */
  public EObject moveMessage(final SequenceMessage toMove, final SequenceMessage previousMessage) {
    final Scenario scenario = (Scenario) toMove.eContainer();
    /*
     * First of all: move the message to the end of the list
     */
    scenario.getOwnedMessages().move(scenario.getOwnedMessages().size() - 1, toMove);

    /*
     * Compute the new index and move !
     */
    int newIndex = scenario.getOwnedMessages().indexOf(previousMessage) + 1;
    if (newIndex >= scenario.getOwnedMessages().size()) {
      newIndex = scenario.getOwnedMessages().size() - 1;
    }
    scenario.getOwnedMessages().move(newIndex, toMove);
    return toMove;
  }

  /**
   * Moves the message <code>toMove</code> on the beginning of the scenario. used in common.odesign, oa.odesign, sequences.odesign
   * @param toMove the message to move.
   * @return the moved message.
   */
  public EObject moveMessageOnBegining(final SequenceMessage toMove) {
    final Scenario scenario = (Scenario) toMove.eContainer();
    scenario.getOwnedMessages().move(0, toMove);
    return toMove;
  }

  private SequenceMessage getMessageOpposite(EObject context) {
    return SequenceMessageExt.getOppositeSequenceMessage((SequenceMessage) context);
  }

  public String getInstanceRoleLabel(InstanceRole ir_p) {
    AbstractInstance part = ir_p.getRepresentedInstance();
    AbstractType type = part.getAbstractType();

    boolean multipart = TriStateBoolean.True.equals(CapellaProjectHelper.isReusableComponentsDriven(type));
    if (multipart && (type != null)) {
      return part.getName() + ICommonConstants.COLON_CHARACTER + type.getName();
    }
    return part.getName();

  }

  /**
   * returns display name of sequence message used in common.odesign
   * @param message the message
   * @return display name of the message
   */
  public String getMessageName(SequenceMessage message) {
    return getMessageName(message, false);
  }

  /**
   * returns display name of sequence message
   * @param message the message
   * @return display name of the message
   */
  public String getDFMessageName(SequenceMessage message, DDiagram diagram_p) {
    if (message == null) {
      return EMPTY_STRING;
    }

    boolean showExchangeItems = false;
    boolean showExchangeItemsParameters = false;
    boolean showFunctionalExchanges = false;

    boolean showFEEI = false;
    boolean showCEEI = false;
    boolean showFEParams = false;
    boolean showFEEIParams = false;


    boolean showCEParams = false;
    boolean showCEEIParams = false;

    List<? extends AbstractExchangeItem> eiOnMessage = message.getExchangedItems();

    for (FilterDescription filter : diagram_p.getActivatedFilters()) {
      if (filter.getName().equals(IMappingNameConstants.SHOW_EXCHANGE_ITEMS)) {
        showExchangeItems = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_EXCHANGE_ITEMS_PARAMETERS)) {
        showExchangeItemsParameters = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_FUNCTIONAL_EXCHANGES)) {
        showFunctionalExchanges = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_FUNCTIONAL_EXCHANGES_ECHANGE_ITEMS)) {
        showFEEI = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_FUNCTIONAL_EXCHANGES_PARAMS)) {
        showFEParams = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_COMPONENT_EXCHANGES_ECHANGE_ITEMS)) {
        showCEEI = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_FUNCTIONAL_EXCHANGES_ECHANGE_ITEMS_PARAMS)) {
        showFEEIParams = true;
      }

      if (filter.getName().equals(IMappingNameConstants.SHOW_COMPONENT_EXCHANGES_PARAMS)) {
        showCEParams = true;
      }
      if (filter.getName().equals(IMappingNameConstants.SHOW_COMPONENT_EXCHANGES_EXCHANGE_ITEMS_PARAMS)) {
        showCEEIParams = true;
      }

    }
    StringBuilder result = new StringBuilder();

    MessageEnd end = message.getSendingEnd() == null ? message.getReceivingEnd() : message.getSendingEnd();
    Event event = end.getEvent();
    AbstractEventOperation op = null;
    if (event != null) {
      if (event instanceof EventSentOperation) {
        EventSentOperation eso = (EventSentOperation) event;
        op = eso.getOperation();
      } else if (event instanceof EventReceiptOperation) {
        EventReceiptOperation ero = (EventReceiptOperation) event;
        op = ero.getOperation();
      }
    }

    if ((op != null) && showCEParams) {
      return getShowCEParams(op, eiOnMessage);
    }
    if ((op != null) && showCEEIParams) {
      return getShowCEEIParams(op, eiOnMessage);
    }

    if ((op != null) && showCEEI) {
      return getCEEIMessageName(op, eiOnMessage);
    }
    if ((op != null) && showFEEI) {
      return getFEEIMessageName(op, eiOnMessage);
    }
    if ((op != null) && (showFEEIParams || showFEParams)) {
      return showFeEiParams(op, showFEEIParams, eiOnMessage);
    }
    if (showExchangeItems || showFunctionalExchanges || showExchangeItemsParameters) {
      if (op != null) {
        if ((op instanceof FunctionalExchange) && (((FunctionalExchange) (op)).getExchangedItems().size() != 0)) {
          FunctionalExchange fe = (FunctionalExchange) op;
          int indice = 0;
          if (showFunctionalExchanges) {
            result.append(getSafeName(fe));
          } else {
            List<? extends AbstractExchangeItem> selectEIList = selectEIList(eiOnMessage, fe.getExchangedItems());
            if (selectEIList.size() != 0) {
              result.append("["); //$NON-NLS-1$
            }
            for (AbstractExchangeItem ei : selectEIList) {
              result.append(InformationServices.getEILabel(ei, showExchangeItemsParameters));
              indice++;
              if (indice < selectEIList.size()) {
                result.append(", "); //$NON-NLS-1$
              }
            }
            if (selectEIList.size() != 0) {
              result.append("]"); //$NON-NLS-1$
            }
          }
        } else if (op instanceof ComponentExchange) {
          ComponentExchange ce = (ComponentExchange) op;
          int indice = 0;
          if (showFunctionalExchanges) {
            for (ComponentExchangeFunctionalExchangeAllocation fea : ce.getOwnedComponentExchangeFunctionalExchangeAllocations()) {
              result.append(getSafeName(fea.getAllocatedFunctionalExchange()));
              indice++;
              if (indice < ce.getOwnedComponentExchangeFunctionalExchangeAllocations().size()) {
                result.append(", "); //$NON-NLS-1$
              }
            }
          } else {
            List<? extends AbstractExchangeItem> selectEIList = selectEIList(eiOnMessage, ce.getConvoyedInformations());
            if (selectEIList.size() != 0) {
              result.append("["); //$NON-NLS-1$
            }
            for (AbstractExchangeItem ei : selectEIList) {
              result.append(InformationServices.getEILabel(ei, showExchangeItemsParameters));
              indice++;
              if (indice < selectEIList.size()) {
                result.append(", "); //$NON-NLS-1$
              }
            }
            if (selectEIList.size() != 0) {
              result.append("]"); //$NON-NLS-1$
            }
          }

        } else {
          result.append(getSafeName(message));
          result.append(" "); //$NON-NLS-1$
        }
      }
    } else {
      result.append(getSafeName(message));
      result.append(" "); //$NON-NLS-1$
    }
    return result.toString();
  }

  private static Object getSafeName(AbstractNamedElement fe) {
    if ("".equals(fe.getName()) || (null == fe.getName())) { //$NON-NLS-1$
      return "<undefined>"; //$NON-NLS-1$
    }
    return fe.getName();
  }

  public static String getShowCEEIParams(AbstractEventOperation op, List<? extends AbstractExchangeItem> eiOnMessage) {
    if (!(op instanceof ComponentExchange)) {
      return getFEEIMessageName(op, eiOnMessage);
    }
    ComponentExchange ce = (ComponentExchange) op;
    StringBuilder result = new StringBuilder();
    result.append(getSafeName(op));
    result.append(" "); //$NON-NLS-1$
    result.append("["); //$NON-NLS-1$
    int indice = 0;
    List<? extends AbstractExchangeItem> selectEIList = selectEIList(eiOnMessage, ce.getConvoyedInformations());
    for (AbstractExchangeItem ei : selectEIList) {
      result.append(InformationServices.getEILabel(ei, true));
      indice++;
      if (indice < selectEIList.size()) {
        result.append(", "); //$NON-NLS-1$
      }
    }

    result.append("]"); //$NON-NLS-1$
    return result.toString();
  }

  public static String getShowCEParams(AbstractEventOperation op, List<? extends AbstractExchangeItem> eiOnMessage) {
    if (op instanceof FunctionalExchange) {
      return ""; //$NON-NLS-1$
    }
    ComponentExchange ce = (ComponentExchange) op;
    StringBuilder result = new StringBuilder();
    List<? extends AbstractExchangeItem> selectEIList;
    selectEIList = selectEIList(eiOnMessage, ce.getConvoyedInformations());

    result.append(getSafeName(ce));
    result.append("("); //$NON-NLS-1$
    int indice = 0;

    List<ExchangeItemElement> eies = new ArrayList<ExchangeItemElement>();
    for (AbstractExchangeItem aei : selectEIList) {
      if (aei instanceof ExchangeItem) {
        ExchangeItem ei = (ExchangeItem) aei;
        eies.addAll(ei.getOwnedElements());
      }
    }

    for (ExchangeItemElement eie : eies) {
      AbstractType type = eie.getAbstractType();
      if (type != null) {
        result.append(type.getName());
      } else {
        result.append("<undefined>"); //$NON-NLS-1$
      }
      indice++;
      if (indice < eies.size()) {
        result.append(", "); //$NON-NLS-1$
      }

    }

    result.append(")"); //$NON-NLS-1$

    return result.toString();

  }

  public static String showFeEiParams(AbstractEventOperation op, boolean showEIName, List<? extends AbstractExchangeItem> eiOnMessage) {
    FunctionalExchange fe = null;
    if (op instanceof ComponentExchange) {
      for (ComponentExchangeFunctionalExchangeAllocation fea : ((ComponentExchange) op).getOwnedComponentExchangeFunctionalExchangeAllocations()) {
        fe = fea.getAllocatedFunctionalExchange();
      }
    } else {
      fe = (FunctionalExchange) op;
    }
    StringBuilder result = new StringBuilder();
    List<? extends AbstractExchangeItem> selectEIList;
    selectEIList = selectEIList(eiOnMessage, fe.getExchangedItems());

    result.append(getSafeName(fe));

    int indice = 0;
    if (showEIName) {
      result.append(" "); //$NON-NLS-1$
      result.append("["); //$NON-NLS-1$
      for (AbstractExchangeItem ei : selectEIList) {
        result.append(InformationServices.getEILabel(ei, true));
        indice++;
        if (indice < selectEIList.size()) {
          result.append(", "); //$NON-NLS-1$
        }
      }
      result.append("]"); //$NON-NLS-1$

    } else {
      result.append("("); //$NON-NLS-1$
      // looking for information
      List<ExchangeItemElement> eies = new ArrayList<ExchangeItemElement>();
      for (AbstractExchangeItem aei : selectEIList) {
        if (aei instanceof ExchangeItem) {
          ExchangeItem ei = (ExchangeItem) aei;
          eies.addAll(ei.getOwnedElements());
        }
      }
      // using gathered information
      for (ExchangeItemElement eie : eies) {
        AbstractType type = eie.getAbstractType();
        if (type != null) {
          result.append(type.getName());
        } else {
          result.append("<undefined>"); //$NON-NLS-1$
        }
        indice++;
        if (indice < eies.size()) {
          result.append(", "); //$NON-NLS-1$
        }

      }
      result.append(")"); //$NON-NLS-1$
    }

    return result.toString();
  }

  public static String getFEEIMessageName(AbstractEventOperation op, List<? extends AbstractExchangeItem> eiOnMessage) {
    StringBuilder result = new StringBuilder();
    List<? extends AbstractExchangeItem> selectEIList;
    if (op instanceof FunctionalExchange) {
      result.append(getSafeName(op));
      selectEIList = selectEIList(eiOnMessage, ((FunctionalExchange) op).getExchangedItems());
    } else {
      ComponentExchange ce = (ComponentExchange) op;
      List<ExchangeItem> itemsOfFe = new ArrayList<ExchangeItem>();
      int indice = 0;
      for (ComponentExchangeFunctionalExchangeAllocation fea : ce.getOwnedComponentExchangeFunctionalExchangeAllocations()) {
        FunctionalExchange fe = fea.getAllocatedFunctionalExchange();
        result.append(getSafeName(fe));
        itemsOfFe.addAll(fe.getExchangedItems());
        indice++;
        if (indice < ce.getOwnedComponentExchangeFunctionalExchangeAllocations().size()) {
          result.append(", "); //$NON-NLS-1$
        }
      }
      selectEIList = selectEIList(eiOnMessage, itemsOfFe);
    }

    result.append(" "); //$NON-NLS-1$
    result.append("["); //$NON-NLS-1$
    int indice = 0;

    for (AbstractExchangeItem ei : selectEIList) {
      result.append(InformationServices.getEILabel(ei, false));
      indice++;
      if (indice < selectEIList.size()) {
        result.append(", "); //$NON-NLS-1$
      }
    }

    result.append("]"); //$NON-NLS-1$

    return result.toString();
  }

  public static String getCEEIMessageName(AbstractEventOperation op, List<? extends AbstractExchangeItem> eiOnMessage) {
    if (!(op instanceof ComponentExchange)) {
      return getFEEIMessageName(op, eiOnMessage);
    }
    ComponentExchange ce = (ComponentExchange) op;
    StringBuilder result = new StringBuilder();
    result.append(getSafeName(op));
    result.append(" "); //$NON-NLS-1$
    result.append("["); //$NON-NLS-1$
    int indice = 0;
    List<? extends AbstractExchangeItem> selectEIList = selectEIList(eiOnMessage, ce.getConvoyedInformations());
    for (AbstractExchangeItem ei : selectEIList) {
      result.append(InformationServices.getEILabel(ei, false));
      indice++;
      if (indice < selectEIList.size()) {
        result.append(", "); //$NON-NLS-1$
      }
    }

    result.append("]"); //$NON-NLS-1$
    return result.toString();
  }

  private static List<? extends AbstractExchangeItem> selectEIList(List<? extends AbstractExchangeItem> onMessage, List<? extends AbstractExchangeItem> byModel) {
    if (onMessage.size() > 0) {
      return onMessage;
    }
    return byModel;
  }

  /**
   * returns display name of sequence message
   * @param message the message
   * @return display name of the message
   */
  public String getMessageName(SequenceMessage message, boolean hideParameters) {
    NamedElement messageOperation = null;
    if (InterfaceHelper.isSharedDataAccess(message)) {
      return SequenceMessageExt.getMessageNameForSharedDataAccess(message);
    }

    if (message == null) {
      return ""; //$NON-NLS-1$
    }

    if ((message.getKind() == MessageKind.REPLY) && hideParameters) {
      return ""; //$NON-NLS-1$
    }

    Event associatedEvent;

    // because of lost/found message, we must select one or the other side
    // of the message
    if (message.getReceivingEnd() != null) {
      associatedEvent = message.getReceivingEnd().getEvent();
    } else {
      associatedEvent = message.getSendingEnd().getEvent();
      // end of lost/found message difference
    }

    if (associatedEvent instanceof EventReceiptOperation) {
      EventReceiptOperation event = (EventReceiptOperation) associatedEvent;
      if (event.getOperation() != null) {
        messageOperation = event.getOperation();
      }
    }
    if (associatedEvent instanceof EventSentOperation) {
      EventSentOperation event = (EventSentOperation) associatedEvent;
      if (event.getOperation() != null) {
        messageOperation = event.getOperation();
      }
    }
    if (messageOperation == null) {
      return message.getName();
    }
    StringBuilder sb = new StringBuilder();

    if (messageOperation instanceof ExchangeItemAllocation) {
      messageOperation = ((ExchangeItemAllocation) messageOperation).getAllocatedItem();
    }

    int nbParameters = 0;
    int nbOutParameters = 0;

    if (messageOperation instanceof ExchangeItem) {
      ExchangeItem item = (ExchangeItem) messageOperation;
      for (ExchangeItemElement element : item.getOwnedElements()) {
        if (element.getKind() == ElementKind.PARAMETER) {
          nbParameters++;
          if ((element.getDirection() == ParameterDirection.OUT) || (element.getDirection() == ParameterDirection.INOUT)
              || (element.getDirection() == ParameterDirection.RETURN)) {
            nbOutParameters++;
          }
        }
      }
    } else if (messageOperation instanceof Operation) {
      for (Parameter param : ((Operation) messageOperation).getOwnedParameters()) {
        nbParameters++;
        if ((param.getDirection() == ParameterDirection.OUT) || (param.getDirection() == ParameterDirection.INOUT)
            || (param.getDirection() == ParameterDirection.RETURN)) {
          nbOutParameters++;
        }
      }
    }

    // don't display the name if you display parameters of a return message
    if ((!hideParameters && (message.getKind() == MessageKind.REPLY) && (nbOutParameters == 0))) {
      sb.append(messageOperation.getName());
    }
    if (message.getKind() != MessageKind.REPLY) {
      sb.append(messageOperation.getName());
    }

    if (!hideParameters) {
      sb.append("("); //$NON-NLS-1$

      List<String> paramNames = new ArrayList<String>(nbParameters);

      if (messageOperation instanceof ExchangeItem) {
        ExchangeItem item = (ExchangeItem) messageOperation;
        for (ExchangeItemElement element : item.getOwnedElements()) {
          if (element.getKind() == ElementKind.PARAMETER) {
            String name = getNameParameter(element, element.getType(), element.getDirection(), message);
            if (name != null) {
              paramNames.add(name);
            }
          }
        }
      } else if (messageOperation instanceof Operation) {

        for (Parameter param : ((Operation) messageOperation).getOwnedParameters()) {
          String name = getNameParameter(param, param.getAbstractType(), param.getDirection(), message);
          if (name != null) {
            paramNames.add(name);
          }
        }
      }

      int numParam = 0;
      for (String name : paramNames) {
        sb.append(name);
        if (numParam < (paramNames.size() - 1)) {
          sb.append(", "); //$NON-NLS-1$          
        }
        numParam++;
      }

      sb.append(")"); //$NON-NLS-1$
    }
    return sb.toString();
  }

  private String getNameParameter(NamedElement parameter_p, AbstractType type, ParameterDirection direction, SequenceMessage message) {
    String name = null;
    if ((message.getKind() == MessageKind.REPLY) && ((direction == ParameterDirection.OUT) || (direction == ParameterDirection.INOUT))) {
      name = parameter_p.getName();
    } else if ((message.getKind() == MessageKind.REPLY) && (direction == ParameterDirection.RETURN)) {
      if (type == null) {
        name = "";//$NON-NLS-1$
      } else {
        name = type.getName();
      }
    } else if (((message.getKind() == MessageKind.SYNCHRONOUS_CALL) || (message.getKind() == MessageKind.ASYNCHRONOUS_CALL))
               && ((direction == ParameterDirection.IN) || (direction == ParameterDirection.INOUT))) {
      name = parameter_p.getName();
    }

    return name;
  }

  /**
   * not used, but can be reused
   */
  public String getMessagePosition(final SequenceMessage message) {
    // the message position is the number of order in the case of a sent
    // message, and is coordinated
    // to the sending message in the context of a return message.
    if (message == null) {
      return "?"; //$NON-NLS-1$
    }
    if (message.getKind() == MessageKind.REPLY) {
      return getMessagePosition(getMessageOpposite(message));
    }

    final Scenario scenario = (Scenario) message.eContainer();
    final int basicPos = scenario.getOwnedMessages().indexOf(message);
    int pos = basicPos + 1;
    for (final SequenceMessage ownedMessage : scenario.getOwnedMessages()) {
      if (ownedMessage.getKind() == MessageKind.REPLY) {
        pos--;
      }
      if (ownedMessage == message) {
        break;
      }
    }
    return Integer.toString(pos);
  }

  /**
   * used in common.odesign, oa.odesign, sequences.odesign
   * @param end
   * @return
   */
  public MessageEnd getPreviousMessageEnd(InteractionFragment end) {
    if (end instanceof MessageEnd) {
      return (MessageEnd) end;
    }
    final int index = ((Scenario) end.eContainer()).getOwnedInteractionFragments().indexOf(end);
    if (index == 0) {
      return null;
    }
    return getPreviousMessageEnd(((Scenario) end.eContainer()).getOwnedInteractionFragments().get(index - 1));
  }

  /**
   * used in common.odesign, oa.odesign, sequences.odesign
   * @param message_p
   * @return
   */
  public String newCallName(final SequenceMessage message_p) {
    return "Message Call"; //$NON-NLS-1$
  }

  public List<StateFragment> getInteractionStatesOnExecution(InstanceRole ir_p) {
    List<StateFragment> result = new ArrayList<StateFragment>(1);
    List<InteractionFragment> fragments = SequenceDiagramServices.getOrderedInteractionFragments((Scenario) ir_p.eContainer());
    Stack<TimeLapse> execStack = new Stack<TimeLapse>();

    for (InteractionFragment ifg : fragments) {
      if (ifg.getCoveredInstanceRoles().contains(ir_p)) {
        if ((ifg instanceof InteractionState) && execStack.isEmpty()) {
          result.add((StateFragment) getStartingExecution(ifg));
        }

        TimeLapse startingExec = getStartingExecution(ifg);
        if ((startingExec != null) && (startingExec instanceof Execution)) {
          execStack.push(startingExec);
        }

        TimeLapse endingExec = getEndingExecution(ifg);
        if ((endingExec != null) && (endingExec instanceof Execution)) {
          execStack.pop();
        }
      }
    }
    return result;
  }

  public List<StateFragment> getInteractionStatesOnExecution(Execution exec) {
    List<StateFragment> result = new ArrayList<StateFragment>(1);
    InstanceRole ir = exec.getCovered();

    List<InteractionFragment> fragments = SequenceDiagramServices.getOrderedInteractionFragments((Scenario) exec.eContainer());
    Stack<TimeLapse> execStack = new Stack<TimeLapse>();
    boolean inCurrentExec = false;
    for (InteractionFragment ifg : fragments) {
      if (ifg.getCoveredInstanceRoles().contains(ir)) {
        if (exec.getStart() == ifg) {
          inCurrentExec = true;
        }

        if (inCurrentExec && (ifg instanceof InteractionState) && (execStack.peek() == exec)) {
          result.add((StateFragment) getStartingExecution(ifg));
        }

        TimeLapse startingExec = getStartingExecution(ifg);
        if ((startingExec != null) && !(startingExec instanceof CombinedFragment)) {
          execStack.push(startingExec);
        }

        TimeLapse endingExec = getEndingExecution(ifg);
        if ((endingExec != null) && !(endingExec instanceof CombinedFragment)) {
          execStack.pop();
        }

        if (exec.getFinish() == ifg) {
          inCurrentExec = false;
        }
      }
    }
    return result;
  }

  /**
   * @param ifg_p
   * @return
   */
  private TimeLapse getEndingExecution(InteractionFragment ifg_p) {
    Scenario s = SequenceDiagramServices.getScenario(ifg_p);
    for (TimeLapse lap : s.getOwnedTimeLapses()) {
      if (lap.getFinish() == ifg_p) {
        return lap;
      }
    }
    return null;
  }

  /**
   * @param ifg_p
   * @return
   */
  private TimeLapse getStartingExecution(InteractionFragment ifg_p) {
    Scenario s = SequenceDiagramServices.getScenario(ifg_p);
    for (TimeLapse lap : s.getOwnedTimeLapses()) {
      if (lap.getStart() == ifg_p) {
        return lap;
      }
    }
    return null;
  }

  public List<InstanceRole> getCoveredFromAbstractFragment(AbstractFragment af_p) {
    return af_p.getStart().getCoveredInstanceRoles();
  }

  public InstanceRole getCoveredFromExecOrIR(EObject context_p, EObject container_p) {
    if (container_p instanceof Execution) {
      Execution exec = (Execution) container_p;
      return exec.getCovered();
    }
    return (InstanceRole) container_p;
  }

  public InteractionFragment getOperandBegin(InteractionOperand operand_p) {
    return operand_p;
  }

  public InteractionFragment getOperandEnd(InteractionOperand operand_p) {
    CombinedFragment cf = null;
    Scenario s = (Scenario) operand_p.eContainer();
    for (TimeLapse tl : s.getOwnedTimeLapses()) {
      if (tl instanceof CombinedFragment) {
        CombinedFragment cftmp = (CombinedFragment) tl;
        if (cftmp.getReferencedOperands().contains(operand_p)) {
          cf = cftmp;
          break;
        }
      }
    }

    // we can't use referencedOperand to check order, we must look
    // in ownedInteractionFragment
    boolean nextWillBeGood = false;
    for (InteractionFragment fragment : s.getOwnedInteractionFragments()) {
      if (fragment instanceof InteractionOperand) {
        if (cf.getReferencedOperands().contains(fragment) && nextWillBeGood) {
          return fragment;
        }
      }
      if (fragment == operand_p) {
        nextWillBeGood = true;
      }
    }
    // if we are here, we cannot found a next, so next will be the end of
    // the CF.
    return cf.getFinish();
  }

  public boolean isFunctionalExecution(Execution execution_p) {
    InteractionFragment end = execution_p.getStart();
    if (end instanceof MessageEnd) {
      MessageEnd me = (MessageEnd) end;
      EventReceiptOperation ero = (EventReceiptOperation) me.getEvent();
      return ero.getOperation() instanceof FunctionalExchange;
    }
    return false;
  }

  /**
   * Returns the scope of available scenario which can be use in an InteractionUse element
   */
  public List<CapellaElement> getReferenceScope(Scenario scenario_p) {
    List<CapellaElement> availableElements = new ArrayList<CapellaElement>();

    IBusinessQuery query =
        BusinessQueriesProvider.getInstance().getContribution(InteractionPackage.Literals.INTERACTION_USE,
            InteractionPackage.Literals.INTERACTION_USE__REFERENCED_SCENARIO);

    if (query != null) {
      availableElements.addAll(query.getAvailableElements(scenario_p));
    }

    return availableElements;
  }

  /**
   * Returns available state modes which can be added into an IS IS-Insert-StateMode
   */
  public Collection<AbstractState> getISStateModes(AbstractInstance instance_p) {
    Collection<AbstractState> result = new java.util.HashSet<AbstractState>();
    Collection<StateMachine> stateMachinas = new java.util.HashSet<StateMachine>();

    if (instance_p instanceof Part) {
      Collection<Part> parts = ComponentExt.getPartAncestors((Part) instance_p, true);
      parts.add((Part) instance_p);

      for (Part part : parts) {
        if (part.getAbstractType() != null) {
          Component component_p = (Component) part.getAbstractType();
          List<GeneralizableElement> elements = GeneralizableElementExt.getAllSuperGeneralizableElements(component_p);
          elements.add(component_p);
          for (GeneralizableElement element : elements) {
            if (element instanceof Block) {
              stateMachinas.addAll(((Block) element).getOwnedStateMachines());
            }
          }
        }
      }

      for (StateMachine machina : stateMachinas) {
        // Retrieve all AbstractState from the StateMachine
        TreeIterator<EObject> childs = machina.eAllContents();
        while (childs.hasNext()) {
          EObject child = childs.next();
          if (child instanceof AbstractState) {
            result.add((AbstractState) child);
          }
        }
      }

    } else if (instance_p instanceof AbstractFunction) {
      // functional scenario can use states by relationship
      // function:availableInStateModes
      AbstractFunction af = (AbstractFunction) instance_p;
      for (State asm : af.getAvailableInStates()) {
        result.add(asm);
      }
    }

    return result;
  }

  public Collection<AbstractFunction> getFunctionsForStateAtOA(EObject context_p, AbstractInstance instance_p) {
    if (instance_p instanceof Role) {
      Role role = (Role) instance_p;
      List<AbstractFunction> result = new ArrayList<AbstractFunction>();
      for (ActivityAllocation alloc : role.getActivityAllocations()) {
        result.add(alloc.getActivity());
      }
      return result;
    }
    return getFunctionsForState(context_p, (Component) instance_p.getAbstractType());
  }

  public Collection<AbstractFunction> getFunctionsForState(EObject context_p, Component component_p) {
    List<AbstractFunction> result = new ArrayList<AbstractFunction>();
    Collection<AbstractFunction> functions = new java.util.HashSet<AbstractFunction>();

    List<Component> baseComponents = new ArrayList<Component>();
    baseComponents.add(component_p);
    // adding all sub Components
    baseComponents.addAll(ComponentExt.getAllSubUsedAndDeployedComponents(component_p));

    for (Component component : baseComponents) {
      List<GeneralizableElement> elements = GeneralizableElementExt.getAllSuperGeneralizableElements(component);
      elements.add(component);
      for (GeneralizableElement element : elements) {
        if (element instanceof AbstractFunctionalBlock) {
          functions.addAll(((AbstractFunctionalBlock) element).getAllocatedFunctions());
        }
      }
    }
    //

    for (AbstractFunction function : functions) {
      result.add(function);
    }

    ArrayList<AbstractFunction> newElements = new ArrayList<AbstractFunction>();

    // Allow whole function for which all sub decompositions are already in result, recursively.
    do {
      newElements.clear();

      for (AbstractFunction abstractFunction : result) {
        EObject container = abstractFunction.eContainer();
        if (container instanceof AbstractFunction) {
          AbstractFunction af = (AbstractFunction) container;
          if (!result.contains(af)) {
            boolean allInnerAreInResult = true;
            for (EObject inner : af.eContents()) {
              if (inner instanceof AbstractFunction) {
                AbstractFunction innerAf = (AbstractFunction) inner;
                if (!result.contains(innerAf)) {
                  allInnerAreInResult = false;
                }
              }
            }
            if (allInnerAreInResult) {
              newElements.add(af);
            }
          }
        }
      }

      result.addAll(newElements);
    } while (newElements.size() > 0);

    return result;
  }

  public String getOperandLabel(InteractionOperand operand_p) {
    String guard = operand_p.getGuard();
    if ((guard == null) || guard.equals("")) { //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
    return "[" + guard + "]"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  public boolean isValidScenarioDrop(EObject context_p, Scenario scenario_p, EObject element_p) {
    if (element_p instanceof Component) {
      for (InstanceRole ir : scenario_p.getOwnedInstanceRoles()) {
        if ((ir.getRepresentedInstance() != null) && (ir.getRepresentedInstance().getAbstractType() != null)) {
          if (ir.getRepresentedInstance().getAbstractType().equals(element_p)) {
            return false; // already unmasked
          }
        }
      }
    } else if (element_p instanceof AbstractFunction) {
      for (InstanceRole ir : scenario_p.getOwnedInstanceRoles()) {
        if (ir.getRepresentedInstance().equals(element_p)) {
          return false; // already unmasked
        }
      }
    } else if (element_p instanceof Role) {
      for (InstanceRole ir : scenario_p.getOwnedInstanceRoles()) {
        if (ir.getRepresentedInstance().equals(element_p)) {
          return false; // already unmasked
        }
      }
    }
    // compatibility :
    if ((scenario_p.getKind() == ScenarioKind.DATA_FLOW) || (scenario_p.getKind() == ScenarioKind.INTERFACE)) {
      return ((element_p instanceof SystemComponent) || (element_p instanceof AbstractActor)) && isCorrectComponentLevel(context_p, element_p);
    } else if (scenario_p.getKind() == ScenarioKind.FUNCTIONAL) {
      return (element_p instanceof AbstractFunction);
    } else if (scenario_p.getKind() == ScenarioKind.INTERACTION) {
      if (scenario_p.getOwnedInstanceRoles().size() != 0) {
        InstanceRole firstIr = scenario_p.getOwnedInstanceRoles().get(0);
        if (firstIr.getRepresentedInstance() instanceof OperationalActivity) {
          return (element_p instanceof OperationalActivity);
        }
        return (element_p instanceof Entity) || (element_p instanceof Role);
      }
      // empty diagram
      return (element_p instanceof Entity) || (element_p instanceof Role) || (element_p instanceof OperationalActivity);
    }
    return true;
  }

  /**
   * in a DnD of SystemComponent, check that this drop is legal : the dropped Element must be inside the context if it is a component. If it's an actor, all
   * cases are legal.
   * @param context_p contextual component
   * @param element_p dropped component
   * @return
   */
  private boolean isCorrectComponentLevel(EObject context_p, EObject element_p) {
    if (element_p instanceof AbstractActor) {
      return true;
    }
    // find the carrier component of the scenario
    SystemComponent referenceComponent = null;
    EObject container = context_p;
    while (referenceComponent == null) {
      container = container.eContainer();
      if (container instanceof SystemComponent) {
        referenceComponent = (SystemComponent) container; // found
      } else if (container instanceof LogicalArchitecture) {
        LogicalArchitecture la = (LogicalArchitecture) container;
        referenceComponent = la.getOwnedLogicalComponent();
      } else if (container instanceof PhysicalArchitecture) {
        PhysicalArchitecture pa = (PhysicalArchitecture) container;
        referenceComponent = pa.getOwnedPhysicalComponent();
      } else if (container instanceof EPBSArchitecture) {
        EPBSArchitecture ea = (EPBSArchitecture) container;
        referenceComponent = ea.getOwnedConfigurationItem();
      }
    }

    // element_p is in the parts of this component or below

    for (Component tested : ComponentExt.getAllSubUsedComponents(referenceComponent)) {
      if (tested.equals(element_p)) {
        return true;
      }
    }
    return false;
  }

}
