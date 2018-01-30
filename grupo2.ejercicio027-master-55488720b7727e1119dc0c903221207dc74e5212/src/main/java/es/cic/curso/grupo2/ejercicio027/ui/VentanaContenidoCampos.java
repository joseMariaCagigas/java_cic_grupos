package es.cic.curso.grupo2.ejercicio027.ui;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import es.cic.curso.grupo2.ejercicio027.modelo.Campo;

public class VentanaContenidoCampos extends Window {
	
	private static final String NUMERICO = "Numérico";
	
	String totalNombres = "";
	String mostrarEnNotificacion = "";
	
	 public VentanaContenidoCampos(List<Campo> listaCampos) {
		 super("Añade el valor a los campos"); 
		 center();
	        
		 setHeight("600px");
		 setWidth("600px");

		 setClosable(false);
		 
		 setModal(true);
	        
		 ArrayList<String> campos = new ArrayList<>();
		 ArrayList<String> tipos = new ArrayList<>();
		 ArrayList<String> nombres = new ArrayList<>();
		 ArrayList<TextField> totalCampos = new ArrayList<>();
	        
		 VerticalLayout verticalLayout = new VerticalLayout();
		 HorizontalLayout toolbar = new HorizontalLayout();
	        
		 toolbar.setMargin(true);
		 toolbar.setSpacing(true);
	        
		 verticalLayout.setMargin(true);
		 verticalLayout.setSpacing(true);

		 int size = listaCampos.size();
	        
		 TextField campoRellenar;
	         
		 for(int i = 0; i < size; i++){
			 campoRellenar = new TextField("Campo " + listaCampos.get(i).getNombreCampo() +
	        			"               de tipo " + listaCampos.get(i).getTipoCampo());
			 totalCampos.add(campoRellenar);
	        	
			 campos.add(listaCampos.get(i).getNombreCampo());
			 tipos.add(listaCampos.get(i).getTipoCampo());
	        	
			 verticalLayout.addComponent(campoRellenar);
			 verticalLayout.setComponentAlignment(campoRellenar, Alignment.TOP_CENTER);
		 }
	        
		 Notification aviso = new Notification("Campos: "+campos+" añadidos correctamente" +
				 " con los valores: "+nombres+"." + " Total nombres: " + totalNombres
				 );
		 aviso.setDelayMsec(5000);
	        
		 Button aceptar = new Button("Aceptar");
		 aceptar.setWidth("230px");
		 aceptar.setIcon(FontAwesome.CHECK);
		 Button cancelar = new Button("Cancelar", event -> close());
		 cancelar.setIcon(FontAwesome.CLOSE);
		 cancelar.setWidth("230px");
	             
		 aceptar.addClickListener(e->{   
			 String nombreCampoString = "";
			 String tipoCampoString = "";
			 mostrarEnNotificacion = "";
			 mostrarEnNotificacion = "";
			 boolean confirma = true;
			 
			 for (int i= 0; i<size;i++){
				 
				 nombreCampoString = totalCampos.get(i).getValue().trim();
				 tipoCampoString = listaCampos.get(i).getTipoCampo();
				 
				 if(comprobarCampos(tipoCampoString, nombreCampoString)){
					 nombres.add(totalCampos.get(i).getValue());
					 totalNombres = totalNombres + totalCampos.get(i).getValue();
					 mostrarEnNotificacion += "Campo " + listaCampos.get(i).getNombreCampo() + " . Valor: " + 
							 totalCampos.get(i).getValue() + "\n";
					 if(i != (size - 1)){
						 totalNombres += ";";
					 }	
				 } else {
					 confirma = false;
					 break;
				 }		 
			 }
	        	
			 if(!confirma){
				 Notification.show("Introduzca todos los campos correctamente.");
			 } else {
				 Notification.show(mostrarEnNotificacion);
				 close();
			 } 	
		 });
	        
		 toolbar.addComponents(cancelar,aceptar);
		 toolbar.setComponentAlignment(aceptar, Alignment.BOTTOM_RIGHT);
		 toolbar.setComponentAlignment(cancelar, Alignment.BOTTOM_LEFT);
	        
		 verticalLayout.addComponent(toolbar);
		 verticalLayout.setComponentAlignment(toolbar, Alignment.BOTTOM_CENTER);

		 setContent(verticalLayout);
	 }
	 
	 private boolean comprobarCampos(String tipoCampo, String nombreCampoString){
		 boolean comprueba = true;
		 if(nombreCampoString.isEmpty()){
			 comprueba = false;
		 } else if (tipoCampo.equals(NUMERICO)) {
			 try{
				 Double.parseDouble(nombreCampoString);
			 }catch(Exception e){
				 comprueba = false;
			 }
		 } 
		 
		 return comprueba;
	 }
}