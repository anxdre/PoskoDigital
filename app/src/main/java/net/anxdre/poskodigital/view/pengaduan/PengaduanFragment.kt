package net.anxdre.poskodigital.view.pengaduan

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_pengaduan.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.pengaduan.PengaduanInput
import net.anxdre.poskodigital.data.api.uploadFileAnyAsync
import net.anxdre.poskodigital.utils.SpFactory
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class PengaduanFragment : Fragment() {
    private var idUser: String? = null
    private var userName: String? = null
    private var imgFile: File? = null
    private val easyImage by lazy {
        EasyImage.Builder(requireContext())
            .setCopyImagesToPublicGalleryFolder(false)
            .setFolderName("EasyImage sample")
            .allowMultiple(false)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        idUser = SpFactory(requireContext()).getAuthId()
        userName = SpFactory(requireContext()).getAuthName()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengaduan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), 200
        )
        btn_upload_image_pengaduan.setOnClickListener {
            easyImage.openChooser(this)
        }

        btn_laporkan.setOnClickListener {
            if (checkField()) {
                GlobalScope.launch(Dispatchers.Main) { imgFile?.let { it1 -> prepareForUpload(it1) } }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        easyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    imgFile = (imageFiles[0].file)
                    btn_upload_image_pengaduan.setImageBitmap(BitmapFactory.decodeFile(imgFile!!.absolutePath))
                }
            })
    }

    private suspend fun prepareForUpload(imgFile: File) {
        val inputData = PengaduanInput(
            et_no_ktp_pengaduan.text.toString(),
            et_ktp_pengaduan.text.toString(),
            et_desc_pengaduan.text.toString()
        )

        val compressedImageFile = Compressor.compress(requireContext(), imgFile)

        try {
            uploadFileAnyAsync("post_pengaduan", compressedImageFile, inputData)
            Toast.makeText(requireContext(), "Berhasil Upload File", Toast.LENGTH_LONG)
                .show()
            findNavController().navigateUp()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Terjadi Kesalahan Upload File", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun checkField(): Boolean {
        return nonEmptyList(
            et_ktp_pengaduan,
            et_no_ktp_pengaduan,
            et_desc_pengaduan
        ) { view, message ->
            view.error = message
        }
    }


}
