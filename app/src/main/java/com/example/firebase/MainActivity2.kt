package com.example.firebase

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream
import kotlin.random.Random


class MainActivity2 : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage

    lateinit var imageView: ImageView
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        storage = Firebase.storage
        imageView = findViewById(R.id.image)
        btn = findViewById(R.id.btn)


// Create a storage reference from our app

// Get a non-default Storage bucket
// While the file names are the same, the references point to different files
//        mountainsRef.name == mountainImagesRef.name // true
//        mountainsRef.path == mountainImagesRef.path // false

        imageView.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
        }


        btn.setOnClickListener {
            val storageRef = storage.reference

            var Imagenae = "Image${Random.nextInt(10000)}.jpg"

            val mountainImagesRef = storageRef.child("HArita/$Imagenae")
// Get the data from an ImageView as bytes
            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainImagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    mountainImagesRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result

                        val database = Firebase.database
                        val myRef = database.getReference("Realtimedataabse").push()

                        var data =
                            Mydata(myRef.key!!, "Haresh", "5465646656", downloadUri.toString())

                        myRef.setValue(data)

                        startActivity(Intent(this@MainActivity2, ViewData::class.java))
                    } else {
                        // Handle failures
                        // ...
                    }
                }


            }


// Create a reference to "mountains.jpg"
//        val mountainsRef = storageRef.child("mountains.jpg")

// Create a reference to 'images/mountains.jpg'
//            val mountainImagesRef = storageRef.child("Harita/")
//            // Get the data from an ImageView as bytes
//            imageView.isDrawingCacheEnabled = true
//            imageView.buildDrawingCache()
//            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
//            val data = baos.toByteArray()
//
//            var uploadTask = mountainImagesRef.putBytes(data)
//            uploadTask.addOnFailureListener {
//                // Handle unsuccessful uploads
//            }.addOnSuccessListener { taskSnapshot ->
//                Log.d("=--=-=---", "onCreate: ")
//
//                val urlTask = uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        Log.d("=--=-=---", "onCreate: ")
//                        task.exception?.let {
//                            throw it
//                        }
//                    }
//                    mountainImagesRef.downloadUrl
//                }.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val downloadUri = task.result
//                        val database = Firebase.database
//                        val myRef = database.getReference("data")
////                        myRef.setValue(data)
//                        myRef.setValue("Hello, World!")
//                        Log.d("=---", "onCreate: =====$downloadUri")
//
//                    } else {
//                        // Handle failures
//                        // ...
//                    }
//                }
//            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                imageView.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}