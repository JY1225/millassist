package eu.robojob.millassist.ui.controls.keyboard;

import eu.robojob.millassist.util.SizeManager;
import javafx.scene.input.KeyCode;

public class NumericKeyboardView extends AbstractKeyboardView {
	
	public NumericKeyboardView() {
		buildView();
	}
	
	@Override
	public double getPreferedWidth() {
		return SizeManager.WIDTH_BOTTOM_LEFT;
	}
	
	@Override
	protected void buildKeyboard() {
		this.setCache(true);
		int row = 0;
		
		int column = 0;
		addKey("Esc", KeyCode.ESCAPE, column++, row, 1, 1, "key-escape", null);
		addKey("Clr", KeyCode.DELETE, column++, row, 1, 1, "key-Clr", null);
		addKey("\u2190", KeyCode.BACK_SPACE, column++, row, 1, 1, "key-back-space", null);
		
		column = 0;
		row++;
		addKey("1", KeyCode.DIGIT1, column++, row, 1, 1, "key-1", null);
		addKey("2", KeyCode.DIGIT2, column++, row, 1, 1, "key-2", null);
		addKey("3", KeyCode.DIGIT3, column++, row, 1, 1, "key-3", null);
		
		column = 0;
		row++;
		addKey("4", KeyCode.DIGIT4, column++, row, 1, 1, "key-4", null);
		addKey("5", KeyCode.DIGIT5, column++, row, 1, 1, "key-5", null);
		addKey("6", KeyCode.DIGIT6, column++, row, 1, 1, "key-6", null);
		
		column = 0;
		row++;
		addKey("7", KeyCode.DIGIT7, column++, row, 1, 1, "key-7", null);
		addKey("8", KeyCode.DIGIT8, column++, row, 1, 1, "key-8", null);
		addKey("9", KeyCode.DIGIT9, column++, row, 1, 1, "key-9", null);
		
		column = 0;
		row++;
		addKey(".", KeyCode.DECIMAL, column++, row, 1, 1, "key-decimal", null);
		addKey("0", KeyCode.DIGIT0, column++, row, 1, 1, "key-0", null);
		addKey("OK", KeyCode.ENTER, column++, row, 1, 1, "key-OK", null);
	}

}
