package com.mabrouk.core.network

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 10/4/22
 */

fun loadLibrary() {
    System.loadLibrary("keys")
}

external fun getApiKey(): String

external fun getBaseUrl(): String
