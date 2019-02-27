package net.vacorp.sqliterecyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_customer.view.*
import kotlinx.android.synthetic.main.io_customer_update.view.*
import kotlinx.android.synthetic.main.io_cutomers.view.*
import java.lang.reflect.Array.set

class CustomerAdapter (mCtx: Context, val customers: ArrayList<Customer>): RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    val mCtx = mCtx

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCustomerName = itemView.txtCustomerName
        val txtMaxCredit = itemView.txtMaxCredit
        val btnUpdate = itemView.btnUpdate
        val btnDelete = itemView.btnDelete
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.io_cutomers, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(p0: CustomerAdapter.ViewHolder, p1: Int) {
        val customer: Customer = customers[p1]
        p0.txtCustomerName.text = customer.customerName
        p0.txtMaxCredit.text = customer.maxCredit.toString()

        p0.btnDelete.setOnClickListener {
            val customerName = customer.customerName
            var alertDialog = AlertDialog.Builder(mCtx)
                    .setTitle("Warning")
                    .setMessage("Are you sure to delete $customerName?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        if (MainActivity.dbHandler.deleteCustomer(customer.customerID)) {
                            customers.removeAt(p1)
                            notifyItemRemoved(p1)
                            notifyItemRangeChanged(p1, customers.size)
                            Toast.makeText(mCtx, "customer $customerName deleted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(mCtx, "error deleting $customerName", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .show()
        }
        p0.btnUpdate.setOnClickListener {
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.io_customer_update, null)
            val textCustName: TextView = view.findViewById(R.id.editUpCustoerName)
            val textMaxCredit: TextView = view.findViewById(R.id.editUpMaxCredit)
            textCustName.text = customer.customerName
            textMaxCredit.text = customer.maxCredit.toString()
            val builder = AlertDialog.Builder(mCtx)
                    .setTitle("Update Customer Info")
                    .setView(view)
                    .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                        val isUpdate: Boolean = MainActivity.dbHandler.updateustomer(
                                customer.customerID.toString(),
                                view.editCustomerName.text.toString(),
                                view.editMaxCredit.text.toString())
                        if (isUpdate == true) {
                            customers[p1].customerName = view.editCustomerName.text.toString()
                            customers[p1].maxCredit = view.editUpMaxCredit.text.toString().toDouble()
                            notifyDataSetChanged()
                            Toast.makeText(mCtx, "customer  updated succesful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(mCtx, "error updating", Toast.LENGTH_SHORT).show()
                        }
                    }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }
}