package zdatbit.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreeMarker工具类
 * @author 董华健
 */
public class ToolFreeMarker {
	
	/**
	 * 渲染模板
	 * @param templateContent
	 * @param paramMap
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static String render(String templateContent, Map<String, Object> paramMap) throws IOException, TemplateException{
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new StringTemplateLoader(templateContent));
		cfg.setDefaultEncoding("UTF-8");

		Template template = cfg.getTemplate("");

		StringWriter writer = new StringWriter();
		template.process(paramMap, writer);

		return writer.toString();
	}
	
	/**
	 * 生成HTML
	 * @param ftlDirectory ftl模板目录
	 * @param ftlName	ftl模板
	 * @param paramMap	参数map
	 * @param htmlPath 生成HTML存放路径
	 */
	public static void makeHtml(String ftlDirectory, String ftlName, Map<String, Object> paramMap, String htmlPath) {
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		try {
			Configuration configuration = new Configuration();
			File file = new File(ftlDirectory);// .ftl模板目录
			configuration.setDirectoryForTemplateLoading(file);
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			Template template = configuration.getTemplate(ftlName, ToolString.encoding);

			File file2 = new File(htmlPath);// 生成html目录
			fileOutputStream = new FileOutputStream(file2);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, ToolString.encoding);
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			template.process(paramMap, bufferedWriter);
			bufferedWriter.flush();
			outputStreamWriter.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			if (null != fileOutputStream) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != outputStreamWriter) {
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

class StringTemplateLoader implements TemplateLoader {

	private String template;

	public StringTemplateLoader(String template) {
		this.template = template;
		if (template == null) {
			this.template = "";
		}
	}

	public void closeTemplateSource(Object templateSource) throws IOException {
		((StringReader) templateSource).close();
	}

	public Object findTemplateSource(String name) throws IOException {
		return new StringReader(template);
	}

	public long getLastModified(Object templateSource) {
		return 0;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return (Reader) templateSource;
	}

}