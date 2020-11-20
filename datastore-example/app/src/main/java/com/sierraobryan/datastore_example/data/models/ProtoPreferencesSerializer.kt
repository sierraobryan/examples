package com.sierraobryan.datastore_example.data.models

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.sierraobryan.datastore_example.MemberPreferences
import java.io.InputStream
import java.io.OutputStream

object ProtoPreferencesSerializer : Serializer<MemberPreferences> {
    override fun readFrom(input: InputStream): MemberPreferences {
        try {
            return MemberPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: MemberPreferences, output: OutputStream) = t.writeTo(output)
}
