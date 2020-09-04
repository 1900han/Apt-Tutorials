package com.megamind.apttutorials.compiler.activity.method

import com.megamind.apttutorials.compiler.activity.ActivityClass
import com.megamind.apttutorials.compiler.activity.entity.Field
import com.megamind.apttutorials.compiler.prebuilt.ACTIVITY_BUILDER
import com.megamind.apttutorials.compiler.prebuilt.CONTEXT
import com.megamind.apttutorials.compiler.prebuilt.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * @description
 * @date 20:42 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
class StartMethod(private val activityClass: ActivityClass, private val name: String) {

    private val fields = ArrayList<Field>()

    private var isStaticMethod = true

    fun staticMethod(staticMethod: Boolean): StartMethod {
        this.isStaticMethod = staticMethod
        return this
    }

    fun addAllField(fields: List<Field>) {
        this.fields += fields;
    }

    fun addField(field: Field) {
        this.fields += field
    }

    fun copy(name: String) = StartMethod(activityClass, name).also {
        it.fields.addAll(fields)
    }

    fun build(typeBuilder: TypeSpec.Builder) {
        val methodBuilder = MethodSpec.methodBuilder(name)
            .addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
            .addParameter(CONTEXT.java, "context")

        methodBuilder.addStatement(
            "\$T intent = new \$T(context, \$T.class)", INTENT.java, INTENT.java,
            activityClass.typeElement
        )

        fields.forEach { field ->
            val name = field.name
            methodBuilder.addParameter(field.asJavaTypeName(), name)
                // intent.putExtra("age", age)
                .addStatement("intent.putExtra(\$S, \$L)", name, name)
        }

        if (isStaticMethod) {
            methodBuilder.addModifiers(Modifier.STATIC)
        } else {
            methodBuilder.addStatement("fillIntent(intent)")
        }

        methodBuilder.addStatement(
            "\$T.INSTANCE.startActivity(context, intent)",
            ACTIVITY_BUILDER.java
        )
        typeBuilder.addMethod(methodBuilder.build())
    }
}