package com.intellij.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.util.PsiErrorElementUtil
import com.intellij.xtextLanguage.xtext.psi.XtextFile


class GenerateAction : AnAction() {

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

    override fun actionPerformed(event: AnActionEvent) {
        val currentProject = event.getProject()
        val dlgMsg = StringBuffer(event.getPresentation().getText().toString() + " Selected!")
        val dlgTitle: String = event.getPresentation().getDescription()
        // If an element is selected in the editor, add info about it.
        // If an element is selected in the editor, add info about it.
        val nav = event.getData(CommonDataKeys.NAVIGATABLE)
        if (nav != null) {
            dlgMsg.append(java.lang.String.format("\nSelected Element: %s", nav.toString()))
        }
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon())
    }
//
//    private fun hasResolveErrors(project: Project, file: PsiFile) : Boolean{
//        val codeAnalyzer = DaemonCodeAnalyzer.getInstance(project) as DaemonCodeAnalyzerImpl
//        val document = PsiDocumentManager.getInstance(project).getDocument(file) ?: return false
//        val infos = codeAnalyzer.runMainPasses(file, document, EmptyProgressIndicator())
//        return false
//    }
}