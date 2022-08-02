/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;

public class IncludeIfExistsModel implements TemplateDirectiveModel {

  @Override
  public void execute(Environment env, java.util.Map map, TemplateModel[] templateModel,
                      TemplateDirectiveBody directiveBody) throws TemplateException, IOException {
    String currentTemplateName = env.getCurrentNamespace().getTemplate().getName();
    String path = map.get("path").toString();
    String fullTemplatePath = env.toFullTemplateName(currentTemplateName, path);

    TemplateLoader templateLoader = env.getConfiguration().getTemplateLoader();
    if (templateLoader.findTemplateSource(fullTemplatePath) != null) {
      env.include(env.getTemplateForInclusion(fullTemplatePath, null, true));
    } else {
      if (directiveBody != null) {
        directiveBody.render(env.getOut());
      }
    }
  }
}
