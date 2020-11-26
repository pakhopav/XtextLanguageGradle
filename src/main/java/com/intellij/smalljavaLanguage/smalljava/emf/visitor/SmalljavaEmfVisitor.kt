package com.intellij.smalljavaLanguage.smalljava.emf.visitor

import smallJava.SJClass
import smallJava.SJImport
import smallJava.SJProgram

open class SmalljavaEmfVisitor {
    open fun visitSJProgram(program: SJProgram) {
        program.imports.forEach {
            visitSJImport(it)
        }
        program.classes.forEach {
            visitSJClass(it)
        }
    }

    open fun visitSJImport(import: SJImport) {

    }

    open fun visitSJClass(sjClass: SJClass) {

    }
}