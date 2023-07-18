package ru.vs.core.factory_generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

internal class FactoryGeneratorSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // Always process all elements
        return emptyList()
    }
}
