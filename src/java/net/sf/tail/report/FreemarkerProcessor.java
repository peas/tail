package net.sf.tail.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerProcessor {
	private Configuration configuration;

	public FreemarkerProcessor(Configuration configuration) {
		this.configuration = configuration;
	}

	public StringBuffer process(Map<String, Object> map, String template) throws IOException {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		try {
			Template temp = configuration.getTemplate(template);
			temp.process(map, out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		out.flush();
		return writer.getBuffer();
	}

}
