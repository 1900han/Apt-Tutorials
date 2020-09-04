package com.megamind.apttutorials.compiler.activity.method

import com.megamind.apttutorials.compiler.activity.ActivityClass
import com.megamind.apttutorials.compiler.util.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * @description
 * @date 19:44 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class ConstantBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        activityClass.fields.forEach { field ->
            typeBuilder.addField(
                FieldSpec.builder(
                    String::class.java,
                    field.prefix + field.name.camelToUnderline().toUpperCase(),
                    Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
                ).initializer("\$S", field.name).build()
            )
        }
    }
}