package es.cic.curso.grupo2.ejercicio027.ui;
//package es.cic.curso.grupo2.ejercicio027.UI;
//
//import com.vaadin.data.fieldgroup.BeanFieldGroup;
//import com.vaadin.data.fieldgroup.PropertyId;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//
//import es.cic.curso.grupo2.ejercicio027.modelo.Plantilla;
//
//public class PlantillaForm extends FormLayout{
//
//	private static final long serialVersionUID = 1082175881117353848L;
//
//	@PropertyId("nombrePlantilla")
//	protected TextField nombrePlantilla;
//	
//	private Plantilla plantilla;
//	
//	private VerticalLayout padre;
//	
//	public PlantillaForm(VistaPlantilla padre){
//		this.padre=padre;
//		
//		final HorizontalLayout h1 = new HorizontalLayout();
//		h1.setSpacing(true);
//		
//		nombrePlantilla=new TextField("Nombre de la plantilla: ");
//		nombrePlantilla.setInputPrompt("Nombre");
//		
//		h1.addComponent(nombrePlantilla);
//		addComponent(h1);
//		
//		setPlantilla(null);
//		
//	}
//	
//	public void setPlantilla(Plantilla plantilla){
//		this.setVisible(plantilla !=null);
//		this.plantilla=plantilla;
//		
//		if(plantilla !=null){
//			BeanFieldGroup.bindFieldsBuffered(plantilla, this);
//		}else{
//			BeanFieldGroup.bindFieldsBuffered(new Plantilla(), this);
//		}
//	}
//	
//}
