package com.intellij.calcLanguage.calc.emf.util

import arithmetics.ArithmeticsFactory
import arithmetics.ArithmeticsPackage
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule

abstract class CalcEmfBridgeRule : EmfBridgeRule {
    protected val eFACTORY = ArithmeticsFactory.eINSTANCE
    protected val ePACKAGE = ArithmeticsPackage.eINSTANCE

}