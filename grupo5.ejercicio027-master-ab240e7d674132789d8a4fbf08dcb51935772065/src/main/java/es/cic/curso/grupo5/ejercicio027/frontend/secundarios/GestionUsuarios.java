package es.cic.curso.grupo5.ejercicio027.frontend.secundarios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import es.cic.curso.grupo5.ejercicio027.backend.dominio.Usuario;
import es.cic.curso.grupo5.ejercicio027.backend.service.UsuarioService;
import es.cic.curso.grupo5.ejercicio027.frontend.principal.MyUI;

public class GestionUsuarios extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897900102340873208L;
	private UsuarioService usuarioService;
	private List<Usuario> listaUsuarios;
	private UsuarioForm detalleUsuario;
	private NativeButton aniadirUsuario;
	private NativeButton modificar;
	private Grid gridUsuarios;
	@SuppressWarnings("unused")
	private MyUI padre;
	private ComboBox usuarios=new ComboBox();
	List<String> listaNombres = new ArrayList<>();
	private NativeButton cancelar;
	private ComboBox mostrar;

	public GestionUsuarios(MyUI padre){
		this.padre = padre;

		usuarioService = ContextLoader.getCurrentWebApplicationContext().getBean(UsuarioService.class);
		
		listaUsuarios = usuarioService.listarUsuario();
		
		final VerticalLayout  vertical = new VerticalLayout();
		vertical.setSpacing(true);
		final VerticalLayout  extra = new VerticalLayout();
		extra.setSpacing(true);

		if(listaUsuarios.isEmpty()){	
			usuarioService.generaBBDD();
		}

		aniadirUsuario = new NativeButton("Añadir Usuario");
		aniadirUsuario.setIcon(FontAwesome.PLUS);
		modificar = new NativeButton("modificar");
		modificar.setIcon(FontAwesome.PENCIL);		
		cancelar = new NativeButton("Cancelar");
		cancelar.setIcon(FontAwesome.REPLY);

		gridUsuarios = new Grid();
		gridUsuarios.setWidth(800, Unit.PIXELS);
		gridUsuarios.setHeight(540, Unit.PIXELS);
		gridUsuarios.setColumns("nombre","password","tipoRol","email","activo");
		gridUsuarios.setFrozenColumnCount(1);
		gridUsuarios.setSelectionMode(SelectionMode.NONE);

		detalleUsuario = new UsuarioForm(this);

		aniadirUsuario.addClickListener(e->{	
			aniadirUsuario.setVisible(false);
			modificar.setVisible(false);
			detalleUsuario.ocultaCheck();
			mostrar.setVisible(false);
			detalleUsuario.cambiarTextoAniadir();
			aniadirUsuarios();
		});
		
		List<String> listaModosMostrar = new ArrayList<>();
		listaModosMostrar.add("Mostrar todos");
		listaModosMostrar.add("mostrar habilitados");		
		listaModosMostrar.add("mostrar deshabilitados");		
		
		mostrar = new ComboBox("Visualizacion",listaModosMostrar);
		mostrar.setNullSelectionAllowed(false);
		mostrar.select(1);
		mostrar.setImmediate(true);
		mostrar.setWidth(300, Unit.PIXELS);
		mostrar.setInputPrompt("que usuarios quiere ver");
		
		
		mostrar.addValueChangeListener(e->{
			
			if(mostrar.getValue()=="Mostrar todos"){
				
				cargaGridUsuarios(null);	
			}
			else {
				
				if(mostrar.getValue()=="mostrar habilitados")
			
				cargarHabilitados();		
			
			else cargarDeshabilitados();
		
			}
		});
			
		
		modificar.addClickListener(e->{
			
			listaUsuarios = usuarioService.listarUsuario();
			listaNombres.clear();
			for(Usuario user :listaUsuarios){			
			 
				listaNombres.add(user.getNombre());
				 
			}
			usuarios = new ComboBox("Nombre",listaNombres);
			usuarios.setInputPrompt("Seleccione usuario a madificar");
			usuarios.setNullSelectionAllowed(false);
			usuarios.select(1);
			usuarios.setImmediate(true);
			usuarios.setWidth(300, Unit.PIXELS);

			mostrar.setVisible(false);
			aniadirUsuario.setVisible(false);
			modificar.setVisible(false);
			usuarios.setVisible(true);
			cancelar.setVisible(true);

			extra.addComponents(usuarios,cancelar);

			usuarios.addValueChangeListener(a->{


				for(Usuario user :listaUsuarios){
					if(usuarios.getValue()==(user.getNombre())){
						detalleUsuario.setVisible(true);
						detalleUsuario.muestraCheck();
						detalleUsuario.cambiarTextoModifi();
						detalleUsuario.setUsuario(user);
						detalleUsuario.rellenaRol(user);
						cancelar.setVisible(false);
					}					
				}		
			});

			cancelar.addClickListener(a->{
				
				mostrar.setVisible(true);
				usuarios.setVisible(false);
				cancelar.setVisible(false);
				aniadirUsuario.setVisible(true);
				modificar.setVisible(true);
							
			});
						
		});

		vertical.addComponents(aniadirUsuario,modificar,mostrar,extra,detalleUsuario);
		addComponents(gridUsuarios,vertical);

		cargaGridUsuarios(null);	
	}
	private void cargarDeshabilitados() {
		 
		modificar.setVisible(true);
		aniadirUsuario.setVisible(true);
		mostrar.setVisible(true);
		detalleUsuario.setVisible(false);
		usuarios.setVisible(false);
		listaUsuarios= usuarioService.listarUsuario();

		List<Usuario> listaUsuariosActivos = new ArrayList<>();
		for (Usuario usuariosActivo : listaUsuarios) {

			if(!(usuariosActivo.isActivo()))
				listaUsuariosActivos.add(usuariosActivo);
		}

		gridUsuarios.setContainerDataSource(
				new BeanItemContainer<>(Usuario.class, listaUsuariosActivos)
				);
		detalleUsuario.setUsuario(null);
			
	}
	private void cargarHabilitados() {
		modificar.setVisible(true);
		aniadirUsuario.setVisible(true);
		mostrar.setVisible(true);
		detalleUsuario.setVisible(false);
		usuarios.setVisible(false);
		listaUsuarios= usuarioService.listarUsuario();

		List<Usuario> listaUsuariosActivos = new ArrayList<>();
		for (Usuario usuariosActivo : listaUsuarios) {

			if((usuariosActivo.isActivo()))
				listaUsuariosActivos.add(usuariosActivo);
		}

		gridUsuarios.setContainerDataSource(
				new BeanItemContainer<>(Usuario.class, listaUsuariosActivos)
				);
		detalleUsuario.setUsuario(null);
		
	}

	private void aniadirUsuarios() {	
		detalleUsuario.setVisible(true);
		Usuario u = new Usuario("","", null,"", true);
		detalleUsuario.setUsuario(u);

	}

	public void cargaGridUsuarios(Usuario user) {
		modificar.setVisible(true);
		aniadirUsuario.setVisible(true);
		mostrar.setVisible(true);
		detalleUsuario.setVisible(false);
		usuarios.setVisible(false);
		mostrar.clear();

		if(user != null){
			usuarioService.modificarUsuario(user);
		}	

		listaUsuarios= usuarioService.listarUsuario();

		List<Usuario> listaUsuariosActivos = new ArrayList<>();
		for (Usuario usuariosActivo : listaUsuarios) {

			if((usuariosActivo.isActivo())||!(usuariosActivo.isActivo()))
				listaUsuariosActivos.add(usuariosActivo);
		}

		gridUsuarios.setContainerDataSource(
				new BeanItemContainer<>(Usuario.class, listaUsuariosActivos)
				);
		detalleUsuario.setUsuario(null);
	}
}