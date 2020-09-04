package com.megamind.apttutorials.compiler.activity.method

import com.megamind.apttutorials.compiler.activity.ActivityClass
import com.megamind.apttutorials.compiler.prebuilt.ACTIVITY
import com.megamind.apttutorials.compiler.prebuilt.BUNDLE
import com.megamind.apttutorials.compiler.prebuilt.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * @description
 * @date 22:52 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class SaveStateMethodBuilder(private val activityClass: ActivityClass) {
    fun build(typeBuilder: TypeSpec.Builder) {
        val saveStateMethodBuilder = MethodSpec.methodBuilder("saveState")
            .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
            .returns(TypeName.VOID)
            .addParameter(ACTIVITY.java, "instance")
            .addParameter(BUNDLE.java, "outState")
            .beginControlFlow("if(instance instanceof \$T)", activityClass.typeElement)
            .addStatement(
                "\$T typedInstance = (\$T) instance",
                activityClass.typeElement,
                activityClass.typeElement
            )
            .addStatement("\$T intent = new \$T()", INTENT.java, INTENT.java)

        activityClass.fields.forEach { field ->
            val name = field.name
            if (field.isPrivate) {
                saveStateMethodBuilder.addStatement(
                    "intent.putExtra(\$S, typedInstance.get\$L())",
                    name,
                    name.capitalize()
                )
            } else {
                saveStateMethodBuilder.addStatement(
                    "intent.putExtra(\$S, typedInstance.\$L)",
                    name,
                    name
                )
            }
        }
        saveStateMethodBuilder.addStatement("outState.putAll(intent.getExtras())").endControlFlow()
        typeBuilder.addMethod(saveStateMethodBuilder.build())
    }
}