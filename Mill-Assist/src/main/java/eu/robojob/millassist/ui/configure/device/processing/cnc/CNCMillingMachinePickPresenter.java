package eu.robojob.millassist.ui.configure.device.processing.cnc;

import java.util.HashSet;
import java.util.Set;

import eu.robojob.millassist.external.device.Clamping;
import eu.robojob.millassist.external.device.DeviceSettings;
import eu.robojob.millassist.external.robot.RobotAirblowSettings;
import eu.robojob.millassist.positioning.Coordinates;
import eu.robojob.millassist.process.PickStep;
import eu.robojob.millassist.ui.general.AbstractFormPresenter;

public class CNCMillingMachinePickPresenter extends AbstractFormPresenter<CNCMillingMachinePickView, CNCMillingMachineMenuPresenter> {

	private PickStep pickStep;
	private DeviceSettings deviceSettings;
	
	public CNCMillingMachinePickPresenter(final CNCMillingMachinePickView view, final PickStep pickStep, final DeviceSettings deviceSettings) {
		super(view);
		this.pickStep = pickStep;
		this.deviceSettings = deviceSettings;
		view.setPickStep(pickStep);
		view.setDeviceSettings(deviceSettings);
		view.build();
	}

	@Override
	public void setPresenter() {
		getView().setPresenter(this);
	}

	public void changedSmoothX(final float smoothX) {
		if (pickStep.getRobotSettings().getSmoothPoint() != null) {
			pickStep.getRobotSettings().getSmoothPoint().setX(smoothX);
		}  else {
			pickStep.getRobotSettings().setSmoothPoint(new Coordinates(smoothX, 0, 0, 0, 0, 0));
		}
		getView().refresh();
	}
	
	public void changedSmoothY(final float smoothY) {
		if (pickStep.getRobotSettings().getSmoothPoint() != null) {
			pickStep.getRobotSettings().getSmoothPoint().setY(smoothY);
		} else {
			pickStep.getRobotSettings().setSmoothPoint(new Coordinates(0, smoothY, 0, 0, 0, 0));
		}
		getView().refresh();
	}
	
	public void changedSmoothZ(final float smoothZ) {
		if (pickStep.getRobotSettings().getSmoothPoint() != null) {
			pickStep.getRobotSettings().getSmoothPoint().setZ(smoothZ);
		} else {
			pickStep.getRobotSettings().setSmoothPoint(new Coordinates(0, 0, smoothZ, 0, 0, 0));
		}
		getView().refresh();
	}
	
	public void resetSmooth() {
		if (deviceSettings.getClamping(pickStep.getDeviceSettings().getWorkArea()) != null) {
			pickStep.getRobotSettings().setSmoothPoint(deviceSettings.getClamping(pickStep.getDeviceSettings().getWorkArea()).getSmoothFromPoint());
			getView().refresh();
		}
	}
	
	public void changedAirblow(final boolean airblow) {
		pickStep.getRobotSettings().setDoMachineAirblow(airblow);
		if (!airblow) {
			pickStep.getRobotSettings().clearAirblowSettings();
		}
	}
	
	void changedClamping(final String clampingName) {
		if (clampingName != null) {
			int clampingId = pickStep.getRobotSettings().getWorkArea().getClampingByName(clampingName).getId();
			RobotAirblowSettings airblowSettings;
			if (pickStep.getRobotSettings().getRobotAirblowSettings(clampingId) == null) {
				airblowSettings = new RobotAirblowSettings();
				pickStep.getRobotSettings().addRobotAirblowSettings(clampingId, airblowSettings);
			} else {
				airblowSettings = pickStep.getRobotSettings().getRobotAirblowSettings(clampingId);
			}
			getView().setTopCoord(airblowSettings.getTopCoord());
			getView().setBottomCoord(airblowSettings.getBottomCoord());
			getView().refreshCoordboxes();
		}
	}

	@Override
	public boolean isConfigured() {
		if (!isAirblowConfigured() && pickStep.getRobotSettings().isDoMachineAirblow()) {
			return false;
		}
		if (pickStep.getRobotSettings().getSmoothPoint() != null) {
			return true;
		}
		return false;
	}
	
	private boolean isAirblowConfigured() {
		for (RobotAirblowSettings airblowSettings: pickStep.getRobotSettings().getRobotAirblowSettings().values()) {
			if (!(airblowSettings.getBottomCoord().getX() < airblowSettings.getTopCoord().getX() &&
				airblowSettings.getBottomCoord().getY() < airblowSettings.getTopCoord().getY()))
				return false;
		}
		return true;
	}

	public void changedTIM(final boolean newValue) {
		pickStep.getRobotSettings().setTurnInMachine(newValue);
	}

	Set<String> getSelectedClampings() {
		Set<String> clNames = new HashSet<String>();
		for (Clamping clamping: pickStep.getRobotSettings().getWorkArea().getAllActiveClampings()) {
			clNames.add(clamping.getName());
		}
		return clNames;
	}
}
