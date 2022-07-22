package com.lebudigital.lebudigital.model

/**
 * Created by ansh on 28/08/18.
 */
class ChatMessage(
        val id: String,
        val text: String,
        val fromId: String,
        val toId: String,
        val timestamp: Long,
        val nama : String
) {
    constructor() : this("", "", "", "", -1,"")
}