package es.cic.curso.grupo3.ejercicio027.frontend.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo3.ejercicio027.frontend.Controles;

public class UsuarioForm extends FormLayout{

	private static final long serialVersionUID = -7257683851885571770L;
	private Controles controles = new Controles();
	private Grid maestro = controles.crearGrid();
	private TextField nombre = controles.crearTextField("Nombre");
	private TextField apellidos = controles.crearTextField("Apellidos");
	private Button crear = controles.crearButton("crear");
	private Button modificar = controles.crearButton("Modificar");
	private Button borrar = controles.crearButton("Borrar");
	private Button cancelar = controles.crearButton("Cancelar");
	private ComboBox selectRol = controles.crearComboBox("Roles");
	private ComboBox select = controles.crearComboBox("Alta");
	
	public UsuarioForm(){
		maestro.setCaption("Usuarios");
		maestro.setColumns("nombre", "apellidos","rol", "alta");
		VerticalLayout layout = controles.crearVerticalLayout();	
		HorizontalLayout capaCampos = controles.crearHorizontalLayout();
		capaCampos.addComponents(nombre, apellidos, selectRol, select);
		HorizontalLayout capaBotones = controles.crearHorizontalLayout();
		capaBotones.setSizeUndefined();
		capaBotones.setSpacing(true);
		capaBotones.addComponents(crear, modificar, borrar, cancelar);
		layout.addComponents(maestro, capaCampos, capaBotones);
		
		addComponents(layout);
	}

	public Grid getMaestro() {
		return maestro;
	}

	public Controles getControles() {
		return controles;
	}
	
	public TextField getNombre() {
		return nombre;
	}

	public TextField getApellidos() {
		return apellidos;
	}

	public ComboBox getSelect() {
		return select;
	}

	public ComboBox getSelectRol() {
		return selectRol;
	}

	public void setSelect(String valor){
		select.setValue(valor);
	}
	
	public Button getBorrar() {
		return borrar;
	}

	public Button getCancelar() {
		return cancelar;
	}
	
	public Button getCrear() {
		return crear;
	}

	public Button getModificar() {
		return modificar;
	}
}
