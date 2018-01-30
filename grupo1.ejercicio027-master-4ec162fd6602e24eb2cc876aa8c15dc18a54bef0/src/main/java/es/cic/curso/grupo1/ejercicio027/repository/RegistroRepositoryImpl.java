package es.cic.curso.grupo1.ejercicio027.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo1.ejercicio027.dominio.Registro;

@Repository
@Transactional
public class RegistroRepositoryImpl extends AbstractRepositoryImpl<Long, Registro>  implements RegistroRepository{

	@Override
	public Class<Registro> getClassDeT() {
		return Registro.class;
	}

	@Override
	public String getNombreTabla() {
		return "REGISTRO";
	}

}
