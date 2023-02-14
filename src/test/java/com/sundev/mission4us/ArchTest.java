package com.sundev.mission4us;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sundev.mission4us");

        noClasses()
            .that()
            .resideInAnyPackage("com.sundev.mission4us.service..")
            .or()
            .resideInAnyPackage("com.sundev.mission4us.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.sundev.mission4us.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
