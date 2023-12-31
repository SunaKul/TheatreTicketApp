package com.reyhansunakul.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.reyhansunakul.hw2.databinding.ActivitySecondBinding
import com.reyhansunakul.hw2.adapter.CustomRecyclerViewAdapter
import com.reyhansunakul.hw2.db.Customer
import com.reyhansunakul.hw2.db.CustomerRoomDatabase
import com.reyhansunakul.hw2.util.Constants
import java.util.Collections

class SecondActivity: AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    val TAG:String="GESTURE"
    var gDetector: GestureDetectorCompat? = null

    lateinit var binding:ActivitySecondBinding

    var adapter: CustomRecyclerViewAdapter?=null

    private val customerDB: CustomerRoomDatabase by lazy {
        Room.databaseBuilder(this, CustomerRoomDatabase::class.java, Constants.DATABASENAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    var customer1: Customer = Customer(123, "Suna", "Superheros", 150.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

      //  setContentView(R.layout.activity_second)
        gDetector =  GestureDetectorCompat(this, this)
        gDetector?.setOnDoubleTapListener(this)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerCustomer.setLayoutManager(LinearLayoutManager(this))

        getData()

        binding.apply {
            fabAdd.setOnClickListener{
                //Assume that these customer details can be taken as an input from the user and input values are validated
                //var customerToAdd = Customer(444,"didem","kutlar",100.0)
                //var customerToAdd = Customer(555,"karan","kutlar",100.0)
                var customerToAdd = Customer(666,"Asya","Tan",100.0)
                customerDB.customerDao().insertCustomer(customerToAdd)
                getData()
               // Snackbar.make(it, "Customer inserted", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Your message", Toast.LENGTH_SHORT).show();

            }
            fabDelete.setOnClickListener{
                customerDB.customerDao().deleteCustomer(customer1)
                getData()
               // Snackbar.make(it, "Customer ${customer1.toString()} is deleted", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Customer ${customer1.toString()} is deleted", Toast.LENGTH_SHORT).show();
            }
        }

        binding.fabDeleteAll.setOnClickListener{
            customerDB.customerDao().deleteAllCustomers()
            getData()
           // Snackbar.make(it, "All customers are deleted", Snackbar.LENGTH_LONG).show()
            Toast.makeText(getApplicationContext(),"All customers are deleted", Toast.LENGTH_SHORT).show();

        }
        binding.fabAddAll.setOnClickListener {
            prepareData()
            getData()
           // Snackbar.make(it, "Customers are added", Snackbar.LENGTH_LONG).show()
            Toast.makeText(getApplicationContext(),"Customers are added", Toast.LENGTH_SHORT).show();


            if (customer1.name.isEmpty()) {
               // Snackbar.make(it, "Please enter customer name", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Please enter customer name", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else if (customer1.surname.isEmpty()) {
               // Snackbar.make(it, "Please enter customer surname", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Please enter customer surname", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            } else if (customer1.id == null) {
               // Snackbar.make(it, "Please enter valid balance", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Please enter valid id", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            else if (customer1.debt == null) {
               // Snackbar.make(it, "Please enter valid balance", Snackbar.LENGTH_LONG).show()
                Toast.makeText(getApplicationContext(),"Please enter valid balance", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
        }


        binding.fabUpdate.setOnClickListener {
            customer1.name = "Reyhan Suna"
            customer1.surname = "Updated"
            customerDB.customerDao().updateCustomer(customer1)
            getData()
          //  Snackbar.make(it, "Customer updated", Snackbar.LENGTH_LONG).show()
            Toast.makeText(getApplicationContext(),"Customer updated", Toast.LENGTH_SHORT).show();
        }


    }


    private fun displayCustomers(customers: MutableList<Customer>) {
        adapter = CustomRecyclerViewAdapter(this, customers)
        binding.recyclerCustomer.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    fun getData(){
        if(customerDB.customerDao().getAllCustomers().isNotEmpty()){
            displayCustomers(customerDB.customerDao().getAllCustomers())
        }
        else{
            binding.recyclerCustomer.adapter = null
        }
    }
    fun prepareData(){
        var customers=ArrayList<Customer>()
        Collections.addAll(customers,
            customer1,
            Customer(148, "Enes", "", 200.0),
            Customer(897, "Mehmet", "Kul", 150.0),
            Customer(333, "Tuna", "Şahin", 100.0))

        customerDB.customerDao().insertAllCustomer(customers)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        Log.i("GESTURE","onTouchEvent ${event.action}")
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        Log.i(TAG, "onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Log.i(TAG, "onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.i(TAG, "onSingleTapUp")
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.i(TAG, "onScroll")
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        Log.i(TAG, "onLongPress")

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.i(TAG, "onFling")
        return true
    }
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        Log.i(TAG, "onSingleTapConfirmed")
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.i(TAG, "onDoubleTap")
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.i(TAG, "onDoubleTapEvent")
        return true
    }



}