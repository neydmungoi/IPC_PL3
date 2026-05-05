package com.ipc.sentinela.data.model

data class Podcast(
    val id: String,
    val titulo: String,
    val autor: String,
    val duracao: String,
    val url: String, // Em produção seria um link, aqui será um resource ID fictício
    val culturaRelacionada: String // "Vinha", "Olival", "Geral"
)
