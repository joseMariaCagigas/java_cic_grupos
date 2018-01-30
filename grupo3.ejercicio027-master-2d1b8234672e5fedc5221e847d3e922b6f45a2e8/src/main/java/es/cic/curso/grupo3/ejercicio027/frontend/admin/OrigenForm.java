package es.cic.curso.grupo3.ejercicio027.frontend.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo3.ejercicio027.frontend.Controles;

public class OrigenForm  extends FormLayout{

	private static final long serialVersionUID = 6351534801238551652L;
	private Controles controles = new Controles();
	private Grid maestro = controles.crearGrid();
	private TextField nombre = controles.crearTextField("Nombre");
	private Button crear = controles.crearButton("crear");
	private Button modificar = controles.crearButton("Modificar");
	private Button borrar = controles.crearButton("Borrar");
	private Button cancelar = controles.crearButton("Cancelar");
	private ComboBox select = controles.crearComboBox("Alta");
	
	public OrigenForm(){
		maestro.setCaption("Orígenes");
		maestro.setColumns("nombre", "alta");
		VerticalLayout layout = controles.crearVerticalLayout();	
		HorizontalLayout capaCampos = controles.crearHorizontalLayout();
		capaCampos.addComponents(nombre, select);
		HorizontalLayout capaBotones = controles.crearHorizontalLayout();
		capaBotones.setSizeUndefined();
		capaBotones.setSpacing(true);
		capaBotones.addComponents(crear, modificar, borrar, cancelar);
		layout.addComponents(maestro, capaCampos, capaBotones);
		
		addComponents(layout);
	}

	public Controles getControles() {
		return controles;
	}

	public Grid getMaestro() {
		return maestro;
	}

	public TextField getNombre() {
		return nombre;
	}

	public ComboBox getSelect() {
		return select;
	}

	public void setSelect(String valor){
		select.setValue(valor);
	}
	
	public Button getCrear() {
		return crear;
	}

	public Button getModificar() {
		return modificar;
	}

	public Button getBorrar() {
		return borrar;
	}

	public Button getCancelar() {
		return cancelar;
	}
}
