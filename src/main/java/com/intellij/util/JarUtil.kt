package com.intellij.util

import org.apache.xerces.dom.ElementImpl
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.*
import java.util.jar.*
import javax.xml.parsers.DocumentBuilderFactory


class JarUtil {
    companion object {
        @JvmStatic
        fun getEcoreModelUris(jarFile: JarFile): List<String>? {
            val result = mutableListOf<String>()
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
                    result.add(uriItem.nodeValue)
                }
            }
            return if (result.isEmpty()) null else result
        }

        @JvmStatic
        fun getBasePackageOfJar(jarFile: JarFile, uri: String): String {
            val pluginXmlEntry = jarFile.getJarEntry("plugin.xml")
            val inputStream = jarFile.getInputStream(pluginXmlEntry)
            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream)
            val packageNode = findAppropriatePackageTag(doc, uri) ?: return ""
            val classItemValue = packageNode.attributes.getNamedItem("class").nodeValue
            val ePackageName = classItemValue.split(".").last()
            val basePackagePath = classItemValue.slice(0..classItemValue.length - ePackageName.length - 2)
            return basePackagePath
        }

        @JvmStatic
        fun getAllClassifiersNames(jarFile: JarFile, uri: String): List<String> {
            val result = mutableListOf<String>()
            val ecoreEntry = findEcoreEntry(jarFile, uri)
            val ecoreDoc =
                DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(jarFile.getInputStream(ecoreEntry))
            val classifiersTags = ecoreDoc.getElementsByTagName("eClassifiers")
            for (i in 0 until classifiersTags.length) {
                val classifierTag = classifiersTags.item(i)
                result.add(classifierTag.attributes.getNamedItem("name").nodeValue)
            }
            return result
        }

        @JvmStatic
        fun getEObjectAndEDatatypeNames(jarFile: JarFile, uri: String): Map<String, String?> {
            val result = mutableMapOf<String, String?>()
            val ecoreEntry = findEcoreEntry(jarFile, uri)
            val ecoreDoc =
                DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(jarFile.getInputStream(ecoreEntry))
            val classifiersTags = ecoreDoc.getElementsByTagName("eClassifiers")
            for (i in 0 until classifiersTags.length) {
                val classifierTag = classifiersTags.item(i)
                val instanceClassName = classifierTag.attributes.getNamedItem("instanceClassName")?.nodeValue
                result.put(classifierTag.attributes.getNamedItem("name").nodeValue, instanceClassName)
            }
            return result
        }

        @JvmStatic
        fun createJarWithFiles(jarPath: String, files: List<File>) {
            val manifest = Manifest()
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0")
            val target = JarOutputStream(FileOutputStream(jarPath), manifest)
            for (file in files) {
                addFileToJar(file, target)
            }
            target.close()
        }

        @JvmStatic
        @Throws(IOException::class)
        fun addFileToJar(source: File, target: JarOutputStream) {
            var inputStream: BufferedInputStream? = null
            try {
                if (source.isDirectory()) {
                    var name: String = source.getPath().replace("\\", "/")
                    if (!name.isEmpty()) {
                        if (!name.endsWith("/")) name += "/"
                        val entry = JarEntry(name)
                        entry.time = source.lastModified()
                        target.putNextEntry(entry)
                        target.closeEntry()
                    }
                    for (nestedFile in source.listFiles()) addFileToJar(nestedFile, target)
                    return
                }
                val entry = JarEntry(source.getPath().replace("\\", "/"))
                entry.time = source.lastModified()
                target.putNextEntry(entry)
                inputStream = BufferedInputStream(FileInputStream(source))
                val buffer = ByteArray(1024)
                while (true) {
                    val count = inputStream.read(buffer)
                    if (count == -1) break
                    target.write(buffer, 0, count)
                }
                target.closeEntry()
            } finally {
                inputStream?.close()
            }
        }

        private fun findAppropriatePackageTag(doc: Document, uri: String): Node? {
            val packageNodes = doc.getElementsByTagName("package")
            for (i in 0 until packageNodes.length) {
                val node = packageNodes.item(i)
                if (node.attributes.getNamedItem("uri").nodeValue == uri) return node
            }
            return null
        }

        private fun findEcoreEntry(jarFile: JarFile, uri: String): JarEntry? {
            val iterator = jarFile.entries()
            while (iterator.hasMoreElements()) {
                val entry = iterator.nextElement()
                if (entry.name.endsWith(".ecore")) {
                    val doc =
                        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(jarFile.getInputStream(entry))
                    val rootTag = doc.getElementsByTagName("ecore:EPackage").item(0) ?: continue
                    val nsUri = rootTag.attributes.getNamedItem("nsURI")?.nodeValue ?: continue
                    if (nsUri == uri) return entry
                }
            }
            return null
        }
    }
}