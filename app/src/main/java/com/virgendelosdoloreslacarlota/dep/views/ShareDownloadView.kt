package com.virgendelosdoloreslacarlota.dep.views

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.databinding.ViewShareDownloadBinding
import com.virgendelosdoloreslacarlota.dep.feature.main.MainActivity
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarSuccessMessage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ShareDownloadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAtr: Int = 0
): LinearLayout(context, attrs, defStyleAtr) {

    @Inject
    lateinit var tracker: Tracker

    private val binding = ViewShareDownloadBinding.inflate(LayoutInflater.from(context),
        this, true)

    fun setOnShareClickListener(url: String, text: String) {
        binding.share.setOnClickListener {
            tracker.setEvent(UserEvents.ShareTap(url))
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
            tracker.setEvent(UserEvents.DownloadTap)
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
        val filename = "${System.currentTimeMillis()}.jpg"
        try {
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadUri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request
                    .VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm!!.enqueue(request)
            showSnackBarSuccessMessage(resources.getString(R.string.download_success_message))
        } catch (e: Exception) {
            showSnackBarErrorMessage(resources.getString(R.string.download_error_message))
        }
    }
}