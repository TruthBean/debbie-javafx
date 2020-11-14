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

import com.truthbean.logger.LoggerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author TruthBean/Rogar·Q
 * @since 0.1.0
 * Created on 2020-07-02 12:16.
 */
public class DebbieJavaFxApplication extends Application {

    static {
        System.setProperty(LoggerFactory.NO_LOGGER, "true");
    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(WindowsCloseEventListener.getInstance());
        init(primaryStage);
    }

    void init(Stage stage) throws Exception {
        PrimaryStage primaryStage = PrimaryStageHolder.get();
        primaryStage.init(stage);
    }
}
