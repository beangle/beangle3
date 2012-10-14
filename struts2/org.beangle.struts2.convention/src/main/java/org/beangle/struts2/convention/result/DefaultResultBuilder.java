/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.result;

import static org.beangle.commons.lang.Strings.contains;
import static org.beangle.commons.lang.Strings.isBlank;
import static org.beangle.commons.lang.Strings.isEmpty;
import static org.beangle.commons.lang.Strings.isNotEmpty;
import static org.beangle.commons.lang.Strings.substringAfter;
import static org.beangle.commons.lang.Strings.substringBefore;
import static org.beangle.commons.web.util.RequestUtils.getServletPath;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.convention.route.ActionBuilder;
import org.beangle.struts2.convention.route.ProfileService;
import org.beangle.struts2.convention.route.ViewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.inject.Inject;

/**
 * 按照各种result的特征进行结果构建<br>
 * 1)chain:/xxx?param1=value1&param2=value2<br>
 * 2)redirectAction:/yyy!method.action?param1=value1&param2=value2<br>
 * 2)redirect:/yyy!methodd?param1=value1&param2=value2<br>
 * 3)path/to/page/page.ftl<br>
 * 
 * @author chaostone
 */
public class DefaultResultBuilder implements ResultBuilder {

  private static final Logger logger = LoggerFactory.getLogger(DefaultResultBuilder.class);

  protected Map<String, ResultTypeConfig> resultTypeConfigs;

  @Inject
  protected ObjectFactory objectFactory;

  @Inject
  protected Configuration configuration;

  @Inject
  protected ViewMapper viewMapper;

  @Inject
  protected ProfileService profileService;

  @Inject
  protected ActionBuilder actionNameBuilder;

  public Result build(String resultCode, ActionConfig actionConfig, ActionContext context) {
    String path = null;
    ResultTypeConfig cfg = null;

    logger.debug("result code:{} for actionConfig:{}", resultCode, actionConfig);
    if (null == resultTypeConfigs) {
      PackageConfig pc = configuration.getPackageConfig(actionConfig.getPackageName());
      this.resultTypeConfigs = pc.getAllResultTypeConfigs();
    }
    // first route by common result
    if (!contains(resultCode, ':')) {
      String className = context.getActionInvocation().getProxy().getAction().getClass().getName();
      String methodName = context.getActionInvocation().getProxy().getMethod();
      if (isEmpty(resultCode)) {
        resultCode = "index";
      }
      StringBuilder buf = new StringBuilder();
      buf.append(viewMapper.getViewPath(className, methodName, resultCode));
      buf.append('.');
      buf.append(profileService.getProfile(className).getViewExtension());
      path = buf.toString();
      cfg = resultTypeConfigs.get("freemarker");
      return buildResult(resultCode, cfg, context, buildResultParams(path, cfg));
    } else {
      // by prefix
      String prefix = substringBefore(resultCode, ":");
      cfg = (ResultTypeConfig) resultTypeConfigs.get(prefix);
      if (prefix.startsWith("chain")) {
        Action action = buildAction(substringAfter(resultCode, ":"));
        Map<String, String> params = buildResultParams(path, cfg);
        addNamespaceAction(action, params);
        if (isNotEmpty(action.getMethod())) {
          params.put("method", action.getMethod());
        }
        return buildResult(resultCode, cfg, context, params);
      } else if (prefix.startsWith("redirect")) {
        String targetResource = substringAfter(resultCode, ":");
        if (contains(targetResource, ':')) { return new ServletRedirectResult(targetResource); }
        Action action = buildAction(targetResource);
        // add special param and ajax tag for redirect
        HttpServletRequest request = ServletActionContext.getRequest();
        String redirectParamStr = request.getParameter("params");
        action.params(redirectParamStr);
        // x-requested-with->XMLHttpRequest
        if (null != request.getHeader("x-requested-with")) {
          action.param("x-requested-with", "1");
        }
        Map<String, String> params = buildResultParams(path, cfg);
        if (null != action.getParams().get("method")) {
          params.put("method", (String) action.getParams().get("method"));
          action.getParams().remove("method");
        }
        if (isNotEmpty(action.getMethod())) {
          params.put("method", action.getMethod());
        }
        addNamespaceAction(action, params);

        ServletRedirectResult result = (ServletRedirectResult) buildResult(resultCode, cfg, context, params);
        for (Map.Entry<String, String> param : action.getParams().entrySet()) {
          String property = param.getKey();
          result.addParameter(property, param.getValue());
        }
        return result;
      } else {
        // 从结果中抽取路径和返回值
        path = substringAfter(resultCode, ":");
        resultCode = "success";
        return buildResult(resultCode, cfg, context, buildResultParams(path, cfg));
      }
    }
  }

  /**
   * 依据跳转路径进行构建
   * 
   * @param path
   * @param param
   * @param redirectParamStr
   */
  private Action buildAction(String path) {
    Action action = (Action) ActionContext.getContext().getContextMap().get("dispatch_action");
    if (null == action) {
      action = new Action();
      String newPath = path;
      if (path.startsWith("?")) {
        newPath = getServletPath(ServletActionContext.getRequest()) + path;
      }
      action.path(newPath);
    } else {
      if (null != action.getClazz()) {
        Action newAction = actionNameBuilder.build(action.getClazz());
        action.name(newAction.getName()).namespace(newAction.getNamespace());
      }
      if (isBlank(action.getName())) {
        action.path(getServletPath(ServletActionContext.getRequest()));
      }
    }
    return action;
  }

  private void addNamespaceAction(Action action, Map<String, String> params) {
    params.put("namespace", action.getNamespace());
    params.put("actionName", action.getName());
  }

  protected Map<String, String> buildResultParams(String defaultParam, ResultTypeConfig resultTypeConfig) {
    Map<String, String> params = new LinkedHashMap<String, String>();
    if (resultTypeConfig.getParams() != null) {
      params.putAll(resultTypeConfig.getParams());
    }
    params.put(resultTypeConfig.getDefaultResultParam(), defaultParam);
    return params;
  }

  /**
   * 构建结果
   * 
   * @param resultCode
   * @param resultTypeConfig
   * @param context
   * @param params
   */
  protected Result buildResult(String resultCode, ResultTypeConfig resultTypeConfig, ActionContext context,
      Map<String, String> params) {
    ResultConfig resultConfig = new ResultConfig.Builder(resultCode, resultTypeConfig.getClassName())
        .addParams(params).build();
    try {
      return objectFactory.buildResult(resultConfig, context.getContextMap());
    } catch (Exception e) {
      throw new XWorkException("Unable to build convention result", e, resultConfig);
    }
  }

  public ObjectFactory getObjectFactory() {
    return objectFactory;
  }

  public void setObjectFactory(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public void setViewMapper(ViewMapper viewMapper) {
    this.viewMapper = viewMapper;
  }

  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }

  public void setActionNameBuilder(ActionBuilder actionNameBuilder) {
    this.actionNameBuilder = actionNameBuilder;
  }

}
