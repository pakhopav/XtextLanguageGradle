package com.intellij.calcLanguage.calc.emf.util

import arithmetics.ArithmeticsFactory
import arithmetics.ArithmeticsPackage

class CalcEmfBridgeUtil {
    companion object {
        val MODULE = ModuleRule()
        val ADDITION = AdditionRule()
        val MULTIPLICATION = MultiplicationRule()
        val IMPORT = ImportRule()
        val eFACTORY = ArithmeticsFactory.eINSTANCE
        val ePACKAGE = ArithmeticsPackage.eINSTANCE

    }
}