package es.cic.curso.grupo2.ejercicio027.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo2.ejercicio027.servicio.ServicioPlantilla;

public class VentanaBorradoPlantilla extends Window {
	
	public VentanaBorradoPlantilla(VistaPlantilla vistaPlantilla, Long id, ServicioPlantilla plantillaService, 
			Grid gridCampos, Grid gridPlantillas){
		center();
        
        setHeight("180px");
        setWidth("500px");

        // Disable the close button
        setClosable(false);
        
        setModal(true);

        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);
        horizontalLayout.setMargin(true);
        horizontalLayout.setSpacing(true);
        
        Label label = new Label("¿Estas seguro de que quieres borrar la plantilla?");
        
        Button aceptar = new Button("Aceptar");
        aceptar.setIcon(FontAwesome.CHECK);
        Button cancelar = new Button("Cancelar", e -> 
        {
        	close();
        });
        cancelar.setIcon(FontAwesome.CLOSE);
        
        aceptar.addClickListener(e -> {
        	plantillaService.borraPlantilla(id);
        	vistaPlantilla.actualizarDatos(gridCampos, gridPlantillas);
        	close();
        });
        
        horizontalLayout.addComponents(aceptar, cancelar);
        verticalLayout.addComponents(label, horizontalLayout);
        
        setContent(verticalLayout);
	}
}
