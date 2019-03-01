package es.noelalonso.drools.example.fire;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import es.noelalonso.drools.example.fire.dto.Fire;
import es.noelalonso.drools.example.fire.dto.Room;
import es.noelalonso.drools.example.fire.dto.Sprinkler;

public class FireExample {

	public static void main(String[] args) {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
		KieSession ksession = kContainer.newKieSession("ksession-rules");

		String[] names = new String[] { "kitchen", "bedroom", "office", "livingroom" };

		Map<String, Room> roomsMap = new HashMap<String, Room>();
		for (String name : names) {
			Room room = new Room();
			room.setName(name);
			roomsMap.put(name, room);
			ksession.insert(room);

			Sprinkler sprinkler = new Sprinkler();
			sprinkler.setRoom(room);
			ksession.insert(sprinkler);
		}

//		Executors.newSingleThreadExecutor().execute(() -> {
//			ksession.fireUntilHalt();
//		});
		ksession.fireAllRules();

		System.out.println();
		System.out.println("------------------------------");
		System.out.println();

		Fire kitchenFire = new Fire();
		kitchenFire.setRoom(roomsMap.get("kitchen"));

		Fire officeFire = new Fire();
		officeFire.setRoom(roomsMap.get("office"));

		FactHandle kitchenFireHandle = ksession.insert(kitchenFire);
		FactHandle officeFireHandle = ksession.insert(officeFire);

		ksession.fireAllRules();

		/*
		System.out.println();
		System.out.println("------------------------------");
		System.out.println();

		ksession.delete(kitchenFireHandle);
		ksession.delete(officeFireHandle);


		ksession.fireAllRules();
		*/
	}
}
