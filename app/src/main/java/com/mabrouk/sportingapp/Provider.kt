package com.mabrouk.sportingapp

import android.nfc.tech.IsoDep
import com.github.devnied.emvnfccard.exception.CommunicationException
import com.github.devnied.emvnfccard.parser.IProvider
import java.io.IOException

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/5/23
 */
class Provider : IProvider {
    var mTagCom: IsoDep?=null
    override fun transceive(pCommand: ByteArray?): ByteArray {
        val response: ByteArray = try {
            // send command to emv card
            mTagCom!!.transceive(pCommand)
        } catch (e: IOException) {
            throw CommunicationException(e.message)
        }

        return response
    }

    override fun getAt(): ByteArray {
        return mTagCom?.historicalBytes!!
    }
}