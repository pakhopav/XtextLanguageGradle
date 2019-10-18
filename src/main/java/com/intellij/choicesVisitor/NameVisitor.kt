//package com.intellij.choicesVisitor
//
//import com.intellij.choicesLanguage.choices.psi.*
//import com.intellij.psi.PsiElement
//
//class NameVisitor {
//    fun visitDomainmodel (node : ChoicesDomainmodel): PsiElement?{
//        return null
//    }
//    fun visitChoice (node : ChoicesChoice): PsiElement?{
//        node.afterMany?.let {visitAfterMany(it)?.let {return@visitChoice it  }}
//        node.choicesSimple?.let {visitChoicesSimple(it)?.let {return@visitChoice it  }}
//        node.choicesSameName?.let {visitChoicesSameName(it)?.let {return@visitChoice it  }}
//        node.literalAfterCall?.let {visitLiteralAfterCall(it)?.let {return@visitChoice it  }}
//        node.choicesDeep?.let {visitChoicesDeep(it)?.let {return@visitChoice it  }}
//        node.sequenceItem?.let {visitSequenceItem(it)?.let {return@visitChoice it  }}
//        return null
//    }
//    fun  visitAfterMany (node : ChoicesAfterMany) : PsiElement?{
//        visitNamedElement(node.namedElement)?.let{return@visitAfterMany it }
//        return null
//    }
//    fun  visitChoicesSimple (node : ChoicesChoicesSimple) : PsiElement?{
//        node.whatever?.let {visitWhatever(it)?.let {return@visitChoicesSimple it  }}
//        node.namedElement?.let {visitNamedElement(it)?.let {return@visitChoicesSimple it  }}
//        node.namedElement2?.let {visitNamedElement2(it)?.let {return@visitChoicesSimple it  }}
//        node.namedElement3?.let {visitNamedElement3(it)?.let {return@visitChoicesSimple it  }}
//        return null
//    }
//    fun  visitChoicesSameName (node : ChoicesChoicesSameName) : PsiElement?{
//        // Same RuleCall in different branches problem
//        node.whatever?.let {visitWhatever(it)?.let {return@visitChoicesSameName it  }}
//        node.namedElement?.let {visitNamedElement(it)?.let {return@visitChoicesSameName it  }}
//        return null
//    }
//    fun  visitChoicesChoicesMultipleElementInBranch (node : ChoicesChoicesMultipleElementInBranch) : PsiElement?{
//        node.whatever?.let {visitWhatever(it)?.let {return@visitChoicesChoicesMultipleElementInBranch it  }}
//        node.namedElementList.forEach{visitNamedElement(it)?.let {return@visitChoicesChoicesMultipleElementInBranch it }}
//        node.namedElement2?.let {visitNamedElement2(it)?.let {return@visitChoicesChoicesMultipleElementInBranch it  }}
//        return null
//    }
//    fun  visitLiteralAfterCall (node : ChoicesLiteralAfterCall) : PsiElement?{
//        return node.id
//    }
//    fun  visitChoicesDeep (node : ChoicesChoicesDeep) : PsiElement?{
//        node.namedElement?.let {visitNamedElement(it)?.let {return@visitChoicesDeep it  }}
//        node.choice?.let {visitChoice(it)?.let {return@visitChoicesDeep it  }}
//
//        return null
//    }
//    fun  visitSequenceItem (node : ChoicesSequenceItem) : PsiElement?{
//
//        return node.id
//    }
//
//    fun  visitWhatever (node : ChoicesWhatever) : PsiElement?{
//
//        return null
//    }
//    fun  visitNamedElement (node : ChoicesNamedElement) : PsiElement?{
//
//        return node.id
//    }
//    fun  visitNamedElement2 (node : ChoicesNamedElement2) : PsiElement?{
//
//        return node.id
//
//    }
//    fun  visitNamedElement3 (node : ChoicesNamedElement3) : PsiElement?{
//
//        return node.id
//    }
//}