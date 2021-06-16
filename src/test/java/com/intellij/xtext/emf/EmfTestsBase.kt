package com.intellij.xtext.emf

//import com.intellij.calcLanguage.calc.emf.CalcEmfBridge
//import com.intellij.xtexttLanguage.xtextt.emf.XtexttEmfBridge
//import com.intellij.xtexttLanguage.xtextt.psi.XtexttFile
//import com.intellij.calc2Language.calc2.emf.Calc2EmfBridge
//import com.intellij.calc2Language.calc2.psi.Calc2File
//import com.intellij.smalljavaLanguage.smalljava.emf.SmalljavaEmfBridge
//import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile
//import com.intellij.calcLanguage.calc.emf.CalcEmfBridge
//import com.intellij.calcLanguage.calc.psi.CalcFile
//import com.intellij.entityLanguage.entity.emf.EntityEmfBridge
//import com.intellij.entityLanguage.entity.psi.EntityFile
//import com.intellij.statLanguage.stat.emf.StatEmfBridge
//import com.intellij.statLanguage.stat.psi.StatFile
import arithmetics.ArithmeticsPackage
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.xtext.AllTests
import junit.framework.TestCase
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.xtext.example.entity.entity.EntityPackage
import org.xtext.example.xtext.xtext.XtextPackage
import smallJava.SmallJavaFactory
import smallJava.SmallJavaPackage
import statemachine.StatemachinePackage
import java.nio.file.Files
import java.nio.file.Paths

open class EmfTestsBase(val myDataFolder: String) : BasePlatformTestCase() {

    open fun getCurrentInputFileName(extention: String): String? {
        return getTestName(true) + ".$extention"
    }

    override fun getTestDataPath(): String {
        return getBasePath()
    }

    override fun getBasePath(): String {
        return AllTests.getTestDataRoot() + myDataFolder
    }

    fun getModelFromXmi(fileName: String): EObject {
        EntityPackage.eINSTANCE.eClass()

        val reg = Resource.Factory.Registry.INSTANCE;
        val m = reg.getExtensionToFactoryMap();
        m.put("entity", XMIResourceFactoryImpl());

        val resSet = ResourceSetImpl();
        val resource = resSet.getResource(URI.createURI("$basePath/$fileName"), true);

        return resource.getContents().get(0);
    }
//
//    fun getEntityEmfModel(): Domainmodel? {
//        val fileName = getCurrentInputFileName("entity")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as EntityFile
//            val bridge = EntityEmfBridge()
//            return bridge.createEmfModel(file) as Domainmodel
//        }
//        return null
//    }
//
//    fun getCalcEmfModel(): arithmetics.Module? {
//        val fileName = getCurrentInputFileName("calc")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as CalcFile
//            val bridge = CalcEmfBridge()
//            return bridge.createEmfModel(file) as Module
//        }
//        return null
//    }

    //    fun getCalc2EmfModel(): Module? {
//        val fileName = getCurrentInputFileName("calc2")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as Calc2File
//            val bridge = Calc2EmfBridge()
//            return bridge.createEmfModel(file) as Module
//        }
//        return null
//    }
//
//    fun getStatEmfModel(): Statemachine? {
//        val fileName = getCurrentInputFileName("stat")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as StatFile
//            val bridge = StatEmfBridge()
//            return bridge.createEmfModel(file) as Statemachine
//        }
//        return null
//    }

    //    fun getXtextEmfModel(): Grammar? {
//        val fileName = getCurrentInputFileName("xtextt")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as XtexttFile
//            val bridge = XtexttEmfBridge()
//            return bridge.createEmfModel(file) as Grammar
//        }
//        return null
//    }
//    fun getSmalljavaEmfModel(): SJProgram? {
//        val fileName = getCurrentInputFileName("smalljava")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as SmalljavaFile
//            ggggg(file)
//            val bridge = SmalljavaEmfBridge()
//            return bridge.createEmfModel(file) as SJProgram
//        }
//        return null
//    }


    //    file.putUserData(NAMESPACE_PROVIDER_KEY, value = CachedValuesManager.getManager(file.getProject()).createCachedValue(() -> {
//        Map<String, List<String>> map = ConcurrentFactoryMap.createMap(key-> {
//            final DomFileDescription<?> description = DomManager.getDomManager(file.getProject()).getDomFileDescription(file);
//            if (description == null) return Collections.emptyList();
//            return description.getAllowedNamespaces(key, file);
//        }
//        );
//        return CachedValueProvider.Result.create(map, file);
//    }, false));
    fun ggggg(elem: PsiElement) {
        val project = elem.project
        val manager = CachedValuesManager.getManager(project)
        val eObj = SmallJavaFactory.eINSTANCE.createSJClass()
        val provider: CachedValueProvider<EObject> = CachedValueProvider<EObject> {
            CachedValueProvider.Result.create(
                eObj,
                PsiModificationTracker.MODIFICATION_COUNT
            )
        }
        val cached = manager.createCachedValue(provider)
        val key: Key<CachedValue<EObject>> = Key.create("Associated emf object")
        elem.putUserData(key, cached)
        print("")
    }

    fun persistSmalljavaEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("smalljava")
        SmallJavaPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("smalljava", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistEntityEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("entity")
        EntityPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("entity", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistCalc2EmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("calc2")
        ArithmeticsPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("calc2", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistCalcEmfModel(model: arithmetics.Module) {
        val testFileName = getCurrentInputFileName("calc")
        ArithmeticsPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("calc", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
            URI.createFileURI(
                "$basePath/$testFileName"
            )
        )

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistStatEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("stat")
        StatemachinePackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("stat", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model)
        resource.save(null)
    }

    fun persistXtexttEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("xtextt")
        XtextPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("xtextt", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model)
        resource.save(null)
    }

    fun assertEqualXmi(compareWith: String, extention: String) {
        val testFileName = getCurrentInputFileName(extention)
        val myFileContent = Files.readAllLines(Paths.get("$basePath/$testFileName"))
        val expectedContent = Files.readAllLines(Paths.get("$basePath/$compareWith"))
        TestCase.assertEquals(myFileContent, expectedContent)
    }

}