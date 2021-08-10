package com.intellij.xtext.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PsiErrorElementUtil
import com.intellij.xtext.generator.MainGenerator
import com.intellij.xtext.language.psi.XtextFile
import com.intellij.xtext.language.psi.XtextREFERENCEGrammarGrammarID
import com.intellij.xtext.language.reference.XtextReferenceUtil
import com.intellij.xtext.metamodel.MetaContextImpl
import com.intellij.xtext.module.GrammarKitGenerator
import com.intellij.xtext.persistence.XtextLanguageInfoManager
import org.apache.commons.io.FileUtils
import java.io.File


class GenerateAction : AnAction() {
    private val separator = File.separator

    override fun update(e: AnActionEvent) {
        val project = e.project
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        if (project != null && psiFile is XtextFile) {
            e.presentation.isVisible = true
            val hasParsingErrors = PsiErrorElementUtil.hasErrors(project, psiFile.virtualFile)
//            val hasOtherErrors = hasResolveErrors(project, psiFile)
            e.presentation.isEnabled = !hasParsingErrors
        } else {
            e.presentation.isEnabledAndVisible = false

        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        if (project != null && psiFile is XtextFile) {
            val grammars = mutableListOf(psiFile)
            findUsedGrammars(psiFile, grammars)
            val extension = XtextLanguageInfoManager.getInstance(project).getExtension()
            val context = MetaContextImpl(grammars)
            var targetPath = project.basePath ?: return
            //Can use RootManager???
            targetPath += "${separator}src${separator}main${separator}java${separator}"
            cleanPsiAndEmfDirectories(targetPath, extension)
            val generator = MainGenerator(extension, context, targetPath)

            saveAll(project)
            generator.generate()
            LocalFileSystem.getInstance().refresh(false)

            val gKitGenerator = GrammarKitGenerator(extension, context, project)
            gKitGenerator.launchGrammarKitGeneration(true)
            gKitGenerator.updatePluginXml()

            LocalFileSystem.getInstance().findFileByIoFile(File(project.basePath))?.refresh(false, true)
        }
    }

    private fun cleanPsiAndEmfDirectories(sourceRootPath: String, extension: String) {
        val emfDir = File("$sourceRootPath${extension}Language${separator}${extension}${separator}emf")
        if (emfDir.exists()) {
            FileUtils.cleanDirectory(emfDir)
        }
        val psiDir = File("$sourceRootPath${extension}Language${separator}${extension}${separator}psi")
        if (psiDir.exists()) {
            FileUtils.cleanDirectory(psiDir)
        }
    }


    //FIXME replace method, or persist used grammars info
    private fun findUsedGrammars(grammar: XtextFile, result: MutableList<XtextFile>) {
        val usedGrammarsNames =
            PsiTreeUtil.findChildrenOfType(grammar, XtextREFERENCEGrammarGrammarID::class.java).map { it.text }
        usedGrammarsNames.forEach {
            val grms = XtextReferenceUtil.findGrammarsInProjectByName(grammar.project, it)
            if (grms.size == 1) {
                val foundGrammar = grms[0]
                result.add(foundGrammar)
                findUsedGrammars(foundGrammar, result)
            }
        }
    }

    private fun saveAll(project: Project) {
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        FileDocumentManager.getInstance().saveAllDocuments()
    }
}