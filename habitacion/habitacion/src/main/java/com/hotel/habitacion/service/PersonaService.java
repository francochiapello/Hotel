package com.hotel.habitacion.service;

import com.hotel.habitacion.domain.Personas;
import com.hotel.habitacion.repository.PersonaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;
    
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    
    //crear usuario
    public boolean guardarU(Personas persona){
        persona.setRole("cliente");
        personaRepository.save(persona);
        return true;
    }
    //borrar usuario
    public boolean borrarU(int id){
        personaRepository.deleteById(id);
        return true;
    }
    //actualizar usuario
    public boolean actualizarU(Personas persona){
        personaRepository.save(persona);
        return true;
    }
    //ver usuarios
    public List<Personas>buscarTodosU(){
        return personaRepository.findAll();
    }
    
    //logear usuario
    public Personas logear(Personas persona){
        return personaRepository.logear(persona.getNombre(),persona.getPass());
    }
}
