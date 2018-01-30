package es.cic.curso.grupo1.ejercicio027.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dto.ConverterEjecucion;
import es.cic.curso.grupo1.ejercicio027.dto.EjecucionDTO;
import es.cic.curso.grupo1.ejercicio027.dto.VariableDTO;
import es.cic.curso.grupo1.ejercicio027.service.EjecucionService;

public class VentanaEjecuciones extends Window {
	
	private Grid grid;
	private Tarea tarea;
	private List<EjecucionDTO> ejecusion;
	private ConverterEjecucion converter;
	private EjecucionService serv;
	
	public VentanaEjecuciones(Tarea tarea){
		super("Ejecuciones de la tarea "+tarea.getCodigo()); // Set window caption
		
		this.tarea=tarea;
		ejecusion = new ArrayList<>();
		converter = new ConverterEjecucion();
		serv = ContextLoader.getCurrentWebApplicationContext().getBean(EjecucionService.class);
        VerticalLayout v = new VerticalLayout(); 
        v.setMargin(true);
        
        grid = new Grid();
		grid.setColumns("ejecucion","fecha","campo","valor");
		grid.setSizeFull();
		cargaGrid();
		v.addComponent(grid);
		setWidth("800px");
		setHeight("500px");
		center();
		setModal(true);
        setContent(v);
		
		
		
	}
	
	public void generaChicha(){
		List<Ejecucion> lista = serv.listaEjecucion();
		List<Ejecucion> excluidas = new ArrayList<>();
		List<Registro> regs = serv.listaRegistros();

		
		for(Ejecucion eje : lista){
			if(!(eje.getTarea().getId() == tarea.getId()))
				excluidas.add(eje);
		}
		lista.removeAll(excluidas);
		for(Ejecucion ejec : lista){
			regs = serv.listaRegistros();
			List<Registro> regEx = new ArrayList<>();
			for(Registro r : regs){
				if(!(r.getEjecucion().getId()==ejec.getId()))
					regEx.add(r);
			}
			regs.removeAll(regEx);
			ejecusion.addAll(converter.convertDTO(ejec,regs));
		}
	}
	
	public void cargaGrid() {
		generaChicha();
		grid.setContainerDataSource(
				new BeanItemContainer<>(EjecucionDTO.class, ejecusion)
				);
	}

}
