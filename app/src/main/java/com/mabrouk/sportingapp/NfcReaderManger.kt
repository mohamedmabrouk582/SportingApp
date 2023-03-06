package com.mabrouk.sportingapp

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.IsoDep
import com.github.devnied.emvnfccard.model.EmvCard
import com.github.devnied.emvnfccard.parser.EmvTemplate


/**
 * @name Mohamed Mabrouk
 * Copyright (c) 3/3/23
 */
class NfcReaderManger(val context: Activity, val getResponse: (data: String) -> Unit) :
    NfcAdapter.ReaderCallback {
    val iProvider = Provider()

    private fun setupConfig(): EmvCard? {
        val config: EmvTemplate.Config = EmvTemplate.Config()
            .setContactLess(true) // Enable contact less reading (default: true)
            .setReadAllAids(true) // Read all aids in card (default: true)
            .setReadTransactions(true) // Read all transactions (default: true)
            .setReadCplc(false) // Read and extract CPCLC data (default: false)
            .setRemoveDefaultParsers(false) // Remove default parsers for GeldKarte and EmvCard (default: false)
            .setReadAt(true) // Read and extract ATR/ATS and description
// Create Parser

        // Create Parser
        val parser = EmvTemplate.Builder() //
            .setProvider(iProvider) // Define provider
            .setConfig(config) // Define config
            //.setTerminal(terminal) (optional) you can define a custom terminal implementation to create APDU
            .build()
        return parser.readEmvCard()
    }

    val nfcAdapter by lazy { NfcAdapter.getDefaultAdapter(context) }

    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        iProvider.mTagCom = isoDep
        val card = setupConfig()
        context.runOnUiThread {
            getResponse("${card?.cardNumber} /n ${card?.expireDate} /n ")
        }
    }


    fun enable() {
        if (isSupportNfc())
            nfcAdapter.enableReaderMode(
                context, this,
                NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
                null
            )
    }

    fun disable() {
        if (isSupportNfc())
            nfcAdapter.disableReaderMode(context)
    }

   private fun isSupportNfc(): Boolean {
        val manager = context.getSystemService(Context.NFC_SERVICE) as NfcManager
        val adapter = manager.defaultAdapter
        return if (adapter != null && adapter.isEnabled) {
            true
        } else if (adapter != null && !adapter.isEnabled) {
            false
        } else {
            false
        }
    }
}