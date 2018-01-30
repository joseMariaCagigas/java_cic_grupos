package es.cic.curso.grupo3.ejercicio027.frontend;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

public class Controles {
	
	public VerticalLayout crearVerticalLayout() {
		return new VerticalLayout();
	}
	
	public HorizontalLayout crearHorizontalLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setMargin(true);
		horizontalLayout.setHeight(80, Unit.PIXELS);
		return horizontalLayout;
	}
	
	public HorizontalLayout crearHorizontalLayoutSinEstilo() { 
		return new HorizontalLayout();
	}
	
	public Panel crearPanel() {
		return new Panel();
	}
	
	public HorizontalSplitPanel crearHorizontalSplitPanel() {
		return new HorizontalSplitPanel();
	}
	
	public HorizontalLayout crearCabecera(Label label, String styleName) {
		HorizontalLayout horizontalLayout = crearHorizontalLayout();
		horizontalLayout.setStyleName(styleName);
		horizontalLayout.addComponent(label);
		return horizontalLayout;
	}
	
	public Grid crearGrid() {
		Grid grid = new Grid();
		grid.setSelectionMode(SelectionMode.SINGLE);
		return grid;
	}
	
	public Label crearLabel() {
		return new Label();
	}
	
	public Label crearLabel(String texto) {
		return new Label(texto);
	}
	
	public Label crearLabel(String texto, String value) {
		Label label = new Label();
		label.setCaption(texto);
		label.setValue(value);
		return label;
	}
	
	public Button crearButton(String texto) {
		return new Button(texto);
	}
	
	public TextField crearTextField(String caption) {
		TextField textField = new TextField();
		textField.setCaption(caption);
		return textField;
	}
	
	public TextField crearTextField() {
		return new TextField();
	}

	public NativeSelect crearNativeSelect() {
		NativeSelect nativeSelect = new NativeSelect();
		nativeSelect.setNewItemsAllowed(false);
		nativeSelect.setImmediate(true);
		return nativeSelect;
	}
	
	
	//===============NUEVOS CONTROLES lUIS=======================================
	
	public ComboBox crearComboBox(String nombre) {
		ComboBox comboBox = new ComboBox(nombre);
		comboBox.setNullSelectionAllowed(false);
		comboBox.setImmediate(true);
		comboBox.setValue(null);
		return comboBox;
	}
	
	public Notification crearNotificacion(String textoNotificacion) {
		Notification notificacion = new Notification(textoNotificacion);
		notificacion.setDelayMsec(4000);
		return notificacion;
	}
	
	public FormLayout crearFormLayout() {
		return new FormLayout();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//===============NUEVOS CONTROLES PABLO=======================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
