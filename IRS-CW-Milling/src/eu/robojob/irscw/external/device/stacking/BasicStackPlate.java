package eu.robojob.irscw.external.device.stacking;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.robojob.irscw.external.communication.AbstractCommunicationException;
import eu.robojob.irscw.external.device.DeviceInterventionSettings;
import eu.robojob.irscw.external.device.DevicePickSettings;
import eu.robojob.irscw.external.device.DevicePutSettings;
import eu.robojob.irscw.external.device.DeviceSettings;
import eu.robojob.irscw.external.device.ClampingManner;
import eu.robojob.irscw.external.device.DeviceActionException;
import eu.robojob.irscw.external.device.DeviceType;
import eu.robojob.irscw.external.device.WorkArea;
import eu.robojob.irscw.external.device.Zone;
import eu.robojob.irscw.positioning.Coordinates;
import eu.robojob.irscw.process.ProcessFlow;
import eu.robojob.irscw.workpiece.WorkPiece;
import eu.robojob.irscw.workpiece.WorkPiece.Type;
import eu.robojob.irscw.workpiece.WorkPieceDimensions;

// it is important this device can keep state, for other (processing) devices, no state is needed
// a workpiece is put, processed and picked, and the device-state is the same as before these actions
// for this device, state is needed to know the configuration (studs) and workpieces (finished, raw, ...) present

// for now, we asume only one layer can be placed, and the pick-locations will be re-used for putting finished workpieces
// also, this stack plate can only contain two types of workpieces, raw workpieces and finished workpieces
// also, the orientation of raw and finished workpieces is the same, and as mentioned earlier pick-locations equal put-locations
public class BasicStackPlate extends AbstractStackingDevice {

	public enum WorkPieceOrientation {
		HORIZONTAL, TILTED
	}
		
	private static Logger logger = Logger.getLogger(BasicStackPlate.class);
	private BasicStackPlateLayout layout;
		
	private WorkPiece rawWorkPiece;
	private WorkPiece finishedWorkPiece;
	
	private StackingPosition currentPickLocation;
	
	public BasicStackPlate(String id, List<Zone> zones, BasicStackPlateLayout layout) {
		super(id, zones);
		this.layout = layout;
		this.rawWorkPiece = null;
		this.finishedWorkPiece = null;
		currentPickLocation = null;
	}
	
	public BasicStackPlate(String id, BasicStackPlateLayout layout) {
		this(id, new ArrayList<Zone>(), layout);
	}
	
	public void setFinishedAmount(int finishedAmount) {
		for (int i = 0; i < layout.getStackingPositions().size(); i++) {
			if (i < finishedAmount) {
				WorkPiece finishedWorkPiece = new WorkPiece(Type.FINISHED, rawWorkPiece.getDimensions());
				layout.getStackingPositions().get(i).setWorkPiece(finishedWorkPiece);
			} else {
				layout.getStackingPositions().get(i).setWorkPiece(rawWorkPiece);
			}
		}
	}

	@Override
	public synchronized boolean canPick(DevicePickSettings pickSettings) throws AbstractCommunicationException {
		for (StackingPosition stackingPos : layout.getStackingPositions()) {
			if ((stackingPos.getWorkPiece() != null) && (stackingPos.getWorkPiece().getType() == Type.RAW)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public synchronized boolean canPut(DevicePutSettings putSettings) throws AbstractCommunicationException {
		for (StackingPosition stackingPos : layout.getStackingPositions()) {
			if (stackingPos.getWorkPiece() == null) {
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean canIntervention(DeviceInterventionSettings interventionSettings) throws AbstractCommunicationException, DeviceActionException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public synchronized Coordinates getPickLocation(WorkArea workArea, ClampingManner clampType) {
		for (StackingPosition stackingPos : layout.getStackingPositions()) {
			if ((stackingPos.getWorkPiece() != null)&&(stackingPos.getWorkPiece().getType() != Type.FINISHED)) {
				currentPickLocation = stackingPos;
				Coordinates c = new Coordinates(stackingPos.getPosition());
				return c;
			}
		}
		return null;
	}
	
	@Override 
	public synchronized Coordinates getLocation(WorkArea workArea, Type type, ClampingManner clampType) {
		for (StackingPosition stackingPos : layout.getStackingPositions()) {
			if ((stackingPos.getWorkPiece() != null)&&(stackingPos.getWorkPiece().getType()==type)) {
				Coordinates c = new Coordinates(stackingPos.getPosition());
				return c;
			}
		}
		return null;
	}

	@Override
	public synchronized Coordinates getPutLocation(WorkArea workArea, WorkPieceDimensions workPieceDimensions, ClampingManner clampType) {
		logger.info("getting put location: " + workPieceDimensions);
		finishedWorkPiece = new WorkPiece(WorkPiece.Type.FINISHED, workPieceDimensions);
		Coordinates c = new Coordinates(currentPickLocation.getPosition());
		return c;
	}

	@Override
	public void prepareForPick(DevicePickSettings pickSettings) {
	}

	@Override
	public void prepareForPut(DevicePutSettings putSettings) {
	}

	@Override
	public void prepareForIntervention(DeviceInterventionSettings interventionSettings) {
	}

	@Override
	public synchronized void pickFinished(DevicePickSettings pickSettings) {
		currentPickLocation.setWorkPiece(null);
	}

	@Override
	public synchronized void putFinished(DevicePutSettings putSettings) {
		//BasicStackPlatePutSettings spPutSettings = (BasicStackPlatePutSettings) putSettings;
		currentPickLocation.setWorkPiece(finishedWorkPiece);
		currentPickLocation = null;
	}

	@Override
	public void interventionFinished(DeviceInterventionSettings interventionSettings) {
	}

	@Override
	public void releasePiece(DevicePickSettings pickSettings) {
	}

	@Override
	public void grabPiece(DevicePutSettings putSettings) {
	}
	
	public BasicStackPlateLayout getLayout() {
		return layout;
	}
	
	public WorkPiece getRawWorkPiece() {
		return rawWorkPiece;
	}
	
	@Override
	public DeviceType getType() {
		return DeviceType.BASIC_STACK_PLATE;
	}

	@Override
	public synchronized void loadDeviceSettings(DeviceSettings deviceSettings) {
		if (deviceSettings instanceof BasicStackPlateSettings) {
			BasicStackPlateSettings settings = (BasicStackPlateSettings) deviceSettings;
			try {
				layout.configureStackingPositions(settings.getWorkPiece(), settings.getOrientation());
				this.rawWorkPiece = settings.getWorkPiece();
				layout.placeRawWorkPieces(rawWorkPiece, settings.getAmount());
			} catch (IncorrectWorkPieceDataException e) {
				logger.error(e);
			}
		} else {
			throw new IllegalArgumentException("Unknown device settings");
		}
	}

	@Override
	public DeviceSettings getDeviceSettings() {
		return new BasicStackPlateSettings(rawWorkPiece, layout.getOrientation(), layout.getRawWorkPieceAmount());
	}

	@Override
	public boolean validatePickSettings(DevicePickSettings pickSettings) {
		// note we assume the corresponding device settings are loaded!
		// the used workarea should be the one workarea configured for this device
		if ((pickSettings != null) && (pickSettings.getWorkArea() != null) && (pickSettings.getWorkArea().equals(getWorkAreas().get(0))) && 
				(layout.getStackingPositions() != null) && (layout.getStackingPositions().size() > 0)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validatePutSettings(DevicePutSettings putSettings) {
		// note we assume the corresponding device settings are loaded!
		// the used workarea should be the one workarea configured for this device
		if ((putSettings != null) && (putSettings.getWorkArea() != null) && (putSettings.getWorkArea().equals(getWorkAreas().get(0))) && 
				(layout.getStackingPositions() != null) && (layout.getStackingPositions().size() > 0)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean validateInterventionSettings(DeviceInterventionSettings interventionSettings) {
		// note we assume the corresponding device settings are loaded!
		// the used workarea should be the one workarea configured for this device
		if ((interventionSettings != null) && (interventionSettings.getWorkArea() != null) && (interventionSettings.getWorkArea().equals(getWorkAreas().get(0))) && 
				(layout.getStackingPositions() != null) && (layout.getStackingPositions().size() > 0)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void interruptCurrentAction() {
	}

	@Override
	public void prepareForProcess(ProcessFlow process) throws AbstractCommunicationException, InterruptedException {
		/*try {
			layout.placeRawWorkPieces(rawWorkPiece, process.getTotalAmount());
		} catch (IncorrectWorkPieceDataException e) {
			e.printStackTrace();
		}*/
	}

}
