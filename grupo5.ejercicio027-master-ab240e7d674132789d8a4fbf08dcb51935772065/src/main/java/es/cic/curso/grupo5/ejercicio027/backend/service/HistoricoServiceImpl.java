package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;
import es.cic.curso.grupo5.ejercicio027.backend.repository.HistoricoRepository;

@Service
@Transactional
public class HistoricoServiceImpl implements HistoricoService{

	@Autowired
	private HistoricoRepository historicoRepository;

    @Override
	public Historico aniadirHistorico(Historico historico) {
        return historicoRepository.add(historico);
	}

    @Override
    public Historico modificarHistorico(Historico historico) {
    	return historicoRepository.update(historico);
    }
   
    @Override
    public void borrarHistorico(Long id) {
        Historico historicoABorrar = obtenerHistorico(id);
        historicoRepository.delete( historicoABorrar);
    }

    @Override
    public Historico obtenerHistorico(Long id) {
        return historicoRepository.read(id);
    }

    @Override
    public List<Historico> listarHistorico() {
        return historicoRepository.list();
    }

}