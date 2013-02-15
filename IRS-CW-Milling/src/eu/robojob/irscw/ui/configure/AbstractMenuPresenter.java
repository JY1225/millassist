package eu.robojob.irscw.ui.configure;

import eu.robojob.irscw.ui.MainContentPresenter;
import eu.robojob.irscw.ui.general.AbstractMenuView;

public abstract class AbstractMenuPresenter<T extends AbstractMenuView<?>> extends eu.robojob.irscw.ui.general.AbstractMenuPresenter<T> {

	private ConfigurePresenter parent;

	public AbstractMenuPresenter(final T view) {
		super(view);
	}

	public ConfigurePresenter getParent() {
		return parent;
	}

	@Override
	public void setParent(final MainContentPresenter parent) {
		this.parent = (ConfigurePresenter) parent;
	}
	
}
