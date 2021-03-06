package com.intellij.module

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.LocalTimeCounter
import com.intellij.xtextLanguage.xtext.XtextFileType
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID
import org.apache.xerces.dom.ElementImpl
import org.eclipse.emf.ecore.EPackage
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.util.jar.JarFile
import java.util.stream.Collectors
import javax.xml.parsers.DocumentBuilderFactory


class XtextModuleBuilderHelper(val project: Project = ProjectManager.getInstance().defaultProject) {
    private val srcPath = "src/main/resources/grammars"
    private val knownGrammars: Map<String, XtextFile>
//    private val knownJARs: Map<String, XtextFile>


    init {
        knownGrammars = initGrammars()
    }

    fun getKnownGrammar(fileName: String): XtextFile? {
        val fName = if (fileName.contains('.')) fileName.split('.').last() else fileName
        return knownGrammars.get(fName)
    }

    fun containsInKnownGrammars(fileName: String): Boolean {
        val fName = if (fileName.contains('.')) fileName.split('.').last() else fileName
        knownGrammars.keys.forEach {
            if (it.equals(fName)) return true
        }
        return false
    }


    private fun initGrammars(): Map<String, XtextFile> {
        val result = HashMap<String, XtextFile>()
        result.put("Terminals", findXtextFileByRelativePath("grammars/Terminals.xtext"))
        result.put("Xbase", findXtextFileByRelativePath("grammars/Xbase.xtext"))
        result.put("Xtext", findXtextFileByRelativePath("grammars/Xtext.xtext"))
        result.put("Xtype", findXtextFileByRelativePath("grammars/Xtype.xtext"))

//        val text  = BufferedReader(InputStreamReader(url.openStream())).lines().collect(Collectors.joining("\n"))
//        val termFile = PsiFileFactory.getInstance(project).createFileFromText("temp." + XtextFileType.INSTANCE.defaultExtension, XtextFileType.INSTANCE, text, LocalTimeCounter.currentTime(), true) as XtextFile
//
//
//        val srcFolder = File(srcPath)
//        srcFolder.listFiles().forEach {
//            val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(it)
//            virtualFile?.let {
//                val xtextFile = PsiManager.getInstance(project).findFile(virtualFile) as? XtextFile
//                xtextFile?.let {
//                    val fileName = PsiTreeUtil.findChildOfType(it, XtextGrammarID::class.java)?.validIDList?.last()?.text
//                        ?: return@forEach
//
//                    result.put(fileName, it)
//                }
//            }
//        }
        return result
    }

    fun getGrammarName(file: XtextFile): String {
        return PsiTreeUtil.findChildOfType(file, XtextGrammarID::class.java)?.validIDList?.last()?.text!!
    }


    fun getEcoreModelUri(jarFile: JarFile): String? {
        val jarEntry = jarFile.getJarEntry("plugin.xml") ?: return null
        val inputStream = jarFile.getInputStream(jarEntry)
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
        val nodeList = doc.getElementsByTagName("extension")
        for (i in 0 until nodeList.length) {
            var item = nodeList.item(i)
            val pointAttr = item.attributes.getNamedItem("point")
            if (pointAttr != null && pointAttr.nodeValue.equals("org.eclipse.emf.ecore.generated_package")) {
                val packageNode = (item as ElementImpl).getElementsByTagName("package")
                val uriItem = packageNode.item(0).attributes.getNamedItem("uri")
                return uriItem.nodeValue
            }
        }
        return null
    }


    fun getEcoreModelEPackage(jarFile: JarFile): EPackage? {
        var ePackage: EPackage? = null
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val jarEntry = jarFile.getJarEntry("plugin.xml") ?: return null
        val doc = documentBuilder.parse(jarFile.getInputStream(jarEntry))
        val nodeList = doc.getElementsByTagName("extension")
        var ePackagePath: String? = null
        loop@ for (i in 0 until nodeList.length) {
            var item = nodeList.item(i)
            val pointAttr = item.attributes.getNamedItem("point")
            if (pointAttr != null && pointAttr.nodeValue.equals("org.eclipse.emf.ecore.generated_package")) {
                val packageNode = (item as ElementImpl).getElementsByTagName("package")
                ePackagePath = packageNode.item(0).attributes.getNamedItem("class").nodeValue
                break@loop
            }
        }
        try {
            val c = Class.forName(ePackagePath)
            val instanceField = c.getDeclaredField("eINSTANCE")
            ePackage = instanceField.get(null) as EPackage?
        } catch (e: Throwable) {
        }
        return ePackage
    }


    fun findXtextFileByAbsolutePath(fileAbsolutePath: String): XtextFile {
        val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(fileAbsolutePath))
            ?: throw FileNotFoundException()
        val xtextFile = PsiManager.getInstance(project).findFile(virtualFile) as XtextFile
        return xtextFile
    }

    fun findXtextFileByRelativePath(fileRelativePath: String): XtextFile {

        val url = javaClass.classLoader.getResource(fileRelativePath)
        val text = BufferedReader(InputStreamReader(url.openStream())).lines().collect(Collectors.joining("\n"))
        val termFile = PsiFileFactory.getInstance(project).createFileFromText(
            "temp." + XtextFileType.INSTANCE.defaultExtension,
            XtextFileType.INSTANCE,
            text,
            LocalTimeCounter.currentTime(),
            true
        ) as? XtextFile


        return termFile ?: throw FileNotFoundException()
    }


    fun isValidGrammarName(name: String): Boolean {
        if (name.isEmpty()) return false
        return name.matches(Regex("[A-Za-z]*"))
    }

    fun isValidGrammarExtension(extension: String): Boolean {
        if (extension.isEmpty()) return false
        return extension.matches(Regex("[a-z]*"))
    }
}