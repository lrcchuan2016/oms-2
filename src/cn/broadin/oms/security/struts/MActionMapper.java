package cn.broadin.oms.security.struts;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.apache.struts2.dispatcher.mapper.ParameterAction;

/**
 * <pre>
 * 重写struts2的ActionMapper,解决可远程执行命令的安全漏洞.
 * 详见：{@link http://struts.apache.org/release/2.3.x/docs/security-bulletins.html}
 * </pre>
 * 
 * <pre>
 * struts.xml中，做如下配置：
 * {@code
 * <bean name="mActionMapper" type="org.apache.struts2.dispatcher.mapper.ActionMapper" 
 *     class="cn.broadin.oms.security.struts.MActionMapper"/>
 * <constant name="struts.mapper.class" value="mActionMapper"/>
 * }
 * </pre>
 * 
 * @deprecated see StrutsLeakFilter
 */
public class MActionMapper extends DefaultActionMapper {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void handleSpecialParameters(HttpServletRequest request, ActionMapping mapping) {
		Set uniqueParameters = new HashSet();
		Map parameterMap = request.getParameterMap();
		System.out.println(parameterMap);
		
		for (Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			
			if ((key.endsWith(".x")) || (key.endsWith(".y"))) {
				key = key.substring(0, key.length() - 2);
			}
			
			// 新增
			if ((key.contains("redirect:")) || (key.contains("redirectAction:")) || (key.contains("action:")) || (key.contains("java."))) { return; }
			
			if (!uniqueParameters.contains(key)) {
				ParameterAction parameterAction = (ParameterAction) this.prefixTrie.get(key);
				if (parameterAction != null) {
					parameterAction.execute(key, mapping);
					uniqueParameters.add(key);
					break;
				}
			}
		}
		
	}
}
