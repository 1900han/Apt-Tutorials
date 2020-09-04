package com.megamind.apttutorials.compiler.activity

import com.megamind.apttutorials.compiler.activity.method.*
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.kotlinpoet.FileSpec
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier
import javax.tools.StandardLocation

/**
 * @description
 * @date 19:38 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/

class ActivityClassBuilder(private val activityClass: ActivityClass) {
    companion object {
        const val POSIX = "Builder"
        const val METHOD_NAME = "start"
        const val METHOD_NAME_NO_OPTIONAL = METHOD_NAME + "WithoutOptional"
        const val METHOD_NAME_FOR_OPTIONAL = METHOD_NAME + "WithOptional"
        const val METHOD_NAME_FOR_OPTIONALS = METHOD_NAME + "WithOptionals"
    }

    fun build(filer: Filer) {
        if (activityClass.isAbstract) return
        val typeBuilder = TypeSpec.classBuilder(activityClass.simpleName + POSIX)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        ConstantBuilder(activityClass).build(typeBuilder)
        StartMethodBuilder(activityClass).build(typeBuilder)
        SaveStateMethodBuilder(activityClass).build(typeBuilder)
        InjectMethodBuilder(activityClass).build(typeBuilder)

        if (activityClass.isKotlin) {
            val fileBuilder =
                FileSpec.builder(activityClass.packageName, activityClass.simpleName + POSIX)
            StartKotlinFunctionBuilder(activityClass).build(fileBuilder)
            writeKotlinToFile(filer, fileBuilder.build())
        }
        writeJavaToFile(filer, typeBuilder.build())
    }

    private fun writeKotlinToFile(filer: Filer, fileSpec: FileSpec) {
        val fileObject = filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            activityClass.packageName,
            fileSpec.name + ".kt"
        )
        fileObject.openWriter().also(fileSpec::writeTo).close()
    }

    private fun writeJavaToFile(filer: Filer, typeSpec: TypeSpec) {
        try {
            JavaFile.builder(activityClass.packageName, typeSpec).build().writeTo(filer)
        } catch (e: Exception) {
        }
    }
}