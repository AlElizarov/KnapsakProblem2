package reports;

import java.io.InputStream;

public class TemplateLoader {

	public static InputStream loadTemplate(String templateId) {
		return TemplateLoader.class.getResourceAsStream(templateId);
	}

}
