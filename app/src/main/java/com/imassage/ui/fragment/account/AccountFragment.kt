package com.imassage.ui.fragment.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.BuildConfig
import com.imassage.R
import com.imassage.databinding.FragmentAccountBinding
import com.imassage.databinding.FragmentMainPageBinding
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.StaticVariables
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.File

class AccountFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AccountViewModelFactory by instance()

    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding
    private lateinit var navController: NavController

    // FOR DATA
    private var fileUri: Uri? = null
    private var mediaPath: String? = null
    private var postPath: String? = null
    private val REQUEST_PICK_PHOTO = 2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(AccountViewModel::class.java)
        UIActions()
        bindUI()
    }
    private fun bindUI() = launch{
        // get account information // todo add loading
        when(val callback = viewModel.accountInformation()){
            is NetworkResponse.Success -> {
                binding.account = callback.body
            }
        }
//        when(val callback = viewModel.updateAccount(name = "mohsen123" , family = null)){
//            is NetworkResponse.Success ->{
//                Log.e("Log__" , "the re")
//            }
//        }
    }
    private fun UIActions(){
        fra_account_name.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountNameDialog)
        }
        fra_account_family.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountFamilyDialog)
        }
        fra_account_gender.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountGenderDialog)
        }
        fra_account_add_image.setOnClickListener{
            getImageFromGallery()
//            navController.navigate(R.id.action_accountFragment_to_editAccountImageDialog)
        }
        fra_account_back.setOnClickListener{
            activity!!.onBackPressed()
        }
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey")
            // Do something with the result
            if(result == StaticVariables.REFRESH){
                bindUI()
            }
        }


    }
    private fun getImageFromGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    fileUri = selectedImage

                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor = activity!!.contentResolver.query(selectedImage!!, filePathColumn, null, null, null)

                    if (BuildConfig.DEBUG && cursor == null) {
                        error("Assertion failed")
                    }
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    binding.imageUri = fileUri
//                    fra_signUp_choose_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    update()

                    cursor.close()
                    postPath = mediaPath
                }
            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(context!!, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }

    }
    fun update() = launch{
        postPath?.let {
            if (it.isNotEmpty()){
                val imageFile = File(postPath!!)

                val requestBody = RequestBody.create(
                        activity!!.contentResolver.getType(fileUri!!)?.toMediaTypeOrNull() ,
                        imageFile
                )
                val body = MultipartBody.Part.createFormData("photo", imageFile.name , requestBody)

//                val description_ = "we are fcosiety"
//                val description: RequestBody = RequestBody.create(
//                        MultipartBody.FORM, description_)

                viewModel.updatePhotoAccount(body)
            }else{
                Toast.makeText(activity!!, "لطفان عکس را انتخاب کنید.", Toast.LENGTH_LONG).show()
            }
        }
    }

}