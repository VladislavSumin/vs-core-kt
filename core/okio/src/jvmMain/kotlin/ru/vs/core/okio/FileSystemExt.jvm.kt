package ru.vs.core.okio

import okio.FileSystem

actual val FileSystem.Companion.FS: FileSystem get() = SYSTEM