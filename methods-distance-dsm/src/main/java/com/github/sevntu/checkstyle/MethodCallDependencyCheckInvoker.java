package com.github.sevntu.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.github.sevntu.checkstyle.analysis.DependencyInformationConsumer;

import java.io.File;
import java.util.List;

public class MethodCallDependencyCheckInvoker {

    private final Checker checker;

    public MethodCallDependencyCheckInvoker(final Configuration methodCallDependencyCheckConfig,
        final DependencyInformationConsumer consumer) throws CheckstyleException {
        final ModuleFactory moduleFactory = new DependencyInformationConsumerInjector(consumer);
        final TreeWalker tw = new TreeWalker();
        tw.setModuleFactory(moduleFactory);
        tw.finishLocalSetup();
        tw.setupChild(methodCallDependencyCheckConfig);
        checker = new Checker();
        checker.setModuleFactory(moduleFactory);
        checker.finishLocalSetup();
        checker.addFileSetCheck(tw);
    }

    public void invoke(final List<File> files) throws CheckstyleException {
        checker.process(files);
    }
}