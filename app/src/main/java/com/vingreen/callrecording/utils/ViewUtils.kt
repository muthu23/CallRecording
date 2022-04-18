package com.vingreen.callrecording.utils

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.vingreen.callrecording.R
import com.vingreen.callrecording.RecordApplication.Companion.baseApplication
import com.vingreen.callrecording.utils.Constant.THEME
import com.vingreen.callrecording.utils.Constant.THEME_DARK
import com.vingreen.callrecording.utils.Constant.THEME_DEFAULT
import com.vingreen.callrecording.utils.Constant.THEME_LIGHT
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object ViewUtils {

     var preferenceHelper = PreferenceHelper(baseApplication!!)

    @MainThread
    fun showToast(context: Context, @StringRes messageResId: Int, isSuccess: Boolean) {
        if (isSuccess)
            Toasty.success(context, messageResId, Toast.LENGTH_SHORT).show()
        else
            Toasty.error(context, messageResId, Toast.LENGTH_SHORT).show()

    }

    @MainThread
    fun showToast(context: Context, messageResId: String?, isSuccess: Boolean) {
        if (isSuccess)
            Toasty.success(context, messageResId!!, Toast.LENGTH_SHORT).show()
        else
            Toasty.error(context, messageResId!!, Toast.LENGTH_SHORT).show()

    }


    @MainThread
    fun showNormalToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @MainThread
    fun showTransportAlert(
        context: Context, @StringRes messageResId: Int,
        callBack: ViewCallBack.Alert
    ) {
        AlertDialog.Builder(context, R.style.Theme_CallRecording)
            .setTitle(R.string.app_name)
            .setMessage(messageResId)
            .setPositiveButton(R.string.yes) { dialog, which ->
                callBack.onPositiveButtonClick(
                    dialog
                )
            }
            .setNegativeButton(R.string.no) { dialog, which ->
                callBack.onNegativeButtonClick(
                    dialog
                )
            }
            .show()
    }

     fun convertRequestBodyText(data: String): RequestBody {
         return data.toRequestBody("text/plain".toMediaTypeOrNull())
     }

    fun dpToPx(dp: Int): Int {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun showAlert(context: Context, @StringRes messageResId: Int, callBack: ViewCallBack.Alert) {
        AlertDialog.Builder(context)
            .setTitle(R.string.app_name)
            .setMessage(messageResId)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                callBack.onPositiveButtonClick(
                    dialog
                )
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                callBack.onNegativeButtonClick(
                    dialog
                )
            }
            .show()
    }

    fun showAlert(
        context: Context,
        @StringRes messageResId: Int,
        callBack: ViewCallBack.AlertWithOk
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.app_name)
            .setMessage(messageResId)
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                callBack.onPositiveButtonClick(
                    dialog
                )
            }
            .show()
    }

    @MainThread
    fun showAlert(context: Context, @StringRes title: Int, @StringRes messageResId: Int) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(messageResId)
            .setPositiveButton(android.R.string.yes) { dialog, which -> dialog.dismiss() }
            .show()
    }

    /*@MainThread
    fun showRationaleAlert(
        context: Context, @StringRes messageResId: Int,
        request: PermissionRequest
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.app_name)
            .setMessage(messageResId)
            .setPositiveButton("Allow") { dialog, which -> request.proceed() }
            .setNegativeButton("Diney") { dialog, which -> request.cancel() }
            .show()
    }*/


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun convertToBitmap(drawable: Drawable, widthPixels: Int, heightPixels: Int): Bitmap {
        val mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mutableBitmap)
        drawable.setBounds(0, 0, widthPixels, heightPixels)
        drawable.draw(canvas)

        return mutableBitmap
    }

    fun getDaysBetweenDates(start: String, end: String): Long {
//        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val startDate: Date
        val endDate: Date
        var numberOfDays: Long = 0
        try {
            startDate = simpleDateFormat.parse(start)!!
            endDate = simpleDateFormat.parse(end)!!
            val timeDiff = endDate.time - startDate.time
            numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return numberOfDays
    }


    fun getDayFormat(str: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH)
        val date = formatter.parse(str)
        val format = SimpleDateFormat("MMM dd", Locale.ENGLISH)
        return format.format(date!!)
    }

    fun getDayWeekFormat(str: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH)
        val date = formatter.parse(str)
        val format = SimpleDateFormat("EEE,dd MMM", Locale.ENGLISH)
        return format.format(date!!)
    }

    fun getDayWeekYearFormat(str: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd ", Locale.ENGLISH)
        val date = formatter.parse(str)
        val format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return format.format(date!!)
    }

    fun setImageViewGlide(context: Context, imageView: ImageView, imagePath: String) {
        if (imagePath != "null") {
            Glide.with(context)
                .load(/*BuildConfig.BASE_IMAGE_URL+*/imagePath)
                .thumbnail(0.5f)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(context.resources.getDrawable(R.mipmap.ic_launcher))
                .thumbnail(0.5f)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }


    fun getDate(date: Date): String {
        val sdf = SimpleDateFormat(" EEE,dd-MMM", Locale.ENGLISH)
        return sdf.format(date)
    }

    fun getYyMmDdFormat(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        return formatter.format(date)
    }


    fun getDaily(date: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        val mDate = formatter.parse(date)
        val format = SimpleDateFormat("EEE dd,", Locale.ENGLISH)
        return format.format(mDate!!)
    }

    fun getCurrentDateTime(date: String): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//        val formatter = SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH)
        return if (date == "today") {
            val today = calendar.time
            formatter.format(today)
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow = calendar.time
            formatter.format(tomorrow)
        }
    }


    fun getDistance(SrcLat: Double, SrcLng: Double, DesLat: Double, DesLng: Double): Int {
        val locationA = Location("point A")
        locationA.latitude = SrcLat
        locationA.longitude = SrcLng
        val locationB = Location("point B")
        locationB.latitude = DesLat
        locationB.longitude = DesLng

        return locationA.distanceTo(locationB).roundToInt() / 1000

    }

    fun getCityName(
        context: Context,
        latitude: String?,
        longitude: String?
    ): List<Address> {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address> = listOf()
        if (latitude != null && longitude != null) {
            addresses = geocoder.getFromLocation(
                latitude.toDouble(),
                longitude.toDouble(),
                1
            ) as List<Address>
        }
        return addresses
    }

    var fileSizeUnits = arrayOf("bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
    fun calculateProperFileSize(noOfBytes: Long): String {
        var sizeToReturn = "" // = FileUtils.byteCountToDisplaySize(bytes), unit = "";
        var bytes = noOfBytes.toDouble()
        var index = 0
        index = 0
        while (index < fileSizeUnits.size) {
            if (bytes < 1024) {
                break
            }
            bytes /= 1024
            index++
        }
        sizeToReturn = bytes.toString() + " " + fileSizeUnits[index]
        return sizeToReturn
    }

    fun getNightMode(context: Context): Int {
        preferenceHelper = PreferenceHelper(context)

        return when (preferenceHelper.getValue(THEME, THEME_DEFAULT)) {
            THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            } else {
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
        }
    }


}

