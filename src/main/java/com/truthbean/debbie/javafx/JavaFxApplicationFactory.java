/**
 * Copyright (c) 2020 TruthBean(Rogar·Q)
 * Debbie is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package com.truthbean.debbie.javafx;

import com.truthbean.Logger;
import com.truthbean.debbie.bean.DebbieApplicationContext;
import com.truthbean.debbie.bean.GlobalBeanFactory;
import com.truthbean.debbie.boot.AbstractApplicationFactory;
import com.truthbean.debbie.boot.DebbieApplication;
import com.truthbean.debbie.event.DefaultEventPublisher;
import com.truthbean.debbie.properties.DebbieConfigurationFactory;
import com.truthbean.logger.LoggerFactory;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 * Created on 2020-07-02 11:17.
 */
public class JavaFxApplicationFactory extends AbstractApplicationFactory {

    @Override
    public DebbieApplication factory(DebbieConfigurationFactory configurationFactory,
                                     DebbieApplicationContext applicationContext, ClassLoader classLoader) {
        GlobalBeanFactory globalBeanFactory = applicationContext.getGlobalBeanFactory();

        PrimaryStage primaryStage = globalBeanFactory.factory(PrimaryStage.class);
        PrimaryStageHolder.set(primaryStage);

        DefaultEventPublisher eventPublisher = globalBeanFactory.factory("eventPublisher");
        WindowsCloseEventListener.createInstance(eventPublisher);
        JavaFxApplication application = new JavaFxApplication(LOGGER, applicationContext);
        eventPublisher.addEventListener(application);
        return application;
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(JavaFxApplicationFactory.class);
}
