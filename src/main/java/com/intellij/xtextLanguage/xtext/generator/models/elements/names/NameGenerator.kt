package com.intellij.xtextLanguage.xtext.generator.models.elements.names

import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.codeStyle.NameUtil
import com.intellij.util.containers.JBIterable
import java.util.*

class NameGenerator {

    companion object {
        val g = NameGenerator()

        @JvmStatic
        fun toGKitClassName(text: String): String {
            return g.toIdentifier(text, null, Case.CAMEL)!!
        }

        @JvmStatic
        fun toGKitTypesName(text: String): String {
            return g.toIdentifier(text, null, Case.UPPER)!!
        }

        @JvmStatic
        fun toPropertyName(text: String): String {
            return g.toKotlinProperty(text)
        }
    }

    fun toKotlinProperty(text: String): String {
        if (text.isEmpty()) return ""
        val fixed = text.replace("[^:\\p{javaJavaIdentifierPart}]".toRegex(), "_")
        val allCaps: Boolean = Case.UPPER.apply(fixed).equals(fixed)
        val sb = StringBuilder()
        if (!Character.isJavaIdentifierStart(fixed[0]) && sb.length == 0) sb.append("_")
        val strings: Array<String> = NameUtil.nameToWords(fixed)
        sb.append(Case.LOWER.apply(strings[0]))
        for (i in 1 until strings.size) {
            sb.append(Case.CAMEL.apply(strings[i]))
        }
        return sb.toString()
    }

    fun toIdentifier(text: String, format: NameFormat?, cas: Case): String? {
        if (text.isEmpty()) return ""
        val fixed = text.replace("[^:\\p{javaJavaIdentifierPart}]".toRegex(), "_")
        val allCaps: Boolean = Case.UPPER.apply(fixed).equals(fixed)
        val sb = StringBuilder()
        if (!Character.isJavaIdentifierStart(fixed[0]) && sb.length == 0) sb.append("_")
        val strings: Array<String> = NameUtil.nameToWords(fixed)
        var i = 0
        val len = strings.size
        while (i < len) {
            val s = strings[i]
            if (cas === Case.CAMEL && s.startsWith("_") && !(i == 0 || i == len - 1)) {
                i++
                continue
            }
            if (cas === Case.UPPER && !s.startsWith("_") && !(i == 0 || StringUtil.endsWith(sb, "_"))) sb.append("_")
            if (cas === Case.CAMEL && !allCaps && Case.UPPER.apply(s).equals(s)) sb.append(s) else sb.append(cas.apply(s))
            i++
        }
        return if (format == null) sb.toString() else format.apply(sb.toString())
    }

    class NameFormat private constructor(format: String?) {
        val prefix: String?
        val suffix: String?
        fun apply(s: String): String {
            var s = s
            if (prefix != null) s = prefix + s
            if (suffix != null) s += suffix
            return s
        }

        fun strip(s: String): String {
            var s = s
            if (prefix != null && s.startsWith(prefix)) s = s.substring(prefix.length)
            if (suffix != null && s.endsWith(suffix)) s = s.substring(0, s.length - suffix.length)
            return s
        }

        companion object {
            val EMPTY = NameFormat("")
            fun from(format: String?): NameFormat {
                return if (StringUtil.isEmpty(format)) EMPTY else NameFormat(format)
            }
        }

        init {
            val parts = JBIterable.of(format?.split("/")) as JBIterable<String>
            prefix = parts.get(0)
            suffix = StringUtil.join(parts.skip(1), "")
        }
    }

    enum class Case {
        LOWER, UPPER, AS_IS, CAMEL;

        fun apply(s: String): String {
            return if (s.isEmpty()) s else when (this) {
                LOWER -> s.toLowerCase(Locale.ENGLISH)
                UPPER -> s.toUpperCase(Locale.ENGLISH)
                AS_IS -> s
                CAMEL -> s.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                        s.substring(1).toLowerCase(Locale.ENGLISH)
                else -> throw AssertionError()
            }
        }
    }
}