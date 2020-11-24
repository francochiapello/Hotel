package com.hotel.habitacion;

import com.hotel.habitacion.domain.Habitaciones;
import com.hotel.habitacion.domain.Personas;
import com.hotel.habitacion.service.HabitacionService;
import com.hotel.habitacion.service.PersonaService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HabitacionController {
    
    private final HabitacionService habitacionService;
    private final PersonaService personaService;

    public HabitacionController(HabitacionService habitacionService, PersonaService personaService) {
        this.habitacionService = habitacionService;
        this.personaService = personaService;
    }
    
   //obtener las habitaciones segun su estado(libre, ocupada, mantenimiento, limpieza)
    @GetMapping("/listadoHE")
    public List<Habitaciones>habitacionE(String estado){
        List<Habitaciones>disponibles =  habitacionService.buscarXestado(estado);
        return disponibles;
    }
    
    //obtener las habitaciones segun su tipo (estandar, normal, suite)
    @GetMapping("/listadoHT")
    public List<Habitaciones>habitacionT(String tipo){
        System.out.println(tipo);
        return habitacionService.buscarXtipo(tipo);
    }
    
    //reservar una habitacion
    // * guardado 
    @PostMapping("/reservarH")
    public String reservarH(int id,@RequestBody Habitaciones habitacion , int cantP){
        String menssage = "";
        if ( id == 0){
            menssage  = "Error, necesitas logearte";
        }else{
            if (cantP <= habitacion.getMaxOcupantes()){
            habitacion.setEstado("ocupada");
            habitacion.setIdReservado(id);
            habitacionService.reservar(habitacion); 
            menssage = "200, se ha reservado la habitacion con exito";
            }else{
                menssage = "Error 404, maximo de personas exedido";
            }
        }
        return menssage;
    }
    //transicion de estados de habitacion
    // *GERENTE Y RECEPCIONISTA ROL = 2(con permisos), CLIENTE ROL = 1 (sin permisos)
    @RequestMapping("/transicion")
    public String transicionH(@RequestBody Habitaciones habitacion,int role,int estadoAnterior){
        String menssage = "";
        System.out.println(role);
        if( role == 1){
            menssage  = "Error 404,no tienes privilegios para esto";
        }else{
            switch (habitacion.getEstado()){
                case "libre": 
                    if(estadoAnterior == 4 || estadoAnterior == 3){
                        habitacionService.transicion(habitacion);
                        menssage = "200,la transciocion se ha realizado con exito";
                    }else{
                        menssage = "Error: codigo 101";
                    }
                    break;
                    
                case "ocupada":
                    if ( estadoAnterior == 1){
                        habitacionService.transicion(habitacion);
                        menssage = "200,la transciocion se ha realizado con exito";
                    }else{
                        menssage = "Error: codigo 101, no se puede realizar esta transcicion";
                    }
                    break;
                case "limpieza":
                    if( estadoAnterior == 2 || estadoAnterior == 3){
                         habitacionService.transicion(habitacion);
                         menssage = "200,la transciocion se ha realizado con exito";
                    }else{
                        menssage = "Error: codigo 101, no se puede realizar esta transcicion";
                    }
                    break;
                case "manteniemto":
                    if( estadoAnterior == 4 || estadoAnterior == 1 || estadoAnterior == 2){
                         habitacionService.transicion(habitacion);
                         menssage = "200,la transciocion se ha realizado con exito";
                    }else{
                        menssage = "Error: codigo 101, no se puede realizar esta transcicion";
                    }
                    break;
                default:
                    menssage = "Error: codigo 101, no se puede realizar esta transcicion";
            }
        }
        System.out.println(menssage);
       return menssage;
    }
    //Created habitacion
    // * Crear habitacion
    @RequestMapping("/crearH")
    public Habitaciones crearH( Model model){
        return new Habitaciones();
    }   
    //* guardar habitacion
    @PostMapping("/guardarH")
    public String guardarH(@RequestBody Habitaciones habitacion, int role){
        String menssage = "";
        if (role == 2){
             habitacionService.guardar(habitacion);
            menssage = "good,habitacion guardada";
        }else{
            menssage = "Error 404, usted no tiene privilegios para hacer esta operacion";
        }
        return menssage;
    }
            
    //Read habitacion
    
    //Update habitacion
    
    //Delete habitacion
    @DeleteMapping("borrarH")
    public String borrarH(int id, int role){
        String menssage = "";
        if (role == 2){
             habitacionService.borrar(id);
            menssage = "200,habitacion guardada";
        }else{
            menssage = "Error 404, usted no tiene privilegios para hacer esta operacion";
        }
        return menssage;
    }
    //Roles gerente (no tiene restriciones)
    
    //Roles recepcioniste (transiciones de habitacion, reservarlas, y listado)
    
    //Roles cliente(obtener listado de las habitaciones libres y reservarlas)
    
    
    //crear usuario
    //* crear el modelo personas
    @RequestMapping("/crearU")
    public Personas crearU(Model model){
        return new Personas();
    }
    //*guardar usuario por defecto cliente
    @PostMapping("/guardarU")
    public boolean guardarU(@RequestBody Personas persona){
        return personaService.guardarU(persona);
    }
    
    //iniciar secion usuario
    @PostMapping("/logearU")
    public Personas logear(@RequestBody Personas persona){
        Personas u = personaService.logear(persona);
        System.out.println(u.getId());
        return u;
    }
    
    //actualizar usuario ID DEL USUSARIO LOGEADO
    @RequestMapping("/actualizarU")
    public String actualizarU(Personas persona , int id){
        String menssage = "";
        if(id == 0){
        menssage = "Error,necesitas logearte para esta accion";
        }else{
        personaService.actualizarU(persona);
        menssage = "200,usuario actualizado";
        }
        return menssage;
    }
    
    //borrar usuario ID = USUARIO A BORRAR, ROLE = ROL DEL USUARIO
    @DeleteMapping("/borrarU")
    public String borrarU(int id, int role){
        String menssage = "";
        if (role == 2){
             personaService.borrarU(id);
            menssage = "200,usuario borrado correctamente";
        }else{
            menssage = "Error 404, usted no tiene privilegios para hacer esta operacion";
        }
        return menssage;
    }
    
    //listar usuarios ROLE = ROL DEL USUARIO
    @GetMapping("/listarU")
    public List<Personas> mostrarU(int role){
         String menssage = "";
         List<Personas> persona = null;
        if (role == 2){
            persona = personaService.buscarTodosU();
            menssage = "200,usuarios";
        }else{
            menssage = "Error 404, usted no tiene privilegios para hacer esta operacion";
        }
        return persona;
    }
}
