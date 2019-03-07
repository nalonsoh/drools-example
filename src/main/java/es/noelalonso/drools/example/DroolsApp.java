package es.noelalonso.drools.example;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Hello world!
 *
 */
public class DroolsApp {
	public static void main(String[] args) {
		// load up the knowledge base
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession();
		
		// go !
        List<EmpleadoDto> jubiladosList = new ArrayList<>();
        kSession.setGlobal("_jubiladosList", jubiladosList);
        
        List<EmpleadoDto> empleados = new ArrayList<EmpleadoDto>();
        EmpleadoDto empleadoDto = new EmpleadoDto();
        empleadoDto.setNombre("Yo");
        empleadoDto.setEdad(65);
        empleados.add(empleadoDto);
        
        empleadoDto = new EmpleadoDto();
        empleadoDto.setNombre("Tu");
        empleadoDto.setEdad(64);
        empleados.add(empleadoDto);
        
        empleados.stream().forEach(e -> {
        	kSession.insert(e);
        });
        
        kSession.insert(jubiladosList);
        kSession.fireAllRules();
        
        empleados.stream().forEach(e -> {
        	System.out.println(e.getNombre() + ": " + e.isActivo());
        });
        
        System.out.println("---------");
        System.out.println("Jubilados:");
        jubiladosList.stream().forEach(e -> {
        	System.out.println(" - " + e.getNombre());
        });
        
        kSession.dispose();
        System.out.println("-- FIN --");
	}

}
