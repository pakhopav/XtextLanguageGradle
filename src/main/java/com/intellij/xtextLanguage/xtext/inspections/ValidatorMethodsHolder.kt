package com.intellij.xtextLanguage.xtext.inspections

import org.eclipse.emf.ecore.EObject
import java.lang.reflect.Method

class ValidatorMethodsHolder(val validator: AbstractValidator) {

    val groupedMethods: Map<Class<*>, List<Method>>

    init {
        groupedMethods = initMethods()
    }

    fun getMethods(eObj: EObject): List<Method> {
        return groupedMethods.entries
                .filter { it.key.isAssignableFrom(eObj::class.java) }
                .flatMap { it.value }
    }

    private fun initMethods(): Map<Class<*>, List<Method>> {
        val allMethods = findAllValidatorMethods()
        val grouped = allMethods.groupBy { it.parameterTypes[0] }
        return grouped
    }

    private fun findAllValidatorMethods(): List<Method> {
        val validatorClass = validator.javaClass
        val withCheckAnnotation = filterWithCheckAnnotation(validatorClass.declaredMethods.toList())
        val withEObjectAsOnlyParameter = filterWithEObjectAsOnlyParameter(withCheckAnnotation)
        return withEObjectAsOnlyParameter
    }

    private fun filterWithCheckAnnotation(methods: List<Method>): List<Method> {
        val result = mutableListOf<Method>()
        methods.filter { it.annotations.isNotEmpty() }.forEach { method ->
            val annotationTypes = method.annotations.map { it.annotationClass }
            if (annotationTypes.contains(Check::class)) result.add(method)
        }
        return result
    }

    private fun filterWithEObjectAsOnlyParameter(methods: List<Method>): List<Method> {
        val result = mutableListOf<Method>()
        methods.filter { it.parameterCount == 1 }.forEach { method ->
            val parameterType = method.parameterTypes[0]
            if (EObject::class.java.isAssignableFrom(parameterType)) result.add(method)
        }
        return result
    }
}