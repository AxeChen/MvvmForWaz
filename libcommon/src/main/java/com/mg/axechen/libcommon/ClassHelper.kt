package com.mg.axechen.libcommon

import java.lang.reflect.ParameterizedType

object ClassHelper {

    fun <T> getClass(t: Any): Class<T> {
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0]
                as Class<T>
    }
}