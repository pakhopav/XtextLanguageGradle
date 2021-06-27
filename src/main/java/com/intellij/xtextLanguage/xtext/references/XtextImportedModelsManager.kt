package com.intellij.xtextLanguage.xtext.references

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Property
import com.intellij.util.xmlb.annotations.XCollection

@State(name = "XtextImportedModelsPackages", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
@Service(Service.Level.PROJECT)
class XtextImportedModelsManager private constructor() : PersistentStateComponent<XtextImportedModelsManager.State> {

    private val packages = mutableMapOf<String, String>()

    fun getPackages() = packages

    fun addModel(modelUri: String, packagePath: String) {
        packages.put(modelUri, packagePath)
    }

    fun refreshPackages(newPackages: Map<String, String>) {
        packages.clear()
        packages.putAll(newPackages)
    }

    override fun getState(): State? {
//        return State(packages.entries.map { Pair(it.key, it.value) }.toMutableList())
        return State(packages)
    }

    override fun loadState(state: State) {
        packages.putAll(state.modelPackages)
    }

    data class State(@Property(surroundWithTag = false) @XCollection(elementTypes = [Map.Entry::class]) val modelPackages: Map<String, String> = mutableMapOf())

    companion object {
        fun getInstance(project: Project): XtextImportedModelsManager {
            return project.getService(XtextImportedModelsManager::class.java)
        }
    }
}