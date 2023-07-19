package ru.vs.core.factory_generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Getting all classes annotated with @GenerateFactory
        resolver.getSymbolsWithAnnotation(GenerateFactory::class.qualifiedName!!)
            .forEach(this::processGenerateFactoryAnnotation)
        // Always process all elements
        return emptyList()
    }

    private fun processGenerateFactoryAnnotation(instance: KSAnnotated) {
        check(instance is KSClassDeclaration) {
            "Only KSClassDeclaration supported, but $instance was received"
        }
        val primaryConstructor = instance.primaryConstructor
        checkNotNull(primaryConstructor)

        val factoryInterface = instance.annotations
            .first { it.annotationType.toTypeName() == GenerateFactory::class.asTypeName() }
            .arguments.first().value as KSType

        generateFactoryImpl(factoryInterface, instance)
    }

    private fun generateFactoryImpl(
        factoryInterface: KSType,
        instance: KSClassDeclaration,
    ) {
        val factoryInterfaceDeclaration = factoryInterface.declaration as KSClassDeclaration
        val factoryInterfaceClassName = factoryInterface.toClassName()
        val factoryImplementationName = factoryInterfaceClassName.simpleName + "Impl"

        // Find single abstract method of factory interface
        val factoryMethod: KSFunctionDeclaration = factoryInterfaceDeclaration.getAllFunctions()
            .filter { it.isAbstract }
            .single()

        // Factory abstract methods arguments
        val factoryMethodParameters = factoryMethod.parameters

        val instancePrimaryConstructor = instance.primaryConstructor!!

        // Add here parameters not defined by factory methods function arguments
        val nonProvidedByFactoryMethodsParams = mutableListOf<KSValueParameter>()


        val codeBlock = CodeBlock.builder()
            .add("return %T(", instance.toClassName())
            .apply {
                val factoryMethodParametersCache = factoryMethodParameters
                    .associateBy { it.name!!.getShortName() }
                    .toMutableMap()

                instancePrimaryConstructor.parameters.forEach { parameter ->
                    if (factoryMethodParametersCache.remove(parameter.name!!.getShortName()) == null) {
                        nonProvidedByFactoryMethodsParams.add(parameter)
                    }

                    add("%L, ", parameter.name!!.getShortName())
                }

                check(factoryMethodParametersCache.isEmpty())
            }
            .add(")")
            .build()

        val constructor = FunSpec.constructorBuilder()
            .apply {
                nonProvidedByFactoryMethodsParams.forEach {
                    addParameter(it.name!!.getShortName(), it.type.toTypeName())
                }
            }
            .build()

        val function = FunSpec.builder(factoryMethod.simpleName.getShortName())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(
                factoryMethodParameters.map {
                    ParameterSpec(it.name!!.getShortName(), it.type.toTypeName())
                }
            )
            .addCode(codeBlock)
            .returns(factoryMethod.returnType!!.toTypeName())
            .build()

        val clazz = TypeSpec.classBuilder(factoryImplementationName)
            .addModifiers(KModifier.INTERNAL)
            .primaryConstructor(constructor)
            .addSuperinterface(factoryInterfaceClassName)
            .apply {
                nonProvidedByFactoryMethodsParams.forEach {
                    val name = it.name!!.getShortName()
                    addProperty(
                        PropertySpec.builder(name, it.type.toTypeName())
                            .initializer(name)
                            .addModifiers(KModifier.PRIVATE)
                            .build()
                    )
                }
            }
            .addFunction(function)
            .build()

        FileSpec.builder(
            factoryInterfaceClassName.packageName,
            factoryImplementationName
        )
            .addType(clazz)
            .build()
            .writeTo(codeGenerator, false)
    }
}
