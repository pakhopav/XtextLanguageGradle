package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class ValidatorGenerator(extension: String) : AbstractGenerator(extension) {
    fun generateValidator() {
        val file = createFile(extension.capitalize() + "Validator.java", myGenDir + "/inspection")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            package $packageDir.inspection;
            
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
