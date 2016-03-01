package com.isuwang.soa.container.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.isuwang.soa.container.Container;
import com.isuwang.soa.container.ContainerStartup;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Logback Container
 *
 * @author craneding
 * @date 16/1/18
 */
public class LogbackContainer implements Container {

    @Override
    public void start() {
        try (InputStream logbackCnfgStream = new BufferedInputStream(ContainerStartup.loadInputStreamInClassLoader("logback.xml"))) {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            configurator.doConfigure(logbackCnfgStream);

            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
    }

}
