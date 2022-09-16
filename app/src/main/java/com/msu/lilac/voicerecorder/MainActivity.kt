package com.msu.lilac.voicerecorder


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val _soundRecorder = SoundRecorder(this)
    private var _isRecording = false
    private var _isPlaying = false
    val recordButton: Button by lazy { findViewById<Button>(R.id.RecordButton) }
    val stopButton: Button by lazy { findViewById<Button>(R.id.StopButton) }
    val playButton: Button by lazy { findViewById<Button>(R.id.PlayButton) }
    val pauseButton: Button by lazy { findViewById<Button>(R.id.PauseButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        //login view

        // other login view

        // main audio record view
        recordButton.setOnClickListener {
            _soundRecorder.startRecording()
        }

        stopButton.setOnClickListener {
            _soundRecorder.stopRecording()
        }

        pauseButton.setOnClickListener {
            _soundRecorder.pausePlayback()
        }

        playButton.setOnClickListener {
            _soundRecorder.playPlayback()
        }
    }
}