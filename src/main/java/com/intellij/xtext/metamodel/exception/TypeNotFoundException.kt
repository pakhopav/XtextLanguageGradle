package com.intellij.xtext.metamodel.exception

class TypeNotFoundException(typeName: String) : Exception("${typeName} wasn`t found") {
}