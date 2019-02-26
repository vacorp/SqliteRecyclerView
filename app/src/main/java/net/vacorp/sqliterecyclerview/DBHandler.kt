package net.vacorp.sqliterecyclerview

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHandler (context:Context, name:String?,factory:SQLiteDatabase.CursorFactory?,version:Int):
        SQLiteOpenHelper(context,DATABASE_NAME, factory, DATABASE_VERSION){

    companion object {

    private val DATABASE_NAME = "MyData.db"
    private  val DATABASE_VERSION = 1

    val CUSTOMERS_TABLE_NAME = "Customer"
    val COLUMN_CUSTOMERID = "customerId"
    val COLUMN_CUSTOMERNAME = "customername"
    val COLUMN_MAXCREDIT = "maxcredit"
}

    override fun onCreate (db: SQLiteDatabase){
               val CREATE_CUSTOMER_TABLE = ("CREATE TABLE $CUSTOMERS_TABLE_NAME (" +
                "$COLUMN_CUSTOMERID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CUSTOMERNAME TEXT," +
                "$COLUMN_MAXCREDIT DOUBLE DEFAULT")
        db.execSQL(CREATE_CUSTOMER_TABLE)
    }

    override fun onUpgrade (db:SQLiteDatabase,OldVersion: Int, newVersion:Int){

    }

    fun getCustomers (mCtx: Context): ArrayList<Customer>
    {
        val qry = "SELECT * FROM $CUSTOMERS_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val customers = ArrayList <Customer>()
        if (cursor.count == 0)
            Toast.makeText(mCtx,"no records found",Toast.LENGTH_SHORT).show()
        else
        {
            while (cursor.moveToNext()){
                val customer = Customer()
                customer.customerID = cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMERID))
                customer.customerName = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMERNAME))
                customer.maxCredit = cursor.getDouble(cursor.getColumnIndex(COLUMN_MAXCREDIT))
                customers.add(customer)
            }
            Toast.makeText(mCtx,"records found: ${cursor.count.toString()}",Toast.LENGTH_SHORT).show()
            cursor.close()
            db.close()
        }
        return customers
    }
    fun addCustomer(mCtx: Context, customer:Customer){
        val values = ContentValues()
        values.put(COLUMN_CUSTOMERNAME,customer.customerName)
        values.put(COLUMN_MAXCREDIT,customer.maxCredit)
        val db= this.writableDatabase
        try {
            db.insert(CUSTOMERS_TABLE_NAME,null,values)
            Toast.makeText(mCtx,"record inserted",Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(mCtx,e.message,Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
}