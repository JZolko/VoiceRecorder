package com.msu.lilac.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.log

class SoundRecorder(mainActivity: MainActivity) {

    private var _mainActivity: MainActivity = mainActivity
    private var _isRecording = false
    private var _mediaRecorder: MediaRecorder? = null
    private var _currentFile = 0
    private var _fileName =Environment.getExternalStorageDirectory().getAbsolutePath() +  "/AudioRecording.3gp"

    private fun CheckPermissions() {

    }
    fun startRecording() {
        if(_isRecording) {
            return
        }
        CheckPermissions()

        // if permssion is granted
        if (ContextCompat.checkSelfPermission(
                _mainActivity,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            _mediaRecorder?.setAudioEncodingBitRate(16)
            _mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            _mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            _mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            _mediaRecorder?.setOutputFile(_fileName)
            try {
                _mediaRecorder?.prepare()
                _mediaRecorder?.start()
                _isRecording = true
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TAG", "prepare() failed" + e)
            }
        }
    }

    fun stopRecording() {
        if(!_isRecording){
            return
        }
        _mediaRecorder?.stop()
        _mediaRecorder?.release()
        _isRecording = false
        _currentFile++

    }
}