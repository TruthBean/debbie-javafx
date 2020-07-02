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
import com.truthbean.debbie.boot.AbstractDebbieApplication;
import com.truthbean.debbie.event.GenericEventListener;
import javafx.application.Application;

import java.lang.management.ManagementFactory;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 * Created on 2020-07-02 11:19.
 */
public class JavaFxApplication extends AbstractDebbieApplication implements GenericEventListener<ApplicationExitEvent> {
    private final Logger logger;
    public JavaFxApplication(Logger logger, DebbieApplicationContext applicationContext) {
        super(logger, applicationContext);
        this.logger = logger;
    }

    @Override
    protected void start(long beforeStartTime, String... args) {
        double uptime = ManagementFactory.getRuntimeMXBean().getUptime();
        logger.info(() -> "application start time spends " + (System.currentTimeMillis() - beforeStartTime) + "ms" +
                " ( JVM running for "  + uptime + "ms )");
        postBeforeStart();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.exit(args)));
        Application.launch(DebbieJavaFxApplication.class, args);
    }

    @Override
    protected void exit(long beforeStartTime, String... args) {
        logger.trace(() -> "application running time spends " + (System.currentTimeMillis() - beforeStartTime) + "ms");
    }

    @Override
    public void onEvent(ApplicationExitEvent event) {
        logger.info(() -> "application closed by javafx");
        this.exit();
    }

    @Override
    public boolean async() {
        return true;
    }

    @Override
    public Class<ApplicationExitEvent> getEventType() {
        return ApplicationExitEvent.class;
    }
}
