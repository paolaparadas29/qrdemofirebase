package com.kazimasum.qrdemofirebase

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.PermissionToken
import com.google.android.gms.tasks.OnCompleteListener
import com.google.zxing.Result
import com.karumi.dexter.listener.PermissionRequest
import com.kazimasum.qrdemofirebase.MainActivity

class qrscanner : AppCompatActivity(), ZXingScannerView.ResultHandler {
    var scannerView: ZXingScannerView? = null
    var dbref: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        dbref = FirebaseDatabase.getInstance().getReference("qrdata")
        Dexter.withContext(applicationContext)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                    scannerView!!.startCamera()
                }

                override fun onPermissionDenied(permissionDeniedResponse: PermissionDeniedResponse) {}
                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }

    @SuppressLint("SetTextI18n")
    override fun handleResult(rawResult: Result) {
        val data = rawResult.text.toString()
        dbref!!.push().setValue(data)
            .addOnCompleteListener {
                MainActivity.qrtext!!.text = "Data inserted successfully"
                onBackPressed()
            }
    }

    override fun onPause() {
        super.onPause()
        scannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView!!.setResultHandler(this)
        scannerView!!.startCamera()
    }
}