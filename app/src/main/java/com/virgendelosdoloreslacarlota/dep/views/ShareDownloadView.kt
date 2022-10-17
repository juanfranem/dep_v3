package com.virgendelosdoloreslacarlota.dep.views

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.virgendelosdoloreslacarlota.dep.databinding.ViewShareDownloadBinding
import com.virgendelosdoloreslacarlota.dep.feature.main.MainActivity
import java.io.File

class ShareDownloadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAtr: Int = 0
): LinearLayout(context, attrs, defStyleAtr) {
    private var msg: String? = ""
    private val binding = ViewShareDownloadBinding.inflate(LayoutInflater.from(context),
        this, true)

    fun setOnShareClickListener(url: String, text: String) {
        binding.share.setOnClickListener {
            ShareCompat.IntentBuilder(context)
                .setType("text/plain")
                .setChooserTitle(text)
                .setText(url)
                .startChooser()
        }
    }


    fun setOnSaveClickListener(activity: FragmentActivity, url: String?) {
        if (url.isNullOrBlank()) {
            binding.save.isVisible = false
            return
        }
        binding.save.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    downloadImage(url)
                }
                shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    (activity as MainActivity).writePermissionLauncher.launch(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
                else -> {
                    (activity as MainActivity).writePermissionLauncher.launch(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun downloadImage(url: String) {
        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)

        Thread {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                val index = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                if (cursor.getInt(index)
                    == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val indexCursor = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                val status = cursor.getInt(indexCursor)
                msg = statusMessage(url, directory, status)
                if (!msg.isNullOrBlank()) {
                    handler.post {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                }
                cursor.close()
            }
        }.start()
    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        val msg: String? = when (status) {
            DownloadManager.STATUS_FAILED -> {
                handler.post {
                    binding.share.isEnabled = true
                }
                null
            }
            DownloadManager.STATUS_PAUSED -> {
                handler.post {
                    binding.share.isEnabled = false
                }
                null
            }
            DownloadManager.STATUS_PENDING -> {
                handler.post {
                    binding.share.isEnabled = false
                }
                null
            }
            DownloadManager.STATUS_RUNNING -> {
                handler.post {
                    binding.share.isEnabled = false
                }
                null
            }
            DownloadManager.STATUS_SUCCESSFUL -> {
                handler.post {
                    binding.share.isEnabled = true
                }
                "Image downloaded successfully in $directory" +
                    File.separator + url.substring(
                url.lastIndexOf("/") + 1
            )
            }
            else -> {
                handler.post {
                    binding.share.isEnabled = true
                }
                "There's nothing to download"
            }
        }
        return msg
    }


    fun setSaveText(text: String) {
        binding.save.text = text
    }

    fun setShareText(text: String) {
        binding.share.text = text
    }
}