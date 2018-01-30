package es.cic.curso.grupo3.ejercicio027.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo3.ejercicio027.domain.Usuario;

@Repository
@Transactional
public class UsuarioRepositoryImpl extends AbstractRepositoryImpl<Long, Usuario> implements UsuarioRepository {

	@Override
	public Class<Usuario> getClassDeT() {
		return Usuario.class;
	}

	@Override
	public String getNombreTabla() {
		return "Usuario";
	}

	@Override
	public Integer consultaEventosDependientes(Usuario usuario) {
		BigInteger registrosAfectados = (BigInteger)entityManager
				.createNativeQuery("select COUNT(*) from EVENTO where usuario_id = ?;")
				.setParameter(1, usuario.getId())
				.getSingleResult();
		return registrosAfectados.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listaUsuariosActivos() {
		return entityManager
				.createNativeQuery("select * from USUARIO where alta = true;", Usuario.class)
				.getResultList();
	}
}
