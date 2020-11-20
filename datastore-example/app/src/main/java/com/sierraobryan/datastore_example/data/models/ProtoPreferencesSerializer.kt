package com.sierraobryan.datastore_example.data.models

import android.util.Log
import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.sierraobryan.datastore_example.MemberPreferences
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets

object ProtoPreferencesSerializer : Serializer<MemberPreferences> {
    override fun readFrom(input: InputStream): MemberPreferences {
        try {
            Log.d("Sierra", convertInputStreamToString(input) ?: "")
            return MemberPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: MemberPreferences, output: OutputStream) = t.writeTo(output)


    private fun convertInputStreamToString(inputStream: InputStream): String? {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            result.write(buffer, 0, length)
        }
        return result.toString(StandardCharsets.UTF_8.name())
    }
}
