package com.example.demo.http

import okhttp3.Cookie
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class SerializableOkHttpCookies(cookies: Cookie) : Serializable {
    @Transient
    private val cookies: Cookie = cookies

    @Transient
    private var clientCookies: Cookie? = null

    fun getCookies(): Cookie {
        var bestCookies: Cookie = cookies
        clientCookies?.let {
            bestCookies = it
        }
        return bestCookies
    }

    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookies.name())
        out.writeObject(cookies.value())
        out.writeLong(cookies.expiresAt())
        out.writeObject(cookies.domain())
        out.writeObject(cookies.path())
        out.writeBoolean(cookies.secure())
        out.writeBoolean(cookies.httpOnly())
        out.writeBoolean(cookies.hostOnly())
        out.writeBoolean(cookies.persistent())
    }

    private fun readObject(`in`: ObjectInputStream) {
        val name = `in`.readObject() as String
        val value = `in`.readObject() as String
        val expiresAt: Long = `in`.readLong()
        val domain = `in`.readObject() as String
        val path = `in`.readObject() as String
        val secure: Boolean = `in`.readBoolean()
        val httpOnly: Boolean = `in`.readBoolean()
        val hostOnly: Boolean = `in`.readBoolean()
        val persistent: Boolean = `in`.readBoolean()
        var builder: Cookie.Builder = Cookie.Builder()
        builder = builder.name(name)
        builder = builder.value(value)
        builder = builder.expiresAt(expiresAt)
        builder = if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        builder = builder.path(path)
        builder = if (secure) builder.secure() else builder
        builder = if (httpOnly) builder.httpOnly() else builder
        clientCookies = builder.build()
    }
}