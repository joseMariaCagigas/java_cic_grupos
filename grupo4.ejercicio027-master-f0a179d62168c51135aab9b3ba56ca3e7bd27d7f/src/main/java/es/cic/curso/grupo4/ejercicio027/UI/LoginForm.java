package es.cic.curso.grupo4.ejercicio027.UI;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class LoginForm extends FormLayout
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8960746799628267877L;
	private TextField usuario;
	private PasswordField pass;
	private Button confirmar;
	private Button cancelar;
	
	public LoginForm(MyUI padre)
	{	
		usuario = new TextField("Usuario");
		pass = new PasswordField("Contraseña");
		confirmar = new Button("Confirmar");
		confirmar.setIcon(FontAwesome.CHECK);
		cancelar = new Button("Cancelar");
		cancelar.setIcon(FontAwesome.CLOSE);
		
		mostrar();
		
		cancelar.addClickListener(e->{
			ocultar();
		});
		
		confirmar.addClickListener(e->{
				String user = usuario.getValue();
				String password = pass.getValue();
				if (comprobarSubir(user, password)){
					padre.ejecutaTareaFTP(padre.c, user, password);
				}else{
					padre.generarEjecucionIncorrecta(padre.c);
					Notification ejecucionFallida = new Notification("Alerta!","Creedenciales Incorrectas");
					ejecucionFallida.setDelayMsec(1000);
					ejecucionFallida.show(Page.getCurrent());
				}
				
				ocultar();
		});
		
		addComponents(usuario, pass, confirmar, cancelar);
	}
	
	public void ocultar(){
		usuario.setVisible(false);
		pass.setVisible(false);
		confirmar.setVisible(false);
		cancelar.setVisible(false);
	}
	
	public boolean comprobarSubir(String user, String pass){
		if (user.equals("anonymous") && pass.equals("")){
			return true;
		}
		return false;
	}
	
	public void mostrar(){
		usuario.setVisible(true);
		pass.setVisible(true);
		confirmar.setVisible(true);
		cancelar.setVisible(true);
	}
}
