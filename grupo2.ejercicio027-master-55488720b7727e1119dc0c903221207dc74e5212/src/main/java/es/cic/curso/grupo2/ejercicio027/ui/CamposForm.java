package es.cic.curso.grupo2.ejercicio027.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;

public class CamposForm extends FormLayout{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6778748489444665744L;

	@PropertyId("nombreCampo")
	protected TextField nombreCampo;
	protected TextField tipoCampo;
	
	private VerticalLayout padre;

	private Campo campos;
	
	protected Button borrar;
	
	public CamposForm(VistaPlantilla padre){
		this.padre=padre;
		
		final HorizontalLayout h1 = new HorizontalLayout();
		h1.setSpacing(true);
		
		nombreCampo=new TextField("Introduce el nombre del campo ");
		tipoCampo = new TextField("Introduce el tipo de campo: ");
		nombreCampo.setInputPrompt("Nombre");
		
		borrar = new Button("Borrar campo");
		
		h1.addComponents(nombreCampo, borrar);
		addComponent(h1);
		
		setCampos(null);
		
	}
	
	public void setCampos(Campo campos){
		this.setVisible(campos !=null);
		this.campos=campos;
		
		if(campos !=null){
			BeanFieldGroup.bindFieldsBuffered(campos, this);
		}else{
			BeanFieldGroup.bindFieldsBuffered(new Campo(), this);
		}
	}
	
	public void mostrarBorrar(){
		borrar.setVisible(true);
	}
	
	public void noMostrarBorrar(){
		borrar.setVisible(false);
	}
	
}
