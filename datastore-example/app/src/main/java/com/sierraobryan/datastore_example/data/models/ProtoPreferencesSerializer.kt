package com.sierraobryan.datastore_example.data.models

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.sierraobryan.datastore_example.MemberPreferences
import java.io.InputStream
import java.io.OutputStream

object ProtoPreferencesSerializer : Serializer<MemberPreferences> {
    override val defaultValue: MemberPreferences = MemberPreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): MemberPreferences {
        try {
            return MemberPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: MemberPreferences, output: OutputStream) = t.writeTo(output)
}
