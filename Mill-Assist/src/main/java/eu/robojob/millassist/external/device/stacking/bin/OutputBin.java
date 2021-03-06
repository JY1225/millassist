package eu.robojob.millassist.external.device.stacking.bin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eu.robojob.millassist.external.communication.AbstractCommunicationException;
import eu.robojob.millassist.external.device.Clamping;
import eu.robojob.millassist.external.device.ClampingManner;
import eu.robojob.millassist.external.device.DeviceActionException;
import eu.robojob.millassist.external.device.DeviceInterventionSettings;
import eu.robojob.millassist.external.device.DevicePickSettings;
import eu.robojob.millassist.external.device.DevicePutSettings;
import eu.robojob.millassist.external.device.DeviceSettings;
import eu.robojob.millassist.external.device.EDeviceGroup;
import eu.robojob.millassist.external.device.SimpleWorkArea;
import eu.robojob.millassist.external.device.Zone;
import eu.robojob.millassist.external.device.stacking.AbstractStackingDevice;
import eu.robojob.millassist.external.device.visitor.AbstractPiecePlacementVisitor;
import eu.robojob.millassist.external.robot.AbstractRobotActionSettings.ApproachType;
import eu.robojob.millassist.positioning.Coordinates;
import eu.robojob.millassist.process.ProcessFlow;
import eu.robojob.millassist.workpiece.IWorkPieceDimensions;
import eu.robojob.millassist.workpiece.WorkPiece.Type;

public class OutputBin extends AbstractStackingDevice {
	
	public OutputBin(final String name) {
		super(name);
		// set first clamping as active one!
		for (SimpleWorkArea wa : getWorkAreas()) {
			wa.setDefaultClamping(wa.getWorkAreaManager().getClampings().iterator().next());
		}
	}

	public OutputBin(final String name, final Set<Zone> zones) {
		super(name, zones);
		// set first clamping as active one!
		for (SimpleWorkArea wa : getWorkAreas()) {
			wa.setDefaultClamping(wa.getWorkAreaManager().getClampings().iterator().next());
		}
	}
	
	@Override
	public void clearDeviceSettings() {		
	}

	@Override
	public void prepareForProcess(final ProcessFlow process) throws AbstractCommunicationException, InterruptedException {
		// no action needed
	}

	@Override
	public boolean canPick(final DevicePickSettings pickSettings) throws AbstractCommunicationException, DeviceActionException {
		// pick is not possible
		return false;
	}

	@Override
	public boolean canPut(final DevicePutSettings putSettings) throws AbstractCommunicationException, DeviceActionException, InterruptedException {
		// put is always possible
		return true;
	}

	@Override
	public boolean canIntervention(final DeviceInterventionSettings interventionSettings) throws AbstractCommunicationException, DeviceActionException {
		// intervention is always possible
		return true;
	}

	@Override
	public void prepareForPick(final DevicePickSettings pickSettings, final int processId) throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// pick is not possible
		throw new IllegalStateException("Pick is not possible");
	}

	@Override
	public void prepareForPut(final DevicePutSettings putSettings, final int processId) throws AbstractCommunicationException, DeviceActionException, InterruptedException {
		// put is always possible
	}

	@Override
	public void prepareForIntervention(final DeviceInterventionSettings interventionSettings) throws AbstractCommunicationException, DeviceActionException,
			InterruptedException {
		// intervention is always possible
	}

	@Override
	public void pickFinished(final DevicePickSettings pickSettings, final int processId) throws AbstractCommunicationException, DeviceActionException, InterruptedException {
		// no action needed
	}

	@Override
	public void putFinished(final DevicePutSettings putSettings) throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// no action needed
	}

	@Override
	public void interventionFinished(final DeviceInterventionSettings interventionSettings) throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// no action needed
	}

	@Override
	public void releasePiece(final DevicePickSettings pickSettings) throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// no action needed
	}

	@Override
	public void grabPiece(final DevicePutSettings putSettings) throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// no action needed
	}

	@Override
	public void reset() throws AbstractCommunicationException, DeviceActionException, InterruptedException {		
		// no action needed
	}

	@Override
	public void loadDeviceSettings(final DeviceSettings deviceSettings) {		
		for (Entry<SimpleWorkArea, Clamping> entry : deviceSettings.getClampings().entrySet()) {
			entry.getKey().setDefaultClamping(entry.getValue());
		}
	}

	@Override
	public DeviceSettings getDeviceSettings() {
		Map<SimpleWorkArea, Clamping> clampings = new HashMap<SimpleWorkArea, Clamping>();
		for (SimpleWorkArea wa : getWorkAreas()) {
			clampings.put(wa, wa.getDefaultClamping());
		}
		return new DeviceSettings(clampings);
	}

	@Override
	public Coordinates getLocationOrientation(final SimpleWorkArea workArea, final ClampingManner clampType) {
		return workArea.getDefaultClamping().getRelativePosition();
	}

	@Override
	public void interruptCurrentAction() {		
		// no action needed
	}

	@Override
	public boolean isConnected() {
		return true;
	}
	
	@Override
	public EDeviceGroup getType() {
		return EDeviceGroup.OUTPUT_BIN;
	}

	@Override
	public <T extends IWorkPieceDimensions> Coordinates getPutLocation(AbstractPiecePlacementVisitor<T> visitor,
			SimpleWorkArea workArea, T dimensions,
			ClampingManner clampType, ApproachType approachType) {
		return visitor.getPutLocation(this, workArea, dimensions, clampType, approachType);
	}

	@Override
	public <T extends IWorkPieceDimensions> Coordinates getPickLocation(AbstractPiecePlacementVisitor<T> visitor,
			SimpleWorkArea workArea, T dimensions,
			ClampingManner clampType, ApproachType approachType) {
		return visitor.getPickLocation(this, workArea, dimensions, clampType, approachType);
	}

	@Override
	public <T extends IWorkPieceDimensions> Coordinates getLocation(
			AbstractPiecePlacementVisitor<T> visitor, SimpleWorkArea workArea,
			Type type, ClampingManner clampType) {
		return visitor.getLocation(this, workArea, type, clampType);
	}


}
