package com.megamind.apttutorials.compiler.activity

import com.bennyhuo.aptutils.types.packageName
import javax.lang.model.element.TypeElement
import com.bennyhuo.aptutils.types.simpleName
import com.megamind.apttutorials.compiler.activity.entity.Field
import java.util.*
import javax.lang.model.element.Modifier

/**
 * @description
 * @date 17:40 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class ActivityClass(val typeElement: TypeElement) {
    val simpleName: String = typeElement.simpleName()
    val packageName: String = typeElement.packageName()

    val fields = TreeSet<Field>()

    val isAbstract = typeElement.modifiers.contains(Modifier.ABSTRACT)
    val builder = ActivityClassBuilder(this)

    val isKotlin = typeElement.getAnnotation(META_DATA) != null

    override fun toString(): String {
        return "$packageName.$simpleName[${fields.joinToString()}]"
    }

    companion object {
        val META_DATA = Class.forName("kotlin.Metadata") as Class<Annotation>
    }
}
