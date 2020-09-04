package com.megamind.apttutorials.compiler.activity.entity

import com.bennyhuo.aptutils.types.asJavaTypeName
import com.bennyhuo.aptutils.types.asKotlinTypeName
import com.sun.tools.javac.code.Symbol

/**
 * @description
 * @date 17:46 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/
open class Field(private val symbol: Symbol.VarSymbol) : Comparable<Field> {

    val name = symbol.qualifiedName.toString()

    open val prefix = "REQUIRED_"

    val isPrivate = symbol.isPrivate

    val isPrimitive = symbol.type.isPrimitive

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }

    fun asJavaTypeName() = symbol.type.asJavaTypeName()

    open fun asKotlinTypeName() = symbol.type.asKotlinTypeName()

    override fun toString(): String {
        return "$name:${symbol.type}"
    }


}