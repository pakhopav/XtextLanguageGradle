package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class ValidatorGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateValidator() {
        val file = createOrFindFile(extension.capitalize() + "Validator.java", myGenDir + "/validation")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            package $packageDir.validation;
            
            import com.intellij.xtextLanguage.xtext.inspections.AbstractValidator;
            import com.intellij.xtextLanguage.xtext.inspections.Check;
            
            public class ${extensionCapitalized}Validator extends AbstractValidator {

                //	@Check
                //	public void checkGreetingStartsWithCapital(Greeting greeting) {
                //		if (!Character.isUpperCase(greeting.getName().charAt(0))) {
                //			warning("Name should start with a capital",
                //					SimplePackage.Literals.GREETING__NAME,
                //					INVALID_NAME);
                //		}
                //	}
            }    
        """.trimIndent()
        )
        out.close()

    }
}
