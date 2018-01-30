package es.cic.curso.grupo2.ejercicio027.ui;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;
import es.cic.curso.grupo2.ejercicio027.servicio.ServicioCampo;

public class VentanaBorradoCampo extends Window {
	public VentanaBorradoCampo(List<Campo> listaCampos, Campo campo, Grid gridCampos, 
			ServicioCampo campoService, Button ejecutar, VistaPlantilla vistaPlantilla){
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
        
        Label label = new Label("¿Estas seguro de que quieres borrar el campo?");
        
        Button aceptar = new Button("Aceptar");
        aceptar.setIcon(FontAwesome.CHECK);
        Button cancelar = new Button("Cancelar", e -> 
        {
        	close();
        });
        cancelar.setIcon(FontAwesome.CLOSE);
        
        aceptar.addClickListener(e -> {
        	listaCampos.remove(campo);
			gridCampos.setContainerDataSource(new BeanItemContainer<>(Campo.class, listaCampos));
			Long campoId = campo.getId();
			campoService.borrarCampo(campoId);
			
			if(!listaCampos.isEmpty()){
				ejecutar.setVisible(true);
			}else{
				ejecutar.setVisible(false);
			}
        	close();
        });
        
        horizontalLayout.addComponents(aceptar, cancelar);
        verticalLayout.addComponents(label, horizontalLayout);
        
        setContent(verticalLayout);
	}
}
