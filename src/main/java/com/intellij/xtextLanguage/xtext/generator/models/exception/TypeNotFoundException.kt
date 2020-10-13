package com.intellij.xtextLanguage.xtext.generator.models.exception

class TypeNotFoundException(typeName: String) : Exception("${typeName} wasn`t found") {
}