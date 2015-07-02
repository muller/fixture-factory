package br.com.six2six.template;

import static br.com.six2six.fixturefactory.TypedRules.beforeDate;
import static br.com.six2six.fixturefactory.TypedRules.random;
import static br.com.six2six.fixturefactory.TypedRules.regex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.TypedRule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.six2six.fixturefactory.model.Invoice;

public class InvoiceTemplate implements TemplateLoader {

	@Override
	public void load() {

	        Invoice template = TypedRule.of(Invoice.class);
	        template.setId(regex("\\d{3,5}").asString());
	        template.setAmmount(random(BigDecimal.class, new MathContext(2)).asBigDecimal());
	        template.setDueDate(beforeDate("2011-04-08", new SimpleDateFormat("yyyy-MM-dd")).asCalendar());

	        Rule rule = TypedRule.get(template);

		Fixture.of(Invoice.class).addTemplate("valid", rule)
		.addTemplate("previousInvoices", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(BigDecimal.class, range(new BigDecimal("45.89"), new BigDecimal("58.67"))));
			add("dueDate", sequenceDate("2011-04-01", new SimpleDateFormat("yyyy-MM-dd"), decrement(1).month()));
			
		}}).addTemplate("nextInvoices", new Rule(){{
			add("id", regex("\\d{3,5}"));
			add("ammount", random(new BigDecimal("58.67"), new BigDecimal("45.89")));
			add("dueDate", sequenceDate("2011-04-30", increment(1).day()));
		}});
	}
}
