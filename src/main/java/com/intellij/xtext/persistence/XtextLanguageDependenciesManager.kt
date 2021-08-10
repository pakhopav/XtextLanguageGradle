package com.intellij.xtext.persistence

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Property
import com.intellij.util.xmlb.annotations.XCollection

@State(name = "XtextImportedModelsPackages", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
@Service(Service.Level.PROJECT)
class XtextLanguageDependenciesManager private constructor() :
    PersistentStateComponent<XtextLanguageDependenciesManager.State> {

    private val ecoreModels = mutableMapOf<String, String>()
    private val grammars = mutableMapOf<String, String>()

    fun getPackages() = ecoreModels
    fun getGrammars() = grammars

    fun addModel(modelUri: String, madelPath: String) {
        ecoreModels.put(modelUri, madelPath)
    }

    fun addGrammar(grammarFQName: String, grammarPath: String) {
        ecoreModels.put(grammarFQName, grammarPath)
    }


    fun refreshGrammars(newGrammars: Map<String, String>) {
        grammars.clear()
        grammars.putAll(newGrammars)
    }

    fun refreshEcoreModels(newModels: Map<String, String>) {
        ecoreModels.clear()
        ecoreModels.putAll(newModels)
    }

    override fun getState(): State? {
        return State(ecoreModels, grammars)
    }

    override fun loadState(state: State) {
        ecoreModels.putAll(state.ecoreModels)
        grammars.putAll(state.grammars)
    }

    data class State(
        @Property(surroundWithTag = false) @XCollection(elementTypes = [Map.Entry::class]) val ecoreModels: Map<String, String> = mutableMapOf(),
        @Property(surroundWithTag = false) @XCollection(elementTypes = [Map.Entry::class]) val grammars: Map<String, String> = mutableMapOf()
    )

    companion object {
        fun getInstance(project: Project): XtextLanguageDependenciesManager {
            return project.getService(XtextLanguageDependenciesManager::class.java)
        }
    }
}