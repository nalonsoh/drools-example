package es.noelalonso.drools.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Hello world!
 *
 */
public class DroolsApp2 {
	private static final String RULE = "import es.noelalonso.drools.example.EmpleadoDto\r\n" + 
			"\r\n" + 
			"rule \"validarEmpleado\"\r\n" + 
			"  	when\r\n" + 
			"    	$a : EmpleadoDto( edad >= 65 )\r\n" + 
			"	then\r\n" + 
			"    	 modify( $a ) { setActivo(false) };\r\n" + 
			"end";
	
	private static final String RULE2 = "import es.noelalonso.drools.example.EmpleadoDto\r\n" + 
			"\r\n" + 
			"rule \"dummy\"\r\n" + 
			"  	when\r\n" + 
			"    	EmpleadoDto( activo == false )\r\n" + 
			"	then\r\n" + 
			"    	System.out.println(\"Otro jubilado!!\");" + 
			"end";
	
	public static void main(String[] args) {
		// load up the knowledge base
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		
		ReleaseId releaseId = new ReleaseIdImpl("com.inditex.mecc.appmicmecpromo", "artifactid",
                "1.0.0-" + UUID.randomUUID().toString().replaceAll("-", ""));
		kfs.generateAndWritePomXML(releaseId);
		
		KieModule kieModule = createKieModule(releaseId, Arrays.asList(RULE, RULE2));
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		 
		KieSession kSession = kContainer.newKieSession();
//        KieSession kSession = kContainer.newKieSession("ksession-rules");
		
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
        
        empleadoDto = new EmpleadoDto();
        empleadoDto.setNombre("Él");
        empleadoDto.setEdad(66);
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
	
    /**
     * Create kie module.
     *
     * @param releaseId
     *            release id
     * @param rules
     *            rules
     * @return the kie module
     */
    protected static KieModule createKieModule(ReleaseId releaseId, List<String> rules) {

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.generateAndWritePomXML(releaseId);
        rules.forEach(rule -> kfs.write("src/main/resources/rules/" + rule.hashCode() + ".drl", rule));

        KieBuilder kb = kieServices.newKieBuilder(kfs).buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            for (Message result : kb.getResults().getMessages()) {
                System.err.println(result.getText());
            }
            throw new IllegalStateException("DRL rule compile errors found");
        }
        return kieServices.getRepository().getKieModule(releaseId);

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
