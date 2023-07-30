package com.example.sampleapp

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.models.api.PostTitleContentApi
import com.example.sampleapp.models.api.PostTitleContentHelper
import com.example.sampleapp.models.repository.TitleWithContentRepo
import com.example.sampleapp.models.response.TitleWithContentResponseItem
import com.example.sampleapp.models.roomDatabase.TitleWithContentDatabase
import com.example.sampleapp.viewModels.MainViewModel
import com.example.sampleapp.views.adapters.RecyclerViewAdapter
import com.example.sampleapp.views.fragments.ShowDataFragment
import com.example.sampleapp.views.services.WhatsappAccessibilityService
import kotlinx.coroutines.*

const val TAG = "SampleApp"
class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var itemsList: List<TitleWithContentResponseItem>? = emptyList()
    private lateinit var accessibilityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titleWithContentRepoService = PostTitleContentHelper.getInstance().create(PostTitleContentApi::class.java)
        val titleWithContentDatabase = TitleWithContentDatabase.getDatabase(this)
        val repository = TitleWithContentRepo(titleWithContentRepoService, titleWithContentDatabase)
        viewModel = MainViewModel(repository)

        accessibilityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (isAccessibilityServiceEnabled()) {
                    startService(Intent(this, WhatsappAccessibilityService::class.java))
                } else {
                    Toast.makeText(this,"Service does not start",Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.i(TAG, "connectService: You need to give accessibility permission to this app")
            }
        }

        GlobalScope.launch {
            // Use coroutine scope to add data to the database and populate RecyclerView
            addDataToDatabase()
            addItemsToRecyclerView()
        }
        setupSharedPreferences()
        binding.accessServicePermButton.setOnClickListener {
            connectService()
        }

    }

    /**
     * This method is used to setup SharedPreferences to store a flag indicating if the toast has been shown
     */
    private fun setupSharedPreferences() {
        val isToastShown = false
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("TOAST_SHOW", isToastShown)
        editor.apply()
    }

    /**
    * Adds data to the database from the ViewModel.
    * Suspended function to perform the task asynchronously in IO context.
    */
    private suspend fun addDataToDatabase() {
        viewModel.addDataFromViewModelToDatabase()
    }

    /**
     * This method is used to fetches data from the database and populates the RecyclerView on the main thread.
     */
    private suspend fun addItemsToRecyclerView() {
        itemsList = viewModel.getDataFromDatabase()
        withContext(Dispatchers.Main) {
            binding.recyclerView.apply {

                val rvAdapter = RecyclerViewAdapter(itemsList!!, this@MainActivity)
                layoutManager =
                    LinearLayoutManager(this@MainActivity,
                        LinearLayoutManager.VERTICAL,
                        false)
                adapter = rvAdapter
            }
        }
    }


    /**
     * This method is used to give permission to accessibility service if it is not given already.
     */
    private fun connectService() {
        if (!isAccessibilityServiceEnabled()) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            accessibilityResultLauncher.launch(intent)
        }else{
            binding.accessServicePermButton.visibility = View.GONE
        }
    }

    /**
     * Checks if the WhatsappAccessibilityService is enabled in the accessibility settings.
     * @return true if the service is enabled, false otherwise.
     */
    private fun isAccessibilityServiceEnabled(): Boolean {
        val am = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)

        for (service in enabledServices) {
            if (service.id == "$packageName/.views.services.WhatsappAccessibilityService") {
                return true
            }
        }
        return false
    }

    override fun itemClickCallback(position: Int) {
        val fragment = ShowDataFragment.newInstance(itemsList!![position].body)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("data")
            .commit()
    }


}