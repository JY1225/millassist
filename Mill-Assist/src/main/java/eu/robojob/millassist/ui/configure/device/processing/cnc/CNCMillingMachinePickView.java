package eu.robojob.millassist.ui.configure.device.processing.cnc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.robojob.millassist.external.device.DeviceSettings;
import eu.robojob.millassist.external.device.processing.cnc.AbstractCNCMachine;
import eu.robojob.millassist.process.PickStep;
import eu.robojob.millassist.ui.controls.NumericTextField;
import eu.robojob.millassist.ui.controls.TextInputControlListener;
import eu.robojob.millassist.ui.general.AbstractFormView;
import eu.robojob.millassist.util.Translator;
import eu.robojob.millassist.util.UIConstants;

public class CNCMillingMachinePickView extends AbstractFormView<CNCMillingMachinePickPresenter> {

	private PickStep pickStep;
	private DeviceSettings deviceSettings;
	private Label lblSmoothInfo;
	private HBox hBoxSmoothPoint;
	private Label lblSmoothX;
	private Label lblSmoothY;
	private Label lblSmoothZ;
	private Button btnResetSmooth;
	private NumericTextField ntxtSmoothX;
	private NumericTextField ntxtSmoothY;
	private NumericTextField ntxtSmoothZ;
	
	private CheckBox cbAirblow;
	private CheckBox cbTIM;
	
	private static final int HGAP = 15;
	private static final int VGAP = 15;
	private static final int MAX_INTEGER_LENGTH = 6;
	
	private static final String SMOOTH_PICK_INFO = "CNCMillingMachinePickView.smoothPickInfo";
	private static final String SMOOTH_X = "CNCMillingMachinePickView.smoothX";
	private static final String SMOOTH_Y = "CNCMillingMachinePickView.smoothY";
	private static final String SMOOTH_Z = "CNCMillingMachinePickView.smoothZ";
	private static final String RESET = "CNCMillingMachinePickView.reset";
	private static final String AIRBLOW = "CNCMillingMachinePickView.airblow";
	private static final String TIM = "CNCMillingMachinePickView.tim";
	
	private static Logger logger = LogManager.getLogger(CNCMillingMachinePickView.class.getName());
		
	public CNCMillingMachinePickView() {
		super();
		getContents().setHgap(HGAP);
		getContents().setVgap(VGAP);
	}
	
	@Override
	protected void build() {
		lblSmoothInfo = new Label(Translator.getTranslation(SMOOTH_PICK_INFO));
		
		lblSmoothX = new Label(Translator.getTranslation(SMOOTH_X));
		lblSmoothY = new Label(Translator.getTranslation(SMOOTH_Y));
		lblSmoothZ = new Label(Translator.getTranslation(SMOOTH_Z));
		
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
		Text txtBtnResetSmooth = new Text(Translator.getTranslation(RESET));
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
		
		hBoxSmoothPoint = new HBox();
		hBoxSmoothPoint.getChildren().addAll(lblSmoothX, ntxtSmoothX, lblSmoothY, ntxtSmoothY, lblSmoothZ, ntxtSmoothZ, btnResetSmooth);
		HBox.setMargin(ntxtSmoothX, new Insets(0, 20, 0, 10));
		HBox.setMargin(ntxtSmoothY, new Insets(0, 20, 0, 10));
		HBox.setMargin(ntxtSmoothZ, new Insets(0, 20, 0, 10));
		hBoxSmoothPoint.setFillHeight(false);
		hBoxSmoothPoint.setAlignment(Pos.CENTER_LEFT);
		
		cbAirblow = new CheckBox(Translator.getTranslation(AIRBLOW));
		cbAirblow.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
				getPresenter().changedAirblow(newValue);
			}
		});		
		cbTIM = new CheckBox(Translator.getTranslation(TIM));
		cbTIM.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean oldValue, final Boolean newValue) {
				if (getPresenter().getMenuPresenter() != null) 
					getPresenter().getMenuPresenter().changedTIM(newValue);
			}
		});
		
		int column = 0;
		int row = 0;
		
		column = 0;
		row = 0;
		getContents().add(lblSmoothInfo, column++, row);
		
		column = 0;
		row++;
		getContents().add(hBoxSmoothPoint, column++, row);
		
		column = 0;
		row++;
		getContents().add(cbAirblow, column++, row);
		
		column = 0;
		row++;
		getContents().add(cbTIM, column++, row);
				
		refresh();
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("settings.properties")));
			if ((properties.get("robot-airblow") != null) && (properties.get("robot-airblow").equals("false"))) {
				cbAirblow.setVisible(false);
				cbAirblow.setManaged(false);
			}
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	@Override
	public void setTextFieldListener(final TextInputControlListener listener) {
		ntxtSmoothX.setFocusListener(listener);
		ntxtSmoothY.setFocusListener(listener);
		ntxtSmoothZ.setFocusListener(listener);
	}
	
	public void setPickStep(final PickStep pickStep) {
		this.pickStep = pickStep;
	}
	
	public void setDeviceSettings(final DeviceSettings deviceSettings) {
		this.deviceSettings = deviceSettings;
	}
	
	public void showTurnInMachine() {
		cbTIM.setVisible(((AbstractCNCMachine)pickStep.getDevice()).getTIMAllowed());
		cbTIM.setSelected(pickStep.getRobotSettings().getTurnInMachine());
	}

	@Override
	public void refresh() {
		if (pickStep.getRobotSettings().getSmoothPoint() != null) {
			ntxtSmoothX.setText("" + pickStep.getRobotSettings().getSmoothPoint().getX());
			ntxtSmoothY.setText("" + pickStep.getRobotSettings().getSmoothPoint().getY());
			ntxtSmoothZ.setText("" + pickStep.getRobotSettings().getSmoothPoint().getZ());
		}
		if (deviceSettings.getClamping(pickStep.getDeviceSettings().getWorkArea()) == null) {
			btnResetSmooth.setDisable(true);
		} else {
			btnResetSmooth.setDisable(false);
		} 
		if (pickStep.getRobotSettings().isDoMachineAirblow()) {
			cbAirblow.setSelected(true);
		} else {
			cbAirblow.setSelected(false);
		}
		showTurnInMachine();
	}
	
	
}
