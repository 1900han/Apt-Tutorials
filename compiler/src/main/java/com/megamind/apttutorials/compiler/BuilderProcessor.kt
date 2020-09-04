package com.megamind.apttutorials.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.megamind.apttutorials.annotations.Builder
import com.megamind.apttutorials.annotations.Optional
import com.megamind.apttutorials.annotations.Required
import com.megamind.apttutorials.compiler.activity.ActivityClass
import com.megamind.apttutorials.compiler.activity.entity.Field
import com.megamind.apttutorials.compiler.activity.entity.OptionalField
import com.sun.tools.javac.code.Symbol
import java.lang.Exception
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

/**
 * @description
 * @date 17:09 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class BuilderProcessor : AbstractProcessor() {

    private val supportedAnnotations =
        setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_7

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return supportedAnnotations.mapTo(HashSet<String>(), Class<*>::getCanonicalName)
    }

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        env: RoundEnvironment
    ): Boolean {
        val activityClasses = HashMap<Element, ActivityClass>()
        env.getElementsAnnotatedWith(Builder::class.java).filter {
            it.kind.isClass
        }.forEach { element: Element ->
            try {
                if (element.asType().isSubTypeOf("android.app.Activity")) {
                    activityClasses[element] = ActivityClass(element as TypeElement)
                } else {
                    Logger.error(element, "Unsupported typeElement:${element.simpleName}")
                }
            } catch (e: Exception) {
                Logger.logParsingError(element, Builder::class.java, e)
            }
        }

        env.getElementsAnnotatedWith(Required::class.java).filter {
            it.kind == ElementKind.FIELD
        }.forEach{element: Element ->
            activityClasses[element.enclosingElement]?.fields?.add(Field(element as Symbol.VarSymbol))
                ?:Logger.error(element,"Field$element annotated as Required while ${element.enclosingElement} not annotated.")
        }

        env.getElementsAnnotatedWith(Optional::class.java).filter {
            it.kind == ElementKind.FIELD
        }.forEach{element: Element ->
            activityClasses[element.enclosingElement]?.fields?.add(OptionalField(element as Symbol.VarSymbol))
                ?:Logger.error(element,"Field$element annotated as Optional while ${element.enclosingElement} not annotated.")
        }

        activityClasses.values.forEach{
            it.builder.build(AptContext.filer)
        }
        return true
    }
}