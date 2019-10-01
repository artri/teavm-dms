package org.fastgrow.teadms.client.view;

import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.Popup;
import org.teavm.flavour.widgets.PopupContent;
import org.teavm.flavour.widgets.PopupDelegate;

@BindTemplate("templates/exception-view.html")
public class ExceptionView implements PopupContent {
	private Throwable error;
	private PopupDelegate delegate;
	
	public ExceptionView(Throwable error) {
		this.error = error;
	}

	public Throwable getError() {
		return error;
	}

	public void close() {
		delegate.close();
	}

	@Override
	public void setDelegate(PopupDelegate delegate) {
		this.delegate = delegate;
	}

	public static void showModal(Throwable error) {
		Popup.showModal(new ExceptionView(error));
	}
}
