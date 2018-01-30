package es.cic.curso.grupo1.ejercicio027.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Ejecucion;
import es.cic.curso.grupo1.ejercicio027.dominio.Registro;
import es.cic.curso.grupo1.ejercicio027.repository.EjecucionRepository;
import es.cic.curso.grupo1.ejercicio027.repository.RegistroRepository;

@Service
@Transactional
public class EjecucionServiceImpl implements EjecucionService {

	@Autowired
	private EjecucionRepository eRepo;
	@Autowired
	private RegistroRepository rRepo;

	@Override
	public Ejecucion addEjecucion(Ejecucion e){
		return eRepo.add(e);
	}

	@Override
	public Registro addRegistro(Registro r){
		return rRepo.add(r);
	}

	@Override
	public Ejecucion updateEjecucion(Ejecucion e){
		return eRepo.update(e);
	}

	@Override
	public Registro updateRegistro(Registro r){
		return rRepo.update(r);
	}

	@Override
	public List<Ejecucion> listaEjecucion(){
		return eRepo.list();
	}

	@Override
	public List<Registro> listaRegistros(){
		return rRepo.list();
	}

	@Override
	public void deleteEjecucion(Ejecucion e){
		eRepo.delete(e);
	}

	@Override
	public void deleteRegistro(Registro r){
		rRepo.delete(r);
	}

	@Override
	public Ejecucion buscaEjecucion(Ejecucion e){
		return eRepo.read(e.getId());
	}

	@Override
	public Ejecucion buscaEjecucion(Long id){
		return eRepo.read(id);
	}

	@Override
	public Registro buscaRegistro(Registro r){
		return rRepo.read(r.getId());
	}

	@Override
	public Registro buscaRegistro(Long id){
		return rRepo.read(id);
	}
}
