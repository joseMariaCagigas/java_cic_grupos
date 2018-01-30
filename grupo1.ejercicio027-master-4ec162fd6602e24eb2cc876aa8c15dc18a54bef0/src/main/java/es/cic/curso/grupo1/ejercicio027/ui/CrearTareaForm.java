package es.cic.curso.grupo1.ejercicio027.ui;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dto.VariableDTO;
import es.cic.curso.grupo1.ejercicio027.service.TareaService;

public class CrearTareaForm  extends FormLayout {

	private HorizontalLayout padre;

	
	private Button guardar;
	private Button cancelar;
	
	private TextField codigo = new TextField("Código de tarea");
	private TextField descripcion = new TextField("Descripción");
	
	private Tarea tarea;
	private TareaService tService;

	private Tarea prod;
	
	
	public CrearTareaForm(VistaCrear padre) {
		
		this.padre = padre;
		tService = ContextLoader.getCurrentWebApplicationContext().getBean(TareaService.class);
		
		VerticalLayout layoutV2 = new VerticalLayout();
		
		codigo.setInputPrompt("Introducir el código de la tarea");
		codigo.setNullSettingAllowed(false);
		codigo.setRequired(true);
		
		descripcion.setInputPrompt("Breve descripción");
		
		guardar = new Button("Guardar");
		guardar.addClickListener( e -> {

			if (codigo.getValue().isEmpty()) {
				Notification.show("Introducir código de tarea");
				guardar.setEnabled(true);
				return;
			}
			if (tService.verificarNombre(codigo.getValue()) && codigo.getValue() != ""){
				tarea = new Tarea();
				tarea.setCodigo(codigo.getValue());
				tarea.setDescripcion(descripcion.getValue());

				padre.addTarea(tarea);
				padre.seleccionar();
				setVariable(null);
			} else {
				guardar.setEnabled(true);
				Notification.show("Código de tarea ya utilizado. Selecciona otro");
			}
		});
		
		cancelar = new Button("Cancelar");
		cancelar.addClickListener( e -> setVariable(null));

		layoutV2.addComponents(codigo, descripcion, guardar, cancelar);
		layoutV2.setSpacing(true);
		addComponent(layoutV2);
		setMargin(true);
		
		setVariable(null);
	}
	
	public void setVariable(Tarea prod) {
		this.setVisible(prod != null);
		this.prod = prod;

		if (prod != null) {
			BeanFieldGroup.bindFieldsUnbuffered(prod, this);
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new VariableDTO(), this);
		}
	}
}

