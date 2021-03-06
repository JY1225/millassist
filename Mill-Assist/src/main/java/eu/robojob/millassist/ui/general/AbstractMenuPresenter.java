package eu.robojob.millassist.ui.general;

import eu.robojob.millassist.ui.controls.TextInputControlListener;

public abstract class AbstractMenuPresenter<T extends AbstractMenuView<?>> {

	private T view;
	
	public AbstractMenuPresenter(final T view) {
		this.view = view;
		setPresenter();
	}
	
	public abstract void setTextFieldListener(TextInputControlListener listener);
	
	protected abstract void setPresenter();
	
	public T getView() {
		return view;
	}
	
	public abstract void openFirst();
		
	public abstract void setParent(final ContentPresenter parent);
	
	public abstract void unregisterListeners();
}
