package com.reyhansunakul.hw2

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.reyhansunakul.hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CustomRecyclerViewAdapter.OrganizationRecyclerAdapterInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var dialog: Dialog
    private lateinit var tvDialogName: TextView
    private lateinit var btnDialogClose: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        OrganizationSys.prepareData()

        // Fill the RecyclerView
        adapter = CustomRecyclerViewAdapter(this, OrganizationSys.getOrganizationArrayList())
        binding.recyclerOrg.adapter = adapter

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerOrg.layoutManager = staggeredGridLayoutManager

        binding.button.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayDialog(org: Organization) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)
        tvDialogName = dialog.findViewById(R.id.tvDialogName)
        btnDialogClose = dialog.findViewById(R.id.btnDialogClose)
        tvDialogName.text = org.producerName
        btnDialogClose.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    override fun displayItem(org: Organization) {
        displayDialog(org)
    }



}