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
public class App {
	public static void main(String[] args) {
		// load up the knowledge base
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
//		KieSession kSession = kContainer.newKieSession();
        KieSession kSession = kContainer.newKieSession("ksession-rules");
		
		 // go !
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
        kSession.fireAllRules();
        
        empleados.stream().forEach(e -> {
        	System.out.println(e.getNombre() + ": " + e.isActivo());
        });
        
        kSession.dispose();
        System.out.println("-- FIN --");
	}
	
//	protected DroolContainer initializeKieServices() {
//        // Init KContainer with loaded rules. If List is empty, the container will be created empty.
//        KieServices kieServices = KieServices.Factory.get();
//        ReleaseId releaseId = new ReleaseIdImpl("com.inditex.mecc.appmicmecpromo", "artifactid",
//                "1.0.0-" + UUID.randomUUID().toString().replaceAll("-", ""));
//
//        List<String> rules = getPromotionsAndRules(null);
//
//        KieModule kieModule = this.createKieModule(releaseId, rules);
//
//        KieContainer newKieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//        kieServices.getRepository().removeKieModule(kieModule.getReleaseId());
//
//        DroolContainer droolContainer = new DroolContainer(rules.size(), newKieContainer);
//
//        return droolContainer;
//    }
}
