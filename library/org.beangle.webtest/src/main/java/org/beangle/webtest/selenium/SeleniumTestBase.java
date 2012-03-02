package org.beangle.webtest.selenium;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

/**
 * EMS 项目 Selenium单元测试用例基类，抄自 com.thoughtworks.selenium.SeleneseTestBase
 * 
 * @author qianjia
 */
public class SeleniumTestBase extends SeleniumTestBootstrap {

    protected StringBuffer verificationErrors = new StringBuffer();

    @AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        checkForVerificationErrors();
    }

    @BeforeMethod(dependsOnGroups = "webdriver.start")
    public void setTestContext(Method method) {
        selenium.setContext(method.getDeclaringClass().getSimpleName() + "." + method.getName());
    }

    /**
     * Asserts that there were no verification errors during the current test, failing immediately
     * if
     * any are found
     */
    @AfterMethod
    public void checkForVerificationErrors() {
        String verificationErrorString = verificationErrors.toString();
        clearVerificationErrors();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    /** Like assertTrue, but fails at the end of the test (during tearDown) */
    public void verifyTrue(boolean b) {
        try {
            assertTrue(b);
        } catch (Error e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Like assertFalse, but fails at the end of the test (during tearDown) */
    public void verifyFalse(boolean b) {
        try {
            assertFalse(b);
        } catch (Error e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Returns the body text of the current page */
    public String getText() {
        return selenium.getEval("this.page().bodyText()");
    }

    /** Like assertEquals, but fails at the end of the test (during tearDown) */
    public void verifyEquals(Object expected, Object actual) {
        try {
            assertEquals(expected, actual);
        } catch (Error e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Like assertEquals, but fails at the end of the test (during tearDown) */
    public void verifyEquals(boolean expected, boolean actual) {
        try {
            assertEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
        } catch (Error e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Like JUnit's Assert.assertEquals, but knows how to compare string arrays */
    public static void assertEquals(Object expected, Object actual) {
        if (expected == null) {
            assertTrue("Expected \"" + expected + "\" but saw \"" + actual + "\" instead", actual == null);
        } else if (expected instanceof String && actual instanceof String) {
            assertEquals((String) expected, (String) actual);
        } else if (expected instanceof String && actual instanceof String[]) {
            assertEquals((String) expected, (String[]) actual);
        } else if (expected instanceof String && actual instanceof Number) {
            assertEquals((String) expected, actual.toString());
        } else if (expected instanceof Number && actual instanceof String) {
            assertEquals(expected.toString(), (String) actual);
        } else if (expected instanceof String[] && actual instanceof String[]) {
            assertEquals((String[]) expected, (String[]) actual);
        } else {
            assertTrue("Expected \"" + expected + "\" but saw \"" + actual + "\" instead", expected
                    .equals(actual));
        }
    }

    /** Like JUnit's Assert.assertEquals, but handles "regexp:" strings like HTML Selenese */
    public static void assertEquals(String expected, String actual) {
        assertTrue("Expected \"" + expected + "\" but saw \"" + actual + "\" instead", seleniumEquals(
                expected, actual));
    }

    /**
     * Like JUnit's Assert.assertEquals, but joins the string array with commas, and handles
     * "regexp:"
     * strings like HTML Selenese
     */
    public static void assertEquals(String expected, String[] actual) {
        assertEquals(expected, join(actual, ','));
    }

    /**
     * Compares two strings, but handles "regexp:" strings like HTML Selenese
     * 
     * @param expectedPattern
     * @param actual
     * @return true if actual matches the expectedPattern, or false otherwise
     */
    public static boolean seleniumEquals(String expectedPattern, String actual) {
        if (expectedPattern == null || actual == null) {
            return expectedPattern == null && actual == null;
        }
        if (actual.startsWith("regexp:") || actual.startsWith("regex:") || actual.startsWith("regexpi:")
                || actual.startsWith("regexi:")) {
            // swap 'em
            String tmp = actual;
            actual = expectedPattern;
            expectedPattern = tmp;
        }
        Boolean b;
        b = handleRegex("regexp:", expectedPattern, actual, 0);
        if (b != null) {
            return b.booleanValue();
        }
        b = handleRegex("regex:", expectedPattern, actual, 0);
        if (b != null) {
            return b.booleanValue();
        }
        b = handleRegex("regexpi:", expectedPattern, actual, Pattern.CASE_INSENSITIVE);
        if (b != null) {
            return b.booleanValue();
        }
        b = handleRegex("regexi:", expectedPattern, actual, Pattern.CASE_INSENSITIVE);
        if (b != null) {
            return b.booleanValue();
        }

        if (expectedPattern.startsWith("exact:")) {
            String expectedExact = expectedPattern.replaceFirst("exact:", "");
            if (!expectedExact.equals(actual)) {
                System.out.println("expected " + actual + " to match " + expectedPattern);
                return false;
            }
            return true;
        }

        String expectedGlob = expectedPattern.replaceFirst("glob:", "");
        expectedGlob = expectedGlob.replaceAll("([\\]\\[\\\\{\\}$\\(\\)\\|\\^\\+.])", "\\\\$1");

        expectedGlob = expectedGlob.replaceAll("\\*", ".*");
        expectedGlob = expectedGlob.replaceAll("\\?", ".");
        if (!Pattern.compile(expectedGlob, Pattern.DOTALL).matcher(actual).matches()) {
            System.out.println("expected \"" + actual + "\" to match glob \"" + expectedPattern
                    + "\" (had transformed the glob into regexp \"" + expectedGlob + "\"");
            return false;
        }
        return true;
    }

    private static Boolean handleRegex(String prefix, String expectedPattern, String actual, int flags) {
        if (expectedPattern.startsWith(prefix)) {
            String expectedRegEx = expectedPattern.replaceFirst(prefix, ".*") + ".*";
            Pattern p = Pattern.compile(expectedRegEx, flags);
            if (!p.matcher(actual).matches()) {
                System.out.println("expected " + actual + " to match regexp " + expectedPattern);
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
        return null;
    }

    /**
     * Compares two objects, but handles "regexp:" strings like HTML Selenese
     * 
     * @see #seleniumEquals(String, String)
     * @return true if actual matches the expectedPattern, or false otherwise
     */
    public static boolean seleniumEquals(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }
        if (expected instanceof String && actual instanceof String) {
            return seleniumEquals((String) expected, (String) actual);
        }
        return expected.equals(actual);
    }

    /** Asserts that two string arrays have identical string contents */
    public static void assertEquals(String[] expected, String[] actual) {
        String comparisonDumpIfNotEqual = verifyEqualsAndReturnComparisonDumpIfNot(expected, actual);
        if (comparisonDumpIfNotEqual != null) {
            throw new AssertionError(comparisonDumpIfNotEqual);
        }
    }

    /**
     * Asserts that two string arrays have identical string contents (fails at the end of the test,
     * during tearDown)
     */
    public void verifyEquals(String[] expected, String[] actual) {
        String comparisonDumpIfNotEqual = verifyEqualsAndReturnComparisonDumpIfNot(expected, actual);
        if (comparisonDumpIfNotEqual != null) {
            verificationErrors.append(comparisonDumpIfNotEqual);
        }
    }

    private static String verifyEqualsAndReturnComparisonDumpIfNot(String[] expected, String[] actual) {
        boolean misMatch = false;
        if (expected.length != actual.length) {
            misMatch = true;
        }
        for (int j = 0; j < expected.length; j++) {
            if (!seleniumEquals(expected[j], actual[j])) {
                misMatch = true;
                break;
            }
        }
        if (misMatch) {
            return "Expected " + stringArrayToString(expected) + " but saw " + stringArrayToString(actual);
        }
        return null;
    }

    private static String stringArrayToString(String[] sa) {
        StringBuffer sb = new StringBuffer("{");
        for (int j = 0; j < sa.length; j++) {
            sb.append(" ").append("\"").append(sa[j]).append("\"");
        }
        sb.append(" }");
        return sb.toString();
    }

    private static String throwableToString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static String join(String[] sa, char c) {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < sa.length; j++) {
            sb.append(sa[j]);
            if (j < sa.length - 1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Like assertNotEquals, but fails at the end of the test (during tearDown) */
    public void verifyNotEquals(Object expected, Object actual) {
        try {
            assertNotEquals(expected, actual);
        } catch (AssertionError e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Like assertNotEquals, but fails at the end of the test (during tearDown) */
    public void verifyNotEquals(boolean expected, boolean actual) {
        try {
            assertNotEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
        } catch (AssertionError e) {
            verificationErrors.append(throwableToString(e));
        }
    }

    /** Asserts that two objects are not the same (compares using .equals()) */
    public static void assertNotEquals(Object expected, Object actual) {
        if (expected == null) {
            assertFalse("did not expect null to be null", actual == null);
        } else if (expected.equals(actual)) {
            fail("did not expect (" + actual + ") to be equal to (" + expected + ")");
        }
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    static public void assertTrue(String message, boolean condition) {
        if (!condition)
            fail(message);
    }

    static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    static public void assertFalse(boolean condition) {
        assertTrue(null, !condition);
    }

    /** Asserts that two booleans are not the same */
    public static void assertNotEquals(boolean expected, boolean actual) {
        assertNotEquals(Boolean.valueOf(expected), Boolean.valueOf(actual));
    }

    /** Sleeps for the specified number of milliseconds */
    public void pause(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    /** Clears out the list of verification errors */
    public void clearVerificationErrors() {
        verificationErrors = new StringBuffer();
    }

    @AfterMethod(alwaysRun = true)
    public void selectDefaultWindow() {
        if (selenium != null) {
            seleniumDriver.switchTo().defaultContent();
//            selenium.selectWindow("null");
        }
    }

    
}
