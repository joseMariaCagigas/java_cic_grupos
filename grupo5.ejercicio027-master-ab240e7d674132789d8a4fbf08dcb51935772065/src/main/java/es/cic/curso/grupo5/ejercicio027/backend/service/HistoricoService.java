package es.cic.curso.grupo5.ejercicio027.backend.service;

import java.util.List;

import es.cic.curso.grupo5.ejercicio027.backend.dominio.Historico;

public interface HistoricoService {

    Historico aniadirHistorico(Historico historico);

    void borrarHistorico(Long id);
    
    Historico modificarHistorico(Historico historico);

    Historico obtenerHistorico(Long id);

    List<Historico> listarHistorico();
}
