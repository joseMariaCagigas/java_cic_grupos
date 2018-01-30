package es.cic.curso.grupo4.ejercicio027.UI;

import java.util.Date;
import org.springframework.web.context.ContextLoader;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import es.cic.curso.grupo4.ejercicio027.dominio.Conector;
import es.cic.curso.grupo4.ejercicio027.servicio.ConectorServicio;

public class HistoricoForm extends FormLayout
{
	private static final long serialVersionUID = -1709925243370171963L;

	private ConectorServicio conectorServicio;
	protected Label filtroConector;
	protected Label filtroTipo;
	protected ComboBox selectTipo;
	protected ComboBox selectConector;
	protected ComboBox selectCorrecta;
	protected DateField fechaInicio;
	protected DateField fechaFinal;
	private Button limpiar;
	protected Label filtroFechaInicio;
	protected Label filtroFechaFinal;
	protected Label filtroCorrecta;
	private Label labelLimpiar;
	private Label labelLimpiar2;

	public HistoricoForm(MyUI padre)
	{
		conectorServicio = ContextLoader.getCurrentWebApplicationContext().getBean(ConectorServicio.class);
		
		HorizontalLayout hLayout = new HorizontalLayout();
		VerticalLayout row0 = new VerticalLayout();
		VerticalLayout row1 = new VerticalLayout();
		VerticalLayout row2 = new VerticalLayout();
		VerticalLayout row3 = new VerticalLayout();
		VerticalLayout row4 = new VerticalLayout();
		
		labelLimpiar = new Label("");
		labelLimpiar2 = new Label("");
		
		filtroTipo = new Label("TIPO");
		
		selectTipo = new ComboBox("");
		selectTipo.setNullSelectionAllowed(true);
		selectTipo.setImmediate(true);
        selectTipo.addItem("Fichero");
        selectTipo.addItem("FTP");
        selectTipo.addItem("Rest");	
			
		filtroConector = new Label("CONECTOR");

		selectConector = new ComboBox("");
		selectConector.setNullSelectionAllowed(true);
		selectConector.setImmediate(true);

		for(Conector c : conectorServicio.listaConectores()){
			selectConector.addItem(c.getNombre());
		}
	    
		limpiar = new Button("Limpiar");
		limpiar.setIcon(FontAwesome.CLOSE);
		limpiar.addClickListener(e -> {	
			selectConector.setValue(null);
			selectTipo.setValue(null);
			selectCorrecta.setValue(null);
			fechaInicio.setValue(null);
			fechaFinal.setValue(null);
			padre.cargaGridHistorico();
		});
		limpiar.setVisible(true);
		VerticalLayout limpiarLayout = new VerticalLayout();
		
		filtroFechaInicio = new Label("DESDE");
        fechaInicio = new DateField();
        fechaInicio.setValue(new Date());
        fechaInicio.setValue(null);
        fechaInicio.setCaption("");
        fechaInicio.setDateFormat("dd/MM/yyyy");
        fechaInicio.setInvalidAllowed(true);
        fechaInicio.addValueChangeListener(e -> {
        	padre.cargaGridHistoricoFiltrado();
        });
        
		filtroFechaFinal = new Label("HASTA");
        fechaFinal = new DateField();
        fechaFinal.setValue(new Date());
        fechaFinal.setValue(null);
        fechaFinal.setCaption("");
        fechaFinal.setDateFormat("dd/MM/yyyy");
        fechaFinal.setInvalidAllowed(true);
        fechaFinal.addValueChangeListener(e -> {	
        	padre.cargaGridHistoricoFiltrado();
        });
        
        filtroCorrecta = new Label("EJECUCIÓN CORRECTA");
		selectCorrecta = new ComboBox("");
		selectCorrecta.setNullSelectionAllowed(true);
		selectCorrecta.setImmediate(true);
		selectCorrecta.addItem("SI");
		selectCorrecta.addItem("NO");
		selectCorrecta.addValueChangeListener(e -> {
			padre.cargaGridHistoricoFiltrado();
	    });
	    	
	    selectConector.addValueChangeListener(e -> {
	    	padre.cargaGridHistoricoFiltrado();
		});
	    
	    selectTipo.addValueChangeListener(e -> {
	    	selectConector.removeAllItems();
			for(Conector c : conectorServicio.listaConectores()){
				if(selectTipo.getValue()!=null){
					if((c.getTipo()).equals(selectTipo.getValue().toString())){
	    				selectConector.addItem(c.getNombre());
	    			}
				}else{
					selectConector.addItem(c.getNombre());
				}
			}
	    	padre.cargaGridHistoricoFiltrado();
		});
	    
	    row0.addComponents(filtroTipo, selectTipo);
		row1.addComponents(filtroConector,selectConector);
		row2.addComponents(filtroCorrecta,selectCorrecta);
		row3.addComponents(filtroFechaInicio,fechaInicio);
		row4.addComponents(filtroFechaFinal,fechaFinal);
		limpiarLayout.addComponents(labelLimpiar,labelLimpiar2,limpiar);
		row0.setMargin(true);
		row1.setMargin(true);
		row2.setMargin(true);
		row3.setMargin(true);
		row4.setMargin(true);
		limpiarLayout.setMargin(true);
		
		hLayout.addComponents(row0,row1,row2,row3,row4,limpiarLayout);
		addComponent(hLayout);
	}
}