package com.imassage.ui.fragment.signUp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.imassage.BuildConfig
import com.imassage.R
import com.imassage.databinding.FragmentSignUpBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SignUpFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()

    private lateinit var viewModel: SignUpViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignUpBinding

    // FOR DATA
    private var fileUri: Uri? = null
    private var mediaPath: String? = null
    private var postPath: String? = null
    private val REQUEST_PICK_PHOTO = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEnterTransitions()
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SignUpViewModel::class.java)
        bindUI()
        uiActions()
        getPermission()
    }
    private fun bindUI() = launch {
    }

    private fun uiActions(){
        fra_signUp_man_woman_group.checkedButtonId
        fra_signUp_next.setOnClickListener {
            if (fra_signUp_name.text?.isNotEmpty()!! && fra_signUp_family.text?.isNotEmpty()!!) {
                hideKeyboard(it)
                setExitTransitions()
                val gender = when (fra_signUp_man_woman_group.checkedButtonId) {
                    R.id.fra_signUp_man -> "true"
                    else -> "false"  // for the other gender Females
                }
                val bundle =
                        if (postPath == null) {
                            bundleOf(
                                    "name" to fra_signUp_name.text.toString(),
                                    "family" to fra_signUp_family.text.toString(),
                                    "gender" to gender,
                                    "image" to "false"
                            )
                        } else {
                            bundleOf(
                                    "name" to fra_signUp_name.text.toString(),
                                    "family" to fra_signUp_family.text.toString(),
                                    "gender" to gender,
                                    "fileUri" to fileUri,
                                    "postPath" to postPath,
                                    "image" to "true"
                            )
                        }
                navController.navigate(R.id.action_signUpFragment_to_registerFormFragment, bundle)
            }else{
                Toast.makeText(context , "نام و نام خانوادگی را وhرد کنید." , Toast.LENGTH_SHORT).show()
            }
        }
        fra_signUp_go_to_login.setOnClickListener {
            hideKeyboard(it)
            setExitTransitions()
            navController.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        fra_signUp_choose_image.setOnClickListener {
            getImageFromGallery()
        }
    }
    private fun getPermission(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) ,1)
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

                    val cursor = requireActivity().contentResolver.query(selectedImage!!, filePathColumn, null, null, null)

                    if (BuildConfig.DEBUG && cursor == null) {
                        error("Assertion failed")
                    }
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    binding.imageUri = fileUri
//                    fra_signUp_choose_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath))

                    cursor.close()
                    postPath = mediaPath
                }
            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }

    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun setEnterTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = 500L
        }
    }

    private fun setExitTransitions() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }

        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
        }
    }
}