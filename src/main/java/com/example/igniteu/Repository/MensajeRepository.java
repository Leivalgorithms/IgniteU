package com.example.igniteu.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.igniteu.models.Mensaje;
import com.example.igniteu.models.Usertable;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    List<Mensaje> findByDestinatarioOrderByFechaEnvioAsc(Optional<Usertable> currentUserOpt);
    List<Mensaje> findByRemitenteOrderByFechaEnvioAsc(Usertable remitente);

    List<Mensaje> findByRemitenteAndDestinatarioOrderByFechaEnvioAsc(Usertable remitente, Usertable destinatario);

    List<Mensaje> findByDestinatarioAndRemitenteOrderByFechaEnvioAsc(Usertable remitente, Usertable destinatario);
}


