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
package org.beangle.ems;

import org.beangle.commons.config.property.MultiProviderPropertyConfig;
import org.beangle.commons.config.property.UrlPropertyConfigProvider;
import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.ems.app.Ems;
import org.beangle.ems.config.service.DaoPropertyConfigProvider;
import org.beangle.ems.dictionary.service.impl.CodeServiceImpl;
import org.beangle.ems.dictionary.service.impl.SeqCodeGenerator;
import org.beangle.ems.io.ClasspathDocLoader;
import org.beangle.ems.log.RemoteLogger;
import org.beangle.ems.log.service.BusinessEventLogger;
import org.beangle.ems.rule.engine.impl.DefaultRuleExecutorBuilder;
import org.beangle.ems.rule.impl.RuleBaseImpl;
import org.beangle.ems.script.OgnlExpressionEvaluator;

public class ServiceModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(ClasspathDocLoader.class).shortName();
    bind("baseCodeService", CodeServiceImpl.class);
    bind(SeqCodeGenerator.class);
    bind(BusinessEventLogger.class);
    bind(OgnlExpressionEvaluator.class);

    // rule bean
    bind(DefaultRuleExecutorBuilder.class, RuleBaseImpl.class);

    // properties config bean
    bind(UrlPropertyConfigProvider.class)
        .property("resources",
            "classpath:system-default.properties;classpath*:META-INF/system.properties;classpath:system.properties");

    bind(MultiProviderPropertyConfig.class).property("providers",
        list(ref(UrlPropertyConfigProvider.class), DaoPropertyConfigProvider.class));

    bind(RemoteLogger.class).property("url", Ems.getInstance().getApi() + "/platform/log/push");
  }

}
