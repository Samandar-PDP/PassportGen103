package uz.digital.passportgeneration.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

object Constants {
    const val DB_NAME = "Passport.db"
    const val TABLE_NAME = "pass_table"
    const val ID = "_id"
    const val NAME = "name"
    const val LAST_NAME = "lastName"
    const val FAT_NAME = "fatName"
    const val REGION = "region"
    const val CITY = "city"
    const val ADDRESS = "address"
    const val GOT_DATE = "gotDate"
    const val LIFE_TIME = "lifeTime"
    const val GENDER = "gender"
    const val IMAGE = "image"
}

fun AppCompatImageView.toByteArray(): ByteArray {
    val bitmap = (this.drawable as BitmapDrawable).bitmap
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

fun ByteArray.toImage(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Fragment.snackBar(text: String) {
    Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
}