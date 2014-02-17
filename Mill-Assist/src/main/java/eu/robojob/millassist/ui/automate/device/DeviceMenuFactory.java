package eu.robojob.millassist.ui.automate.device;

import java.util.HashMap;
import java.util.Map;

import eu.robojob.millassist.external.device.stacking.conveyor.normal.Conveyor;
import eu.robojob.millassist.external.device.stacking.stackplate.BasicStackPlate;
import eu.robojob.millassist.process.ProcessFlow;
import eu.robojob.millassist.ui.automate.AbstractMenuPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.conveyor.normal.ConveyorAmountsPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.conveyor.normal.ConveyorAmountsView;
import eu.robojob.millassist.ui.automate.device.stacking.conveyor.normal.ConveyorMenuPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.conveyor.normal.ConveyorMenuView;
import eu.robojob.millassist.ui.automate.device.stacking.stackplate.BasicStackPlateLayoutPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.stackplate.BasicStackPlateMenuPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.stackplate.BasicStackPlateMenuView;
import eu.robojob.millassist.ui.automate.device.stacking.stackplate.BasicStackPlateRefillPresenter;
import eu.robojob.millassist.ui.automate.device.stacking.stackplate.BasicStackPlateRefillView;
import eu.robojob.millassist.ui.general.device.stacking.conveyor.normal.ConveyorFinishedWorkPieceLayoutPresenter;
import eu.robojob.millassist.ui.general.device.stacking.conveyor.normal.ConveyorFinishedWorkPieceLayoutView;
import eu.robojob.millassist.ui.general.device.stacking.conveyor.normal.ConveyorRawWorkPieceLayoutPresenter;
import eu.robojob.millassist.ui.general.device.stacking.conveyor.normal.ConveyorRawWorkPieceLayoutView;
import eu.robojob.millassist.ui.general.device.stacking.stackplate.BasicStackPlateLayoutView;
import eu.robojob.millassist.ui.general.model.DeviceInformation;

public class DeviceMenuFactory {
	
	private ProcessFlow processFlow;
	
	private Map<Integer, AbstractMenuPresenter<?>> presentersBuffer;
	
//	private static Logger logger = LogManager.getLogger(DeviceMenuFactory.class.getName());
	
	public DeviceMenuFactory(final ProcessFlow processFlow) {
		this.processFlow = processFlow;
		presentersBuffer = new HashMap<Integer, AbstractMenuPresenter<?>>();
	}
	
	public boolean hasDeviceMenu(final DeviceInformation deviceInfo) {
		switch(deviceInfo.getType()) {
			case BASIC_STACK_PLATE:
				return true;
			case CONVEYOR:
				return true;
			case CONVEYOR_EATON:
				return true;
			default:
				return false;
		}
	}
	
	public synchronized AbstractMenuPresenter<?> getDeviceMenu(final DeviceInformation deviceInfo) {
		AbstractMenuPresenter<?> menuPresenter = presentersBuffer.get(deviceInfo.getIndex());
		if (menuPresenter == null) {
			switch(deviceInfo.getType()) {
				case BASIC_STACK_PLATE:
					menuPresenter = getBasicStackPlateMenuPresenter(deviceInfo);
					break;
				case CONVEYOR:
					menuPresenter = getConveyorMenuPresenter(deviceInfo);
					break;
				case CONVEYOR_EATON:
					menuPresenter = getConveyorEatonMenuPresenter(deviceInfo);
					break;
				default:
					menuPresenter = null;
			}
			presentersBuffer.put(deviceInfo.getIndex(), menuPresenter);
		}
		return menuPresenter;
	}
	
	public eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter getConveyorEatonMenuPresenter(final DeviceInformation deviceInfo) {
		eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuView conveyorMenuView = new eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuView();
		eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter conveyorMenuPresenter;
		if (deviceInfo.getPickStep() != null) {
			// first device
			conveyorMenuPresenter = new eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter(conveyorMenuView, getEatonRawWorkPieceLayoutPresenter(deviceInfo), getEatonAmountsPresenter(deviceInfo.getPickStep().getProcessFlow(), (eu.robojob.millassist.external.device.stacking.conveyor.eaton.Conveyor) deviceInfo.getDevice()));
		} else {
			// last device
			conveyorMenuPresenter = new eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter(conveyorMenuView, getEatonFinishedWorkPieceLayoutPresenter(deviceInfo), getEatonAmountsPresenter(deviceInfo.getPutStep().getProcessFlow(), (eu.robojob.millassist.external.device.stacking.conveyor.eaton.Conveyor) deviceInfo.getDevice()));
		}
		return conveyorMenuPresenter;
	}
	
	public eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorAmountsPresenter getEatonAmountsPresenter(final ProcessFlow processFlow, final eu.robojob.millassist.external.device.stacking.conveyor.eaton.Conveyor conveyor) {
		eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorAmountsView amountsView = new eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorAmountsView();
		eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorAmountsPresenter amountsPresenter = new eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorAmountsPresenter(amountsView, processFlow, conveyor);
		return amountsPresenter;
	}
			
	public ConveyorMenuPresenter getConveyorMenuPresenter(final DeviceInformation deviceInfo) {
		ConveyorMenuView conveyorMenuView = new ConveyorMenuView();
		ConveyorMenuPresenter conveyorMenuPresenter;
		if (deviceInfo.getPickStep() != null) {
			// first device
			conveyorMenuPresenter = new ConveyorMenuPresenter(conveyorMenuView, getRawWorkPieceLayoutPresenter(deviceInfo), getAmountsPresenter(deviceInfo.getPickStep().getProcessFlow(), (Conveyor) deviceInfo.getDevice()));
		} else {
			// last device
			conveyorMenuPresenter = new ConveyorMenuPresenter(conveyorMenuView, getFinishedWorkPieceLayoutPresenter(deviceInfo), getAmountsPresenter(deviceInfo.getPutStep().getProcessFlow(), (Conveyor) deviceInfo.getDevice()));
		}
		return conveyorMenuPresenter;
	}
	
	public ConveyorAmountsPresenter getAmountsPresenter(final ProcessFlow processFlow, final Conveyor conveyor) {
		ConveyorAmountsView amountsView = new ConveyorAmountsView();
		ConveyorAmountsPresenter amountsPresenter = new ConveyorAmountsPresenter(amountsView, processFlow, conveyor);
		return amountsPresenter;
	}
	
	public eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorRawWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter> getEatonRawWorkPieceLayoutPresenter(final DeviceInformation deviceInfo) {
		eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorRawWorkPieceLayoutView view = new eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorRawWorkPieceLayoutView();
		eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorRawWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter> presenter = new eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorRawWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter>(view, (eu.robojob.millassist.external.device.stacking.conveyor.eaton.Conveyor) deviceInfo.getDevice());
		return presenter;
	}
	
	public eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorFinishedWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter> getEatonFinishedWorkPieceLayoutPresenter(final DeviceInformation deviceInfo) {
		eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorFinishedWorkPieceLayoutView view = new eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorFinishedWorkPieceLayoutView();
		eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorFinishedWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter> presenter = new eu.robojob.millassist.ui.general.device.stacking.conveyor.eaton.ConveyorFinishedWorkPieceLayoutPresenter<eu.robojob.millassist.ui.automate.device.stacking.conveyor.eaton.ConveyorMenuPresenter>(view, (eu.robojob.millassist.external.device.stacking.conveyor.eaton.Conveyor) deviceInfo.getDevice());
		return presenter;
	}
	
	public ConveyorRawWorkPieceLayoutPresenter<ConveyorMenuPresenter> getRawWorkPieceLayoutPresenter(final DeviceInformation deviceInfo) {
		ConveyorRawWorkPieceLayoutView view = new ConveyorRawWorkPieceLayoutView();
		ConveyorRawWorkPieceLayoutPresenter<ConveyorMenuPresenter> presenter = new ConveyorRawWorkPieceLayoutPresenter<ConveyorMenuPresenter>(view, (Conveyor) deviceInfo.getDevice());
		presenter.hideButtons();
		return presenter;
	}
	
	public ConveyorFinishedWorkPieceLayoutPresenter<ConveyorMenuPresenter> getFinishedWorkPieceLayoutPresenter(final DeviceInformation deviceInfo) {
		ConveyorFinishedWorkPieceLayoutView view = new ConveyorFinishedWorkPieceLayoutView();
		ConveyorFinishedWorkPieceLayoutPresenter<ConveyorMenuPresenter> presenter = new ConveyorFinishedWorkPieceLayoutPresenter<ConveyorMenuPresenter>(view, (Conveyor) deviceInfo.getDevice());
		return presenter;
	}
	
	public BasicStackPlateMenuPresenter getBasicStackPlateMenuPresenter(final DeviceInformation deviceInfo) {
		BasicStackPlateMenuView stackingDeviceMenuView = new BasicStackPlateMenuView();
		BasicStackPlateMenuPresenter basicStackPlateMenuPresenter = new BasicStackPlateMenuPresenter(stackingDeviceMenuView, getBasicStackPlateLayoutPresenter(deviceInfo), getBasicStackPlateRefillPresenter(deviceInfo));
		return basicStackPlateMenuPresenter;
	}
	
	public BasicStackPlateLayoutPresenter getBasicStackPlateLayoutPresenter(final DeviceInformation deviceInfo) {
		BasicStackPlateLayoutView<BasicStackPlateLayoutPresenter> view = new BasicStackPlateLayoutView<BasicStackPlateLayoutPresenter>();
		BasicStackPlateLayoutPresenter basicStackPlateLayoutPresenter = new BasicStackPlateLayoutPresenter(view, (BasicStackPlate) deviceInfo.getDevice());
		return basicStackPlateLayoutPresenter;
	}
	
	public BasicStackPlateRefillPresenter getBasicStackPlateRefillPresenter(final DeviceInformation deviceInfo) {
		BasicStackPlateRefillView view = new BasicStackPlateRefillView();
		BasicStackPlateRefillPresenter basicStackPlateRefillPresenter = new BasicStackPlateRefillPresenter(view, (BasicStackPlate) deviceInfo.getDevice(), processFlow);
		return basicStackPlateRefillPresenter;
	}
	
	public void clearBuffer() {
		for (AbstractMenuPresenter<?> presenter : presentersBuffer.values()) {
			presenter.unregisterListeners();
		}
		presentersBuffer.clear();
	}
}
