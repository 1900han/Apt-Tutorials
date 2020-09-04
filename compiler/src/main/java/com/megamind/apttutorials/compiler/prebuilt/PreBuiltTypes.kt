package com.megamind.apttutorials.compiler.prebuilt

import com.bennyhuo.aptutils.types.ClassType

/**
 * @description
 * @date 20:10 9/4/20
 * @author HanZhengYa
 * @since 1.0
 **/

val CONTEXT = ClassType("android.content.Context")
val INTENT = ClassType("android.content.Intent")
val ACTIVITY = ClassType("android.app.Activity")
val BUNDLE = ClassType("android.os.Bundle")
val BUNDLE_UTILS = ClassType("com.megamind.apttutorials.runtime.utils.BundleUtils")

val ACTIVITY_BUILDER = ClassType("com.megamind.apttutorials.runtime.ActivityBuilder")