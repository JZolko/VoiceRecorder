package com.msu.lilac.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SoundRecorder(mainActivity: MainActivity) {

    private var _mainActivity: MainActivity = mainActivity
    private var _isRecording = false
    private var _mediaRecorder: MediaRecorder? = null
    private var _mediaPlayer = MediaPlayer()
    private var _currentFile = 0
    private var _fileName = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOWNLOADS
    ).toString() + "/AudioRecording.3gp"
    //private var _fileName = "/sdcard/Download/AudioRecording.3gp"
    private var _isPlaying = false


    private fun CheckPermissions() {
        val permissionRecord = ContextCompat.checkSelfPermission(_mainActivity,
            Manifest.permission.RECORD_AUDIO)
        val permissionWriteStorage = ContextCompat.checkSelfPermission(_mainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionReadStorage = ContextCompat.checkSelfPermission(_mainActivity,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        val permissionReadInternalStorage = ContextCompat.checkSelfPermission(_mainActivity,
            Manifest.permission.MANAGE_MEDIA)

        if (permissionRecord != PackageManager.PERMISSION_GRANTED ||
            permissionWriteStorage != PackageManager.PERMISSION_GRANTED ||
            permissionReadStorage != PackageManager.PERMISSION_GRANTED ||
            permissionReadInternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(_mainActivity,
                arrayOf(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        _mediaPlayer = MediaPlayer()
        _mediaRecorder = MediaRecorder()

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
            _mediaRecorder?.setAudioEncodingBitRate(96)
            _mediaRecorder?.setAudioSamplingRate(48000)
            _mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            _mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            _mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            _mediaRecorder?.setOutputFile(_fileName)
            try {
                _mediaRecorder?.prepare()
                _mediaRecorder?.start()
                _isRecording = true
                _mainActivity.recordButton.text = "Recording"
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TAG", "prepare() failed" + e)
                _mainActivity.recordButton.text = "failed"

            }
        }
    }

    fun stopRecording() {
        if(!_isRecording){
            return
        }
        try {
            _mediaRecorder?.stop()
            _mediaRecorder?.release()
            _isRecording = false
            _currentFile++
            _mainActivity.recordButton.text = "NO"
            _mainActivity.stopButton.text = "Stopped"
        }catch (e: Exception){
            e.printStackTrace()
            Log.e("TAG", "stop prepare() failed" + e)
            _mainActivity.stopButton.text = "failed"
        }

    }

    fun pausePlayback() {
        if(_isRecording || !_isPlaying) {
            return
        }
        _mediaPlayer.pause()
        _isRecording = false
        _isPlaying = false
    }

    fun playPlayback() {
        if (_isRecording) {
            return
        }

        try {
            _mediaPlayer.setDataSource(_fileName)
            _mediaPlayer.prepare()
            _mediaPlayer.start()
            _isRecording = false
            _isPlaying = true
            _mainActivity.playButton.text = "Playing"
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "prepare() failed" + e)
            _mainActivity.playButton.text = "failed"

        }
    }
}