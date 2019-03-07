package es.noelalonso.drools.example.account;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.QueryResults;

import es.noelalonso.drools.example.account.dto.Account;
import es.noelalonso.drools.example.account.dto.AccountBalance;
import es.noelalonso.drools.example.account.dto.AccountPeriod;
import es.noelalonso.drools.example.account.dto.CashFlow;

public class AccountBalanceExample {

	private static DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) throws ParseException {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kContainer = kieServices.getKieClasspathContainer();
		KieSession ksession = kContainer.newKieSession("ksession-rules");

		ksession.insert(new Account(1001, 0));
		ksession.insert(new AccountPeriod(DF.parse("01/01/2007"), DF.parse("31/03/2007")));
		ksession.insert(new CashFlow(DF.parse("10/01/2007"), 100, 1, 1001));
		ksession.insert(new CashFlow(DF.parse("10/02/2007"), 200, 2, 1001));
		ksession.insert(new CashFlow(DF.parse("10/03/2007"), 75, 1, 1001));
		ksession.insert(new CashFlow(DF.parse("10/04/2007"), 20, 2, 1001));

		System.out.println(ksession.getFactCount());
		System.out.println(ksession.getEntryPoints().size());
		for (EntryPoint entryPoint : ksession.getEntryPoints()) {
			System.out.println(entryPoint);
		}

		ksession.getAgenda().getAgendaGroup("calculation").setFocus();
		ksession.fireAllRules();

		ksession.getAgenda().getAgendaGroup("report").setFocus();
		ksession.fireAllRules();

		System.out.println("\nQuery results:");
		QueryResults queryResults = ksession.getQueryResults("balaces");
		queryResults.forEach((row) -> {
			AccountBalance acc = (AccountBalance) row.get("$acc");
			System.out.println(" -> " + acc);
		});

	}
}
