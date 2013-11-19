package eu.robojob.millassist.ui.configure.device.processing.cnc;

import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import eu.robojob.millassist.external.device.Clamping;
import eu.robojob.millassist.external.device.ClampingManner.Type;
import eu.robojob.millassist.external.device.processing.cnc.milling.CNCMillingMachine;
import eu.robojob.millassist.ui.controls.IconFlowSelector;
import eu.robojob.millassist.ui.controls.TextInputControlListener;
import eu.robojob.millassist.ui.general.AbstractFormView;
import eu.robojob.millassist.ui.general.model.DeviceInformation;
import eu.robojob.millassist.util.Translator;
import eu.robojob.millassist.util.UIConstants;

public class CNCMillingMachineConfigureView extends AbstractFormView<CNCMillingMachineConfigurePresenter> {

	private static final String CLAMP_WIDTH_ICON = "M 7 0 L 7 5.5625 L 7 11.15625 L 9 11.15625 L 9 0 L 7 0 z M 7 5.5625 L 3.5 2.0625 L 2.28125 3.3125 L 3.65625 4.6875 L 0 4.6875 L 0 6.46875 L 3.65625 6.46875 L 2.28125 7.84375 L 3.5 9.0625 L 7 5.5625 z M 11 0 L 11 11.15625 L 23.75 11.15625 L 23.75 5.5625 L 23.75 0 L 11 0 z M 23.75 5.5625 L 27.25 9.0625 L 28.5 7.84375 L 27.125 6.46875 L 30.75 6.46875 L 30.75 4.6875 L 27.125 4.6875 L 28.5 3.3125 L 27.25 2.0625 L 23.75 5.5625 z";
	private static final String CLAMP_HEIGHT_ICON = "M 6.96875 0 L 6.96875 2 L 18.125 2 L 18.125 0 L 6.96875 0 z M 6.96875 4 L 6.96875 8.375 L 6.96875 16.75 L 18.125 16.75 L 18.125 8.375 L 18.125 4 L 6.96875 4 z M 18.125 8.375 L 21.625 11.875 L 22.875 10.65625 L 21.5 9.28125 L 25.125 9.28125 L 25.125 7.5 L 21.5 7.5 L 22.875 6.125 L 21.625 4.875 L 18.125 8.375 z M 6.96875 8.375 L 3.46875 4.875 L 2.25 6.125 L 3.625 7.5 L -0.03125 7.5 L -0.03125 9.28125 L 3.625 9.28125 L 2.25 10.65625 L 3.46875 11.875 L 6.96875 8.375 z";
	
	private Label lblMachine;
	private ComboBox<String> cbbMachine;
	private Label lblWorkArea;
	private ComboBox<String> cbbWorkArea;
	private Label lblClampingName;
	private IconFlowSelector ifsClamping;
	private Label lblClampingType;
	private Button btnLength;
	private Button btnWidth;
	private DeviceInformation deviceInfo;
	private Set<String> cncMillingMachineIds;
	private SVGPath svgPathIconLength;
	private SVGPath svgPathIconWidth;
	
	private static final int HGAP = 10;
	private static final int VGAP = 10;
	private static final int COMBO_WIDTH = 150;
	private static final int COMBO_HEIGHT = 40;
	private static final double BTN_WIDTH = 110;
	private static final double ICONFLOWSELECTOR_WIDTH = 530;
	private static final double BTN_HEIGHT = UIConstants.BUTTON_HEIGHT;
	
	private static final String DEVICE = "CNCMillingMachineConfigureView.machine";
	private static final String WORKAREA = "CNCMillingMachineConfigureView.workArea";
	private static final String CLAMPING = "CNCMillingMachineConfigureView.clampingName";
	private static final String CLAMPING_TYPE = "CNCMillingMachineConfigureView.clampingType";
	private static final String LENGTH = "CNCMillingMachineConfigureView.length";
	private static final String WIDTH = "CNCMillingMachineConfigureView.width";
	
	private static final String CSS_CLASS_BUTTON_CLAMPING = "btn-clamping";

	public void setDeviceInfo(final DeviceInformation deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	public void setCNCMillingMachineIds(final Set<String> cncMillingMachineIds) {
		this.cncMillingMachineIds = cncMillingMachineIds;
	}
	
	@Override
	protected void build() {
		getContents().setVgap(VGAP);
		getContents().setHgap(HGAP);
		getContents().getChildren().clear();
		
		lblMachine = new Label(Translator.getTranslation(DEVICE));
		int column = 0;
		int row = 0;
		getContents().add(lblMachine, column++, row);
		cbbMachine = new ComboBox<String>();
		cbbMachine.setPrefSize(COMBO_WIDTH, COMBO_HEIGHT);
		cbbMachine.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> arg0, final String oldValue, final String newValue) {
				if (newValue != null) {
					if ((oldValue == null) || (!oldValue.equals(newValue))) {
						getPresenter().changedDevice(newValue);
					}
				}
			}
		});
		getContents().add(cbbMachine, column++, row);
		lblWorkArea = new Label(Translator.getTranslation(WORKAREA));
		getContents().add(lblWorkArea, column++, row);
		cbbWorkArea = new ComboBox<String>();
		cbbWorkArea.setPrefSize(COMBO_WIDTH, COMBO_HEIGHT);
		cbbWorkArea.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
				if (newValue != null) {
					if ((oldValue == null) || (!oldValue.equals(newValue))) {
						if ((deviceInfo.getPickStep().getDeviceSettings().getWorkArea() == null) || (!newValue.equals(deviceInfo.getPickStep().getDeviceSettings().getWorkArea().getName()))) {
							getPresenter().changedWorkArea(newValue);
						}
					}
				}
			}
		});
		getContents().add(cbbWorkArea, column++, row);
		column = 0;
		row++;
		lblClampingName = new Label(Translator.getTranslation(CLAMPING));
		getContents().add(lblClampingName, column++, row, 4, 1);
		column = 0;
		row++;
		ifsClamping = new IconFlowSelector();
		ifsClamping.setPrefWidth(ICONFLOWSELECTOR_WIDTH);
		getContents().add(ifsClamping, column++, row, 4, 1);
		column = 0;
		row++;
		lblClampingType = new Label(Translator.getTranslation(CLAMPING_TYPE));
		btnLength = createButton(CLAMP_HEIGHT_ICON, CSS_CLASS_BUTTON_CLAMPING, Translator.getTranslation(LENGTH), BTN_WIDTH, BTN_HEIGHT, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().changedClampingTypeLength();
			}
		});
		HBox hboxGraphicLength = (HBox) btnLength.getGraphic();
		StackPane spIconLength = (StackPane) hboxGraphicLength.getChildren().get(0);
		svgPathIconLength = (SVGPath) spIconLength.getChildren().get(0);
				
		btnLength.getStyleClass().add(CSS_CLASS_FORM_BUTTON_BAR_LEFT);
		btnWidth = createButton(CLAMP_WIDTH_ICON, CSS_CLASS_BUTTON_CLAMPING, Translator.getTranslation(WIDTH), BTN_WIDTH, BTN_HEIGHT, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().changedClampingTypeWidth();
			}
		});
		HBox hboxGraphicWidth = (HBox) btnWidth.getGraphic();
		StackPane spIconWidth = (StackPane) hboxGraphicWidth.getChildren().get(0);
		svgPathIconWidth = (SVGPath) spIconWidth.getChildren().get(0);
		
		btnWidth.getStyleClass().add(CSS_CLASS_FORM_BUTTON_BAR_RIGHT);
		HBox hboxBtns = new HBox();
		hboxBtns.getChildren().add(lblClampingType);
		hboxBtns.getChildren().add(btnLength);
		hboxBtns.getChildren().add(btnWidth);
		hboxBtns.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(lblClampingType, new Insets(0, 10, 0, 0));
		getContents().add(hboxBtns, column++, row, 4, 1);
		GridPane.setMargin(lblWorkArea, new Insets(0, 0, 0, 10));
		refresh();
	}
	
	@Override
	public void refresh() {
		refreshMachines();
		refreshClampingButtons();
		refreshWorkAreas();
		refreshClampings();
		refreshClampType();
	}
	
	public void refreshMachines() {
		getPresenter().refreshMachineNames();
		cbbMachine.getItems().clear();
		cbbMachine.getItems().addAll(cncMillingMachineIds);
		cbbMachine.setValue(null);
		lblMachine.setDisable(false);
		cbbMachine.setDisable(false);
		if (cbbMachine.getItems().size() == 1) {
			cbbMachine.setValue(cbbMachine.getItems().get(0));
			lblMachine.setDisable(true);
			cbbMachine.setDisable(true);
		} else if (deviceInfo.getDevice() != null) {
			cbbMachine.setValue(deviceInfo.getDevice().getName());
		}
	}
	
	public void refreshWorkAreas() {
		if ((deviceInfo.getDevice() != null) && (deviceInfo.getDevice().getWorkAreas() != null)) {
			cbbWorkArea.setValue(null);
			cbbWorkArea.getItems().clear();
			cbbWorkArea.getItems().addAll(deviceInfo.getDevice().getWorkAreaNames());
			if ((deviceInfo.getPutStep() != null) && (deviceInfo.getPutStep().getDeviceSettings() != null)
					&& (deviceInfo.getPutStep().getDeviceSettings().getWorkArea() != null)) {
				cbbWorkArea.setValue(deviceInfo.getPutStep().getDeviceSettings().getWorkArea().getName());
			}
			if (deviceInfo.getDevice().getWorkAreaNames().size() > 1) {
				cbbWorkArea.setDisable(false);
				lblWorkArea.setDisable(false);
			} else {
				cbbWorkArea.setDisable(true);
				lblWorkArea.setDisable(true);
			}
		}
	}

	public void refreshClampingButtons() {
		// for now we assume the clamping's corner to be 0, -90 or +90	
		if (deviceInfo.getDevice() != null) {
			if (deviceInfo.getDeviceSettings() != null) {
				if (deviceInfo.hasProcessingStep()) {
					if (deviceInfo.getProcessingStep().getDeviceSettings().getWorkArea() != null) {
						if (deviceInfo.getProcessingStep().getDeviceSettings().getWorkArea().getActiveClamping() != null) {
							double clampingR = deviceInfo.getProcessingStep().getDeviceSettings().getWorkArea().getActiveClamping().getRelativePosition().getR();
							double clampingWidthDeltaR = ((CNCMillingMachine) deviceInfo.getDevice()).getClampingWidthR();
							svgPathIconLength.setRotate(-clampingR);
							svgPathIconWidth.setRotate(-(clampingR + clampingWidthDeltaR - 90));	// the -90 is because of the default image
						}
					}
				}
			}
		}
	}
	
	public void refreshClampings() {
		ifsClamping.clearItems();
		if ((deviceInfo.getPutStep().getDeviceSettings() != null) && (deviceInfo.getPutStep().getDeviceSettings().getWorkArea() != null)) {
			int itemIndex = 0;
			for (final Clamping clamping : deviceInfo.getPutStep().getDeviceSettings().getWorkArea().getClampings()) {
				ifsClamping.addItem(itemIndex, clamping.getName(), clamping.getImageUrl(), new EventHandler<MouseEvent>() {
					@Override
					public void handle(final MouseEvent arg0) {
						getPresenter().changedClamping(clamping);
					}
				});
				itemIndex++;
			}
			if (deviceInfo.getPutStep().getDeviceSettings().getWorkArea().getActiveClamping() != null) {
				ifsClamping.setSelected(deviceInfo.getPutStep().getDeviceSettings().getWorkArea().getActiveClamping().getName());
			}
		}
	}
	
	public void selectClamping(final String clampingName) {
		ifsClamping.setSelected(clampingName);
	}
	
	public void refreshClampType() {
		btnLength.getStyleClass().remove(CSS_CLASS_FORM_BUTTON_ACTIVE);
		btnWidth.getStyleClass().remove(CSS_CLASS_FORM_BUTTON_ACTIVE);
		if (deviceInfo.getProcessingStep() != null) {
			if (deviceInfo.getProcessingStep().getProcessFlow().getClampingType().getType() == Type.LENGTH) {
				btnLength.getStyleClass().add(CSS_CLASS_FORM_BUTTON_ACTIVE);
			} else {
				btnWidth.getStyleClass().add(CSS_CLASS_FORM_BUTTON_ACTIVE);
			}
		}
	}
	
	@Override
	public void setTextFieldListener(final TextInputControlListener listener) {
	}

}
