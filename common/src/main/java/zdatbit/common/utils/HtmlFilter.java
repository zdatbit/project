package zdatbit.common.utils;

import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class HtmlFilter {
	private final static Whitelist user_content_filter=Whitelist.basicWithImages();
	static {
		//增加可信标签到白名单
		user_content_filter.addTags("embed","object","param","span","div");
		//增加可信属性
		user_content_filter.addAttributes(":all", "style", "class", "id", "name");
		user_content_filter.addAttributes("object", "width", "height","classid","codebase");
		user_content_filter.addAttributes("param", "name", "value");
		user_content_filter.addAttributes("embed", "src","quality","width","height","allowFullScreen","allowScriptAccess","flashvars","name","type","pluginspage");
	}
	/**
	 * 对用户输入进行过滤
	 * @param html
	 * @return
	 */
	public static String filter(String html){
		if (ToolString.isEmpty(html)) {
			return "";
		}
		html=html.trim();
		
		return escapeHtml(html);
//		return Jsoup.clean(html, user_content_filter.relaxed());
	}
	/**
	 * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
	 * @param html
	 * @return
	 */
	public static String relaxed(String html) {
		if (ToolString.isEmpty(html)) {
			return "";
		}
		return Jsoup.clean(html, Whitelist.relaxed());
	}
	/**
	 * 去掉所有标签，返回纯文字.适用于textarea，input
	 * @param html
	 * @return
	 */
	public static String pureText(String html) {
		if (ToolString.isEmpty(html)) {
			return "";
		}
		return Jsoup.clean(html, Whitelist.none());
	}
	/**
	 * 转义html
	 * @param html
	 * @return
	 */
	public static String escapeHtml(String html){
		if(ToolString.isEmpty(html)){
			return "";
		}
//		html=html.replaceAll("'", "’");//20160901添加，将所有英文单引号，改为了中文单引号
		html=stripXSS(html);
//		html=Jsoup.clean(html, user_content_filter);
		html=StringEscapeUtils.escapeSql(html);
		
//		html=html.replaceAll("(?i)<(/?script[^>]*)>|(?i)<(/?iframe[^>]*)>|(?i)&lt;(/?script[^>]*)&gt;|(?i)&lt;(/?iframe[^>]*)&gt;", "");
//        html = html.replaceAll("eval\\((.*)\\)", "");
		//过滤IOS表情
		html=ToolString.filterEmoji(html);
		html=xssEncode(html);
//		html=html.replaceAll("\\"+"\"", "&quot;");
//		html=html.replaceAll("&", "&amp;");
//		html=html.replaceAll("<", "&lt;");
//		html=html.replaceAll(">", "&gt;");
//		html=html.replaceAll("'", "&apos;");
		return html;
	}
	/**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '>':
                sb.append('＞');// 全角大于号
                break;
            case '<':
                sb.append('＜');// 全角小于号
                break;
            case '\'':
                sb.append('‘');// 全角单引号
                break;
            case '\"':
                sb.append('“');// 全角双引号
                break;
            case '&':
                sb.append('＆');// 全角
                break;
            case '\\':
                sb.append('＼');// 全角斜线
                break;
            case '#':
                sb.append('＃');// 全角井号
                break;
            case '%':    // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
                processUrlEncoder(sb, s, i);
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }
	public static void processUrlEncoder(StringBuilder sb, String s, int index){
        if(s.length() >= index + 2){
            if(s.charAt(index+1) == '3' && (s.charAt(index+2) == 'c' || s.charAt(index+2) == 'C')){    // %3c, %3C
                sb.append('＜');
                return;
            }
            if(s.charAt(index+1) == '6' && s.charAt(index+2) == '0'){    // %3c (0x3c=60)
                sb.append('＜');
                return;
            }            
            if(s.charAt(index+1) == '3' && (s.charAt(index+2) == 'e' || s.charAt(index+2) == 'E')){    // %3e, %3E
                sb.append('＞');
                return;
            }
            if(s.charAt(index+1) == '6' && s.charAt(index+2) == '2'){    // %3e (0x3e=62)
                sb.append('＞');
                return;
            }
        }
        sb.append(s.charAt(index));
    }
	private static String stripXSS(String value){
        if(value !=null){
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
//            value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
        	value=value.replaceAll("(?i)<(/?script[^>]*)>|(?i)<(/?iframe[^>]*)>|(?i)&lt;(/?script[^>]*)&gt;|(?i)&lt;(/?iframe[^>]*)&gt;", "");
        	value = value.replaceAll("eval\\((.*)\\)", "");
        	value = value.replaceAll("expression\\((.*)\\)", "");
            value = value.replaceAll("","");
            // Avoid anything between script tags
            Pattern scriptPattern =Pattern.compile("(.*?)",Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
//            scriptPattern =Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
//            value = scriptPattern.matcher(value).replaceAll("");
//            scriptPattern =Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
//            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome  tag
            scriptPattern =Pattern.compile("",Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome  tag
            scriptPattern =Pattern.compile("",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) e­xpressions
            scriptPattern =Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid e­xpression(...) e­xpressions
            scriptPattern =Pattern.compile("e­xpression\\((.*?)\\)",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... e­xpressions
            scriptPattern =Pattern.compile("javascript:",Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid vbscript:... e­xpressions
            scriptPattern =Pattern.compile("vbscript:",Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= e­xpressions
            scriptPattern =Pattern.compile("(onload.*?=)",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onerror(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onfocus(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onmouse(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onkey(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onchange(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern =Pattern.compile("onclick(.*?)=",Pattern.CASE_INSENSITIVE |Pattern.MULTILINE |Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }
	public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
            case '<':
                buffer.append("&lt;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '&':
                buffer.append("&amp;");
                break;
            case '"':
                buffer.append("&quot;");
                break;
            case 10:
            case 13:
                break;
            default:
                buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }
	/**
	 * 反转义html
	 * @param html
	 * @return
	 */
	public static String unEscapeHtml(String html){
		if(ToolString.isEmpty(html)){
			return "";
		}
		html=StringEscapeUtils.unescapeHtml(html);
		html=html.replaceAll("&quot;", "\\"+"\"");
////		html=html.replaceAll("&amp;", "&");
		html=html.replaceAll("&lt;", "<");
		html=html.replaceAll("&gt;", ">");
		html=html.replaceAll("&apos;", "'");
		return html;
	}
	public static String getBasicHtmlandimage(String html) {
		if (ToolString.isEmpty(html)) {
			return "";
		}
	    return Jsoup.clean(html, Whitelist.basicWithImages());
	}
	
	public static void main(String[] args){
//		System.out.println("<tr id=\"issue-405\" class=\"hascontextmenu even issue tracker-1 status-3 priority-2 priority-default assigned-to-me assigned-to-my-group\">");
//		System.out.println(HtmlFilter.filter("<tr id=\"issue-405\" class=\"hascontextmenu even issue tracker-1 status-3 priority-2 priority-default assigned-to-me assigned-to-my-group\">".trim()));
//		System.out.println(HtmlFilter.unEscapeHtml("&lt;tr id=&quot;issue-405&quot; class=&quot;hascontextmenu even issue tracker-1 status-3 priority-2 priority-default assigned-to-me assigned-to-my-group&quot;&gt;"));
		System.out.println(HtmlFilter.escapeHtml("2 <img onerror=alert(1) />resultType=json&<script>alert(1)</script>1 <iframe type='text/'>111alert(1)</script>test'<img onerror=alert(1) />"));
//		System.out.println(HtmlFilter.escapeHtml("%28select+1234%2C"));
	}
}
