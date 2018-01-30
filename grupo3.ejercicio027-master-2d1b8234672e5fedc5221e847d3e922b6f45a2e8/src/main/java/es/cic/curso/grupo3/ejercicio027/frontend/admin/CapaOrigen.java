package es.cic.curso.grupo3.ejercicio027.frontend.admin;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo3.ejercicio027.domain.Origen;
import es.cic.curso.grupo3.ejercicio027.frontend.Controles;
import es.cic.curso.grupo3.ejercicio027.service.OrigenService;

public class CapaOrigen extends VerticalLayout{

	private static final long serialVersionUID = 3773706688402882045L;
	private Controles controles = new Controles();
	private OrigenForm origenForm = new OrigenForm();
	private Grid maestro = origenForm.getMaestro();
	private TextField nombre = origenForm.getNombre();
	private ComboBox select = origenForm.getSelect();
	private Button crear = origenForm.getCrear();
	private Button modificar = origenForm.getModificar();
	private Button borrar = origenForm.getBorrar();
	private Button cancelar = origenForm.getCancelar();
	private OrigenService origenService;
	private Origen origen;
	private Long origenId;
	
	public CapaOrigen() {
		super();
		origenService = ContextLoader.getCurrentWebApplicationContext().getBean(OrigenService.class);
	}
	
	public VerticalLayout creaCapa(){
		ocultaControles();
		cargaSelect();
		VerticalLayout layout = controles.crearVerticalLayout();
		cargaOrigenes();
		
		maestro.addSelectionListener(e -> {
			origen = null;
			if(!e.getSelected().isEmpty()){
				origen = (Origen) e.getSelected().iterator().next();
				maestro();
			}
		});
		crear.addClickListener(e -> crear());
		modificar.addClickListener(e -> modificar());
		borrar.addClickListener(e -> borrar());
		cancelar.addClickListener(e -> ocultaControles());
		
		layout.addComponents(origenForm);
		return layout;
	}
	
	private void maestro(){
		origenId = origen.getId();
		nombre.setValue(origen.getNombre());
		select.setValue(Boolean.toString(origen.isAlta()));
		muestraControles();
	}
	
	private void borrar(){
		origen = origenService.obtenerOrigen(origenId);
		if(origenService.consultaEventosDependientes(origen) > 0){
			origen.setAlta(false);
			Notification.show("Este origen tiene eventos relacionados,"
					+ " se ha procedido a su desactivación, lo podra dar de alta desde el panel de administración");
		}else{
			origenService.eliminarOrigen(origen.getId());
		}
		cargaOrigenes();
		ocultaControles();
	}
	
	private void modificar(){
		origen = origenService.obtenerOrigen(origenId);
		String nombreParaObj = nombre.getValue().toString();
		boolean altaParaObj = Boolean.parseBoolean(select.getValue().toString());
		origen.setNombre(nombreParaObj);
		origen.setAlta(altaParaObj);
		origenService.actualizarOrigen(origen);
		nombre.setValue("");
		cargaOrigenes();
		ocultaControles();
	}
	
	private void crear(){
		String nombreParaObj;
		String altaParaObj = "";
		if("crear".equals(crear.getCaption())) {
			crear.setCaption("aceptar");
			controlesParaCrear();
		}else{
			nombreParaObj = nombre.getValue().toString();
			insetarOrigenConComprobacion(nombreParaObj, altaParaObj);
		}
	}
	
	private void  insetarOrigenConComprobacion(String nombreParaObj, String altaParaObj){
		if(select.getValue() != null){
			altaParaObj = select.getValue().toString();
		}
		if("".equals(nombreParaObj) || "".equals(altaParaObj)){
			Notification.show("Debes de rellenar todos los campos");
		}else{
			insertarOrigen(nombreParaObj, altaParaObj);
			crear.setCaption("crear");
		}
	}
	
	private void insertarOrigen(String nombreParaObj, String altaParaObj){
		if(!origenExiste(nombreParaObj)){
			origenService.agregarOrigen(nombreParaObj, Boolean.parseBoolean(altaParaObj));
			nombre.setValue("");
			ocultaControles();
			cargaOrigenes();
		}else{
			Notification.show("Ya hay un origen con ese nombre");
		}
	}
	
	private void cargaOrigenes(){
		maestro.setContainerDataSource(
    			new BeanItemContainer<>(Origen.class, origenService.obtenerListaOrigenes())	
    		);
		maestro.setHeightByRows(maestro.getContainerDataSource().size());
		maestro.setHeightMode(HeightMode.ROW);
	}
	
	private void cargaSelect(){
		select.addItem("true");
		select.addItem("false");
	}
	
	private boolean origenExiste(String nombre){
		List<Origen>origenes = origenService.obtenerListaOrigenes();
		for(Origen origenLista : origenes){
			if(nombre.equals(origenLista.getNombre())){
				return true;
			}
		}
		return false;
	}
	
	private void muestraControles(){
		nombre.setVisible(true);
		select.setVisible(true);
		crear.setEnabled(false);
		modificar.setEnabled(true);
		borrar.setEnabled(true);
		cancelar.setEnabled(true);
	}
	
	private void ocultaControles(){
		nombre.setVisible(false);
		select.setVisible(false);
		crear.setEnabled(true);
		modificar.setEnabled(false);
		borrar.setEnabled(false);
		cancelar.setEnabled(false);
	}
	
	private void controlesParaCrear(){
		nombre.setVisible(true);
		select.setVisible(true);
		nombre.setValue("");
		modificar.setEnabled(false);
		borrar.setEnabled(false);
		cancelar.setEnabled(true);
	}
}