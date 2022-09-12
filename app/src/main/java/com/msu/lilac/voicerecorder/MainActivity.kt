package com.msu.lilac.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val _soundRecorder = SoundRecorder(this)
    private var _isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //login view

        // other login view

        // main audio record view
        val recordButton = findViewById<Button>(R.id.RecordButton)
        recordButton.setOnClickListener {
            if (!_isRecording) {
                _soundRecorder.startRecording()
                _isRecording = true
                recordButton.text = "Recording"
            }
        }

        val stopButton = findViewById<Button>(R.id.StopButton)
        stopButton.setOnClickListener {
            _soundRecorder.stopRecording()
            _isRecording = false
            stopButton.text = "Stopped"
            recordButton.text = "Record"
        }
    }
}