package eu.robojob.millassist.ui.configure.device.processing.cnc;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import eu.robojob.millassist.external.device.DeviceSettings;
import eu.robojob.millassist.external.device.processing.cnc.AbstractCNCMachine;
import eu.robojob.millassist.positioning.Coordinates;
import eu.robojob.millassist.process.PutStep;
import eu.robojob.millassist.ui.controls.CoordinateBox;
import eu.robojob.millassist.ui.controls.NumericTextField;
import eu.robojob.millassist.ui.controls.TextInputControlListener;
import eu.robojob.millassist.ui.general.AbstractFormView;
import eu.robojob.millassist.util.PropertyManager;
import eu.robojob.millassist.util.PropertyManager.Setting;
import eu.robojob.millassist.util.Translator;
import eu.robojob.millassist.util.UIConstants;

public class CNCMillingMachinePutView extends AbstractFormView<CNCMillingMachinePutPresenter> {

	private PutStep putStep;
	private DeviceSettings deviceSettings;
	
	private Label lblSmoothInfo;
	
	private HBox hBoxSmoothPoint;
	
	private Label lblSmoothX;
	private Label lblSmoothY;
	private Label lblSmoothZ;
	
	private Button btnResetSmooth;
	
	private Label lblReleasePieceBeforeClamp;
	private Button btnBeforeClamp;
	private Button btnAfterClamp;
	
	private NumericTextField ntxtSmoothX;
	private NumericTextField ntxtSmoothY;
	private NumericTextField ntxtSmoothZ;
	
	private CoordinateBox coordBAirblowBottom;
	private CoordinateBox coordBAirblowTop;
	private Button btnResetAirblow;
	private ComboBox<String> cbbClamping;
	private static final int COMBO_WIDTH = 150;
	private static final int COMBO_HEIGHT = 40;
	
	private static final int HGAP = 15;
	private static final int VGAP = 10;
	private static final int MAX_INTEGER_LENGTH = 6;
	
	private CheckBox cbAirblow;
	private CheckBox cbTIM;
	private CheckBox cbMachineAirblow;
	private CheckBox cbClampingPressureLow;
	
	private static final String SMOOTH_PUT_INFO = "CNCMillingMachinePutView.smoothPickInfo";
	private static final String SMOOTH_X = "CNCMillingMachinePutView.smoothX";
	private static final String SMOOTH_Y = "CNCMillingMachinePutView.smoothY";
	private static final String SMOOTH_Z = "CNCMillingMachinePutView.smoothZ";
	private static final String SMOOTH_RESET = "CNCMillingMachinePutView.resetSmooth";
	private static final String AIRBLOW = "CNCMillingMachinePutView.airblow";
	private static final String ROBOT_RELEASES = "CNCMillingMachinePutView.robotReleases";
	private static final String AFTER_CLAMP = "CNCMillingMachinePutView.afterClamp";
	private static final String BEFORE_CLAMP = "CNCMillingMachinePutView.beforeClamp";
	private static final String TIM = "CNCMillingMachinePickView.tim";
	private static final String MACHINE_AIRBLOW = "CNCMillingMachinePickView.machineAirblow";
	private static final String LOW_CLAMPING_PRESSURE = "CNCMillingMachinePutView.lowClampingPressure";
	
	private static final String CSS_CLASS_CENTER_TEXT = "center-text";
		
	public CNCMillingMachinePutView() {
		super();
		getContents().setVgap(VGAP);
		getContents().setHgap(HGAP);
	}
	
	public void setPutStep(final PutStep putStep) {
		this.putStep = putStep;
	}
	
	public void setDeviceSettings(final DeviceSettings deviceSettings) {
		this.deviceSettings = deviceSettings;
	}
	
	@Override
	protected void build() {
		lblSmoothInfo = new Label(Translator.getTranslation(SMOOTH_PUT_INFO));
		
		lblSmoothX = new Label(Translator.getTranslation(SMOOTH_X));
		lblSmoothX.setMinWidth(25);
		lblSmoothY = new Label(Translator.getTranslation(SMOOTH_Y));
		lblSmoothY.setMinWidth(25);
		lblSmoothZ = new Label(Translator.getTranslation(SMOOTH_Z));
		lblSmoothZ.setMinWidth(25);
		
		ntxtSmoothX = new NumericTextField(MAX_INTEGER_LENGTH);
		ntxtSmoothX.setPrefSize(UIConstants.NUMERIC_TEXT_FIELD_WIDTH, UIConstants.TEXT_FIELD_HEIGHT);
		ntxtSmoothX.setOnChange(new ChangeListener<Float>() {
			@Override
			public void changed(final ObservableValue<? extends Float> observable, final Float oldValue, final Float newValue) {
				getPresenter().changedSmoothX(newValue);
			}
		});
		ntxtSmoothY = new NumericTextField(MAX_INTEGER_LENGTH);
		ntxtSmoothY.setPrefSize(UIConstants.NUMERIC_TEXT_FIELD_WIDTH, UIConstants.TEXT_FIELD_HEIGHT);
		ntxtSmoothY.setOnChange(new ChangeListener<Float>() {
			@Override
			public void changed(final ObservableValue<? extends Float> observable, final Float oldValue, final Float newValue) {
				getPresenter().changedSmoothY(newValue);
			}
		});
		ntxtSmoothZ = new NumericTextField(MAX_INTEGER_LENGTH);
		ntxtSmoothZ.setPrefSize(UIConstants.NUMERIC_TEXT_FIELD_WIDTH, UIConstants.TEXT_FIELD_HEIGHT);
		ntxtSmoothZ.setOnChange(new ChangeListener<Float>() {
			@Override
			public void changed(final ObservableValue<? extends Float> observable, final Float oldValue, final Float newValue) {
				getPresenter().changedSmoothZ(newValue);
			}
		});
		
		btnResetSmooth = new Button();
		Text txtBtnResetSmooth = new Text(Translator.getTranslation(SMOOTH_RESET));
		txtBtnResetSmooth.getStyleClass().addAll(CSS_CLASS_FORM_BUTTON_LABEL, CSS_CLASS_CENTER_TEXT);
		btnResetSmooth.setGraphic(txtBtnResetSmooth);
		btnResetSmooth.setAlignment(Pos.CENTER);
		btnResetSmooth.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().resetSmooth();
			}
		});
		btnResetSmooth.getStyleClass().add(CSS_CLASS_FORM_BUTTON);
		btnResetSmooth.setPrefSize(UIConstants.BUTTON_HEIGHT * 1.5, UIConstants.BUTTON_HEIGHT);
		btnResetSmooth.setTranslateX(15);
		
		hBoxSmoothPoint = new HBox();
		hBoxSmoothPoint.getChildren().addAll(lblSmoothX, ntxtSmoothX, lblSmoothY, ntxtSmoothY, lblSmoothZ, ntxtSmoothZ);
		HBox.setMargin(ntxtSmoothX, new Insets(0, 10, 0, 10));
		HBox.setMargin(ntxtSmoothY, new Insets(0, 10, 0, 10));
		HBox.setMargin(ntxtSmoothZ, new Insets(0, 10, 0, 10));
		hBoxSmoothPoint.setFillHeight(false);
		hBoxSmoothPoint.setAlignment(Pos.CENTER_LEFT);
		hBoxSmoothPoint.setTranslateX(30);
		
		cbAirblow = new CheckBox(Translator.getTranslation(AIRBLOW));
		cbAirblow.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
				getPresenter().changedAirblow(newValue);
				showAirblow();
			}
		});
		
		lblReleasePieceBeforeClamp = new Label(Translator.getTranslation(ROBOT_RELEASES));
		
		HBox hboxReleaseButtons = new HBox();
		hboxReleaseButtons.getChildren().add(lblReleasePieceBeforeClamp);
		HBox.setMargin(lblReleasePieceBeforeClamp, new Insets(0, 10, 0, 0));
		hboxReleaseButtons.setAlignment(Pos.CENTER_LEFT);
		
		btnBeforeClamp = createButton(Translator.getTranslation(BEFORE_CLAMP), UIConstants.BUTTON_HEIGHT*3, UIConstants.BUTTON_HEIGHT, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().changedReleaseBefore(true);
			}
		});
		HBox.setMargin(btnBeforeClamp, new Insets(0,0,0,13));
		btnBeforeClamp.getStyleClass().add(CSS_CLASS_FORM_BUTTON_BAR_LEFT);
		hboxReleaseButtons.getChildren().add(btnBeforeClamp);
		btnAfterClamp = createButton(Translator.getTranslation(AFTER_CLAMP), UIConstants.BUTTON_HEIGHT*3, UIConstants.BUTTON_HEIGHT, new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().changedReleaseBefore(false);
			}
		});
		btnAfterClamp.getStyleClass().add(CSS_CLASS_FORM_BUTTON_BAR_RIGHT);
		hboxReleaseButtons.getChildren().add(btnAfterClamp);
		
		cbTIM = new CheckBox(Translator.getTranslation(TIM));
		cbTIM.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
				if (getPresenter().getMenuPresenter() != null) 
					getPresenter().getMenuPresenter().changedTIM(newValue);
			}
		});
		
		cbMachineAirblow = new CheckBox(Translator.getTranslation(MACHINE_AIRBLOW));
		cbMachineAirblow.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
				getPresenter().changedMachineAirblow(newValue);
			}
		});
		
		cbClampingPressureLow = new CheckBox(Translator.getTranslation(LOW_CLAMPING_PRESSURE));
		cbClampingPressureLow.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
                getPresenter().changedClampingPressureLow(newValue);
            }
		});
		
		cbbClamping = new ComboBox<String>();
		cbbClamping.setPrefSize(COMBO_WIDTH, COMBO_HEIGHT);
		cbbClamping.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(final ObservableValue<? extends String> arg0, final String oldValue, final String newValue) {
				if (newValue != null) {
					if ((oldValue == null) || (!oldValue.equals(newValue))) {
						getPresenter().changedClamping(newValue);
					}
				}
			}
			
		});
		coordBAirblowBottom = new CoordinateBox(MAX_INTEGER_LENGTH, "X", "Y", "Z");
		coordBAirblowBottom.addChangeListeners(new ChangeListener<Float>() {
			@Override
			public void changed(final ObservableValue<? extends Float> observableValue, final Float oldValue, final Float newValue) {
				coordBAirblowBottom.updateCoordinate();
				getPresenter().changedCoordinate();
			}
		});
		coordBAirblowBottom.setTranslateX(30);
		coordBAirblowTop = new CoordinateBox(MAX_INTEGER_LENGTH, "X", "Y");
		coordBAirblowTop.addChangeListeners(new ChangeListener<Float>() {
			@Override
			public void changed(final ObservableValue<? extends Float> observableValue, final Float oldValue, final Float newValue) {
				coordBAirblowTop.updateCoordinate();
				getPresenter().changedCoordinate();
			}
		});
		coordBAirblowTop.setTranslateX(30);
		
		btnResetAirblow = new Button();
		Text txtBtnResetAirblow = new Text(Translator.getTranslation(SMOOTH_RESET));
		txtBtnResetSmooth.getStyleClass().addAll(CSS_CLASS_FORM_BUTTON_LABEL, CSS_CLASS_CENTER_TEXT);
		btnResetAirblow.setGraphic(txtBtnResetAirblow);
		btnResetAirblow.setAlignment(Pos.CENTER);
		btnResetAirblow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				getPresenter().resetAirblow(cbbClamping.getSelectionModel().getSelectedItem());
			}
		});
		btnResetAirblow.getStyleClass().add(CSS_CLASS_FORM_BUTTON);
		btnResetAirblow.setPrefSize(UIConstants.BUTTON_HEIGHT * 1.5, UIConstants.BUTTON_HEIGHT);
		btnResetAirblow.setTranslateX(15);
		
		int column = 0;
		int row = 0;
		getContents().add(lblSmoothInfo, column++, row);
		
		column = 0;
		row++;
		getContents().add(hBoxSmoothPoint, column++, row);
		getContents().add(btnResetSmooth, column, row);
		
		column = 0;
		row++;
		getContents().add(hboxReleaseButtons, column++, row);
		
		
		column = 0;
		row++;
		getContents().add(cbMachineAirblow, column++, row);
		
		column = 0;
        row++;
        getContents().add(cbClampingPressureLow, column++, row);
		
		column = 0;
		row++;
		getContents().add(cbTIM, column++, row);
		
		column = 0;
		row++;
		HBox airblowHBox = new HBox();
		airblowHBox.getChildren().addAll(cbAirblow, cbbClamping);
		cbbClamping.setTranslateX(10);
		cbbClamping.setTranslateY(-8);
		btnResetAirblow.setTranslateY(-5);
		getContents().add(airblowHBox, column++, row++);
		getContents().add(coordBAirblowBottom, 0, row);
		getContents().add(btnResetAirblow, column, row++);
		getContents().add(coordBAirblowTop, 0, row++);
		if (PropertyManager.hasSettingValue(Setting.AIRBLOW, "false")) {
			cbAirblow.setVisible(false);
			cbAirblow.setManaged(false);
			putStep.getRobotSettings().setRobotAirblow(false);
		}
	}
	
	public void showTurnInMachine() {
		cbTIM.setVisible(((AbstractCNCMachine)putStep.getDevice()).getTIMAllowed());
		cbTIM.setManaged(((AbstractCNCMachine)putStep.getDevice()).getTIMAllowed());
		cbTIM.setSelected(putStep.getRobotSettings().getTurnInMachine());
	}
	
	public void showMachineAirblow() {
		cbMachineAirblow.setVisible(((AbstractCNCMachine)putStep.getDevice()).getMachineAirblow());
		cbMachineAirblow.setManaged(((AbstractCNCMachine)putStep.getDevice()).getMachineAirblow());
		cbMachineAirblow.setSelected(putStep.getDeviceSettings().getMachineAirblow());
	}
	
	private void showClampingPressureLow() {
	    cbClampingPressureLow.setVisible(((AbstractCNCMachine) putStep.getDevice()).isClampingPressureSelectable());
	    cbClampingPressureLow.setManaged(((AbstractCNCMachine) putStep.getDevice()).isClampingPressureSelectable());
	    cbClampingPressureLow.setSelected(putStep.getDeviceSettings().isClampingPressureLow());
	}
	
	private void showAirblow() {
		coordBAirblowBottom.setVisible(cbAirblow.isSelected() && cbAirblow.isVisible());
		coordBAirblowBottom.setManaged(coordBAirblowBottom.isVisible());
		coordBAirblowTop.setVisible(cbAirblow.isSelected() && cbAirblow.isVisible());
		coordBAirblowTop.setManaged(coordBAirblowTop.isVisible());
		cbbClamping.setVisible(coordBAirblowBottom.isVisible());
		cbbClamping.setManaged(coordBAirblowBottom.isVisible());
		btnResetAirblow.setVisible(coordBAirblowTop.isVisible());
		btnResetAirblow.setManaged(coordBAirblowBottom.isVisible());
		if (cbAirblow.isSelected() && cbAirblow.isVisible()) {
			getPresenter().changedClamping(cbbClamping.getItems().get(0));
		}
	}

	@Override
	public void setTextFieldListener(final TextInputControlListener listener) {
		ntxtSmoothX.setFocusListener(listener);
		ntxtSmoothY.setFocusListener(listener);
		ntxtSmoothZ.setFocusListener(listener);
		coordBAirblowBottom.setTextFieldListener(listener);
		coordBAirblowTop.setTextFieldListener(listener);
	}

	@Override
	public void refresh() {
		hideNotification();
		refreshClampingBox();
		if (putStep.getRobotSettings().getSmoothPoint() != null) {
			ntxtSmoothX.setText("" + putStep.getRobotSettings().getSmoothPoint().getX());
			ntxtSmoothY.setText("" + putStep.getRobotSettings().getSmoothPoint().getY());
			ntxtSmoothZ.setText("" + putStep.getRobotSettings().getSmoothPoint().getZ());
		}
		if (deviceSettings.getDefaultClamping(putStep.getDeviceSettings().getWorkArea()) == null) {
			btnResetSmooth.setDisable(true);
		} else {
			btnResetSmooth.setDisable(false);
		}
		if (putStep.getRobotSettings().isRobotAirblow()) {
			cbAirblow.setSelected(true);
		} else {
			cbAirblow.setSelected(false);
		}
		btnBeforeClamp.getStyleClass().remove(AbstractFormView.CSS_CLASS_FORM_BUTTON_ACTIVE);
		btnAfterClamp.getStyleClass().remove(AbstractFormView.CSS_CLASS_FORM_BUTTON_ACTIVE);
		if (putStep.getRobotSettings().isReleaseBeforeMachine()) {
			btnBeforeClamp.getStyleClass().add(AbstractFormView.CSS_CLASS_FORM_BUTTON_ACTIVE);
		} else {
			btnAfterClamp.getStyleClass().add(AbstractFormView.CSS_CLASS_FORM_BUTTON_ACTIVE);
		}
		refreshCoordboxes();
		showTurnInMachine();
		showMachineAirblow();
		showAirblow();
		showClampingPressureLow();
		getPresenter().isConfigured();
	}
	
	public void refreshCoordboxes() {
		coordBAirblowBottom.reset();
		coordBAirblowTop.reset();
	}
	
	private void refreshClampingBox() {
		cbbClamping.getItems().clear();
		cbbClamping.getItems().addAll(getPresenter().getSelectedClampings());
		cbbClamping.setValue(null);
		cbbClamping.setDisable(false);
		if (cbbClamping.getItems().get(0) != null) {
			cbbClamping.setValue(cbbClamping.getItems().get(0));
		}
	}
	
	void setBottomCoord(Coordinates coord) {
		coordBAirblowBottom.setCoordinate(coord);
	}
	
	void setTopCoord(Coordinates coord) {
		coordBAirblowTop.setCoordinate(coord);
	}
}
