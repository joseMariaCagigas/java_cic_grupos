package es.cic.curso.grupo3.ejercicio027.frontend.admin;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import es.cic.curso.grupo3.ejercicio027.frontend.Controles;

public class PestaniasAdmin extends VerticalLayout{

	private static final long serialVersionUID = -3074849845476649817L;
	private Controles controles = new Controles();
	private TabSheet pestanias = new TabSheet();
	private CapaUsuario capaUsuarioClass = new CapaUsuario();
	private CapaOrigen capaOrigenClass = new CapaOrigen();
	
	public VerticalLayout creaCapa(){
		VerticalLayout layout = controles.crearVerticalLayout();
		pestanias.setHeight(100.f, Unit.PERCENTAGE);
		pestanias.addStyleName(ValoTheme.TABSHEET_FRAMED);
		pestanias.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		
		VerticalLayout capaUsuario = capaUsuarioClass.creaCapa();		
		pestanias.addTab(capaUsuario, "Usuarios");
		
		VerticalLayout capaOrigen = capaOrigenClass.creaCapa();		
		pestanias.addTab(capaOrigen, "Orígenes");
		
		layout.addComponents(pestanias);
		return layout;
		
	}
	
	
	
	
}
