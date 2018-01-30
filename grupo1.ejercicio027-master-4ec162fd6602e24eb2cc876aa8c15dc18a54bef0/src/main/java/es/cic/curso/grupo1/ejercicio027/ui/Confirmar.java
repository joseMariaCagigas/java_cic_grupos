package es.cic.curso.grupo1.ejercicio027.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dto.VariableDTO;

public class Confirmar extends Window {

	private Tarea tarea;
	private VariableDTO var;
	private VistaCrear padre;
	private LayoutVistaVariables padre2;
	
	public Confirmar() {
		super();
	}

	public Confirmar(VistaCrear padre, Tarea tarea) {		
        super("Borrar la tarea "+tarea.getCodigo()+"?");   
        
        HorizontalLayout v = new HorizontalLayout(); 
        this.tarea = tarea;
        this.padre = padre;
        
        Button borrar = new Button("Si");
		borrar.addClickListener( e -> {
        	padre.borrarTarea(tarea);
        	close();
        });
		
        Button cancelar = new Button("No");  
        cancelar.addClickListener( e ->	close()); 
        
        v.addComponents(borrar, cancelar);
        v.setMargin(true);
        v.setSpacing(true);
        center();
        setModal(true);
        setHeight("100px");
        setWidth("300px");
        setContent(v);
	}
	
	public Confirmar(LayoutVistaVariables padre2, VariableDTO var) {
        super("Borrar la variable "+var.getNombre()+"?");
        
        HorizontalLayout v = new HorizontalLayout(); 
        this.var = var;
        this.padre2 = padre2;
        
        Button borrar = new Button("Si");
		borrar.addClickListener( e -> {
        	padre2.borrarVariable(var);
        	close();
        });
		
        Button cancelar = new Button("No");  
        cancelar.addClickListener( e ->	close()); 
        
        v.addComponents(borrar, cancelar);
        v.setMargin(true);
        v.setSpacing(true);
        center();
        setModal(true);
        setHeight("100px");
        setWidth("300px");
        setContent(v);
	}
}
