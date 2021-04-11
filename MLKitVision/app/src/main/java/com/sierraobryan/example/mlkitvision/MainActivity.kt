package com.sierraobryan.example.mlkitvision

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.sierraobryan.example.mlkitvision.ml.FlowerModel
import org.tensorflow.lite.support.image.TensorImage
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    enum class ProcessType {
        DEFAULT, TENSOR
    }

    private var imageBitmap: Bitmap? = null
    private val processType = ProcessType.TENSOR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!allPermissionsGranted()) {
            getRuntimePermissions()
        }
        val button = findViewById<Button>(R.id.select_image_button)
        val processImageButton = findViewById<Button>(R.id.process_image_button)
        button.setOnClickListener {
            startChooseImageIntentForResult()
        }

        processImageButton.setOnClickListener {
            if (imageBitmap != null) {
               when (processType) {
                   ProcessType.DEFAULT -> processDefault(imageBitmap!!)
                   ProcessType.TENSOR -> processTensorImage(imageBitmap!!)
               }
            }
        }
    }

    private fun processDefault(bitmap: Bitmap, rotation: Int = 0) {
        val imageInput = InputImage.fromBitmap(bitmap, rotation)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        labeler.process(imageInput).addOnSuccessListener { labels ->

            val recyclerView = findViewById<RecyclerView>(R.id.labels)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = LabelAdapter(labels)
            recyclerView.visibility = View.VISIBLE

        }.addOnFailureListener {
            Toast.makeText(this, getString(R.string.nothing_found), Toast.LENGTH_SHORT).show()
        }
    }

    private fun processTensorImage(bitmap: Bitmap) {
        val tfImage = TensorImage.fromBitmap(imageBitmap!!)
        val flowerModel = FlowerModel.newInstance(this)
        val outputs = flowerModel.process(tfImage).probabilityAsCategoryList.apply {
            sortByDescending { it.score }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.labels)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TFImageAdapter(outputs)
        recyclerView.visibility = View.VISIBLE
    }

    private fun getRequiredPermissions(): Array<String?> {
        return try {
            val info = this.packageManager
                .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            if (ps != null && ps.isNotEmpty()) {
                ps
            } else {
                arrayOfNulls(0)
            }
        } catch (e: Exception) {
            arrayOfNulls(0)
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in getRequiredPermissions()) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    return false
                }
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val allNeededPermissions = ArrayList<String>()
        for (permission in getRequiredPermissions()) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    allNeededPermissions.add(permission)
                }
            }
        }

        if (allNeededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this, allNeededPermissions.toTypedArray(), PERMISSION_REQUESTS
            )
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Permission granted: $permission")
            return true
        }
        Log.i(TAG, "Permission NOT granted: $permission")
        return false
    }

    private fun startChooseImageIntentForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CHOOSE_IMAGE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        onSelectImageResult(data?.data != null)
        if (requestCode == REQUEST_CHOOSE_IMAGE &&
            resultCode == Activity.RESULT_OK
        ) {
            val imageUri = data!!.data
            setPreview(imageUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setPreview(imageUri: Uri?) {
        try {
            if (imageUri == null) return

            val preview = findViewById<ImageView>(R.id.preview)

            val imageBitmap = getBitmapFromUri(imageUri) ?: return

            this.imageBitmap = imageBitmap

            preview.setImageBitmap(imageBitmap)

        } catch (e: IOException) {
            Toast.makeText(this,
                getString(R.string.something_went_wrong),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    private fun onSelectImageResult(isSuccessful: Boolean = true) {
        findViewById<RecyclerView>(R.id.labels).visibility = View.GONE
        findViewById<Button>(R.id.process_image_button).isEnabled = isSuccessful
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUESTS = 1
        private const val REQUEST_CHOOSE_IMAGE = 1002
    }
}