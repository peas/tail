package net.sf.tail.report.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.tail.AnalysisCriterion;
import net.sf.tail.analysis.evaluator.Decision;
import net.sf.tail.report.FreemarkerProcessor;
import net.sf.tail.report.Report;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class ReportHTMLGenerator {

	static final String COMPLETE_TEMPLATE_DIR = "Data/templates/complete";

	static final String SLICE_TEMPLATE_DIR = "Data/templates/slice";

	static final String DEFAULT_TEMPLATE_DIR = "Data/templates/total";

	public StringBuffer generate(Report report, String imagePath, List<String> urls) throws IOException {
		return generate(report, Collections.<AnalysisCriterion> emptyList(), imagePath, urls);
	}

	public StringBuffer generate(Decision decision, String image) throws IOException {
		return generate(decision, Collections.<AnalysisCriterion> emptyList(), image);
	}

	public StringBuffer generate(Report report, String imagePath) throws IOException {
		return generate(report, Collections.<AnalysisCriterion> emptyList(), imagePath);
	}

	public StringBuffer generate(Report report, List<AnalysisCriterion> criterion, String imagePath, List<String> urls)
			throws IOException {
		Configuration cfg = loadConfigurationDir(COMPLETE_TEMPLATE_DIR);
		String adjustOSURL = "";
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		{
			adjustOSURL =  "file:///";
		}

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("report", report);
		map.put("criterions", criterion);
		
		List<String> stringUrl = new ArrayList<String>();
		for (String string : urls) {
			String newUrl = adjustOSURL + string.replace(" ", "%20");
			stringUrl.add(newUrl);
		} 
			  
		map.put("urls", stringUrl);
		map.put("image", adjustOSURL + imagePath.replace(" ", "%20"));

		FreemarkerProcessor processor = new FreemarkerProcessor(cfg);
		return processor.process(map, "report.ftl");
	}

	public StringBuffer generate(Decision decision, List<AnalysisCriterion> criteria, String imagePath)
			throws IOException {
		Configuration cfg = loadConfigurationDir(SLICE_TEMPLATE_DIR);
		String adjustOSURL = "";
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		{
			adjustOSURL =  "file:///";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("decision", decision);
		map.put("criteria", criteria);
		map.put("image", adjustOSURL + imagePath.replace(" ", "%20"));

		FreemarkerProcessor processor = new FreemarkerProcessor(cfg);
		return processor.process(map, "sliceReport.ftl");
	}

	public StringBuffer generate(Report report, List<AnalysisCriterion> criterion, String imagePath) throws IOException {
		Configuration cfg = loadConfigurationDir(DEFAULT_TEMPLATE_DIR);
		String adjustOSURL = "";
		if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
		{
			adjustOSURL =  "file:///";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("report", report);
		map.put("criterions", criterion);
		map.put("image", adjustOSURL + imagePath.replace(" ", "%20"));

		FreemarkerProcessor processor = new FreemarkerProcessor(cfg);
		return processor.process(map, "report.ftl");
	}

	private Configuration loadConfigurationDir(String dir) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		cfg.setObjectWrapper(new BeansWrapper());
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}
}
