package com.intellij.xtextLanguage.xtext.generator.models.elements

open class ParserRule : ModelRule() {
    override lateinit var name: String
    override lateinit var returnTypeText: String
    var bnfExtensionsStrings = mutableListOf<String>()
    override val alternativeElements = mutableListOf<RuleElement>()

    override var isDataTypeRule = false
    var isReferenced = false
    var isReferencedDelegateRule = false
    var isPrivate = false


//
//    constructor(myRule: XtextParserRule) {
//        name = myRule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
//        visitor.visitRule(myRule)
//        alternativeElements.addAll(visitor.getAlternativeElementsList())
//        returnTypeText = (visitor.getChangedType() ?: myRule.typeRef?.text ?: "").replace("^", "")
//        isPrivate = myRule.fragmentKeyword != null
//    }

//    constructor()



    fun copy(): ParserRule {
        val copy = ParserRule()
        copy.name = name
        copy.returnTypeText = returnTypeText
        copy.alternativeElements.addAll(alternativeElements)
        copy.isDataTypeRule = isDataTypeRule
        copy.bnfExtensionsStrings = bnfExtensionsStrings
        copy.isPrivate = isPrivate
        copy.isReferenced = isReferenced
        copy.isReferencedDelegateRule = isReferencedDelegateRule
        return copy
    }


}