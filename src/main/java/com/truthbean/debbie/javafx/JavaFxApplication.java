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
import com.truthbean.debbie.bean.GlobalBeanFactory;
import com.truthbean.debbie.boot.AbstractApplication;
import com.truthbean.debbie.boot.DebbieApplication;
import com.truthbean.debbie.concurrent.NamedThreadFactory;
import com.truthbean.debbie.concurrent.ThreadPooledExecutor;
import com.truthbean.debbie.core.ApplicationContext;
import com.truthbean.debbie.event.DefaultEventPublisher;
import com.truthbean.debbie.properties.DebbieConfigurationCenter;
import com.truthbean.logger.LoggerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ThreadFactory;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 * Created on 2020-07-02 11:19.
 */
public class JavaFxApplication extends AbstractApplication  {
    private static final Logger logger = LoggerFactory.getLogger(JavaFxApplication.class);

    private static JavaFxApplication application;
    public JavaFxApplication() {
        application = this;
    }

    public static JavaFxApplication getApplication() {
        return application;
    }

    @Override
    public DebbieApplication init(DebbieConfigurationCenter configurationCenter, ApplicationContext applicationContext,
                                  ClassLoader classLoader) {
        logger.trace("init ... ");
        GlobalBeanFactory globalBeanFactory = applicationContext.getGlobalBeanFactory();

        PrimaryStage primaryStage = globalBeanFactory.factory(PrimaryStage.class);
        PrimaryStageHolder.set(primaryStage);

        DefaultEventPublisher eventPublisher = globalBeanFactory.factory("eventPublisher");
        WindowsCloseEventListener.createInstance(eventPublisher);
        super.setLogger(logger);

        return this;
    }

    private final ThreadFactory namedThreadFactory = new NamedThreadFactory("javafx-application-");
    private final ThreadPooledExecutor singleThreadPool = new ThreadPooledExecutor(1, 1, namedThreadFactory);

    @Override
    protected void start(long beforeStartTime, String... args) {
        double uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        logger.info(() -> "application start time spends " + (System.currentTimeMillis() - beforeStartTime) + "ms" +
                " ( JVM running for "  + uptime + "ms )");
        postBeforeStart();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.exit(args)));
        singleThreadPool.execute(() -> Application.launch(DebbieJavaFxApplication.class, args));
    }

    @Override
    protected void exit(long beforeStartTime, String... args) {
        logger.trace(() -> "application running time spends " + (System.currentTimeMillis() - beforeStartTime) + "ms");
        singleThreadPool.destroy();
        try {
            Stage stage = PrimaryStageHolder.get().getStage();
            if (stage != null && stage.isShowing()) {
                stage.close();
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
