package es.cic.curso.grupo1.ejercicio027.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.dominio.Tarea;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableNumero;
import es.cic.curso.grupo1.ejercicio027.dominio.VariableTexto;
import es.cic.curso.grupo1.ejercicio027.repository.EjecucionRepository;
import es.cic.curso.grupo1.ejercicio027.repository.TareaRepository;

@Repository
public class TestHelper {
	
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private TareaRepository tareaRepo;
	@Autowired
	private EjecucionRepository ejeRepo;
	
	public Long generaTarea() {
		Tarea t = new Tarea();
		t.setCodigo("Calc01");
		t.setDescripcion("Calculadora simple");
		
		em.persist(t);
		return t.getId();
	}
	
	public Long generaVariableTexto(){
		VariableTexto v = new VariableTexto();
		v.setTarea(tareaRepo.read(generaTarea()));
		v.setNombreVarTex("Texto 1");
		v.setValorVarTex("valor 1");
		
		em.persist(v);
		return v.getId();
	}
	
	public Long generaVariableNumero(){
		VariableNumero v = new VariableNumero();
		v.setTarea(tareaRepo.read(generaTarea()));
		v.setNombreVarNum("Numero 1");
		v.setValorVarNum(1);
		
		em.persist(v);
		return v.getId();
	}
	
	public Long generaEjecucion(){
		Ejecucion e = new Ejecucion();
		e.setTarea(tareaRepo.read(generaTarea()));
		e.setFecha("02/02/2017");
		
		em.persist(e);
		return e.getId();
	}
	
	public Long generaRegistro(){
		Registro r = new Registro();
		r.setEjecucion(ejeRepo.read(generaEjecucion()));
		r.setCampo("Campo");
		r.setValor("valor");
		
		em.persist(r);
		return r.getId();
	}
}