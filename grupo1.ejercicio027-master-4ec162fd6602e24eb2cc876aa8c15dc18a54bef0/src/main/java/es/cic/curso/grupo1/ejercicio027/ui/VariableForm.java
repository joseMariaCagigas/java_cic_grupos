package es.cic.curso.grupo1.ejercicio027.ui;

import java.util.ArrayList;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import es.cic.curso.grupo1.ejercicio027.dto.VariableDTO;


public class VariableForm extends FormLayout {
	@PropertyId("nombre")
	protected TextField nombre;
	
	@PropertyId("tipo")
	protected OptionGroup tipo;

	private Button accion;
	
	private Button cancelar;
	
	private VariableDTO prod;
	
	private LayoutVistaVariables padre;
	
	public VariableForm(LayoutVistaVariables padre) {
		this.padre = padre;
		nombre = new TextField("Nombre: ");
		nombre.setRequired(true);
		ArrayList<String> values = new ArrayList<>(2);
		values.add("Numero");
		values.add("Texto");                
		tipo = new OptionGroup("Tipo",values);
		cancelar = new Button("Cancelar");
		cancelar.addClickListener(e -> setVariable(null));
		accion = new Button("Añadir");
		accion.addClickListener(e -> {
			if (nombre.getValue().equals("")){
				Notification.show("Nombre necesario");
			} else {
				padre.anadeVariable(prod);
				padre.cargaGrid();
				nombre.clear();
				setVariable(null);
			}

		});
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponents(accion,cancelar);
		addComponents(nombre,tipo, hl);
		

		setVariable(null);
	}

	public void setVariable(VariableDTO prod) {
		this.setVisible(prod != null);
		this.prod = prod;

		if (prod != null) {
			BeanFieldGroup.bindFieldsUnbuffered(prod, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new VariableDTO(), this);
		}
	}
}

