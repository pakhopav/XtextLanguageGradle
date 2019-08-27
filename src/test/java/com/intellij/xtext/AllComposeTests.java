package com.intellij.xtext;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.application.ex.PathManagerEx;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;

public class AllComposeTests {
    private static final String TEST_DATA_FOLDER = "testData/composeFile";

    public static Test suite() {
        TestSuite result = new TestSuite();
        result.addTestSuite(XtextCompletionTest.class);
        return result;
    }

    @NotNull
    public static String getTestDataRoot() {
        String resources = PathManager.getResourceRoot(AllComposeTests.class, "/" + TEST_DATA_FOLDER);
        if (resources == null) {
            String home = PathManagerEx.getHomePath(AllComposeTests.class);
            if (home == null) {
                throw new IllegalStateException("Can't find neither resources root nor home path ");
            }
            resources = PathManagerEx.getHomePath(AllComposeTests.class) + "/plugins/Docker/Docker-compose/testData";
        }
        return resources + "/" + TEST_DATA_FOLDER;
    }
}
