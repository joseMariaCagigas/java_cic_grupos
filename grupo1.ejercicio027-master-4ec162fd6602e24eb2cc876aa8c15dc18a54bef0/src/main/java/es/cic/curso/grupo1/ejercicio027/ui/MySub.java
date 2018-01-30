package es.cic.curso.grupo1.ejercicio027.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.exception.FormatoExcepcion;
import es.cic.curso.grupo1.ejercicio027.service.EjecucionService;
import es.cic.curso.grupo1.ejercicio027.service.VariableNumeroService;
import es.cic.curso.grupo1.ejercicio027.service.VariableTextoService;

public class MySub extends Window {
	
	private Tarea tarea;
	private VariableNumeroService numeroSv;
	private VariableTextoService textoSv;
	private EjecucionService ejecucionSv;
	private List<TextField> listaTFNumeros;
	private List<TextField> listaTFTexto;
	private String st;
    public MySub(Tarea tarea) {
        super("Ejecucion de la tarea "+tarea.getCodigo()); // Set window caption
        listaTFNumeros = new ArrayList<>();
        listaTFTexto = new ArrayList<>();
        VerticalLayout v = new VerticalLayout(); 
        v.setMargin(true);
        numeroSv = ContextLoader.getCurrentWebApplicationContext().getBean(VariableNumeroService.class);
		textoSv = ContextLoader.getCurrentWebApplicationContext().getBean(VariableTextoService.class);
		ejecucionSv = ContextLoader.getCurrentWebApplicationContext().getBean(EjecucionService.class);
		this.tarea=tarea;
        TextField aux;
        List<VariableNumero> listaN = numeroSv.getVarNums();
		List<VariableTexto> listaT = textoSv.getVarTex();
		for(VariableNumero vn : listaN){
			aux = new TextField(vn.getNombreVarNum()+" (Numero)");
			listaTFNumeros.add(aux);
			v.addComponent(aux);
		}
		for(VariableTexto vt : listaT){
			aux= new TextField(vt.getNombreVarTex()+" (Texto)");
			listaTFTexto.add(aux);
			v.addComponent(aux);
		}
		
        Button guardar = new Button("Guardar");
        guardar.addClickListener(e->{
        	try{
        		 st = creaNotificacion();
             	Notification.show(st);
             	close();
        	}catch(FormatoExcepcion ex){
    			Notification.show(ex.getMessage());
    			
    		}
        });
        v.setMargin(true);
        v.setSpacing(true);
        v.addComponents(guardar);
        center();
        setModal(true);
        setHeight("300px");
        setWidth("500px");
        setContent(v);
    }
		
    public String creaNotificacion() throws FormatoExcepcion{
    	String s = new String("Valores guardados: \n");
    	
    	
    	Ejecucion ejecucion = new Ejecucion();
    	LocalDateTime date = LocalDateTime.now();
    	ejecucion.setFecha(date.toString());
    	ejecucion.setTarea(tarea);
    	ejecucionSv.addEjecucion(ejecucion);
    	Registro registro;
    	
    	for(int i=0;i<listaTFNumeros.size();i++){
    		try{
    			Double.parseDouble(listaTFNumeros.get(i).getValue());
    			s += listaTFNumeros.get(i).getCaption() +": " +listaTFNumeros.get(i).getValue() + "\n";
    			registro = new Registro();
    			registro.setEjecucion(ejecucion);
    			registro.setCampo(listaTFNumeros.get(i).getCaption());
    			registro.setValor(listaTFNumeros.get(i).getValue());
    			ejecucionSv.addRegistro(registro);
    			s += registro.getCampo() +" guardado con exito \n";
    			}catch(Exception ex){
    				throw new FormatoExcepcion("Error en el campo: "+listaTFNumeros.get(i).getCaption());
    			}
    	
    	}
    	for(int i=0;i<listaTFTexto.size();i++){
    		s += listaTFTexto.get(i).getCaption() +": " +listaTFTexto.get(i).getValue() + "\n";
    		registro = new Registro();
			registro.setEjecucion(ejecucion);
			registro.setCampo(listaTFTexto.get(i).getCaption());
			registro.setValor(listaTFTexto.get(i).getValue());
			ejecucionSv.addRegistro(registro);
			s += registro.getCampo() +" guardado con exito \n";
    	}
    	return s;
    }
    
}
