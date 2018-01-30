package es.cic.curso.grupo1.ejercicio027.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;

public class ModificarTareaForm  extends FormLayout {

	private static final long serialVersionUID = -1609355107244227326L;
	
	private TextField codigo;
	private TextField descripcion;
	private Button modNom;
	private Button modDesc;
	private VistaCrear padre;
	private Tarea tarea;

    
    public ModificarTareaForm(VistaCrear padre) {
    	
    	this.padre = padre;
    	
    	codigo = new TextField("Introduce el nuevo código");
    	modNom = new Button("Modificar código");
		
    	descripcion = new TextField("Introduce la nueva descripción");
    	modDesc = new Button("Modificar descripción");
    	
    	codigo.setVisible(true);
    	modNom.setVisible(true);
    	
    	descripcion.setVisible(true);
    	modDesc.setVisible(true);
		
//		codigo.addTextChangeListener(e -> {
//			varCod = codigo.getValue();
//			if (padre.isDisponible(varCod)){
//				Notification.show("Nombre disponible.");
//				modNom.setVisible(true);
//			} else {
//				Notification.show("Código de tarea ya utilizado."
//						+ "Selecciona otro");
//				modNom.setVisible(false);
//			}
//		});
    	
		modNom.addClickListener(e -> {
			String varCod = codigo.getValue();
			if (padre.isDisponible(varCod) && varCod != ""){
				padre.modificarTareaNom(tarea, varCod);
				Notification.show("Código modificado.");
				padre.seleccionarTarea();
			} else {
				Notification.show("Código de tarea ya utilizado. Selecciona otro");
			}
        });

		modDesc.addClickListener(e -> {
			String varDes = descripcion.getValue();
			padre.modificarTareaDesc(tarea, varDes);
			Notification.show("Descripción modificada.");
			padre.seleccionarTarea();
		});

    	addComponents(codigo, modNom, descripcion, modDesc);
    }
    
	public void setTarea(Tarea tarea) {
		this.setVisible(tarea != null);
		this.tarea = tarea;

		if (tarea != null) {
			BeanFieldGroup.bindFieldsUnbuffered(tarea, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Tarea(), this);
		}
	}
}