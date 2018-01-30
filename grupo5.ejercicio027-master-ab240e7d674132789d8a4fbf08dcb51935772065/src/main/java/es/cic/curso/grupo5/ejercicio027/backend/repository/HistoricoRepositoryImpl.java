package es.cic.curso.grupo5.ejercicio027.backend.repository;

import org.springframework.stereotype.Repository;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;

@Repository
public class HistoricoRepositoryImpl extends AbstractRepositoryImpl <Long, Historico> implements HistoricoRepository {

	@Override
    public Class<Historico> getClassDeT() {
        return Historico.class;
    }

	@Override
    public String getNombreTabla() {
        return "HISTORICO";
    }
}
