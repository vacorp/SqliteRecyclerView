package net.vacorp.sqliterecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_customer.*

class AddCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        btnSave.setOnClickListener(){
            if (editCustomerName.text.isEmpty())
            {
                Toast.makeText(this,"no customer name found", Toast.LENGTH_SHORT).show()
                editCustomerName.requestFocus()
            }
            else
            {
                val customer = Customer()
                customer.customerName = editCustomerName.text.toString()
                if (editMaxCredit.text.isEmpty())
                    customer.maxCredit =0.0
                else
                    customer.maxCredit= editMaxCredit.text.toString().toDouble()
                MainActivity.dbHandler.addCustomer (this,customer)
                clearEdits()
                editCustomerName.requestFocus()
            }
        }

        btnCancel.setOnClickListener(){
            clearEdits()
            finish()
        }
    }
    fun clearEdits(){
        editCustomerName.text.clear()
        editMaxCredit.text.clear()
    }
}
