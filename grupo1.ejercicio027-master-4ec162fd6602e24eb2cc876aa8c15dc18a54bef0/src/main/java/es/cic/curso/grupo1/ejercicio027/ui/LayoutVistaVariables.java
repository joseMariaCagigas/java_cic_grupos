package es.cic.curso.grupo1.ejercicio027.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.dto.ConverterVariableNumImpl;
import es.cic.curso.grupo1.ejercicio027.dto.ConverterVariableTexImpl;
import es.cic.curso.grupo1.ejercicio027.dto.VariableDTO;
import es.cic.curso.grupo1.ejercicio027.service.TareaService;
import es.cic.curso.grupo1.ejercicio027.service.VariableNumeroService;
import es.cic.curso.grupo1.ejercicio027.service.VariableTextoService;

public class LayoutVistaVariables extends VerticalLayout{


	private List<VariableDTO> lista;
	private Grid grid;
	private ConverterVariableNumImpl numVar;
	private ConverterVariableTexImpl textVar;
	private VariableDTO varActual;
	private Button borrar;
	private Button anadir;
	private VariableGridForm detalle;
	private VariableForm detalleVar;
	private TareaService tareaSv;
	private Tarea tarea;
	private VariableNumeroService numeroSv;
	private VariableTextoService textoSv;

	public LayoutVistaVariables(){

		this(new Tarea());

	}
	public LayoutVistaVariables(Tarea tarea){
		this.tarea=tarea;
		tareaSv = ContextLoader.getCurrentWebApplicationContext().getBean(TareaService.class);
		numeroSv = ContextLoader.getCurrentWebApplicationContext().getBean(VariableNumeroService.class);
		textoSv = ContextLoader.getCurrentWebApplicationContext().getBean(VariableTextoService.class);
		varActual = new VariableDTO();
		numVar = new ConverterVariableNumImpl();
		textVar = new ConverterVariableTexImpl();
		setMargin(true);
		borrar = new Button("Borrar");
		borrar.addClickListener(e ->{
			
			Confirmar comp = new Confirmar(this, varActual);
			UI.getCurrent().addWindow(comp);
			
		});
		borrar.setVisible(false);
		anadir = new Button("Añadir");
		anadir.addClickListener(e -> aniadirGridVar());
		detalleVar = new VariableForm(this);

		VerticalLayout vertical1 = new VerticalLayout();
		VerticalLayout vertical2 = new VerticalLayout();
		lista= new ArrayList<>();
		Label titulo = new Label("Lista de variables correspondientes"
				+ " a la tarea "+tarea.getCodigo());

		grid = new Grid();
		grid.setColumns("nombre","tipo");
		cargaGrid();
		grid.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				VariableDTO filaSeleccionada =  (VariableDTO) grid.getSelectedRow();
				if(filaSeleccionada!=null){
					varActual = filaSeleccionada;
					aniadirGrid();
					borrar.setVisible(true);
					detalle.setVisible(true);
					anadir.setVisible(false);
				}else{
					borrar.setVisible(false);
					detalle.setVisible(false);
					anadir.setVisible(true);
				}
			}
		});
		detalle = new VariableGridForm(this);


		vertical1.addComponents(titulo,grid);
		vertical2.setMargin(true);
		vertical2.addComponents(anadir,borrar,detalle,detalleVar);
		addComponents(vertical1, vertical2);
	}

	public void generaChicha(){
		try{

			tarea = tareaSv.add(tarea);
			}catch(Exception e){

				tarea = tareaSv.update(tarea);
			}
		lista.clear();
		List<VariableNumero> listaN = numeroSv.getVarNums();
		List<VariableTexto> listaT = textoSv.getVarTex();
		for(VariableNumero vn : listaN){
			VariableDTO e = new VariableDTO();
			e.setTarea(new Tarea());
			e.setNombre("");
			e.setTipo("");
			if(vn.getTarea().getId()==tarea.getId()){
				e = numVar.converToDTO(vn);
				lista.add(e);
			}
		}
		for(VariableTexto vt : listaT){
			VariableDTO e = new VariableDTO();
			e.setTarea(new Tarea());
			e.setNombre("");
			e.setTipo("");
			if(vt.getTarea().getId()==tarea.getId()){
				e = textVar.converToDTO(vt);
				lista.add(e);
			}
		}

	}

	public void cargaGrid() {
		generaChicha();
		grid.setContainerDataSource(
				new BeanItemContainer<>(VariableDTO.class, lista)
				);
	}

	public void anadeVariable(VariableDTO v){
		v.setTarea(tarea);
		if(v.getTipo().equalsIgnoreCase("Numero")){
			VariableNumero numero = numVar.converToENTITY(v);
			numeroSv.add(numero);
		}else{

			VariableTexto texto = textVar.converToENTITY(v);
			textoSv.add(texto);
		}
	}
	public void modificaVariable(VariableDTO v){
		if(v.getTipo().equalsIgnoreCase("Numero")){
			VariableNumero numero = numVar.converToENTITY(v);

			try{
				textoSv.borrarVarTex(varActual.getIdVariable());
				numero.setId(null);
				numeroSv.add(numero);
			}catch(Exception e){
				numeroSv.update(numero);
			}

		}else{
			VariableTexto texto = textVar.converToENTITY(v);

			try{
				numeroSv.borrarVarNum(varActual.getIdVariable());
				texto.setId(null);
				textoSv.add(texto);
			}catch(Exception e){
				textoSv.update(texto);
			}

		}

		cargaGrid();
	}
	public void aniadirGrid() {
		VariableDTO p = varActual;
		detalle.setVariable(p);
	}
	public void aniadirGridVar() {
		VariableDTO p = new VariableDTO() ;
		p.setNombre("");
		p.setTipo("Numero");
		detalleVar.setVariable(p);
	}
	
	public void borrarVariable(VariableDTO varActual) {
		if(varActual.getTipo().equalsIgnoreCase("numero"))
			numeroSv.borrarVarNum(varActual.getIdVariable());
		else
			textoSv.borrarVarTex(varActual.getIdVariable());
		detalleVar.setVariable(null);
		detalle.setVariable(null);
		cargaGrid();
	}
}
