package com.tngtech.archunit.exampletest;

import java.util.logging.Logger;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.example.layers.ClassViolatingCodingRules;
import com.tngtech.archunit.lang.CompositeArchRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;
import static com.tngtech.archunit.library.GeneralCodingRules.packageNameShouldMatchForTestsIdentifyingAs;
import static com.tngtech.archunit.library.GeneralCodingRules.testNameShouldMatchProduction;

@Category(Example.class)
public class CodingRulesTest {

    private final JavaClasses classes = new ClassFileImporter().importPackagesOf(ClassViolatingCodingRules.class);

    @Test
    public void classes_should_not_access_standard_streams_defined_by_hand() {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes);
    }

    @Test
    public void classes_should_not_access_standard_streams_from_library() {
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    }

    @Test
    public void classes_should_not_throw_generic_exceptions() {
        NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    }

    @Test
    public void classes_should_not_use_java_util_logging() {
        NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(classes);
    }

    @Test
    public void loggers_should_be_private_static_final() {
        fields().that().haveRawType(Logger.class)
                .should().bePrivate()
                .andShould().beStatic()
                .andShould().beFinal()
                .because("we agreed on this convention")
                .check(classes);
    }

    @Test
    public void classes_should_not_use_jodatime() {
        NO_CLASSES_SHOULD_USE_JODATIME.check(classes);
    }

    @Test
    public void classes_should_not_use_field_injection() {
        NO_CLASSES_SHOULD_USE_FIELD_INJECTION.check(classes);
    }

    @Test
    public void no_classes_should_access_standard_streams_or_throw_generic_exceptions() {
        CompositeArchRule.of(NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS)
                .and(NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS).check(classes);
    }

    @Test
    public void package_name_should_match_for_tests() {
        packageNameShouldMatchForTestsIdentifyingAs("Test").check(classes);
    }

    @Test
    public void prod_code_should_match_test() {
        testNameShouldMatchProduction("Test").check(classes);
    }
}
