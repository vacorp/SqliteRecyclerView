package net.vacorp.sqliterecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View


import android.view.ViewGroup
import kotlinx.android.synthetic.main.io_cutomers.view.*

class CustomerAdapter (mCtx: Context, val customers: ArrayList<Customer>): RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    val mCtx = mCtx
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val txtCustomerName = itemView.txtCustomerName
        val txtMaxCredit = itemView.txtMaxCredit
        val btnUpdate = itemView.btnUpdate
        val btnDelete = itemView.btnDelete
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.io_cutomers, p0,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(p0: CustomerAdapter.ViewHolder, p1: Int) {
        val customer: Customer = customers[p1]
        p0.txtCustomerName.text = customer.customerName
        p0.txtMaxCredit.text = customer.maxCredit.toString()


    }
}