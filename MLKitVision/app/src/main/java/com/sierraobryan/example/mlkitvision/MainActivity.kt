package com.sierraobryan.example.mlkitvision

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.sierraobryan.example.mlkitvision.ml.CustomFlowerModel
import com.sierraobryan.example.mlkitvision.ml.FlowerModel
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors

typealias RecognitionListener = (recognition: List<Category>) -> Unit

class MainActivity : AppCompatActivity() {

    enum class ProcessType {
        DEFAULT, TENSOR
    }

    private val viewFinder by lazy {
        findViewById<PreviewView>(R.id.viewFinder) // Display the preview image from Camera
    }

    private var imageBitmap: Bitmap? = null
    private val processType = ProcessType.TENSOR

    // CameraX variables
    private lateinit var preview: Preview // Preview use case, fast, responsive view of the camera
    private lateinit var imageAnalyzer: ImageAnalysis // Analysis use case, for running ML code
    private lateinit var camera: Camera
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!allPermissionsGranted()) {
            getRuntimePermissions()
        } else {
            viewFinder.visibility = View.VISIBLE
            startCamera()
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
        val tfImage = TensorImage.fromBitmap(bitmap)
        val flowerModel = CustomFlowerModel.newInstance(this)
        val outputs = flowerModel.process(tfImage)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score }
            }
        displayOutputs(outputs)
    }

    private fun displayOutputs(outputs: List<Category>) {
        if (outputs.isNotEmpty()) {
            val recyclerView = findViewById<RecyclerView>(R.id.labels)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = TFImageAdapter(outputs)
            recyclerView.visibility = View.VISIBLE
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUESTS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                viewFinder.visibility = View.GONE
            }
        }
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

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            preview = Preview.Builder().build()

            imageAnalyzer = ImageAnalysis.Builder()
                // This sets the ideal size for the image to be analyse, CameraX will choose the
                // the most suitable resolution which may not be exactly the same or hold the same
                // aspect ratio
                .setTargetResolution(Size(224, 224))
                // How the Image Analyser should pipe in input, 1. every frame but drop no frame, or
                // 2. go to the latest frame and may drop some frame. The default is 2.
                // STRATEGY_KEEP_ONLY_LATEST. The following line is optional, kept here for clarity
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysisUseCase: ImageAnalysis ->
                    analysisUseCase.setAnalyzer(cameraExecutor, ImageAnalyzer { outputs ->
                        runOnUiThread {
                            displayOutputs(outputs)
                        }
                    })
                }

            // Select camera, back is the default. If it is not available, choose front camera
            val cameraSelector =
                if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA))
                    CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera - try to bind everything at once and CameraX will find
                // the best combination.
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

                // Attach the preview to preview view, aka View Finder
                preview.setSurfaceProvider(viewFinder.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    inner class ImageAnalyzer(val listener: RecognitionListener) : ImageAnalysis.Analyzer {

        override fun analyze(imageProxy: ImageProxy) {

            val tfImage = TensorImage.fromBitmap(toBitmap(imageProxy))
            val flowerModel = CustomFlowerModel.newInstance(this@MainActivity)

            val outputs = flowerModel.process(tfImage)
                .probabilityAsCategoryList.apply {
                    sortByDescending { it.score }
                }

            listener(outputs)

            imageProxy.close()
        }

        private val yuvToRgbConverter = YuvToRgbConverter(this@MainActivity)
        private lateinit var bitmapBuffer: Bitmap
        private lateinit var rotationMatrix: Matrix

        @SuppressLint("UnsafeExperimentalUsageError")
        private fun toBitmap(imageProxy: ImageProxy): Bitmap? {

            val image = imageProxy.image ?: return null

            // Initialise Buffer
            if (!::bitmapBuffer.isInitialized) {
                // The image rotation and RGB image buffer are initialized only once
                Log.d(TAG, "Initalise toBitmap()")
                rotationMatrix = Matrix()
                rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                bitmapBuffer = Bitmap.createBitmap(
                    imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
                )
            }


            // Pass image to an image analyser
            yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)

            // Create the Bitmap in the correct orientation
            return Bitmap.createBitmap(
                bitmapBuffer,
                0,
                0,
                bitmapBuffer.width,
                bitmapBuffer.height,
                rotationMatrix,
                false
            )
        }

    }

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUESTS = 1
        private const val REQUEST_CHOOSE_IMAGE = 1002
    }
}