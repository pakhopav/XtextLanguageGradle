package com.intellij.xtext.module.wizard

import java.util.jar.JarFile

class EcoreModelJarInfo(
    val uri: String,
    var path: String? = null,
    var targetPath: String? = null,
    var file: JarFile? = null
) {
}