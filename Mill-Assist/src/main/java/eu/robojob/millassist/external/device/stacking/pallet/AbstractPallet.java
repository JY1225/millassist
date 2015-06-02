package eu.robojob.millassist.external.device.stacking.pallet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.robojob.millassist.external.communication.AbstractCommunicationException;
import eu.robojob.millassist.external.device.ClampingManner;
import eu.robojob.millassist.external.device.DeviceActionException;
import eu.robojob.millassist.external.device.DeviceInterventionSettings;
import eu.robojob.millassist.external.device.DevicePickSettings;
import eu.robojob.millassist.external.device.DevicePutSettings;
import eu.robojob.millassist.external.device.EDeviceGroup;
import eu.robojob.millassist.external.device.SimpleWorkArea;
import eu.robojob.millassist.external.device.Zone;
import eu.robojob.millassist.external.device.stacking.AbstractStackingDevice;
import eu.robojob.millassist.external.device.stacking.stackplate.gridplate.GridPlate;
import eu.robojob.millassist.external.device.visitor.AbstractPiecePlacementVisitor;
import eu.robojob.millassist.external.robot.AbstractRobotActionSettings.ApproachType;
import eu.robojob.millassist.positioning.Coordinates;
import eu.robojob.millassist.process.ProcessFlow;
import eu.robojob.millassist.workpiece.IWorkPieceDimensions;

public abstract class AbstractPallet extends AbstractStackingDevice {

    private static Logger logger = LogManager.getLogger(AbstractPallet.class.getName());
    private PalletStackingPosition currentPutLocation;
    /**
     * List of listeners
     */
    private List<UnloadPalletListener> listeners = new ArrayList<UnloadPalletListener>();
    protected float maxHeight;

    public AbstractPallet(String name, final Set<Zone> zones) {
        super(name, zones);
    }

    public AbstractPallet(String name) {
        this(name, new HashSet<Zone>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareForProcess(ProcessFlow process) throws AbstractCommunicationException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPick(DevicePickSettings pickSettings) throws AbstractCommunicationException,
            DeviceActionException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EDeviceGroup getType() {
        return EDeviceGroup.STACKING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canIntervention(DeviceInterventionSettings interventionSettings)
            throws AbstractCommunicationException, DeviceActionException {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareForPick(DevicePickSettings pickSettings, int processId) throws AbstractCommunicationException,
            DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareForPut(DevicePutSettings putSettings, int processId) throws AbstractCommunicationException,
            DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareForIntervention(DeviceInterventionSettings interventionSettings)
            throws AbstractCommunicationException, DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pickFinished(DevicePickSettings pickSettings, int processId) throws AbstractCommunicationException,
            DeviceActionException, InterruptedException {
        // Cannot pick
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putFinished(DevicePutSettings putSettings) throws AbstractCommunicationException,
            DeviceActionException, InterruptedException {
        currentPutLocation.setWorkPiece(getFinishedWorkPiece());
        currentPutLocation.incrementAmount();
        currentPutLocation = null;
        notifyLayoutChanged();
        logger.info("put finished!");
    }

    public void notifyLayoutChanged() {
        for (UnloadPalletListener listener : getListeners()) {
            listener.layoutChanged();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interventionFinished(DeviceInterventionSettings interventionSettings)
            throws AbstractCommunicationException, DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void releasePiece(DevicePickSettings pickSettings) throws AbstractCommunicationException,
            DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void grabPiece(DevicePutSettings putSettings) throws AbstractCommunicationException, DeviceActionException,
            InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() throws AbstractCommunicationException, DeviceActionException, InterruptedException {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interruptCurrentAction() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends IWorkPieceDimensions> Coordinates getPickLocation(AbstractPiecePlacementVisitor<T> visitor,
            SimpleWorkArea workArea, T dimensions, ClampingManner clampType, ApproachType approachType) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<UnloadPalletListener> getListeners() {
        return listeners;
    }

    public void addListener(final UnloadPalletListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(final UnloadPalletListener listener) {
        this.listeners.remove(listener);
    }

    public void clearListeners() {
        this.listeners.clear();
    }

    public PalletStackingPosition getCurrentPutLocation() {
        return currentPutLocation;
    }

    public void setCurrentPutLocation(PalletStackingPosition currentPutLocation) {
        this.currentPutLocation = currentPutLocation;
    }
    
    public abstract void setDefaultLayout(PalletLayout layout);
    public abstract void setDefaultGrid(GridPlate gridPlate);

    public float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

}
