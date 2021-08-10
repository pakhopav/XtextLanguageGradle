package com.intellij.xtext.persistence

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Attribute

@State(name = "XtextLanguageInfo", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
@Service(Service.Level.PROJECT)
class XtextLanguageInfoManager private constructor() : PersistentStateComponent<XtextLanguageInfoManager.State> {
    private var name = ""
    private var extension = ""

    fun getName() = name
    fun getExtension() = extension

    fun setName(newName: String) {
        name = newName
    }

    fun setExtension(newExtension: String) {
        extension = newExtension
    }

    fun setNameAndExtension(newName: String, newExtension: String) {
        name = newName
        extension = newExtension
    }

    override fun getState(): State? {
        return State(name, extension)
    }

    override fun loadState(state: State) {
        name = state.languageName
        extension = state.languageExtension
    }

    data class State(@Attribute val languageName: String = "", @Attribute val languageExtension: String = "")

    companion object {
        fun getInstance(project: Project): XtextLanguageInfoManager {
            return project.getService(XtextLanguageInfoManager::class.java)
        }
    }
}