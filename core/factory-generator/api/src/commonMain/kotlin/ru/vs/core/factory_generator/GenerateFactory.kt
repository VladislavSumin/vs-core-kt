package ru.vs.core.factory_generator

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class GenerateFactory(val factoryInterface: KClass<*> = Any::class)
