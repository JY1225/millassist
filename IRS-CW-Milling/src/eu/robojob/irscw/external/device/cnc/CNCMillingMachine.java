package eu.robojob.irscw.external.device.cnc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import eu.robojob.irscw.external.communication.CommunicationException;
import eu.robojob.irscw.external.communication.ExternalCommunication;
import eu.robojob.irscw.external.communication.SocketConnection;
import eu.robojob.irscw.external.device.AbstractProcessingDevice;
import eu.robojob.irscw.external.device.Clamping;
import eu.robojob.irscw.external.device.DeviceActionException;
import eu.robojob.irscw.external.device.WorkArea;
import eu.robojob.irscw.external.device.Zone;
import eu.robojob.irscw.positioning.Coordinates;
import eu.robojob.irscw.workpiece.WorkPieceDimensions;

//TODO refactor so many of the used methods are contained in the AbstractCNCMachine (as they also apply to that class)
//TODO cyclus start voorzien
public class CNCMillingMachine extends AbstractCNCMachine {

	private CNCMachineCommunication cncMachineCommunication;
	
	private static Logger logger = Logger.getLogger(AbstractProcessingDevice.class);
	
	private static final int READ_TIMEOUT = 10000;
	private static final int PREPARE_PUT_TIMEOUT = 30000;
		
	public CNCMillingMachine(String id, SocketConnection socketConnection) {
		super(id);
		this.cncMachineCommunication = new CNCMachineCommunication(socketConnection);
	}
	
	public CNCMillingMachine(String id, List<Zone> zones, SocketConnection socketConnection) {
		super(id, zones);
		this.cncMachineCommunication = new CNCMachineCommunication(socketConnection);
	}
	
	@Override
	public String getStatus() throws CommunicationException {
		return null;
	}

	@Override
	public void startCyclus(AbstractProcessingDeviceStartCyclusSettings startCylusSettings) throws CommunicationException, DeviceActionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareForStartCyclus(AbstractProcessingDeviceStartCyclusSettings startCylusSettings) throws CommunicationException, DeviceActionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepareForPick(AbstractDevicePickSettings pickSettings) throws CommunicationException {
		// check first workarea is selected 
		if (pickSettings.getWorkArea().getId().equals(getWorkAreas().get(0))) {
			// first WA
			int command = 0;
			command = command | CNCMachineConstants.IPC_PICK_WA1_RQST;
			
			int registers[] = {command};
			cncMachineCommunication.writeRegisters(CNCMachineConstants.IPC_REQUEST, registers);
			
		} else {
			throw new IllegalArgumentException("I only have one workarea!");
		}
	}

	@Override
	public void prepareForPut(AbstractDevicePutSettings putSettings) throws CommunicationException, DeviceActionException {
		// check first workarea is selected 
		if (putSettings.getWorkArea().getId().equals(getWorkAreas().get(0).getId())) {
			// first WA
			int command = 0;
			command = command | CNCMachineConstants.IPC_PUT_WA1_REQUEST;
			
			// with this kind of machines, the work-area stays the same, so the WA (clamp) of the finished product is the same as that of the raw
			int cncTask = 0;
			cncTask = cncTask | CNCMachineConstants.WA1_CNC_PROCESS;
			
			int registers[] = {command, cncTask};
			
			logger.info("Writing request for IPC_PICK: " + command + " - " + cncTask);
			cncMachineCommunication.writeRegisters(CNCMachineConstants.IPC_REQUEST, registers);
			logger.info("Wrote request");
			
			logger.info("About to check is put is prepared");
			// check put is prepared
			boolean putReady = cncMachineCommunication.checkRegisterValueBitPattern(CNCMachineConstants.STATUS, CNCMachineConstants.R_PUT_WA1_READY, PREPARE_PUT_TIMEOUT);
			if (!putReady) {
				throw new DeviceActionException("Machine could not prepare for put");
			} else {
				logger.info("put is prepared!");
			}
					
		} else {
			throw new IllegalArgumentException("Wrong workarea, should be: " + getWorkAreas().get(0).getId() + " but got: " + putSettings.getWorkArea().getId());
		}
	}

	@Override
	public void prepareForIntervention(AbstractDeviceInterventionSettings interventionSettings) throws CommunicationException {
		// TODO Auto-generated method stub
		
	}

	//TODO these are not taken into account by the machine for now...
	@Override
	public void pickFinished(AbstractDevicePickSettings pickSettings) throws CommunicationException {
	}
	@Override
	public void putFinished(AbstractDevicePutSettings putSettings) throws CommunicationException {
	}
	@Override
	public void interventionFinished(AbstractDeviceInterventionSettings interventionSettings) throws CommunicationException {
	}

	@Override
	public void releasePiece(AbstractDevicePickSettings pickSettings) throws CommunicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void grabPiece(AbstractDevicePutSettings putSettings) throws CommunicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPick(AbstractDevicePickSettings pickSettings) throws CommunicationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canPut(AbstractDevicePutSettings putSettings) throws CommunicationException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static class CNCMillingMachinePutSettings extends AbstractCNCMachinePutSettings{
		public CNCMillingMachinePutSettings(WorkArea workArea) {
			super(workArea);
		}

		@Override
		public boolean isPutPositionFixed() {
			return false;
		}
	}
	public static class CNCMillingMachinePickSettings extends AbstractCNCMachinePickSettings{
		public CNCMillingMachinePickSettings(WorkArea workArea) {
			super(workArea);
		}

	}
	public static class CNCMillingMachineInterventionSettings extends AbstractCNCMachineInterventionSettings{
		public CNCMillingMachineInterventionSettings(WorkArea workArea) {
			super(workArea);
		}
	}
	public static class CNCMillingMachineStartCylusSettings extends AbstractCNCMachineStartCyclusSettings {
		public CNCMillingMachineStartCylusSettings(WorkArea workArea) {
			super(workArea);
		}
	}
	public class CNCMillingMachineSettings extends AbstractDeviceSettings {

		private Map<WorkArea, Clamping> clampings;
		
		public CNCMillingMachineSettings() {
			clampings = new HashMap<WorkArea, Clamping>();
		}
		
		public CNCMillingMachineSettings(List<WorkArea> workAreas) {
			this();
			for (WorkArea workArea : workAreas) {
				clampings.put(workArea, workArea.getActiveClamping());
			}
		}
		
		public void setClamping(WorkArea workArea, Clamping clamping) {
			clampings.put(workArea, clamping);
		}

		public Map<WorkArea, Clamping> getClampings() {
			return clampings;
		}

		public void setClampings(Map<WorkArea, Clamping> clampings) {
			this.clampings = clampings;
		}
		
		public Clamping getClamping(WorkArea workArea) {
			return clampings.get(workArea);
		}
	
	}

	@Override
	public void loadDeviceSettings(AbstractDeviceSettings deviceSettings) {
		if (deviceSettings instanceof CNCMillingMachineSettings) {
			CNCMillingMachineSettings settings = (CNCMillingMachineSettings) deviceSettings;
			for (Entry<WorkArea, Clamping> entry : settings.getClampings().entrySet()) {
				entry.getKey().setActiveClamping(entry.getValue());
			}
		} else {
			throw new IllegalArgumentException("Unknown device settings");
		}
	}

	@Override
	public AbstractDeviceSettings getDeviceSettings() {
		return new CNCMillingMachineSettings(getWorkAreas());
	}

	@Override
	public boolean validateStartCyclusSettings(AbstractProcessingDeviceStartCyclusSettings startCyclusSettings) {
		CNCMillingMachineStartCylusSettings cncMillingStartCyclusSettings = (CNCMillingMachineStartCylusSettings) startCyclusSettings;
		if ((cncMillingStartCyclusSettings != null) && (cncMillingStartCyclusSettings.getWorkArea() != null) && (getWorkAreas().contains(cncMillingStartCyclusSettings.getWorkArea())) &&
				(cncMillingStartCyclusSettings.getWorkArea().getActiveClamping() != null) ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validatePickSettings(AbstractDevicePickSettings pickSettings) {
		CNCMillingMachinePickSettings cncMillingMachinePickSettings = (CNCMillingMachinePickSettings) pickSettings;
		if ((cncMillingMachinePickSettings != null) && (cncMillingMachinePickSettings.getWorkArea() != null) && (getWorkAreas().contains(cncMillingMachinePickSettings.getWorkArea())) &&
				(cncMillingMachinePickSettings.getWorkArea().getActiveClamping() != null) ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validatePutSettings(AbstractDevicePutSettings putSettings) {
		CNCMillingMachinePutSettings cncMillingMachinePutSettings = (CNCMillingMachinePutSettings) putSettings;
		if ((cncMillingMachinePutSettings != null) && (cncMillingMachinePutSettings.getWorkArea() != null) && (getWorkAreas().contains(cncMillingMachinePutSettings.getWorkArea())) &&
				(cncMillingMachinePutSettings.getWorkArea().getActiveClamping() != null) ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validateInterventionSettings(AbstractDeviceInterventionSettings interventionSettings) {
		CNCMillingMachineInterventionSettings cncMillingMachineInterventionSettings = (CNCMillingMachineInterventionSettings) interventionSettings;
		if ((cncMillingMachineInterventionSettings != null) && (cncMillingMachineInterventionSettings.getWorkArea() != null) && (getWorkAreas().contains(cncMillingMachineInterventionSettings.getWorkArea())) &&
				(cncMillingMachineInterventionSettings.getWorkArea().getActiveClamping() != null) ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public AbstractDeviceInterventionSettings getInterventionSettings(AbstractDevicePickSettings pickSettings) {
		return new CNCMillingMachineInterventionSettings(pickSettings.getWorkArea());
	}

	@Override
	public AbstractDeviceInterventionSettings getInterventionSettings(AbstractDevicePutSettings putSettings) {
		return new CNCMillingMachineInterventionSettings(putSettings.getWorkArea());
	}

	@Override
	public Coordinates getPickLocation(WorkArea workArea) {
		return workArea.getActiveClamping().getRelativePosition();
	}

	@Override
	public Coordinates getPutLocation(WorkArea workArea,
			WorkPieceDimensions workPieceDimensions) {
		Coordinates c = workArea.getActiveClamping().getRelativePosition();
		c.offset(new Coordinates(workPieceDimensions.getLength(), workPieceDimensions.getWidth(), workPieceDimensions.getHeight(), 0, 0, 0));
		return c;
	}

	@Override
	public boolean isConnected() {
		return cncMachineCommunication.isConnected();
	}
	
	public void disconnect() {
		cncMachineCommunication.disconnect();
	}
}
