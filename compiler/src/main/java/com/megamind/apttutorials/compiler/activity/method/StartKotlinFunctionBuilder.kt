package com.megamind.apttutorials.compiler.activity.method

import com.megamind.apttutorials.compiler.activity.ActivityClass
import com.megamind.apttutorials.compiler.activity.ActivityClassBuilder
import com.megamind.apttutorials.compiler.activity.entity.OptionalField
import com.megamind.apttutorials.compiler.prebuilt.ACTIVITY_BUILDER
import com.megamind.apttutorials.compiler.prebuilt.CONTEXT
import com.megamind.apttutorials.compiler.prebuilt.INTENT
import com.squareup.kotlinpoet.*

/**
 * @description
 * @date 23:41 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class StartKotlinFunctionBuilder(private val activityClass: ActivityClass) {
    fun build(fileBuilder: FileSpec.Builder) {
        val name = ActivityClassBuilder.METHOD_NAME + activityClass.simpleName
        val functionBuilder = FunSpec.builder(name).receiver(CONTEXT.kotlin)
            .addModifiers(KModifier.PUBLIC)
            .returns(UNIT)
            .addStatement(
                "val intent= %T(this, %T::class.java)",
                INTENT.kotlin,
                activityClass.typeElement
            )

        activityClass.fields.forEach { field ->
            val name = field.name
            val className = field.asKotlinTypeName()

            if (field is OptionalField) {
                functionBuilder.addParameter(
                    ParameterSpec.builder(name, className).defaultValue("null").build()
                )
            } else {
                functionBuilder.addParameter(name, className)
            }

            functionBuilder.addStatement("intent.putExtra(%S, %L)", name, name)
        }

        functionBuilder.addStatement(
            "%T.INSTANCE.startActivity(this, intent)",
            ACTIVITY_BUILDER.kotlin
        )
        fileBuilder.addFunction(functionBuilder.build())
    }
}